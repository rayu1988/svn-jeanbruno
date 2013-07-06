package br.gov.go.sectec.portalemprego.web.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.gov.go.sectec.portalemprego.comum.enumerator.SexoEnum;
import br.gov.go.sectec.portalemprego.comum.enumerator.SimNaoEnum;
import br.gov.go.sectec.portalemprego.comum.exception.NegocioException;
import br.gov.go.sectec.portalemprego.comum.interfacebo.AlunoBo;
import br.gov.go.sectec.portalemprego.comum.utilitario.DataUtil;
import br.gov.go.sectec.portalemprego.comum.utilitario.MensagemNegocioUtils;
import br.gov.go.sectec.portalemprego.comum.utilitario.MoedaUtil;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.Aluno;
import br.gov.go.sectec.portalemprego.core.entidade.CargoInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.Escolaridade;
import br.gov.go.sectec.portalemprego.core.entidade.EstadoCivil;
import br.gov.go.sectec.portalemprego.core.entidade.Telefone;
import br.gov.go.sectec.portalemprego.core.entidade.TipoDeficiencia;
import br.gov.go.sectec.portalemprego.core.entidade.TipoGrau;
import br.gov.go.sectec.portalemprego.core.entidade.TipoTelefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CargoInteresseDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EscolaridadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EstadoCivilDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.ExperienciaProfissionalDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.FormacaoAcademicaDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.PaisDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TelefoneDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoDeficienciaDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoGrauDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoTelefoneDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.UfDTO;
import br.gov.go.sectec.portalemprego.web.form.AlunoForm;

