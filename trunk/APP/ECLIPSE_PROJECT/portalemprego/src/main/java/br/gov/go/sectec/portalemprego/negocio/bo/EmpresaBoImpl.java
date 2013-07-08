package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.exception.NegocioException;
import br.gov.go.sectec.portalemprego.comum.interfacebo.AreaInteresseBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.CidadeBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.EmpresaBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.PaisBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.RamoAtividadeBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.TipoTelefoneBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.UfBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.EmpresaDao;
import br.gov.go.sectec.portalemprego.comum.utilitario.CpfCnpjUtil;
import br.gov.go.sectec.portalemprego.comum.utilitario.EmailUtil;
import br.gov.go.sectec.portalemprego.comum.utilitario.MensagemNegocioUtils;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.AreaInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.Empresa;
import br.gov.go.sectec.portalemprego.core.entidade.Pais;
import br.gov.go.sectec.portalemprego.core.entidade.RamoAtividade;
import br.gov.go.sectec.portalemprego.core.entidade.TipoTelefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.AreaInteresseDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.PaisDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.RamoAtividadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoTelefoneDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.UfDTO;

/**
 * <p>
 * <b>Title:</b> EmpresaBoImpl.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe de negocio.
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Silv�nio
 * 
 * @version 1.0.0
 */
