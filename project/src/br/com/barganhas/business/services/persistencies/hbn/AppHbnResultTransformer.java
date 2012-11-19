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
	//HQL � processado e essas propriedades abaixo s�o carregadas
	//"Items de Proje��o" do HQL, Ex.: "USR.id, USR.nome" etc...
	private String[] 					arryItemProj;
	//Classe do HQL (Ex.: br.com...entidade.UsuarioTO)
	private String 						classe;
	//Alias da Classe do HQL (Ex.: br.com...entidade.UsuarioTO USUARIO)
	private String 						aliasMor;
	//HQL recortado onde o "from" come�a
	private String 						fromIni;
	//Todos os alias que est�o no HQL s�o inseridos separadamente nessa lista, sem repetir.
	private List<String> 				listaAl;
	//Armazena o Path de um Alias em rela��o a Classe do HQL (Mais descri��o sobre esse processo est� no metodo loadAliaPath)
	private Map<String, String> 		mapaAliasPath;
	//Resultado Final de todo o processamento, que � o caminho completo de cada "Item de Proje��o" em rela��o a classe do HQL
	private String[] 					arryFinal;
	//Classe do Resultado (Classe que de fato vai ser retornada, � opcional)
	private Class<?> 					classeRs;
	//Usado quando a entidade princial do HQL, n�o � o resultado que ser� retornado.
	//Ex.: select DEP.id, DEP.nome from UsuarioTO USR join USR.departamento DEP (A entidade principal � o UsuarioTO, mas DepartamentoTO's ser�o retornados)
	//Para esse caso o corte correto � "departamento"
	private String 						corte;
	
	// Para up = false, Para cada Object[], um objeto � criado. E todos os objetos criados s�o independentes.
	// O    up = true , pode ser usado por exemplo, quando se quer listar "DepartamentoTO" e seus "DepartamentoTO subordinados"
	//						    nesse caso ser�o criados Objetos "DepartamentoTO", e dentro da sua lista de "DepartamentoTO subordinados", ser�o colocados
	//							os objetos "DepartamentoTO subordinados"...
	// As propriedades abaixo "listaCollection", "mapaInstance", "mapaObjetoMor", est�o relacionados ao up = true
	private boolean 					up = false;
	
	// Indica se erros de path de propriedade ser�o ignorados
	private boolean 					ignorePathError = false;
	
	// Armazena o nome de todas as propriedades do objeto que s�o Collection's..
	private Set<String> 				listaCollection = new HashSet<String>();
	
	// Um HQL que tras um DepartamentoTO e seus DepartamentoTO's subordinados, a propriedade que armazena os supordinados � DepartamentoTO.listaDepartamentoSubordinado
	// Ent�o para cada execu��o de "transformTuple", uma instancia de um "DepartamentoTO subordinado" deve ser criado e adicionado na lista
	// Esse Map armazena as instancias criadas para que as propriedades sejam setadas.., a cada execu��o do "transformTuple" esse Map deve ser resetado
	private Map<String, Object> 		mapaInstance = new HashMap<String, Object>();
	
	// Quando o up = true , no caso do exemplo citado acima, um Objeto DepartamentoTO � modificado em varios momentos diferentes (execu��es do transformTuple)
	//							  , ent�o � necess�rio armazenar a sua refer�ncia em algum lugar. A Key do Map � o id do objeto no banco
	//								que deve ser tamb�m O PRIMEIRO ITEM DA PROJE��O DO HQL
	private Map<Object, Object> 		mapaObjetoMor = new HashMap<Object, Object>();
	
	// Valor do parametro "arryResult", da ultima invoca��o do m�todo transformTuple
	protected Object[] 					arryResult;
	
	@Override
	public Object transformTuple(Object[] arryResult, String[] aliases) {
		this.arryResult = arryResult;
		try{
			this.mapaInstance.clear();
			
			// Pega a referencia do objeto que deve ser tratado nessa execu��o
			Object objeto = this.pegarObjetoMor(arryResult);
			
			int i = 0;
			for(Object result : arryResult) {
				if(result != null) {
					// O "objSet" � o objeto que ir� receber algum novo valor em suas propriedades
					Object objSet = objeto;
					
					// Pega o path da propriedade que ser� setada no objeto
					String propertyPath = this.pegarPropertyPathCorte(arryFinal[i]);
					
					// Verifica se essa propriedade � uma Cole��o
					if(this.listaCollection.contains(propertyPath)){
						// Se for, pega o Objeto dentro dessa lista que ir� receber o novo valor em suas propriedades
						objSet = this.gets(objeto, propertyPath);
						// O nome das propriedades de lista costumam ser "listaDepartamento.organizacao.id", ent�o o nome correto � "organizacao.id"
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
	 * Pega o Objeto que deve ser processado na execu��o do transformTuple
	 */
	private Object pegarObjetoMor(Object[] arryItemProjResult) throws Exception{
		// Se up == false, ent�o � sempre uma nova instancia
		if(!up){
			return this.pegarInstance();
		}
		
		// Se up == true, ent�o o mesmo objeto pode ser processado mais de uma vez, o "mapaObjetoMor", armazena as referencias pelo seu id
		Object objeto = this.mapaObjetoMor.get(arryItemProjResult[0]);
		// Caso o objeto n�o exista ainda...
		if(objeto == null){
		   // Cria a instancia
		   objeto = this.pegarInstance();
		   // Inicia suas cole��es
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
	 * Inicializa todas as Cole��es List.class e Set.class, necess�rias para montar o objeto
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
	 * Cria uma nova instancia que ser� processada
	 */
	protected Object pegarInstance() throws Exception{
		//Usa a ClasseRs caso ela n�o estiver null
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
			throw new IllegalArgumentException("hql n�o pode ser NULL!");
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
		//Carrega um array com os "item da proje��o" (Ex.: "CD.id, USR.id, USR.nome")
		this.loadProjetion();
		//Carrega o Nome da Classe do HQL e seu Alias(Mais descri��o no proprio metodo)
		this.loadBegin();
		//Carrega os chamados alias Filhos do HQL (Mais descri��o no proprio metodo)
		this.loadAlias();
		//Carrega o caminho dos alias em rela��o a Classe do HQL (Muito mais descri��o no proprio metodo)
		this.loadAliaPath();
		//Cada "Item de Proje��o", tem um alias, e mais um caminho (Ex.: DEPT.id, onde 'DEPT' � o alias, e 'id' o caminho), esse metodo
		//Carrega o caminho completo dos "Item de Proje��o" em rela��o a classe do HQL
		this.loadArryFinal();
	}
	
	/**
	 * Carrega um array com os "item da proje��o" (Ex.: "CD.id, USR.id, USR.nome")
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
		
		//Pega a String que est� entre os dois index (Esse intervalo tem os "Item de Proje��o")
		String projetions = hql.substring(i, f);
		
		//Cria um Array com os itens de proje��o
		arryItemProj = projetions.replace(" ", "").replace("\t", "").split(",");
	}
	
	/**
	 * Carrega dados basicos:
	 * 1� A classe onde o HQL come�a (Ex.: ... br.com.dataeasy.docflow3.bus.ctr.entidade.UsuarioTO )
	 * 2� E o Alias da classe descrita acima (Ex.: ... br.com.dataeasy.docflow3.bus.ctr.entidade.UsuarioTO USUARIO)
	 */
	private void loadBegin(){
		// Cria um Regex que vai capturar o bloco onde est� o Nome da Classe e o seu Alias
		Pattern p = Pattern.compile("[\\s]*[\\w\\.]+[\\s]");
		// O Regex come�a a trabalhar a partir do "from" do HQL
		// Esse espa�o concatenado � para garantir que o regex funcione, j� que ele procura um bloco de Texto e em seguida um espa�o, se caso o programador n�o colocou
		// o espa�o apos o alias da tabela (isso pode acontecer em um hql sem where), o regex iria falhar..
		Matcher m = p.matcher(this.pegarFromIni() + " "); 
		
		//Varios itens ser�o encontrados, porem:
		int c = 0;
		while(m.find()){
			//O segundo item � a Classe
			if(c == 1) classe = m.group().trim();
			//O terceiro item � o Alias da Classe
			if(c == 2) aliasMor = m.group().trim();
			c++;
			//E a partir do tercerio item, mais nada importa..
			if(c == 3) break;
		}
	}
	
	/**
	 * Carrega os "Alias Filhos" do HQL. Ex.: select CD.id, DEPT.id, USR.id, USR.nome, USR.email from ...... CD
	 * Os "Alias Filhos" s�o : "DEPT", "USR", O "CD" �  o "Alias Pai/Mor" ent�o ele n�o entra nessa historia
	 */
	private void loadAlias(){
		//Cria a lista que vai receber os Alias
		listaAl = new ArrayList<String>();
		//Itera na lista de "Item de Proje��o" (O conteudo dessa lista � DEPT.id, USR.id, etc...)
		for(String s : arryItemProj){
			//Faz um Split com ".", e pega o primeiro valor (Se 's' for "DEPT.id", o retorno ser� "DEPT")
			String al = s.split("\\.")[0];
			//Verifica se o alias j� esta na lista, se n�o o adiciona
			if(!listaAl.contains(al) && !aliasMor.equals(al)){
				listaAl.add(al);
			}
		}
	}

	/**
	 * Para cada alias existe um caminho completo em rela��o a Classe do HQL, Aqui eles s�o carregados...
	 * Ex.: select CD.id, DEPT.id, USR.id, USR.nome, USR.email from br.com.dataeasy.docflow3.bus.ctr.entidade.ColaboradorDepartamentoTO CD
	 * 				join ... ...
	 * 
	 * O Caminho completo de DEPT.id  � : departamento.id
	 * O Caminho completo de USR.nome � : colaborador.usuario.nome
	 */
	private void loadAliaPath(){
		//Cria um Mapa com o Alias/Caminho
		mapaAliasPath = new HashMap<String, String>();
		
		//Itera em cada Alias
		for(String al : listaAl){
			//Vai armazena o caminho inteiro do alias
			String caminho = "";
			//Nome do alias que est� sendo processado no momento
			String alias = al;
			//Imagina que um Alias tem um "Alias Pai" que tem um "Alias Pai", logo podemos ver que existe uma hierarquia de Alias, "Alias v�"... "Alias tatar�v�"
			while(true){
				//Pega o join que deu origem a esse alias
				String myJoin = this.pegarMyJoin(alias);
				
				//Verifica se o Alias que deu origem ao alias atual � o "Alias Pai/Mor", se for n�o � necessario percorrer mais caminho..
				boolean isMor = this.meuPaiEhAliaMor(myJoin);
				//Adicona na variavel o 'path' que deu origem a esse alias
				caminho = this.pegarPath(myJoin) + "." + caminho;
				//Pega o 'AliasPai' que deu origem ao 'alias atual' (Ex.: join AliasPai.caminho AliasAtual)
				alias = this.pegarAliasPai(myJoin);
				
				//Se o alias for o Mor, o caminho acaba aqui, mas se n�o for isso significa que tem mais caminho a percorrer.
				//(Imagine que um Alias) que surgiu apos 3 join! Ex.: join CD.colaborador COL join COL.usuario USR join USR.perfilAcesso PERFIL ........
				if(isMor) {
					mapaAliasPath.put(al, caminho.substring(0, caminho.length() - 1));
					break;
				}
			}
		}
	}
	
	/**
	 * Carrega o caminho completo dos "Item de Proje��o" em rela��o a classe do HQL
	 */
	private void loadArryFinal(){
		//Cria um Chamado Array Final
		arryFinal = new String[arryItemProj.length];
		
		int i = 0;
		//Itera em cada item de Proje��o
		for(String proj : arryItemProj){
			//Pega o "Alias" do "Item de Proje��o"
			String al = proj.split("\\.")[0];
			
			//Se � o "Alias Mor" (Ex.: CD.id), ent�o basta deixar somente o "id"
			if(al.equals(aliasMor)){
				arryFinal[i] = proj.replaceFirst(al + ".", "");
			}else{
				//Se n�o � o "Alias Mor", pega o caminho relativo do Alias, e coloca no lugar do proprio alias.
				//Ex.: USR.id, onde o caminho do USR � "colaborador.usuario", ent�o o resultado � "colaborador.usuario.id"
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
		//Cria um regex que vai encontrar no HQL o padr�o "join ... Alias"
		Pattern p = Pattern.compile("join[\\s]+[\\w\\.]+[\\s]+"+al+"", Pattern.CASE_INSENSITIVE);
		//Come�a a fazer a busca a partir do from do HQL
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
	 * Retorna 'CD' que � o "Alias Pai" desse join
	 */
	private String pegarAliasPai(String myJoin){
		String fullPath = pegarFullPathOfJoin(myJoin);
		return fullPath.split("\\.")[0];
	}
	
	/**
	 * 
	 * Dado um join : 'join CD.colaborador.usuario USR'
	 * Verifica se o 'CD', que � o a "Alias Pai" desse join, � o "Alias Mor/Pai" do HQL
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
	 * Recorta o HQL onde o "from" come�a..
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
