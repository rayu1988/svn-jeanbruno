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

import com.opensymphony.xwork2.ActionSupport;

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

/**
 * <p>
 * <b>Title:</b> EmpresaAction.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe responsável por controlar os dados da empresa
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Silvânio
 * 
 * @version 1.0.0
 */
@ParentPackage(value = "portalemprego")
@Namespace("/empresa")
@Component("empresaAction")
public class EmpresaAction extends PremiumAction {

	private static final long serialVersionUID = -4096579557902623573L;

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

	@Action(value = "/abreRemover", results = { @Result(location = "/views/empresa/teste.jsp", name = EmpresaAction.SUCCESS) })
	public String abreRemover() {

		return "success";
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/excluirVagasEmpresa",results = { @Result(name = ActionSupport.SUCCESS, type = "json",params = { "includeProperties", "paisList.*"  }) })
	public String excluirVagasEmpresa() {

		this.vagaList = (List<VagasOfertadasDTO>) this.obterDaSessao("vagaList");

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
	@Action(value = "/adicionarVagasOfertasEmpresa", results = { @Result(location = "empresa.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String adicionarVagasOfertasEmpresa() {

		final boolean isValido = this.validarDadosVagas();

		if (!isValido) {

			return PremiumAction.SUCCESS;

		}

		this.prepararDadosVagaDTO();

		if (ValidatorUtil.isNull(this.obterDaSessao("vagaList"))) {

			this.vagaList = new ArrayList<VagasOfertadasDTO>();

			this.vagaList.add(this.getVaga());

		} else {

			this.vagaList = (List<VagasOfertadasDTO>) this.obterDaSessao("vagaList");

			this.vagaList.add(this.getVaga());

		}

		this.setVaga(null);

		this.adicionarVagasSessao();

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void prepararDadosVagaDTO() {

		final Cidade cidade = this.getEmpresaBo().obterCidadePorId(this.getVaga().getIdCidade());

		final AreaInteresse area = this.getEmpresaBo().obterAreaInteressePorId(this.getVaga().getIdArea());

		this.getVaga().setDsCidade(cidade.getDsNome());

		this.getVaga().setIdCidade(cidade.getIdCidade());

		this.getVaga().setDsArea(area.getDsAreaInteresse());

		this.getVaga().setIdArea(area.getIdAreaInteresse());
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/editarTelefoneEmpresa", results = { @Result(name = ActionSupport.SUCCESS, type = "json") })
	public String editarTelefoneEmpresa() {
		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao("telefoneList");
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
	@Action(value = "/salvarTelefoneEmpresa", results = { @Result(location = "updateEmpresaTelefone.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String salvarTelefoneEmpresa() {
		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao("telefoneList");
		
		final TelefoneDTO dto = this.obterTelefone();
		this.telefoneList.set(this.getIndexElementList().intValue(), dto);
		
		this.adicionarTelefoneSessao();
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/excluirTelefoneEmpresa", results = { @Result(location = "updateEmpresaTelefone.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String excluirTelefoneEmpresa() {
		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao("telefoneList");
		this.telefoneList.remove(this.getIndexElementList().intValue());
		this.adicionarTelefoneSessao();
		return ActionSupport.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "/adicionarTelefoneEmpresa", results = { @Result(location = "updateEmpresaTelefone.page", type = "tiles", name = ActionSupport.SUCCESS) })
	public String adicionarTelefoneEmpresa() {
		final boolean isValido = this.validarDadosTelefone();
		if (!isValido) {
			return ActionSupport.SUCCESS;
		}
		if (ValidatorUtil.isNull(this.obterDaSessao("telefoneList"))) {
			this.telefoneList = new ArrayList<TelefoneDTO>();
			final TelefoneDTO dto = this.obterTelefone();
			this.telefoneList.add(dto);
		} else {
			this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao("telefoneList");
			final TelefoneDTO dto = this.obterTelefone();
			this.telefoneList.add(dto);
		}
		this.setTelefone(null);
		this.setIdTipoTelefone(null);
		this.adicionarTelefoneSessao();
		
		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Actions({ @Action(value = "/obterTelefones", results = { @Result(name = ActionSupport.SUCCESS, type = "json",params = { "includeProperties", "telefoneList.*"  }) }) })
	public String obterTelefones() {

		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao("telefoneList");

		return ActionSupport.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Actions({ @Action(value = "/listarVagasOfertadas", results = { @Result(name = PremiumAction.SUCCESS, type = "json",params = { "includeProperties", "vagaList.*"  }) }) })
	public String listarVagasOfertadas() {

		this.vagaList = (List<VagasOfertadasDTO>) this.obterDaSessao("vagaList");

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por abrir o formulario de cadastro de empresa.
	 * 
	 * @author Silvânio
	 * 
	 * @return String para localização
	 */
	@Action(value = "/voltarInicioEmpresa", results = { @Result(location = "/views/index.jsp", type = "redirect", name = EmpresaAction.SUCCESS) })
	public String voltarInicioEmpresa() {

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por abrir o formulario de cadastro de empresa.
	 * 
	 * @author Silvânio
	 * 
	 * @return String para localização
	 */
	@Action(value = "/iniciarEmpresa", results = { @Result(location = "empresa.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String iniciarEmpresa() {

		this.limparDados();

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por iniciar os dados do emprego
	 * 
	 * @author Silvânio
	 * 
	 * @return tela iniciao emprego.
	 */
	@Action(value = "/iniciarEmprego", results = { @Result(location = "empregosEfetivados.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String iniciarEmprego() {

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por incluir os dados da empresa
	 * 
	 * @author Silvânio
	 * 
	 * @return tela de inclusao.
	 */
	@Action(value = "/incluirEmpresa", results = { @Result(location = "empresa.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String incluirEmpresa() {

		try {

			this.getEmpresa().getEndereco().setCidade(this.getEmpresaBo().obterCidadePorId(this.getIdCidade()));

			this.obterDadosTelefone();

			this.getEmpresa().setRamoAtividade(this.getEmpresaBo().obterRamoAtividadePorId(this.getIdRamoAtividade()));

			this.obterDadosVagasOfertadas();

			this.empresaBo.incluir(this.getEmpresa());

			this.limparDados();

			this.adicionarMensagemSucesso(PremiumAction.OPERACAO_SUCESSO);

		} catch (final NegocioException ne) {

			this.adicionarMensagemValidacao(ne);

		}

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por oberer os dados das vagas ofertadas.
	 * 
	 * @author Silvânio
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void obterDadosVagasOfertadas() {

		final List<VagasOfertadasDTO> dtoList = (List<VagasOfertadasDTO>) this.obterDaSessao("vagaList");

		if (ValidatorUtil.isNotNull(dtoList)) {
			final List<VagaOfertada> vagas = new ArrayList<VagaOfertada>();

			for (final VagasOfertadasDTO dto : dtoList) {

				final VagaOfertada vaga = new VagaOfertada();

				final Cidade cidade = this.empresaBo.obterCidadePorId(dto.getIdCidade());

				final AreaInteresse area = this.empresaBo.obterAreaInteressePorId(dto.getIdArea());

				vaga.setCidade(cidade);

				vaga.setNuVagaOfertada(dto.getQtdVagas());

				vaga.setDtExpiracaoVaga(DataUtil.obterData(dto.getDataExpiracaoStr()));

				vaga.setDsVagaOfertada(dto.getDsVagaOfertada());

				vaga.setEmpresa(this.getEmpresa());

				vaga.setAreaInteresse(area);

				vagas.add(vaga);

			}

			this.getEmpresa().setVagaOfertadas(vagas);
		}
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void obterDadosTelefone() {

		final List<TelefoneDTO> telefones = (List<TelefoneDTO>) this.obterDaSessao("telefoneList");

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
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void inicializarListaCidade() {

		if (ValidatorUtil.isNotNull(this.getIdUf())) {

			this.setCidadeList(this.getEmpresaBo().obterCidadePorIdUf(this.getIdUf()));

			this.incluirSessao("cidadeList", this.getCidadeList());

		} else {

			this.setCidadeList((List<CidadeDTO>) this.obterDaSessao("cidadeList"));

		}
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void inicializarListaUf() {

		if (ValidatorUtil.isNotNull(this.getIdPais())) {

			this.setUfList(this.getEmpresaBo().obetUfPorIdPais(this.getIdPais()));

			this.incluirSessao("ufList", this.getUfList());

		} else {

			this.setUfList((List<UfDTO>) this.obterDaSessao("ufList"));

		}

	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
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
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void inicializarListaPais() {

		this.setPaisList(this.getEmpresaBo().listarPais());

	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @return
	 */
	private boolean validarDadosTelefone() {
		if (ValidatorUtil.isNull(this.getIdTipoTelefone()) || this.getIdTipoTelefone() == 0 || ValidatorUtil.isNull(this.getTelefone())
				|| ValidatorUtil.isNull(this.getTelefone().getNuTelefone()) || ValidatorUtil.isNull(this.getTelefone().getNuDdd())
				|| ValidatorUtil.isNull(this.getTelefone().getNuDdi())) {
			this.adicionarMensagemValidacao("Todos os campos de telefone são obrigatórios");
			return false;
		}
		return true;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @return
	 */
	private boolean validarDadosVagas() {

		final Date data = DataUtil.obterData(this.getVaga().getDataExpiracaoStr());

		if (ValidatorUtil.isNull(this.getVaga().getIdCidade()) || ValidatorUtil.isNull(this.getVaga().getQtdVagas()) || ValidatorUtil.isNull(this.getVaga().getDataExpiracaoStr())
				|| ValidatorUtil.isBlank(this.getVaga().getDataExpiracaoStr()) || ValidatorUtil.isNull(data) || ValidatorUtil.isNull(this.getVaga().getIdArea())) {

			this.adicionarMensagemValidacao("Todos os campos de vagas são obrigatórios");

			return false;
		}

		return true;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
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
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void adicionarTelefoneSessao() {

		this.createIndexTelefone();

		this.incluirSessao("telefoneList", this.telefoneList);
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void adicionarVagasSessao() {

		this.createIndexVagas();

		this.incluirSessao("vagaList", this.vagaList);

	}

	/**
	 * Método responsável por limpar os dados.
	 * 
	 * @author Silvânio
	 */
	private void limparDados() {

		this.setEmpresa(new Empresa());

		this.getEmpresa().setEndereco(new Endereco());

		this.getEmpresa().setRamoAtividade(new RamoAtividade());

		this.removerSessao("ufList");

		this.removerSessao("cidadeList");

		this.removerSessao("telefoneList");

		this.removerSessao("vagaList");

		this.setIdPais(null);

		this.setIdUf(null);

		this.setIdCidade(null);

		this.setIdRamoAtividade(null);
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
}
