package br.gov.go.sectec.portalemprego.web.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.go.sectec.portalemprego.comum.exception.NegocioException;
import br.gov.go.sectec.portalemprego.comum.interfacebo.EmpresaBo;
import br.gov.go.sectec.portalemprego.comum.utilitario.DataUtil;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.AreaInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.Empresa;
import br.gov.go.sectec.portalemprego.core.entidade.Endereco;
import br.gov.go.sectec.portalemprego.core.entidade.RamoAtividade;
import br.gov.go.sectec.portalemprego.core.entidade.Telefone;
import br.gov.go.sectec.portalemprego.core.entidade.TipoTelefone;
import br.gov.go.sectec.portalemprego.core.entidade.VagaOfertada;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.AreaInteresseDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.PaisDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.RamoAtividadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TelefoneDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoTelefoneDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.UfDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.VagasOfertadasDTO;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * <b>Title:</b> EmpresaAction.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe respons�vel por controlar os dados da empresa
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
@ParentPackage(value = "portalemprego")
@Namespace("/empresa")
@Component("empresaAction")
public class EmpresaAction extends PremiumAction {

	private static final long serialVersionUID = -4096579557902623573L;
	
	public static final String SESSION_EMPRESA_LOGIN = "empresaLogin";
	
	private static final String SESSION_VAGA_LIST = "vagaList";
	private static final String SESSION_TELEFONE_LIST = "telefoneList";
	private static final String SESSION_UF_LIST = "ufList";
	private static final String SESSION_CIDADE_LIST = "cidadeList";

	@Autowired
	private EmpresaBo empresaBo;
	
	private List<PaisDTO> paisList;
	private List<UfDTO> ufList;
	private List<CidadeDTO> cidadeList;
	private List<CidadeDTO> cidadeVagaList;
	private List<TipoTelefoneDTO> tipoTelefoneList;
	private List<TelefoneDTO> telefoneList;
	private List<RamoAtividadeDTO> ramoList;
	private List<VagasOfertadasDTO> vagaList;
	private List<AreaInteresseDTO> areaList;
	private Telefone telefone;
	private VagasOfertadasDTO vaga;
	private Integer indexElementList;
	private String idVagasEx;
	private String dataEx;
	private Empresa empresa;
	private Long idPais;
	private Long idUf;
	private Long idCidade;
	private Long idTipoTelefone;
	private Long idRamoAtividade;
	private Long idCidadeVaga;
	private ProcessingReturn processingReturn;