@Service("empresaBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EmpresaBoImpl extends PremiumBOImpl<Empresa, Integer> implements EmpresaBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EmpresaDao empresaDao;

	@Autowired
	private PaisBo paisBo;

	@Autowired
	private UfBo ufBo;

	@Autowired
	private CidadeBo cidadeBo;

	@Autowired
	private TipoTelefoneBo tipoTelefoneBo;

	@Autowired
	private RamoAtividadeBo ramoBo;

	@Autowired
	private AreaInteresseBo areaBo;

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @return lista de pais.
	 */
	@Override
	public List<PaisDTO> listarPais() {

		return this.paisBo.listarPaisOrdenado();
	}

	/**
	 * M�todo respons�vel por obter pais por id.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idPais
	 * 
	 * @return
	 */
	@Override
	public Pais obterPaisPorId(final Long idPais) {

		return this.paisBo.consultar(idPais);
	}

	/**
	 * M�todo respons�vel por incluir a empresa.
	 * 
	 * @author Silv�nio J�nior
	 * 
	 */

	public void incluir(final Empresa empresa) {
		this.validarDadosObrigatorios(empresa);
		this.validarDados(empresa);
		this.validarCNPJJaExiste(empresa);
		retirarMascaras(empresa);
		empresa.setDsSenha(DigestUtils.md5Hex(empresa.getDsSenha()));
		
		super.incluir(empresa);
	}

	private void validarCNPJJaExiste(Empresa empresa) {
		String cpnjSemMascara = empresa.getNumCNPJ().replace(".", "").replace("/", "").replace("-", "");
		if (this.empresaDao.checkExistEmpresa(cpnjSemMascara)) {
			throw new NegocioException("O Cnpj " + empresa.getNumCNPJ() + " j� foi cadastrado");
		}
	}

	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @param empresa
	 */
	private void retirarMascaras(Empresa empresa) {

		empresa.setNumCNPJ(empresa.getNumCNPJ().replace(".", "").replace("/", "").replace("-", ""));
		
		if(!ValidatorUtil.isBlank(empresa.getNumCPFResponsavel())){
			
			empresa.setNumCPFResponsavel(empresa.getNumCPFResponsavel().replace(".", "").replace("-", ""));
			
		}
		
	}

	/**
	 * M�todo respons�vel por validar os dados da empresa
	 * 
	 * @author Silv�nio
	 * 
	 * @param empresa
	 */
	private void validarDados(final Empresa empresa) {
		if (!CpfCnpjUtil.isValid(empresa.getNumCNPJ())) {
			throw new NegocioException("O Cnpj " + empresa.getNumCNPJ() + " � inv�lido");
		}

		if (!ValidatorUtil.isBlank(empresa.getNumCPFResponsavel()) && !CpfCnpjUtil.isValid(empresa.getNumCPFResponsavel())) {
			throw new NegocioException("O CPF " + empresa.getNumCPFResponsavel() + " � inv�lido");
		}

		if (!EmailUtil.validarEmail(empresa.getEmailResponsavel())) {
			throw new NegocioException("O E-mail " + empresa.getEmailResponsavel() + " � inv�lido");
		}

		if (!empresa.getDsSenha().equals(empresa.getDsSenhaConfirmacao())) {
			throw new NegocioException("A senha e a confimar��o de senha est�o diferentes");
		}

	}

	/**
	 * M�todo respons�vel por validar os dados obrigat�rios.
	 * 
	 * @author Silv�nio
	 * 
	 * @param empresa
	 */
	private void validarDadosObrigatorios(final Empresa empresa) {

		final MensagemNegocioUtils mensagemUtils = new MensagemNegocioUtils();

		mensagemUtils.validar(empresa.getNumCNPJ(), "O campo CNPJ � obrigat�rio");

		mensagemUtils.validar(empresa.getDsRazsoSocial(), "O campo Raz�o Social � obrigat�rio");

		mensagemUtils.validar(empresa.getEmailResponsavel(), "O campo E-mail � obrigat�rio");

		if (!empresa.getEndereco().isTodosPreenchidos()) {

			mensagemUtils.add("Dados obrigat�rios de endere�o n�o informados.");

		}

		mensagemUtils.validar(empresa.getTelefones(), "O campo Telefone � obrigat�rio");

		if (mensagemUtils.validarEntidade(empresa.getRamoAtividade(), "O campo Ramo Atividade � obrigat�rio")) {

			mensagemUtils.validar(empresa.getRamoAtividade().getIdRamoAtividade(), "O campo Ramo Atividade � obrigat�rio");

		}

		mensagemUtils.validar(empresa.getDsLogin(), "O campo �suario � obrigat�rio");

		mensagemUtils.validar(empresa.getDsSenha(), "O campo Senha � obrigat�rio");

		mensagemUtils.validar(empresa.getDsSenhaConfirmacao(), "O campo Confirmar Senha � obrigat�rio");

		mensagemUtils.execute();

	}

	/**
	 * M�todo respons�vel por retornar lista de ufs.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idPais
	 *            identificador do pais.
	 * 
	 * @return lista de uf.
	 */
	@Override
	public List<UfDTO> obetUfPorIdPais(final Long idPais) {

		return this.ufBo.obterPorIdPais(idPais);
	}

	/**
	 * M�todo respons�vel por obter a cidade pelo id de uf.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idUF
	 *            identificador uf.
	 * 
	 * @return lida de uf.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<CidadeDTO> obterCidadePorIdUf(final Long idUF) {

		return this.cidadeBo.obterCidadePorIdUf(idUF);
	}

	@Override
	public Cidade obterCidadePorId(final Long idCidade) {

		return this.cidadeBo.consultar(idCidade);
	}

	/**
	 * M�todo respons�vel por listar Tipos de telefone.
	 * 
	 * @author Silv�nio
	 * 
	 * @return lista de telefone.
	 */
	@Override
	public List<TipoTelefoneDTO> listarTipoTelefone() {

		return this.tipoTelefoneBo.listarTipoTelefone();
	}

	/**
	 * M�todo respons�vel por listar os ramos de atividades.
	 * 
	 * @author Silv�nio
	 * 
	 * @return lista de ramo de atividade.
	 */
	@Override
	public List<RamoAtividadeDTO> listarRamoAtividade() {

		return this.ramoBo.listarOrdenado();
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idTipoTelefone
	 * 
	 * @return
	 */
	@Override
	public TipoTelefone obterTipoTelefonePorId(final Long idTipoTelefone) {

		return this.tipoTelefoneBo.consultar(idTipoTelefone);
	}

	/**
	 * M�todo respons�vel por obter os ramos de atividades.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idRamoAtividade
	 * 
	 * @return RamoAtividade
	 */
	@Override
	public RamoAtividade obterRamoAtividadePorId(final Long idRamoAtividade) {

		return this.ramoBo.consultar(idRamoAtividade);
	}

	/**
	 * M�todo respons�vel por sigla do estado
	 * 
	 * @author Silv�nio
	 * 
	 * @param string
	 * 
	 * @return List<CidadeDTO>
	 */
	@Override
	public List<CidadeDTO> obterCidadePorSiglaUf(final String siglaUf) {

		return this.cidadeBo.obterCidadePorSiglaUf(siglaUf);
	}

	/**
	 * M�todo respons�vel por listar os dados da area de interesse.
	 * 
	 * @author Silv�nio
	 * 
	 * @return lista de area
	 */
	@Override
	public List<AreaInteresseDTO> listarAreaInteresse() {

		return this.areaBo.listarAreaInteresseOrdenada();
	}

	@Override
	public AreaInteresse obterAreaInteressePorId(final Long idArea) {

		return this.areaBo.consultar(idArea);
	}

	@Override
	EmpresaDao getDAO() {

		return this.empresaDao;
	}

	/**
	 * 
	 * M�todo respons�vel por validar os dados informados para recupera��o de senha
	 * 
	 * @author Joffre
	 * 
	 */
	public void recuperarSenha(final Empresa empresa) {

		EmpresaBoImpl.validarDadosRecuperarSenha(empresa);
		final MensagemNegocioUtils msg = new MensagemNegocioUtils();

		final Empresa empresaDonaDoCNPJ = this.empresaDao.buscarEmpresaPorCNPJ(empresa.getNumCNPJ());
		this.empresaDao.buscarEmpresaPorEmail(empresa.getEmailResponsavel());

		if (empresaDonaDoCNPJ.getEmailResponsavel().equalsIgnoreCase(empresa.getEmailResponsavel())) {
			this.enviarEmailRecuperarSenha(empresa);
			msg.add("Foi enviado um email com os dados para recupera��o da senha. Acesse sua caixa nos pr�ximos minutos.");
		} else {
			throw new NegocioException("Email e CNPJ n�o pertencem ao mesmo cadastro.");
		}

	}

	/**
	 * M�todo respons�vel por validar os dados informados para recupera��o de senha
	 * 
	 * @author Joffre
	 * 
	 * @param empresa
	 */
	private static void validarDadosRecuperarSenha(final Empresa empresa) {

		final MensagemNegocioUtils msg = new MensagemNegocioUtils();

		if (ValidatorUtil.isBlank(empresa.getNumCNPJ())) {
			msg.add("� obrigat�rio informar o CNPJ.");
		}

		if (ValidatorUtil.isBlank(empresa.getEmailResponsavel())) {
			msg.add("� obrigat�rio informar o Email correspondente.");
		}

	}

	/**
	 * 
	 * M�todo respons�vel por enviar email com a senha do usuario
	 * 
	 * @author Joffre
	 * 
	 * @param empresa
	 * @return true / false
	 */
	private Boolean enviarEmailRecuperarSenha(final Empresa empresa) {

		
		return Boolean.FALSE;
	/*	// public boolean enviarEmail(String mailServer, String para, String de, String assunto,
		// String mensagem, String login_instituicao, String senha_instituicao)
		// throws AddressException, MessagingException {
		try {
			final Properties mailProps = new Properties();

			// mailProps.put("mail.smtp.host", mailServer);
			mailProps.put("mail.smtp.auth", "true");
			mailProps.put("mail.debug", "true");
			mailProps.put("mail.smtp.debug", "true");
			mailProps.put("mail.mime.charset", "ISO?8859?1");
			mailProps.put("mail.smtp.port", "465");
			mailProps.put("mail.smtp.starttls.enable", "true");
			mailProps.put("mail.smtp.socketFactory.port", "465");
			mailProps.put("mail.smtp.socketFactory.fallback", "false");
			mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			final Session mailSession = Session.getDefaultInstance(mailProps);

			// As duas linhas seguintes de c�digo, colocam no formato de endere�os,
			// supostamente v�lidos, de email os dados passados pelos par�metros to e from.
			final InternetAddress destinatario = new InternetAddress(empresa.getEmailResponsavel());
			final InternetAddress remetente = new InternetAddress("contato@premiumcs.com.br");

			// As duas linhas de c�digo a seguir, s�o respons�veis por setar os atributos e
			// propriedades necess�rias do objeto message para que o email seja enviado.
			// inicializa��o do objeto Message
			final Message message = new MimeMessage(mailSession);

			// Defini��o da Data que est� enviando o email
			message.setSentDate(new Date());// novo

			// Defini��o de quem est� enviando o email
			message.setFrom(remetente);

			// define o(s) destinat�rio(s) e qual o tipo do destinat�rio.
			// os poss�veis tipos de destinat�rio: TO, CC, BCC
			message.setRecipient(Message.RecipientType.TO, destinatario);

			// defini��o do assunto do email
			message.setSubject("Recupera��o de Senha");

			// defini��o do conte�do da mensagem e do tipo da mensagem
			message.setContent("Sua senha �: " + empresa.getDsSenha(), "text/plain");

			// as linhas de c�digo seguinte � a respons�vel pelo envio do email
			final Transport transport = mailSession.getTransport("smtp");
			transport.connect("smtp.premiumcs.com.br", "contato@premiumcs.com.br", "contatopremium");
			message.saveChanges();
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			return true;
		} catch (final Exception e) {
			return false;
		}*/
	}

	@Override
	public Empresa login(String dsLogin, String dsSenha) {
		return this.empresaDao.login(dsLogin, DigestUtils.md5Hex(dsSenha));
	}

	@Override
	public Empresa obterEmpresaPeloId(Long id) {
		return this.empresaDao.obterEmpresaPeloId(id);
	}

	@Override
	public Empresa salvar(Empresa empresa) {
		Empresa cadastroAtual = this.obterEmpresaPeloId(empresa.getIdEmpresa());
		
		empresa.setDsSenha(cadastroAtual.getDsSenha());
		empresa.getEndereco().setIdEndereco(cadastroAtual.getEndereco().getIdEndereco());
		empresa.setDsSenhaConfirmacao(empresa.getDsSenha());
		
		this.validarDadosObrigatorios(empresa);
		this.validarDados(empresa);
		retirarMascaras(empresa);
		return this.empresaDao.salvar(empresa);
	}
}
