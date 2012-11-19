package br.com.barganhas.business.services.persistencies.hbn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.ReflectionsHelper;
import org.hibernate.transform.ResultTransformer;

public class AppHbnResultTransformer implements ResultTransformer{
	
	private static final long 			serialVersionUID = 993112340068220262L;
	
	//HQL que vai ser executado pelo Hiberante
	private String 						hql;
	//HQL é processado e essas propriedades abaixo são carregadas
	//"Items de Projeção" do HQL, Ex.: "USR.id, USR.nome" etc...
	private String[] 					arryItemProj;
	//Classe do HQL (Ex.: br.com...entidade.UsuarioTO)
	private String 						classe;
	//Alias da Classe do HQL (Ex.: br.com...entidade.UsuarioTO USUARIO)
	private String 						aliasMor;
	//HQL recortado onde o "from" começa
	private String 						fromIni;
	//Todos os alias que estão no HQL são inseridos separadamente nessa lista, sem repetir.
	private List<String> 				listaAl;
	//Armazena o Path de um Alias em relação a Classe do HQL (Mais descrição sobre esse processo está no metodo loadAliaPath)
	private Map<String, String> 		mapaAliasPath;
	//Resultado Final de todo o processamento, que é o caminho completo de cada "Item de Projeção" em relação a classe do HQL
	private String[] 					arryFinal;
	//Classe do Resultado (Classe que de fato vai ser retornada, é opcional)
	private Class<?> 					classeRs;
	//Usado quando a entidade princial do HQL, não é o resultado que será retornado.
	//Ex.: select DEP.id, DEP.nome from UsuarioTO USR join USR.departamento DEP (A entidade principal é o UsuarioTO, mas DepartamentoTO's serão retornados)
	//Para esse caso o corte correto é "departamento"
	private String 						corte;
	
	// Para up = false, Para cada Object[], um objeto é criado. E todos os objetos criados são independentes.
	// O    up = true , pode ser usado por exemplo, quando se quer listar "DepartamentoTO" e seus "DepartamentoTO subordinados"
	//						    nesse caso serão criados Objetos "DepartamentoTO", e dentro da sua lista de "DepartamentoTO subordinados", serão colocados
	//							os objetos "DepartamentoTO subordinados"...
	// As propriedades abaixo "listaCollection", "mapaInstance", "mapaObjetoMor", estão relacionados ao up = true
	private boolean 					up = false;
	
	// Indica se erros de path de propriedade serão ignorados
	private boolean 					ignorePathError = false;
	
	// Armazena o nome de todas as propriedades do objeto que são Collection's..
	private Set<String> 				listaCollection = new HashSet<String>();
	
	// Um HQL que tras um DepartamentoTO e seus DepartamentoTO's subordinados, a propriedade que armazena os supordinados é DepartamentoTO.listaDepartamentoSubordinado
	// Então para cada execução de "transformTuple", uma instancia de um "DepartamentoTO subordinado" deve ser criado e adicionado na lista
	// Esse Map armazena as instancias criadas para que as propriedades sejam setadas.., a cada execução do "transformTuple" esse Map deve ser resetado
	private Map<String, Object> 		mapaInstance = new HashMap<String, Object>();
	
	// Quando o up = true , no caso do exemplo citado acima, um Objeto DepartamentoTO é modificado em varios momentos diferentes (execuções do transformTuple)
	//							  , então é necessário armazenar a sua referência em algum lugar. A Key do Map é o id do objeto no banco
	//								que deve ser também O PRIMEIRO ITEM DA PROJEÇÃO DO HQL
	private Map<Object, Object> 		mapaObjetoMor = new HashMap<Object, Object>();
	
	// Valor do parametro "arryResult", da ultima invocação do método transformTuple
	protected Object[] 					arryResult;
	