	@Action(value = "/iniciarLogin", results = { @Result(location = "loginEmpresa.page", type = "tiles", name = ActionSupport.SUCCESS)} )
	public String iniciarLogin() {
		this.limparDados();
		
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/login", results = { @Result(location = "empresa.page", type = "tiles", name = ActionSupport.SUCCESS),
										@Result(location = "loginEmpresa.page", type = "tiles", name = ActionSupport.ERROR)} )
	public String login() {
		try {
			final Empresa empresa = this.getEmpresaBo().login(this.getEmpresa().getDsLogin(), this.getEmpresa().getDsSenha());
			if (empresa == null) {
				throw new NegocioException("Usu�rio ou senha inv�lido.");
			}
			this.setEmpresa(empresa);
			this.incluirSessao(SESSION_EMPRESA_LOGIN, this.getEmpresa());
			this.popularDadosDaTela();
			
			return ActionSupport.SUCCESS;
		} catch (final NegocioException ne) {
			this.adicionarMensagemValidacao(ne);
			return ActionSupport.ERROR;
		}
	}
	
	@Action(value = "/alterarEmpresa", results = { @Result(location = "empresa.page", type = "tiles", name = ActionSupport.SUCCESS)} )
	public String alterarEmpresa() {
		this.setEmpresa((Empresa)this.obterDaSessao(SESSION_EMPRESA_LOGIN));
		this.popularDadosDaTela();
		
		return ActionSupport.SUCCESS;
	}
	
	private void popularDadosDaTela() {
		// carrega lista de telefones
		this.telefoneList = new ArrayList<TelefoneDTO>();
		for (Telefone telefone : this.getEmpresa().getTelefones()) {
			TelefoneDTO dto = new TelefoneDTO();
			dto.setIdDTO(telefone.getIdTelefone());
			dto.setNumeroDTO(telefone.getNuTelefone());
			dto.setNuDdd(telefone.getNuDdd());
			dto.setNuDdi(telefone.getNuDdi());
			dto.setDsTipo(telefone.getTipoTelefone().getDsTipoTelefone());
			dto.setIdTipo(telefone.getTipoTelefone().getIdTipoTelefone());
			this.telefoneList.add(dto);
		}
		this.adicionarTelefoneSessao();
		
		// carrega lista de vagas
		this.vagaList = new ArrayList<VagasOfertadasDTO>();
		for (VagaOfertada vagaOfertada : this.getEmpresa().getVagaOfertadas()) {
			VagasOfertadasDTO dto = new VagasOfertadasDTO();
			dto.setDsCidade(vagaOfertada.getCidade().getDsNome());
			dto.setIdCidade(vagaOfertada.getCidade().getIdCidade());
			dto.setDsArea(vagaOfertada.getAreaInteresse().getDsAreaInteresse());
			dto.setIdArea(vagaOfertada.getAreaInteresse().getIdAreaInteresse());
			dto.setQtdVagas(vagaOfertada.getNuVagaOfertada());
			dto.setDataExpiracaoStr(DataUtil.brazilianShortFormat.format(vagaOfertada.getDtExpiracaoVaga()));
			dto.setDsVagaOfertada(vagaOfertada.getDsVagaOfertada());
			this.vagaList.add(dto);
		}
		this.adicionarVagasSessao();
		
		this.setIdRamoAtividade(this.getEmpresa().getRamoAtividade().getIdRamoAtividade());
		this.setIdPais(this.getEmpresa().getEndereco().getCidade().getUf().getPais().getIdPais());
		this.setIdUf(this.getEmpresa().getEndereco().getCidade().getUf().getIdUf());
		this.setIdCidade(this.getEmpresa().getEndereco().getCidade().getIdCidade());
		
		// inicializar as seguintes listas para a session
		this.inicializarListaUf();
		this.inicializarListaCidade();
	}
	
	@Action(value = "/logout", results = { @Result(location = "index.page", type = "tiles", name = ActionSupport.SUCCESS)} )
	public String logout() {
		this.removerSessao(SESSION_EMPRESA_LOGIN);
		this.limparDados();
		
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/abreRemover", results = { @Result(location = "/views/empresa/teste.jsp", name = ActionSupport.SUCCESS) })
	public String abreRemover() {
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/excluirVagasEmpresa",results = { @Result(name = ActionSupport.SUCCESS, type = "json",params = { "includeProperties", "paisList.*"  }) })
	public String excluirVagasEmpresa() {
		this.vagaList = (List<VagasOfertadasDTO>) this.obterDaSessao(SESSION_VAGA_LIST);

		this.vagaList.remove(Integer.parseInt(this.idVagasEx));

		this.adicionarVagasSessao();

		return ActionSupport.SUCCESS;
	}

	 @Action(value = "/obterPaisEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json",params = { "includeProperties", "paisList.*"  }) })
	public String obterPaisEmpresa() {
		this.inicializarListaPais();

		return ActionSupport.SUCCESS;
	}

	@Action(value = "/obterUfEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json",params = { "includeProperties", "ufList.*"  }   ) })
	public String obterUf() {
		this.inicializarListaUf();
		
		return ActionSupport.SUCCESS;
	}

	@Action(value = "/listarAreaInteresse", results = { @Result(name = "success", type = "json",params = { "includeProperties", "areaList.*"  }) })
	public String listarAreaInteresse() {
		this.setAreaList(this.getEmpresaBo().listarAreaInteresse());

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/obterCidadeEmpresa", results = { @Result(name = "success", type = "json",params = { "includeProperties", "cidadeList.*"  }) })
	public String obterCidade() {

		this.inicializarListaCidade();

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/listarCidadesGoias", results = { @Result(name = "success", type = "json",params = { "includeProperties", "cidadeVagaList.*"  }) })
	public String listarCidadesGoias() {

		this.setCidadeVagaList(this.getEmpresaBo().obterCidadePorSiglaUf("GO"));

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/obterTipoTelefoneEmpresa", results = { @Result(name = "success", type = "json",params = { "includeProperties", "tipoTelefoneList.*"  }) })
	public String obterTipoTelefoneEmpresa() {
		this.setTipoTelefoneList(this.getEmpresaBo().listarTipoTelefone());

		return PremiumAction.SUCCESS;
	}

	@Actions({ @Action(value = "/listarRamoAtividade", results = { @Result(name = "success", type = "json",params = { "includeProperties", "ramoList.*"  }) }) })
	public String listarRamoAtividade() {
		this.setRamoList(this.getEmpresaBo().listarRamoAtividade());

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/adicionarVagasOfertasEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json") })
	public String adicionarVagasOfertasEmpresa() {
		if (!(this.processingReturn = this.validateRequiredFieldsFilled()).getSuccess()) {
			return PremiumAction.SUCCESS;
		} else if (!(this.processingReturn = this.isDateFieldRightFilled()).getSuccess()) {
			return PremiumAction.SUCCESS;
		}

		this.prepararDadosVagaDTO();

		if (ValidatorUtil.isNull(this.obterDaSessao(SESSION_VAGA_LIST))) {
			this.vagaList = new ArrayList<VagasOfertadasDTO>();
			this.vagaList.add(this.getVaga());
		} else {
			this.vagaList = (List<VagasOfertadasDTO>) this.obterDaSessao(SESSION_VAGA_LIST);
			this.vagaList.add(this.getVaga());
		}
		
		this.setVaga(null);
		this.adicionarVagasSessao();
		
		this.processingReturn = new ProcessingReturn();
		return PremiumAction.SUCCESS;
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 */
	private void prepararDadosVagaDTO() {
		final Cidade cidade = this.getEmpresaBo().obterCidadePorId(this.getVaga().getIdCidade());
		final AreaInteresse area = this.getEmpresaBo().obterAreaInteressePorId(this.getVaga().getIdArea());
		this.getVaga().setDsCidade(cidade.getDsNome());
		this.getVaga().setDsArea(area.getDsAreaInteresse());
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/editarTelefoneEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json") })
	public String editarTelefoneEmpresa() {
		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao(SESSION_TELEFONE_LIST);
		TelefoneDTO dto = this.telefoneList.get(this.getIndexElementList());
		
		this.telefone = new Telefone();
		this.telefone.setIdTelefone(dto.getIdDTO());
		this.telefone.setNuTelefone(dto.getNumeroDTO());
		this.telefone.setNuDdd(dto.getNuDdd());
		this.telefone.setNuDdi(dto.getNuDdi());
		
		this.setIdTipoTelefone(dto.getIdTipo());
		
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/editarVagasOfertasEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json") })
	public String editarVagasOfertasEmpresa() {
		this.vagaList = (List<VagasOfertadasDTO>) this.obterDaSessao(SESSION_VAGA_LIST);
		VagasOfertadasDTO dto = this.vagaList.get(this.getIndexElementList().intValue());
		this.setVaga(dto);
		
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/salvarTelefoneEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json") })
	public String salvarTelefoneEmpresa() {
		if (!this.validarDadosTelefone()) {
			this.processingReturn = new ProcessingReturn("Todos os campos de telefone s�o obrigat�rios");
			return ActionSupport.SUCCESS;
		}
		
		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao(SESSION_TELEFONE_LIST);
		
		final TelefoneDTO dto = this.obterTelefone();
		this.telefoneList.set(this.getIndexElementList().intValue(), dto);
		
		this.adicionarTelefoneSessao();
		
		this.processingReturn = new ProcessingReturn();
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/salvarVagasOfertasEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json") })
	public String salvarVagasOfertasEmpresa() {
		if (!(this.processingReturn = this.validateRequiredFieldsFilled()).getSuccess()) {
			return PremiumAction.SUCCESS;
		} else if (!(this.processingReturn = this.isDateFieldRightFilled()).getSuccess()) {
			return PremiumAction.SUCCESS;
		}
		
		this.prepararDadosVagaDTO();
		
		this.vagaList = (List<VagasOfertadasDTO>) this.obterDaSessao(SESSION_VAGA_LIST);
		this.vagaList.set(this.getIndexElementList().intValue(), this.getVaga());
		
		this.setVaga(null);
		this.adicionarVagasSessao();
		
		this.processingReturn = new ProcessingReturn();
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/excluirTelefoneEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json") })
	public String excluirTelefoneEmpresa() {
		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao(SESSION_TELEFONE_LIST);
		this.telefoneList.remove(this.getIndexElementList().intValue());
		this.adicionarTelefoneSessao();

		this.processingReturn = new ProcessingReturn();
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/excluirVagasOfertasEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json") })
	public String excluirVagasOfertasEmpresa() {
		this.vagaList = (List<VagasOfertadasDTO>) this.obterDaSessao(SESSION_VAGA_LIST);
		this.vagaList.remove(this.getIndexElementList().intValue());
		
		this.setVaga(null);
		this.adicionarVagasSessao();
		
		this.processingReturn = new ProcessingReturn();
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/adicionarTelefoneEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json") })
	public String adicionarTelefoneEmpresa() {
		if (!this.validarDadosTelefone()) {
			this.processingReturn = new ProcessingReturn("Todos os campos de telefone s�o obrigat�rios");
			return ActionSupport.SUCCESS;
		}
		if (ValidatorUtil.isNull(this.obterDaSessao(SESSION_TELEFONE_LIST))) {
			this.telefoneList = new ArrayList<TelefoneDTO>();
			this.telefoneList.add(this.obterTelefone());
		} else {
			this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao(SESSION_TELEFONE_LIST);
			this.telefoneList.add(this.obterTelefone());
		}
		this.setTelefone(null);
		this.setIdTipoTelefone(null);
		this.adicionarTelefoneSessao();
		
		this.processingReturn = new ProcessingReturn();
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/loadTablePhoneNumber", results = { @Result(location = "loadTablePhoneNumber.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String loadTablePhoneNumber() {
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/loadTableJobVacancy", results = { @Result(location = "loadTableJobVacancy.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String loadTableJobVacancy() {
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Actions({ @Action(value = "/obterTelefones", results = { @Result(name = ActionSupport.SUCCESS, type = "json",params = { "includeProperties", "telefoneList.*"  }) }) })
	public String obterTelefones() {

		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao(SESSION_TELEFONE_LIST);

		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Actions({ @Action(value = "/listarVagasOfertadas", results = { @Result(name = PremiumAction.SUCCESS, type = "json",params = { "includeProperties", "vagaList.*"  }) }) })
	public String listarVagasOfertadas() {

		this.vagaList = (List<VagasOfertadasDTO>) this.obterDaSessao(SESSION_VAGA_LIST);

		return PremiumAction.SUCCESS;
	}

	/**
	 * M�todo respons�vel por abrir o formulario de cadastro de empresa.
	 * 
	 * @author Silv�nio
	 * 
	 * @return String para localiza��o
	 */
	@Action(value = "/voltarInicioEmpresa", results = { @Result(location = "/views/index.jsp", type = "redirect", name = ActionSupport.SUCCESS) })
	public String voltarInicioEmpresa() {
		return ActionSupport.SUCCESS;
	}

	/**
	 * M�todo respons�vel por abrir o formulario de cadastro de empresa.
	 * 
	 * @author Silv�nio
	 * 
	 * @return String para localiza��o
	 */
	@Action(value = "/iniciarEmpresa", results = { @Result(location = "empresa.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String iniciarEmpresa() {
		this.limparDados();

		return ActionSupport.SUCCESS;
	}

	/**
	 * M�todo respons�vel por iniciar os dados do emprego
	 * 
	 * @author Silv�nio
	 * 
	 * @return tela iniciao emprego.
	 */
	@Action(value = "/iniciarEmprego", results = { @Result(location = "empregosEfetivados.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String iniciarEmprego() {
		return ActionSupport.SUCCESS;
	}

	/**
	 * M�todo respons�vel por incluir os dados da empresa
	 * 
	 * @author Silv�nio
	 * 
	 * @return tela de inclusao.
	 */
	@Action(value = "/incluirEmpresa", results = { @Result(location = "empresa.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String incluirEmpresa() {
		try {
			this.getEmpresa().getEndereco().setCidade(this.getEmpresaBo().obterCidadePorId(this.getIdCidade()));
			this.obterDadosTelefone();
			this.getEmpresa().setRamoAtividade(this.getEmpresaBo().obterRamoAtividadePorId(this.getIdRamoAtividade()));
			this.obterDadosVagasOfertadas();
			
			this.empresaBo.incluir(this.getEmpresa());
			
			// faz um refresh no objeto Empresa e popula as informa��es da tela novamente
			this.setEmpresa(this.empresaBo.obterEmpresaPeloId(this.empresa.getIdEmpresa()));
			this.incluirSessao(SESSION_EMPRESA_LOGIN, this.getEmpresa());
			this.popularDadosDaTela();
			
			this.adicionarMensagemSucesso(PremiumAction.OPERACAO_SUCESSO);
		} catch (final NegocioException ne) {
			this.adicionarMensagemValidacao(ne);
		}
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/salvarEmpresa", results = { @Result(location = "empresa.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String salvarEmpresa() {
		try {
			Empresa cadastroAtual = (Empresa) this.obterDaSessao(SESSION_EMPRESA_LOGIN);
			this.getEmpresa().setIdEmpresa(cadastroAtual.getIdEmpresa());
			
			this.obterDadosTelefone();
			this.obterDadosVagasOfertadas();
			
			Cidade cidade = new Cidade();
			cidade.setIdCidade(this.getIdCidade());
			this.getEmpresa().getEndereco().setCidade(cidade);
			
			RamoAtividade ramoAtividade = new RamoAtividade();
			ramoAtividade.setIdRamoAtividade(this.getIdRamoAtividade());
			this.getEmpresa().setRamoAtividade(ramoAtividade);
			
			// persiste as informa��es na base, faz um refresh no objeto Empresa e popula as informa��es da tela novamente
			this.setEmpresa(this.empresaBo.salvar(this.getEmpresa()));
			this.incluirSessao(SESSION_EMPRESA_LOGIN, this.getEmpresa());
			this.popularDadosDaTela();
		} catch (final NegocioException ne) {
			this.adicionarMensagemValidacao(ne);
		}
		return ActionSupport.SUCCESS;
	}
	
	@Action(value = "/excluirEmpresaPorId", results = { @Result(location = "index.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String excluirEmpresaPorId() {
		try {
			if (this.getEmpresa() == null || this.getEmpresa().getIdEmpresa() == null) {
				throw new NegocioException("N�o foi poss�vel efetuar a exclus�o da empresa");
			}
			this.empresaBo.excluir(this.empresaBo.obterEmpresaPeloId(this.getEmpresa().getIdEmpresa()));
			
			this.adicionarMensagemSucesso(PremiumAction.OPERACAO_SUCESSO);
		} catch (final NegocioException ne) {
			this.adicionarMensagemValidacao(ne);
		}
		return ActionSupport.SUCCESS;
	}
	
	/**
	 * M�todo respons�vel por oberer os dados das vagas ofertadas.
	 * 
	 * @author Silv�nio
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void obterDadosVagasOfertadas() {
		final List<VagasOfertadasDTO> dtoList = (List<VagasOfertadasDTO>) this.obterDaSessao(SESSION_VAGA_LIST);
		if (ValidatorUtil.isNotNull(dtoList)) {
			final List<VagaOfertada> vagas = new ArrayList<VagaOfertada>();
			for (final VagasOfertadasDTO dto : dtoList) {
				final VagaOfertada vaga = new VagaOfertada();
				final Cidade cidade = new Cidade();
				cidade.setIdCidade(dto.getIdCidade());
				vaga.setCidade(cidade);
				vaga.setNuVagaOfertada(dto.getQtdVagas());
				vaga.setDtExpiracaoVaga(DataUtil.obterData(dto.getDataExpiracaoStr()));
				vaga.setDsVagaOfertada(dto.getDsVagaOfertada());
				vaga.setEmpresa(this.getEmpresa());
				AreaInteresse area = new AreaInteresse();
				area.setIdAreaInteresse(dto.getIdArea());
				vaga.setAreaInteresse(area);
				vagas.add(vaga);
			}
			this.getEmpresa().setVagaOfertadas(vagas);
		}
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void obterDadosTelefone() {
		final List<TelefoneDTO> telefones = (List<TelefoneDTO>) this.obterDaSessao(SESSION_TELEFONE_LIST);
		final List<Telefone> fones = new ArrayList<Telefone>();
		if (ValidatorUtil.isNotNull(telefones)) {
			for (final TelefoneDTO dto : telefones) {
				final Telefone telefone = new Telefone();
				final TipoTelefone tipo = this.getEmpresaBo().obterTipoTelefonePorId(dto.getIdTipo());
				telefone.setTipoTelefone(tipo);
				telefone.setNuDdi(dto.getNuDdi());
				telefone.setNuDdd(dto.getNuDdd());
				telefone.setNuTelefone(dto.getNumeroDTO());
				telefone.setEmpresa(this.getEmpresa());
				fones.add(telefone);
			}
			this.getEmpresa().setTelefones(fones);
		}
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void inicializarListaCidade() {
		if (ValidatorUtil.isNotNull(this.getIdUf())) {
			this.setCidadeList(this.getEmpresaBo().obterCidadePorIdUf(this.getIdUf()));
			this.incluirSessao(SESSION_CIDADE_LIST, this.getCidadeList());
		} else {
			this.setCidadeList((List<CidadeDTO>) this.obterDaSessao(SESSION_CIDADE_LIST));
		}
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void inicializarListaUf() {
		if (ValidatorUtil.isNotNull(this.getIdPais())) {
			this.setUfList(this.getEmpresaBo().obetUfPorIdPais(this.getIdPais()));
			this.incluirSessao(SESSION_UF_LIST, this.getUfList());
		} else {
			this.setUfList((List<UfDTO>) this.obterDaSessao(SESSION_UF_LIST));
		}
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @return
	 */
	private TelefoneDTO obterTelefone() {
		final TelefoneDTO dto = new TelefoneDTO();
		dto.setIdDTO(this.telefone.getIdTelefone());
		dto.setNumeroDTO(this.telefone.getNuTelefone());
		dto.setNuDdd(this.telefone.getNuDdd());
		dto.setNuDdi(this.telefone.getNuDdi());
		final TipoTelefone tipo = this.getEmpresaBo().obterTipoTelefonePorId(this.getIdTipoTelefone());
		dto.setDsTipo(tipo.getDsTipoTelefone());
		dto.setIdTipo(tipo.getIdTipoTelefone());
		return dto;
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 */
	private void inicializarListaPais() {
		this.setPaisList(this.getEmpresaBo().listarPais());
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @return
	 */
	private boolean validarDadosTelefone() {
		return !(ValidatorUtil.isNull(this.getIdTipoTelefone()) || this.getIdTipoTelefone() == 0 || ValidatorUtil.isNull(this.getTelefone())
				|| ValidatorUtil.isNull(this.getTelefone().getNuTelefone()) || ValidatorUtil.isNull(this.getTelefone().getNuDdd())
				|| ValidatorUtil.isNull(this.getTelefone().getNuDdi()));
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @return
	 */
	private ProcessingReturn validateRequiredFieldsFilled() {
		if ( !ValidatorUtil.isNull(this.getVaga().getIdCidade()) && 
				!this.getVaga().getIdCidade().equals(new Long(0)) && 
				!ValidatorUtil.isNull(this.getVaga().getIdArea()) &&
				!this.getVaga().getIdArea().equals(new Long(0)) &&
				!ValidatorUtil.isNull(this.getVaga().getQtdVagas()) &&
				!ValidatorUtil.isNull(this.getVaga().getDataExpiracaoStr()) && 
				!ValidatorUtil.isBlank(this.getVaga().getDataExpiracaoStr()) &&
				!ValidatorUtil.isBlank(this.getVaga().getDsVagaOfertada()) ) {
			return new ProcessingReturn();
		}
		return new ProcessingReturn("Todos os campos do formul�rio para Disponibiliza��o de Vagas s�o obrigat�rios.");
	}

	private ProcessingReturn isDateFieldRightFilled() {
		String errorMsg = "A data de expira��o deve ter o formato dd/mm/aaaa, e ainda precisa ser maior ou igual a hoje.";
		try {
			final Date dataExpiracao = DataUtil.obterData(this.getVaga().getDataExpiracaoStr());
			Date now = DataUtil.getTodayMidNight();
			if (dataExpiracao.getTime() >= now.getTime()) {
				return new ProcessingReturn();
			}
			return new ProcessingReturn(errorMsg);
		} catch (Exception e) {
			return new ProcessingReturn(errorMsg);
		}
	}
	
	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 */
	private void createIndexTelefone() {

		int i = 0;
		for (final TelefoneDTO dto : this.telefoneList) {

			dto.setIndex(i);

			i++;

		}

	}

	private void createIndexVagas() {
		int i = 0;
		for (final VagasOfertadasDTO dto : this.vagaList) {
			dto.setIndex(i);
			i++;
		}
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 */
	private void adicionarTelefoneSessao() {
		this.createIndexTelefone();
		this.incluirSessao(SESSION_TELEFONE_LIST, this.telefoneList);
	}

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 */
	private void adicionarVagasSessao() {
		this.createIndexVagas();
		this.incluirSessao(SESSION_VAGA_LIST, this.vagaList);
	}

	/**
	 * M�todo respons�vel por limpar os dados.
	 * 
	 * @author Silv�nio
	 */
	private void limparDados() {
		this.setEmpresa(new Empresa());
		this.getEmpresa().setEndereco(new Endereco());
		this.getEmpresa().setRamoAtividade(new RamoAtividade());
		
		this.setIdPais(null);
		this.setIdUf(null);
		this.setIdCidade(null);
		this.setIdRamoAtividade(null);

		this.removerSessao(SESSION_UF_LIST);
		this.removerSessao(SESSION_CIDADE_LIST);
		this.removerSessao(SESSION_TELEFONE_LIST);
		this.removerSessao(SESSION_VAGA_LIST);
	}

	public EmpresaBo getEmpresaBo() {

		return this.empresaBo;
	}

	public void setEmpresaBo(final EmpresaBo empresaBo) {

		this.empresaBo = empresaBo;
	}

	public List<PaisDTO> getPaisList() {

		return this.paisList;
	}

	public void setPaisList(final List<PaisDTO> paisList) {

		this.paisList = paisList;
	}

	public List<UfDTO> getUfList() {

		return this.ufList;
	}

	public void setUfList(final List<UfDTO> ufList) {

		this.ufList = ufList;
	}

	public List<CidadeDTO> getCidadeList() {

		return this.cidadeList;
	}

	public void setCidadeList(final List<CidadeDTO> cidadeList) {

		this.cidadeList = cidadeList;
	}

	public List<TipoTelefoneDTO> getTipoTelefoneList() {

		return this.tipoTelefoneList;
	}

	public void setTipoTelefoneList(final List<TipoTelefoneDTO> tipoTelefoneList) {

		this.tipoTelefoneList = tipoTelefoneList;
	}

	public List<TelefoneDTO> getTelefoneList() {

		return this.telefoneList;
	}

	public void setTelefoneList(final List<TelefoneDTO> telefoneList) {

		this.telefoneList = telefoneList;
	}

	public Telefone getTelefone() {

		return this.telefone;
	}

	public void setTelefone(final Telefone telefone) {

		this.telefone = telefone;
	}

	public List<RamoAtividadeDTO> getRamoList() {

		return this.ramoList;
	}

	public void setRamoList(final List<RamoAtividadeDTO> ramoList) {

		this.ramoList = ramoList;
	}

	public List<CidadeDTO> getCidadeVagaList() {

		return this.cidadeVagaList;
	}

	public void setCidadeVagaList(final List<CidadeDTO> cidadeVagaList) {

		this.cidadeVagaList = cidadeVagaList;
	}

	public List<VagasOfertadasDTO> getVagaList() {

		return this.vagaList;
	}

	public void setVagaList(final List<VagasOfertadasDTO> vagaList) {

		this.vagaList = vagaList;
	}

	public List<AreaInteresseDTO> getAreaList() {

		return this.areaList;
	}

	public void setAreaList(final List<AreaInteresseDTO> areaList) {

		this.areaList = areaList;
	}

	public VagasOfertadasDTO getVaga() {
		return this.vaga;
	}

	public void setVaga(final VagasOfertadasDTO vaga) {

		this.vaga = vaga;
	}

	public String getIdVagasEx() {

		return this.idVagasEx;
	}

	public void setIdVagasEx(final String idVagasEx) {

		this.idVagasEx = idVagasEx;
	}

	public String getDataEx() {

		return this.dataEx;
	}

	public void setDataEx(final String dataEx) {

		this.dataEx = dataEx;
	}

	public Empresa getEmpresa() {

		return this.empresa;
	}

	public void setEmpresa(final Empresa empresa) {

		this.empresa = empresa;
	}

	public Long getIdPais() {

		return this.idPais;
	}

	public void setIdPais(final Long idPais) {

		this.idPais = idPais;
	}

	public Long getIdUf() {

		return this.idUf;
	}

	public void setIdUf(final Long idUf) {

		this.idUf = idUf;
	}

	public Long getIdCidade() {

		return this.idCidade;
	}

	public void setIdCidade(final Long idCidade) {

		this.idCidade = idCidade;
	}

	public Long getIdTipoTelefone() {

		return this.idTipoTelefone;
	}

	public void setIdTipoTelefone(final Long idTipoTelefone) {

		this.idTipoTelefone = idTipoTelefone;
	}

	public Long getIdRamoAtividade() {

		return this.idRamoAtividade;
	}

	public void setIdRamoAtividade(final Long idRamoAtividade) {

		this.idRamoAtividade = idRamoAtividade;
	}

	public Long getIdCidadeVaga() {

		return this.idCidadeVaga;
	}

	public void setIdCidadeVaga(final Long idCidadeVaga) {

		this.idCidadeVaga = idCidadeVaga;
	}

	public Integer getIndexElementList() {
	
		return indexElementList;
	}

	
	public void setIndexElementList(Integer indexElementList) {
	
		this.indexElementList = indexElementList;
	}

	public ProcessingReturn getProcessingReturn() {
		return processingReturn;
	}

	public void setProcessingReturn(ProcessingReturn processingReturn) {
		this.processingReturn = processingReturn;
	}
}