/**
 * <p>
 * <b>Title:</b> AtualizarCurriculoAlunoAction.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
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
@Namespace("/curriculo")
@Component("atualizarCurriculoAlunoAction")
public class AtualizarCurriculoAlunoAction extends PremiumAction {

	private static final long serialVersionUID = 1615598164753711415L;

	private List<PaisDTO> paisList;

	private List<UfDTO> ufList;

	private List<CidadeDTO> cidadeList;

	private List<EstadoCivilDTO> estadoCivilList;

	private List<EscolaridadeDTO> escolaridadeList;

	private List<TipoTelefoneDTO> tipoTelefoneList;

	private List<CargoInteresseDTO> cargoInteresseList;

	private List<TelefoneDTO> telefoneList;

	private List<TipoGrauDTO> tipoGrauList;

	private List<TipoDeficienciaDTO> tipoDeficienciaList;

	private List<FormacaoAcademicaDTO> formacaoList;

	private List<ExperienciaProfissionalDTO> experienciaList;

	private List<SexoEnum> sexoList = new ArrayList<SexoEnum>(Arrays.asList(SexoEnum.values()));

	private List<SimNaoEnum> simNaoList = new ArrayList<SimNaoEnum>(Arrays.asList(SimNaoEnum.values()));

	private String login;

	private String senha;

	private Aluno aluno;

	private FormacaoAcademicaDTO formacaoAcademica;

	private ExperienciaProfissionalDTO experienciaProfissional;

	private Telefone telefone;

	private Long idPaisSelecao;

	private Long idUfSelecao;

	private Long idCidadeSelecao;

	private Long idEstadoCivil;

	private Long idEscolaridade;

	private Long idTipoTelefone;

	private Long idCargoInteresse;

	private Integer tipoSexo;

	private Integer idTelefoneEx;

	private Long idTipoDeficiencia;

	private Long idTipoGrau;

	private Integer indexEcluirFormacao;

	private Integer indexExcluirExperiencia;

	private boolean aceiteTermoCompromisso;

	@Autowired
	private AlunoForm alunoForm;

	@Autowired
	private AlunoBo alunoBo;

	@SuppressWarnings("unchecked")
	@Action(value = "/salvarCurriculo", results = { @Result(location = "atualizar.curriculo.experiencia.profissional.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String salvarCurriculo() {

		this.aluno = (Aluno) this.obterDaSessao("aluno");

		this.formacaoList = (List<FormacaoAcademicaDTO>) this.obterDaSessao("formacaoList");

		this.experienciaList = (List<ExperienciaProfissionalDTO>) this.obterDaSessao("experienciaList");

		this.aluno.setIsAceiteTermoCompromisso(this.aceiteTermoCompromisso);
		

		if(!ValidatorUtil.isBlank(aluno.getVlPretSalarioStr())){
			
			aluno.setVlPretensaoSalarial(MoedaUtil.obterBigDecimal(aluno.getVlPretSalarioStr()));
			
		}

		this.alunoBo.salvarCurriculo(this.aluno, this.formacaoList, this.experienciaList);

		this.adicionarMensagemSucesso(PremiumAction.OPERACAO_SUCESSO);

		return PremiumAction.SUCCESS;

	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void removerDadosSessao() {

		this.removerSessao("aluno");

		this.removerSessao("experienciaList");

		this.removerSessao("formacaoList");

		this.removerSessao("telefoneList");

	}

	@Action(value = "/logarAluno", results = { @Result(location = "atualizar.curriculo.dadospessoais.page", type = "tiles", name = EmpresaAction.SUCCESS),
			@Result(location = "/views/index.jsp",
			 name = EmpresaAction.LOGIN) })
	public String logarAluno() {

		try {

			this.removerDadosSessao();
			
			final Aluno aluno = this.alunoBo.obterAlunoPorloginSenha(this.getLogin(), this.getSenha());

			if (ValidatorUtil.isNotNull(aluno)) {

				this.atribuirDadosAluno(aluno);

				adicionarTelefone() ;
				
				return PremiumAction.SUCCESS;
			}
		} catch (final NegocioException e) {

			this.adicionarMensagemValidacao(e);

		}

		return PremiumAction.LOGIN;
	}

	@Actions({ @Action(value = "/obterTipoTelefoneCurriculo", results = { @Result(name = "success", type = "json", params = { "includeProperties", "tipoTelefoneList.*" }) }) })
	public String obterTipoTelefoneCurriculo() {

		this.setTipoTelefoneList(this.alunoBo.listarTipoTelefone());

		return PremiumAction.SUCCESS;
	}

	@Actions({ @Action(value = "/listarTipoGrauCurriculo", results = { @Result(name = "success", type = "json", params = { "includeProperties", "tipoGrauList.*" }) }) })
	public String listarTipoGrauCurriculo() {

		this.setTipoGrauList(this.alunoBo.listarTipoGrau());

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/obterTelefonesCurriculo", results = { @Result(name = PremiumAction.SUCCESS, type = "json", params = { "includeProperties", "telefoneList.*" }) })
	public String obterTelefonesCurriculo() {

		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao("telefoneList");

		if (this.telefoneList == null) {

			this.telefoneList = new ArrayList<TelefoneDTO>();

		}

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/listarDadosFormacaoAcademica", results = { @Result(name = PremiumAction.SUCCESS, type = "json", params = { "includeProperties", "formacaoList.*" }) })
	public String listarDadosFormacaoAcademica() {

		this.formacaoList = (List<FormacaoAcademicaDTO>) this.obterDaSessao("formacaoList");

		if (this.formacaoList == null) {

			final Aluno al = (Aluno) this.obterDaSessao("aluno");

			this.formacaoList = this.alunoBo.listarFormacaoPorIdAluno(al.getIdAluno());

			if (this.formacaoList == null) {

				this.formacaoList = new ArrayList<FormacaoAcademicaDTO>();

			}

			for (final FormacaoAcademicaDTO dto : this.formacaoList) {

				dto.formatarDatas();

			}

			this.criarIndexFormacao();

			this.incluirSessao("formacaoList", this.formacaoList);
		}

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/listarExperienciaCurriculo", results = { @Result(name = PremiumAction.SUCCESS, type = "json", params = { "includeProperties", "experienciaList.*" }) })
	public String listarExperienciaCurriculo() {

		this.experienciaList = (List<ExperienciaProfissionalDTO>) this.obterDaSessao("experienciaList");

		if (this.experienciaList == null) {

			final Aluno al = (Aluno) this.obterDaSessao("aluno");

			this.experienciaList = this.alunoBo.listarExperienciaPorIdAluno(al.getIdAluno());

			if (this.experienciaList == null) {

				this.experienciaList = new ArrayList<ExperienciaProfissionalDTO>();

			}

			for (final ExperienciaProfissionalDTO dto : this.experienciaList) {

				dto.setDataEntradaStr(DataUtil.format(dto.getDtDataEntradaDTO()));

				dto.setDataSaidaStr(DataUtil.format(dto.getDtDataSaidaDTO()));

			}

			this.criarIndexExperienciaProfissional();

			this.incluirSessao("experienciaList", this.experienciaList);

		}

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/excluirTelefoneCurriculo", results = { @Result(name = "success", type = "json", params = { "includeProperties", "telefoneList.*" }) })
	public String excluirTelefoneEmpresa() {

		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao("telefoneList");

		this.telefoneList.remove(this.idTelefoneEx.intValue());

		this.adicionarTelefoneSessao();

		this.aluno = (Aluno) this.obterDaSessao("aluno");

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/excluirFormacaoCurriculo", results = { @Result(name = "success", type = "json", params = { "includeProperties", "formacaoList.*" }) })
	public String excluirFormacaoCurriculo() {

		this.formacaoList = (List<FormacaoAcademicaDTO>) this.obterDaSessao("formacaoList");

		this.formacaoList.remove(this.indexEcluirFormacao.intValue());

		this.criarIndexFormacao();

		this.incluirSessao("formacaoList", this.formacaoList);

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/excluirExperienciaCurriculo", results = { @Result(name = "success", type = "json", params = { "includeProperties", "experienciaList.*" }) })
	public String excluirExperienciaCurriculo() {

		this.experienciaList = (List<ExperienciaProfissionalDTO>) this.obterDaSessao("experienciaList");

		this.experienciaList.remove(this.indexExcluirExperiencia.intValue());

		this.criarIndexExperienciaProfissional();

		this.incluirSessao("experienciaList", this.experienciaList);

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/adicionarTelefoneCurriculo", results = { @Result(location = "atualizar.curriculo.dadospessoais.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String adicionarTelefoneCurriculo() {

		final boolean isValido = this.validarDadosTelefone();

		this.iniciarDados();

		if (!isValido) {

			return PremiumAction.SUCCESS;

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

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/adicionarFormacao", results = { @Result(location = "atualizar.curriculo.formacao.academica.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String adicionarFormacao() {

		try {

			this.validarDadosFormacaoAcademica();

			this.validarPeriodoDataFormacao();

			final TipoGrau tipo = this.alunoBo.obterTipoGrauPorid(this.formacaoAcademica.getIdGrau());

			this.formacaoAcademica.setGrau(tipo.getDsTipoGrau());

			this.formacaoAcademica.setPeriodo(this.formacaoAcademica.getDtInicio() + " à " + this.formacaoAcademica.getDtFim());

			this.formacaoList = (List<FormacaoAcademicaDTO>) this.obterDaSessao("formacaoList");

			if (ValidatorUtil.isEmpty(this.formacaoList)) {

				this.formacaoList = new ArrayList<FormacaoAcademicaDTO>();

			}

			this.formacaoList.add(this.formacaoAcademica);

			this.criarIndexFormacao();

			this.incluirSessao("formacaoList", this.formacaoList);

			this.formacaoAcademica = new FormacaoAcademicaDTO();

		} catch (final NegocioException ne) {

			this.adicionarMensagemValidacao(ne);
		}

		this.iniciarDados();

		return PremiumAction.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/adicionarExperienciaCurriculo", results = { @Result(location = "atualizar.curriculo.experiencia.profissional.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String adicionarExperienciaCurriculo() {

		try {

			this.validarDadosExperienciaProfissional();

			this.experienciaList = (List<ExperienciaProfissionalDTO>) this.obterDaSessao("experienciaList");

			if (ValidatorUtil.isEmpty(this.experienciaList)) {

				this.experienciaList = new ArrayList<ExperienciaProfissionalDTO>();

			}

			this.experienciaList.add(this.experienciaProfissional);

			this.criarIndexExperienciaProfissional();

			this.incluirSessao("experienciaList", this.experienciaList);

			this.experienciaProfissional = new ExperienciaProfissionalDTO();

		} catch (final NegocioException ne) {

			this.adicionarMensagemValidacao(ne);
		}

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void criarIndexFormacao() {

		int i = 0;

		for (final FormacaoAcademicaDTO dto : this.formacaoList) {

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
	private void criarIndexExperienciaProfissional() {

		int i = 0;

		for (final ExperienciaProfissionalDTO dto : this.experienciaList) {

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
	private void validarPeriodoDataFormacao() {

		final Date dataInicio = DataUtil.obterData(this.formacaoAcademica.getDtInicio());

		final Date dataFim = DataUtil.obterData(this.formacaoAcademica.getDtFim());

		if (dataInicio.after(dataFim)) {

			throw new NegocioException("Periodo inválido");

		}
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void validarDadosFormacaoAcademica() {

		final MensagemNegocioUtils msg = new MensagemNegocioUtils();

		msg.validar(this.formacaoAcademica.getIdGrau(), "O Campo Grau é obrigatório");

		if (this.formacaoAcademica.getIdGrau() <= 0) {

			msg.add("O Campo Grau é obrigatório");

		}

		msg.validar(this.formacaoAcademica.getInstituicao(), "O Campo Instituição é obrigatorio");

		msg.validar(this.formacaoAcademica.getCurso(), "O Campo Curso é obrigatorio");

		msg.validar(this.formacaoAcademica.getDtInicio(), "O Campo Período data inicial é obrigatorio");

		msg.validar(this.formacaoAcademica.getDtFim(), "O Campo Período data final  é obrigatorio");

		msg.execute();
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 */
	private void validarDadosExperienciaProfissional() {

		final MensagemNegocioUtils msg = new MensagemNegocioUtils();

		msg.validar(this.experienciaProfissional.getDsEmpresaDTO(), "O Campo Empresa é obrigatório");

		msg.validar(this.experienciaProfissional.getDsCargo(), "O Campo Cargo é obrigatório");

		msg.validar(this.experienciaProfissional.getDsFuncaoDTO(), "O Campo Funcao é obrigatório");

		msg.validar(this.experienciaProfissional.getDataEntradaStr(), "O Campo Data Entrada é obrigatório");

		msg.execute();
	}

	@Action(value = "/abreFormacaoAcademica", results = { @Result(location = "atualizar.curriculo.formacao.academica.page", type = "tiles", name = EmpresaAction.SUCCESS),
			@Result(location = "atualizar.curriculo.dadospessoais.page", type = "tiles", name = EmpresaAction.ALERT) })
	public String abreFormacaoAcademica() {

		this.atribuirDadosPessoalAlterados();

		this.incluirSessao("aluno", this.aluno);

		try {

			this.alunoBo.validarDadosPessoais(this.aluno);

			return PremiumAction.SUCCESS;

		} catch (final NegocioException ne) {

			this.adicionarMensagemValidacao(ne);

			this.iniciarDados();

			return PremiumAction.ALERT;
		}
	}

	@SuppressWarnings("unchecked")
	@Action(value = "/voltarFormacaoAcademica", results = { @Result(location = "atualizar.curriculo.formacao.academica.page", type = "tiles", name = EmpresaAction.SUCCESS),
			@Result(location = "atualizar.curriculo.dadospessoais.page", type = "tiles", name = EmpresaAction.ALERT) })
	public String voltarFormacaoAcademica() {

		this.formacaoList = (List<FormacaoAcademicaDTO>) this.obterDaSessao("formacaoList");

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/abreDadosExperienciaProfissional", results = {
			@Result(location = "atualizar.curriculo.experiencia.profissional.page", type = "tiles", name = EmpresaAction.SUCCESS),
			@Result(location = "atualizar.curriculo.formacao.academica.page", type = "tiles", name = EmpresaAction.ALERT) })
	public String abreDadosExperienciaProfissional() {

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void iniciarDados() {

		this.setEstadoCivilList(this.getAlunoBo().listarEstadoCivil());

		this.setEscolaridadeList(this.getAlunoBo().listarEscolatidade());
	}

	/**
	 * Método responsável por obter os dados do aluno alterados.
	 * 
	 * @author Silvânio
	 * 
	 */
	private void atribuirDadosPessoalAlterados() {

		final EstadoCivil estadoCivil = this.alunoBo.obterEstadoCivilPorId(this.idEstadoCivil);

		final Escolaridade escolaridade = this.alunoBo.obterEscolaridadePorId(this.idEscolaridade);

		final Cidade cidade = this.alunoBo.obterCidadePorId(this.idCidadeSelecao);

		final TipoDeficiencia tipoDeficiencia = this.alunoBo.obterTipoDeficienciaPorId(this.idTipoDeficiencia);

		final CargoInteresse cargoInteresse = this.alunoBo.obterCargoInteressePorId(this.idCargoInteresse);

		this.aluno.setTelefones(this.obterTelefoneEntidade());

		this.aluno.setTpSexo(this.tipoSexo);

		if (!ValidatorUtil.isBlank(this.aluno.getDtNascimentoStr())) {

			this.aluno.setDtNascimento(DataUtil.obterData(this.aluno.getDtNascimentoStr()));

		}

		this.getAluno().setEstadoCivil(estadoCivil);

		this.getAluno().setEscolaridade(escolaridade);

		this.getAluno().getEndereco().setCidade(cidade);

		this.getAluno().setTipoDeficiencia(tipoDeficiencia);

		this.getAluno().setCargoInteresse(cargoInteresse);
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Telefone> obterTelefoneEntidade() {

		this.telefoneList = (List<TelefoneDTO>) this.obterDaSessao("telefoneList");

		if (ValidatorUtil.isNull(this.telefoneList)) {

			return null;

		}

		final List<Telefone> telefones = new ArrayList<Telefone>();

		for (final TelefoneDTO dto : this.telefoneList) {

			final Telefone telefone = new Telefone();

			telefone.setIdTelefone(dto.getIdDTO());

			final TipoTelefone tipo = this.getAlunoBo().obterTipoTelefonePorId(dto.getIdTipo());

			telefone.setTipoTelefone(tipo);

			telefone.setNuDdi(dto.getNuDdi());

			telefone.setNuDdd(dto.getNuDdd());

			telefone.setNuTelefone(dto.getNumeroDTO());

			telefone.setAluno(this.aluno);

			telefones.add(telefone);

		}

		return telefones;
	}

	@Action(value = "/abreDadosPessoais", results = { @Result(location = "atualizar.curriculo.dadospessoais.page", type = "tiles", name = EmpresaAction.SUCCESS) })
	public String abreDadosPessoais() {

		this.aluno = (Aluno) this.obterDaSessao("aluno");

		this.atribuirDadosAluno(this.aluno);

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/sairCurriculo", results = { @Result(location = "/views/index.jsp", type = "redirect", name = EmpresaAction.SUCCESS) })
	public String sairCurriculo() {

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void adicionarTelefoneSessao() {

		this.criarteIndexTelefone();

		this.incluirSessao("telefoneList", this.telefoneList);
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void criarteIndexTelefone() {

		int i = 0;
		for (final TelefoneDTO dto : this.telefoneList) {

			dto.setIndex(i);

			i++;

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

		final TipoTelefone tipo = this.alunoBo.obterTipoTelefonePorId(this.getIdTipoTelefone());

		dto.setDsTipo(tipo.getDsTipoTelefone());

		dto.setIdTipo(tipo.getIdTipoTelefone());

		return dto;
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
	 * Método responsável por atribuir os dados consultados do aluno.
	 * 
	 * @author Silvânio
	 * 
	 * @param aluno
	 */
	private void atribuirDadosAluno(final Aluno aluno) {

		this.setAluno(aluno);

		this.iniciarDados();

		this.getAluno().setDtEmisaoStr(DataUtil.format(this.getAluno().getDtCadastro()));

		this.getAluno().setDtNascimentoStr(DataUtil.format(this.getAluno().getDtNascimento()));

		if (ValidatorUtil.isNotNull(this.getAluno().getEscolaridade())) {

			this.setIdEscolaridade(this.getAluno().getEscolaridade().getIdEscolaridade());

		}

		if (ValidatorUtil.isNotNull(this.getAluno().getEstadoCivil())) {

			this.setIdEstadoCivil(this.getAluno().getEstadoCivil().getIdEstadoCivil());

		}

		this.setIdPaisSelecao(this.getAluno().getEndereco().getCidade().getUf().getPais().getIdPais());

		this.setIdUfSelecao(this.getAluno().getEndereco().getCidade().getUf().getIdUf());

		this.setIdCidadeSelecao(this.getAluno().getEndereco().getCidade().getIdCidade());

		final TipoDeficiencia tp = this.alunoBo.obterTipoDeficienciaPorIdAluno(this.getAluno().getIdAluno());

		if (ValidatorUtil.isNotNull(tp)) {

			this.setIdTipoDeficiencia(this.getAluno().getTipoDeficiencia().getIdTipoDeficiencia());

		}

		final CargoInteresse cargoInteresse = this.alunoBo.obterCargoInteressePorIdAluno(this.getAluno().getIdAluno());

		if (ValidatorUtil.isNotNull(cargoInteresse)) {

			this.setIdCargoInteresse(cargoInteresse.getIdCargoInteresse());

		}
		
		if(ValidatorUtil.isNotNull(aluno.getVlPretensaoSalarial())){
		
			 aluno.setVlPretSalarioStr(MoedaUtil.formataMoeda(aluno.getVlPretensaoSalarial()));
		}
		this.setTipoSexo(this.getAluno().getTpSexo());

		this.incluirSessao("idPais", this.getIdPaisSelecao());

		this.incluirSessao("idUf", this.getIdUfSelecao());

		this.incluirSessao("idCidade", this.getIdCidadeSelecao());

		this.incluirSessao("aluno", aluno);
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void adicionarTelefone() {

		this.telefoneList = this.alunoBo.listarTelefonePorIdAluno(aluno.getIdAluno());

		this.criarteIndexTelefone();

		this.incluirSessao("telefoneList", this.telefoneList);

	}

	@Actions({ @Action(value = "/listarTipoDeficiencia", results = { @Result(name = "success", type = "json", params = { "includeProperties", "tipoDeficienciaList.*" }) }) })
	public String listarTipoDeficiencia() {

		this.tipoDeficienciaList = this.alunoBo.listarTIpoDeficiencia();

		return PremiumAction.SUCCESS;
	}

	@Actions({ @Action(value = "/listarCargoInteresse", results = { @Result(name = "success", type = "json", params = { "includeProperties", "cargoInteresseList.*" }) }) })
	public String listarCargoInteresse() {

		this.cargoInteresseList = this.alunoBo.listarCargoInteresse();

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/obterPaisCurriculo", results = { @Result(name = "success", type = "json", params = { "includeProperties", "paisList.*" }) })
	public String obterPaisCurriculo() {

		this.inicializarListaPais();

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/obterUf", results = { @Result(name = "success", type = "json", params = { "includeProperties", "ufList.*" }) })
	public String obterUf() {

		this.inicializarListaUf();

		return PremiumAction.SUCCESS;
	}

	@Action(value = "/obterCidade", results = { @Result(name = "success", type = "json", params = { "includeProperties", "cidadeList.*" }) })
	public String obterCidade() {

		this.inicializarListaCidade();

		return PremiumAction.SUCCESS;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void inicializarListaPais() {

		this.setPaisList(this.getAlunoBo().listarPais());

		this.obterPaisSessao();

	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void inicializarListaCidade() {

		this.obterUfSessao();

		this.setCidadeList(this.alunoBo.obterCidadePorIdUf(this.getIdUfSelecao()));

		this.obterCidadeSessao();

	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void inicializarListaUf() {

		this.obterPaisSessao();

		this.setUfList(this.alunoBo.obterUfPorIdPais(this.getIdPaisSelecao()));

		this.obterUfSessao();

	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void obterPaisSessao() {

		if (ValidatorUtil.isNull(this.idPaisSelecao)) {

			this.setIdPaisSelecao((Long) this.obterDaSessao("idPais"));

		}
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void obterUfSessao() {

		if (ValidatorUtil.isNull(this.idUfSelecao)) {

			this.setIdUfSelecao((Long) this.obterDaSessao("idUf"));

		}
	}

	/**
	 * o Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 */
	private void obterCidadeSessao() {

		if (ValidatorUtil.isNull(this.idCidadeSelecao)) {

			this.setIdCidadeSelecao((Long) this.obterDaSessao("idUf"));

		}
	}

	public AlunoBo getAlunoBo() {

		return this.alunoBo;
	}

	public void setAlunoBo(final AlunoBo alunoBo) {

		this.alunoBo = alunoBo;
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

	public List<EstadoCivilDTO> getEstadoCivilList() {

		return this.estadoCivilList;
	}

	public void setEstadoCivilList(final List<EstadoCivilDTO> estadoCivilList) {

		this.estadoCivilList = estadoCivilList;
	}

	public List<EscolaridadeDTO> getEscolaridadeList() {

		return this.escolaridadeList;
	}

	public void setEscolaridadeList(final List<EscolaridadeDTO> escolaridadeList) {

		this.escolaridadeList = escolaridadeList;
	}

	public List<SexoEnum> getSexoList() {

		return this.sexoList;
	}

	public void setSexoList(final List<SexoEnum> sexoList) {

		this.sexoList = sexoList;
	}

	public String getLogin() {

		return this.login;
	}

	public void setLogin(final String login) {

		this.login = login;
	}

	public String getSenha() {

		return this.senha;
	}

	public void setSenha(final String senha) {

		this.senha = senha;
	}

	public Aluno getAluno() {

		return this.aluno;
	}

	public void setAluno(final Aluno aluno) {

		this.aluno = aluno;
	}

	public Long getIdPaisSelecao() {

		return this.idPaisSelecao;
	}

	public void setIdPaisSelecao(final Long idPaisSelecao) {

		this.idPaisSelecao = idPaisSelecao;
	}

	public Long getIdUfSelecao() {

		return this.idUfSelecao;
	}

	public void setIdUfSelecao(final Long idUfSelecao) {

		this.idUfSelecao = idUfSelecao;
	}

	public Long getIdCidadeSelecao() {

		return this.idCidadeSelecao;
	}

	public void setIdCidadeSelecao(final Long idCidadeSelecao) {

		this.idCidadeSelecao = idCidadeSelecao;
	}

	public static long getSerialversionuid() {

		return AtualizarCurriculoAlunoAction.serialVersionUID;
	}

	public Long getIdEstadoCivil() {

		return this.idEstadoCivil;
	}

	public void setIdEstadoCivil(final Long idEstadoCivil) {

		this.idEstadoCivil = idEstadoCivil;
	}

	public Long getIdEscolaridade() {

		return this.idEscolaridade;
	}

	public void setIdEscolaridade(final Long idEscolaridade) {

		this.idEscolaridade = idEscolaridade;
	}

	public Integer getTipoSexo() {

		return this.tipoSexo;
	}

	public void setTipoSexo(final Integer tipoSexo) {

		this.tipoSexo = tipoSexo;
	}

	public List<TipoTelefoneDTO> getTipoTelefoneList() {

		return this.tipoTelefoneList;
	}

	public void setTipoTelefoneList(final List<TipoTelefoneDTO> tipoTelefoneList) {

		this.tipoTelefoneList = tipoTelefoneList;
	}

	public Long getIdTipoTelefone() {

		return this.idTipoTelefone;
	}

	public void setIdTipoTelefone(final Long idTipoTelefone) {

		this.idTipoTelefone = idTipoTelefone;
	}

	public Telefone getTelefone() {

		return this.telefone;
	}

	public void setTelefone(final Telefone telefone) {

		this.telefone = telefone;
	}

	public List<TelefoneDTO> getTelefoneList() {

		return this.telefoneList;
	}

	public void setTelefoneList(final List<TelefoneDTO> telefoneList) {

		this.telefoneList = telefoneList;
	}

	public Integer getIdTelefoneEx() {

		return this.idTelefoneEx;
	}

	public void setIdTelefoneEx(final Integer idTelefoneEx) {

		this.idTelefoneEx = idTelefoneEx;
	}

	public AlunoForm getAlunoForm() {

		return this.alunoForm;
	}

	public void setAlunoForm(final AlunoForm alunoForm) {

		this.alunoForm = alunoForm;
	}

	public List<SimNaoEnum> getSimNaoList() {

		return this.simNaoList;
	}

	public void setSimNaoList(final List<SimNaoEnum> simNaoList) {

		this.simNaoList = simNaoList;
	}

	public Long getIdTipoDeficiencia() {

		return this.idTipoDeficiencia;
	}

	public void setIdTipoDeficiencia(final Long idTipoDeficiencia) {

		this.idTipoDeficiencia = idTipoDeficiencia;
	}

	public List<TipoDeficienciaDTO> getTipoDeficienciaList() {

		return this.tipoDeficienciaList;
	}

	public void setTipoDeficienciaList(final List<TipoDeficienciaDTO> tipoDeficienciaList) {

		this.tipoDeficienciaList = tipoDeficienciaList;
	}

	public List<CargoInteresseDTO> getCargoInteresseList() {

		return this.cargoInteresseList;
	}

	public void setCargoInteresseList(final List<CargoInteresseDTO> cargoInteresseList) {

		this.cargoInteresseList = cargoInteresseList;
	}

	public Long getIdCargoInteresse() {

		return this.idCargoInteresse;
	}

	public void setIdCargoInteresse(final Long idCargoInteresse) {

		this.idCargoInteresse = idCargoInteresse;
	}

	public Long getIdTipoGrau() {

		return this.idTipoGrau;
	}

	public void setIdTipoGrau(final Long idTipoGrau) {

		this.idTipoGrau = idTipoGrau;
	}

	public List<TipoGrauDTO> getTipoGrauList() {

		return this.tipoGrauList;
	}

	public void setTipoGrauList(final List<TipoGrauDTO> tipoGrauList) {

		this.tipoGrauList = tipoGrauList;
	}

	public List<FormacaoAcademicaDTO> getFormacaoList() {

		return this.formacaoList;
	}

	public void setFormacaoList(final List<FormacaoAcademicaDTO> formacaoList) {

		this.formacaoList = formacaoList;
	}

	public FormacaoAcademicaDTO getFormacaoAcademica() {

		return this.formacaoAcademica;
	}

	public void setFormacaoAcademica(final FormacaoAcademicaDTO formacaoAcademica) {

		this.formacaoAcademica = formacaoAcademica;
	}

	public Integer getIndexEcluirFormacao() {

		return this.indexEcluirFormacao;
	}

	public void setIndexEcluirFormacao(final Integer indexEcluirFormacao) {

		this.indexEcluirFormacao = indexEcluirFormacao;
	}


	public List<ExperienciaProfissionalDTO> getExperienciaList() {

		return this.experienciaList;
	}

	public void setExperienciaList(final List<ExperienciaProfissionalDTO> experienciaList) {

		this.experienciaList = experienciaList;
	}

	public ExperienciaProfissionalDTO getExperienciaProfissional() {

		return this.experienciaProfissional;
	}

	public void setExperienciaProfissional(final ExperienciaProfissionalDTO experienciaProfissional) {

		this.experienciaProfissional = experienciaProfissional;
	}

	public Integer getIndexExcluirExperiencia() {

		return this.indexExcluirExperiencia;
	}

	public void setIndexExcluirExperiencia(final Integer indexExcluirExperiencia) {

		this.indexExcluirExperiencia = indexExcluirExperiencia;
	}

	public boolean isAceiteTermoCompromisso() {

		return this.aceiteTermoCompromisso;
	}

	public void setAceiteTermoCompromisso(final boolean aceiteTermoCompromisso) {

		this.aceiteTermoCompromisso = aceiteTermoCompromisso;
	}

}