	@Override
	public Object transformTuple(Object[] arryResult, String[] aliases) {
		this.arryResult = arryResult;
		try{
			this.mapaInstance.clear();
			
			// Pega a referencia do objeto que deve ser tratado nessa execução
			Object objeto = this.pegarObjetoMor(arryResult);
			
			int i = 0;
			for(Object result : arryResult) {
				if(result != null) {
					// O "objSet" é o objeto que irá receber algum novo valor em suas propriedades
					Object objSet = objeto;
					
					// Pega o path da propriedade que será setada no objeto
					String propertyPath = this.pegarPropertyPathCorte(arryFinal[i]);
					
					// Verifica se essa propriedade á uma Coleção
					if(this.listaCollection.contains(propertyPath)){
						// Se for, pega o Objeto dentro dessa lista que irá receber o novo valor em suas propriedades
						objSet = this.gets(objeto, propertyPath);
						// O nome das propriedades de lista costumam ser "listaDepartamento.organizacao.id", então o nome correto é "organizacao.id"
						propertyPath = propertyPath.substring(propertyPath.indexOf(".") + 1, propertyPath.length());
					}
					
					
					try{
						// Seta o Valor no "objSet"...
						ReflectionsHelper.setNestedDefaultValue(objSet, propertyPath, result, "\\.");
					}catch (Exception e){
						if(ignorePathError) continue;
						throw e;
					}
					
				}
				i++;
			}
			
			return objeto;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Pega o Objeto que deve ser processado na execução do transformTuple
	 */
	private Object pegarObjetoMor(Object[] arryItemProjResult) throws Exception{
		// Se up == false, então é sempre uma nova instancia
		if(!up){
			return this.pegarInstance();
		}
		
		// Se up == true, então o mesmo objeto pode ser processado mais de uma vez, o "mapaObjetoMor", armazena as referencias pelo seu id
		Object objeto = this.mapaObjetoMor.get(arryItemProjResult[0]);
		// Caso o objeto não exista ainda...
		if(objeto == null){
		   // Cria a instancia
		   objeto = this.pegarInstance();
		   // Inicia suas coleções
		   this.initAllCollection(objeto);
		   // Armazena a referencia no Map
		   this.mapaObjetoMor.put(arryItemProjResult[0], objeto);
		}
		
		return objeto;
	}

	
	
	@SuppressWarnings("unchecked")
	private Object gets(Object obj, String path) throws Exception{
		String id = obj.hashCode() + "-" + path.split("\\.")[0];
		
		Object instance = mapaInstance.get(id);
		if(instance == null){
			String propriedade = path.split("\\.")[0];
			Field f = obj.getClass().getDeclaredField(propriedade);
			instance = ReflectionsHelper.getGenericClassType(f).newInstance();
			
			((Collection)PropertyUtils.getPropertyDescriptor(obj, propriedade).getReadMethod().invoke(obj, (Object[])null)).add(instance);
			
			mapaInstance.put(id, instance);
		}
		
		return instance;
	}
	
	/**
	 * Inicializa todas as Coleções List.class e Set.class, necessárias para montar o objeto
	 */
	@SuppressWarnings("unchecked")
	private void initAllCollection(Object objeto) throws Exception{
		for(String path : this.arryFinal){
			path = this.pegarPropertyPathCorte(path);
			
			String propriedade = path.split("\\.")[0];
			Class<?> type = PropertyUtils.getPropertyType(objeto, propriedade);
			if(List.class == type){
				ReflectionsHelper.setAttValue(objeto, propriedade, new ArrayList());
				listaCollection.add(path);
			}
			
			if(Set.class == type){
				ReflectionsHelper.setAttValue(objeto, propriedade, new HashSet());
				listaCollection.add(path);
			}
		}
	}

	/**
	 * Cria uma nova instancia que será processada
	 */
	protected Object pegarInstance() throws Exception{
		//Usa a ClasseRs caso ela não estiver null
		boolean isRs = (classeRs != null);
		return isRs ? classeRs.newInstance():Class.forName(classe).newInstance();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List transformList(List collection) {
		return collection;
	}
	
	public AppHbnResultTransformer(String hql, boolean up){
		if(hql == null) {
			throw new IllegalArgumentException("hql não pode ser NULL!");
		}
		this.hql = hql.trim();
		this.up = up;
		this.load();
	}
	
	public AppHbnResultTransformer(String hql, boolean up, boolean ignorePathError){
		this(hql, up);
		this.ignorePathError = ignorePathError;
	}
	
	
	public AppHbnResultTransformer(String hql, Class<?> classe, String corte, boolean up){
		this(hql, up);
		this.classeRs = classe;
		this.corte    = corte;
	}
	
	/**
	 * Cria uma Count do HQL que vai ser executada no banco
	 */
	public String hqlCount(String hql){
		this.hql = hql; 
		int idxFrom = this.indexOfIgnoreCase(hql, " from ");
		return "select count(distinct " + this.aliasMor + ".id) " + hql.substring(idxFrom, hql.length());
	}
	
	private void load(){
		//Carrega um array com os "item da projeção" (Ex.: "CD.id, USR.id, USR.nome")
		this.loadProjetion();
		//Carrega o Nome da Classe do HQL e seu Alias(Mais descrição no proprio metodo)
		this.loadBegin();
		//Carrega os chamados alias Filhos do HQL (Mais descrição no proprio metodo)
		this.loadAlias();
		//Carrega o caminho dos alias em relação a Classe do HQL (Muito mais descrição no proprio metodo)
		this.loadAliaPath();
		//Cada "Item de Projeção", tem um alias, e mais um caminho (Ex.: DEPT.id, onde 'DEPT' é o alias, e 'id' o caminho), esse metodo
		//Carrega o caminho completo dos "Item de Projeção" em relação a classe do HQL
		this.loadArryFinal();
	}
	
	/**
	 * Carrega um array com os "item da projeção" (Ex.: "CD.id, USR.id, USR.nome")
	 */
	private void loadProjetion(){
		String select = "select";
		String slDist = "select distinct";
		
		//Pega o index da palavra chave "select distinct"/"select" e "from" do HQL
		int i = 0;
		if(hql.startsWith(slDist) || hql.startsWith(slDist.toUpperCase())){
			i = this.indexOfIgnoreCase(hql, slDist) + slDist.length();
		}else{
			i = this.indexOfIgnoreCase(hql, select) + select.length();
		}
		
		int f = this.indexOfIgnoreCase(hql, "from");
		
		//Pega a String que está entre os dois index (Esse intervalo tem os "Item de Projeção")
		String projetions = hql.substring(i, f);
		
		//Cria um Array com os itens de projeção
		arryItemProj = projetions.replace(" ", "").replace("\t", "").split(",");
	}
	
	/**
	 * Carrega dados basicos:
	 * 1º A classe onde o HQL começa (Ex.: ... br.com.dataeasy.docflow3.bus.ctr.entidade.UsuarioTO )
	 * 2º E o Alias da classe descrita acima (Ex.: ... br.com.dataeasy.docflow3.bus.ctr.entidade.UsuarioTO USUARIO)
	 */
	private void loadBegin(){
		// Cria um Regex que vai capturar o bloco onde está o Nome da Classe e o seu Alias
		Pattern p = Pattern.compile("[\\s]*[\\w\\.]+[\\s]");
		// O Regex começa a trabalhar a partir do "from" do HQL
		// Esse espaço concatenado é para garantir que o regex funcione, já que ele procura um bloco de Texto e em seguida um espaço, se caso o programador não colocou
		// o espaço apos o alias da tabela (isso pode acontecer em um hql sem where), o regex iria falhar..
		Matcher m = p.matcher(this.pegarFromIni() + " "); 
		
		//Varios itens serão encontrados, porem:
		int c = 0;
		while(m.find()){
			//O segundo item é a Classe
			if(c == 1) classe = m.group().trim();
			//O terceiro item é o Alias da Classe
			if(c == 2) aliasMor = m.group().trim();
			c++;
			//E a partir do tercerio item, mais nada importa..
			if(c == 3) break;
		}
	}
	
	/**
	 * Carrega os "Alias Filhos" do HQL. Ex.: select CD.id, DEPT.id, USR.id, USR.nome, USR.email from ...... CD
	 * Os "Alias Filhos" são : "DEPT", "USR", O "CD" é  o "Alias Pai/Mor" então ele não entra nessa historia
	 */
	private void loadAlias(){
		//Cria a lista que vai receber os Alias
		listaAl = new ArrayList<String>();
		//Itera na lista de "Item de Projeção" (O conteudo dessa lista é DEPT.id, USR.id, etc...)
		for(String s : arryItemProj){
			//Faz um Split com ".", e pega o primeiro valor (Se 's' for "DEPT.id", o retorno será "DEPT")
			String al = s.split("\\.")[0];
			//Verifica se o alias já esta na lista, se não o adiciona
			if(!listaAl.contains(al) && !aliasMor.equals(al)){
				listaAl.add(al);
			}
		}
	}

	/**
	 * Para cada alias existe um caminho completo em relação a Classe do HQL, Aqui eles são carregados...
	 * Ex.: select CD.id, DEPT.id, USR.id, USR.nome, USR.email from br.com.dataeasy.docflow3.bus.ctr.entidade.ColaboradorDepartamentoTO CD
	 * 				join ... ...
	 * 
	 * O Caminho completo de DEPT.id  é : departamento.id
	 * O Caminho completo de USR.nome é : colaborador.usuario.nome
	 */
	private void loadAliaPath(){
		//Cria um Mapa com o Alias/Caminho
		mapaAliasPath = new HashMap<String, String>();
		
		//Itera em cada Alias
		for(String al : listaAl){
			//Vai armazena o caminho inteiro do alias
			String caminho = "";
			//Nome do alias que está sendo processado no momento
			String alias = al;
			//Imagina que um Alias tem um "Alias Pai" que tem um "Alias Pai", logo podemos ver que existe uma hierarquia de Alias, "Alias vô"... "Alias tatarávô"
			while(true){
				//Pega o join que deu origem a esse alias
				String myJoin = this.pegarMyJoin(alias);
				
				//Verifica se o Alias que deu origem ao alias atual é o "Alias Pai/Mor", se for não é necessario percorrer mais caminho..
				boolean isMor = this.meuPaiEhAliaMor(myJoin);
				//Adicona na variavel o 'path' que deu origem a esse alias
				caminho = this.pegarPath(myJoin) + "." + caminho;
				//Pega o 'AliasPai' que deu origem ao 'alias atual' (Ex.: join AliasPai.caminho AliasAtual)
				alias = this.pegarAliasPai(myJoin);
				
				//Se o alias for o Mor, o caminho acaba aqui, mas se não for isso significa que tem mais caminho a percorrer.
				//(Imagine que um Alias) que surgiu apos 3 join! Ex.: join CD.colaborador COL join COL.usuario USR join USR.perfilAcesso PERFIL ........
				if(isMor) {
					mapaAliasPath.put(al, caminho.substring(0, caminho.length() - 1));
					break;
				}
			}
		}
	}
	
	/**
	 * Carrega o caminho completo dos "Item de Projeção" em relação a classe do HQL
	 */
	private void loadArryFinal(){
		//Cria um Chamado Array Final
		arryFinal = new String[arryItemProj.length];
		
		int i = 0;
		//Itera em cada item de Projeção
		for(String proj : arryItemProj){
			//Pega o "Alias" do "Item de Projeção"
			String al = proj.split("\\.")[0];
			
			//Se é o "Alias Mor" (Ex.: CD.id), então basta deixar somente o "id"
			if(al.equals(aliasMor)){
				arryFinal[i] = proj.replaceFirst(al + ".", "");
			}else{
				//Se não é o "Alias Mor", pega o caminho relativo do Alias, e coloca no lugar do proprio alias.
				//Ex.: USR.id, onde o caminho do USR é "colaborador.usuario", então o resultado é "colaborador.usuario.id"
				String relPath = mapaAliasPath.get(al);
				arryFinal[i] = proj.replaceFirst(al, relPath);
			}
			i++;
		}
	}
	
	/**
	 * Todo alias surge a partir de um join (Exceto o "Alias Mor/Pai"), Ex.: " join CD.departamento DEPT "
	 * Para o alias "DEPT", esse metodo vai retornar a String 'join CD.departamento DEPT'
	 */
	private String pegarMyJoin(String al){
		//Cria um regex que vai encontrar no HQL o padrão "join ... Alias"
		Pattern p = Pattern.compile("join[\\s]+[\\w\\.]+[\\s]+"+al+"", Pattern.CASE_INSENSITIVE);
		//Começa a fazer a busca a partir do from do HQL
		Matcher m = p.matcher(this.pegarFromIni());
		//Encontra o primeiro elemento e retorna
		if(m.find()){
			return m.group();
		}
		return null;
	}
	
	/**
	 * Dado um join : 'join CD.colaborador.usuario USR '
	 * Retorna 'colaborador.usuario'
	 */
	private String pegarPath(String myJoin){
		String fullPath = pegarFullPathOfJoin(myJoin);
		return fullPath.substring(fullPath.indexOf(".") + 1, fullPath.length());
	}
	
	/**
	 * Dado um join : 'join CD.colaborador.usuario USR'
	 * Retorna 'CD' que é o "Alias Pai" desse join
	 */
	private String pegarAliasPai(String myJoin){
		String fullPath = pegarFullPathOfJoin(myJoin);
		return fullPath.split("\\.")[0];
	}
	
	/**
	 * 
	 * Dado um join : 'join CD.colaborador.usuario USR'
	 * Verifica se o 'CD', que é o a "Alias Pai" desse join, é o "Alias Mor/Pai" do HQL
	 */
	private boolean meuPaiEhAliaMor(String myJoin){
		String aliasPai = pegarAliasPai(myJoin);
		return aliasPai.equals(aliasMor);
	}
	
	/**
	 * Dado um join : 'join CD.colaborador.usuario USR'
	 * Retorna 'CD.colaborador.usuario'
	 */
	private String pegarFullPathOfJoin(String myJoin){
		return myJoin.split("[\\s]+")[1];
	}
	
	/**
	 * Recorta o HQL onde o "from" começa..
	 */
	private String pegarFromIni(){
		if(fromIni == null){
			fromIni = hql.substring(this.indexOfIgnoreCase(hql, "from"), hql.length());
		}
		return fromIni;
	}
	
	/**
	 * Retorna o index da String, estando ela no UpperCase ou LowserCase
	 */
	private int indexOfIgnoreCase(String fonte, String str){
		int i = fonte.indexOf(str.toUpperCase());
		if(i != -1){
			return i;
		}
		return fonte.indexOf(str.toLowerCase());
	}
	
	public String pegarPropertyPathCorte(String propertyPath){
		if(GeneralsHelper.isStringOk(corte)){
			propertyPath = propertyPath.replaceFirst(corte + ".", "");
		}
		return propertyPath;
	}
	
	public String aliasMor(){
		return this.aliasMor;
	}
	
}
