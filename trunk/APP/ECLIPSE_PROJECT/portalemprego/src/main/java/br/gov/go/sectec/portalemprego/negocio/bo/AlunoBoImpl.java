package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.exception.NegocioException;
import br.gov.go.sectec.portalemprego.comum.interfacebo.AlunoBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.CargoInteresseBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.CidadeBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.CurriculoBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.EscolaridadeBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.EstadoCivilBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.ExperienciaProfissionalBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.FormacaoAcademicaBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.PaisBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.TelefoneBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.TipoDeficienciaBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.TipoGrauBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.TipoTelefoneBo;
import br.gov.go.sectec.portalemprego.comum.interfacebo.UfBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.AlunoDao;
import br.gov.go.sectec.portalemprego.comum.utilitario.CpfCnpjUtil;
import br.gov.go.sectec.portalemprego.comum.utilitario.DataUtil;
import br.gov.go.sectec.portalemprego.comum.utilitario.MensagemNegocioUtils;
import br.gov.go.sectec.portalemprego.comum.utilitario.MoedaUtil;
import br.gov.go.sectec.portalemprego.comum.utilitario.ValidatorUtil;
import br.gov.go.sectec.portalemprego.core.entidade.Aluno;
import br.gov.go.sectec.portalemprego.core.entidade.CargoInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.Curriculo;
import br.gov.go.sectec.portalemprego.core.entidade.Escolaridade;
import br.gov.go.sectec.portalemprego.core.entidade.EstadoCivil;
import br.gov.go.sectec.portalemprego.core.entidade.ExperienciaProfissional;
import br.gov.go.sectec.portalemprego.core.entidade.FormacaoAcademica;
import br.gov.go.sectec.portalemprego.core.entidade.Pais;
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

/**
 * 
 * <p>
 * <b>Title:</b> AlunoBoImpl.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe de negocio
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
@Service("alunoBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AlunoBoImpl extends PremiumBOImpl<Aluno, Long> implements AlunoBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AlunoDao dao;

	@Autowired
	private PaisBo paisBo;

	@Autowired
	private UfBo ufBo;

	@Autowired
	private CidadeBo cidadeBo;

	@Autowired
	private TipoTelefoneBo tipoTelefoneBo;

	@Autowired
	private EstadoCivilBo estadoCivilBo;

	@Autowired
	private EscolaridadeBo escolaridadeBo;

	@Autowired
	private TelefoneBo telefoneBo;

	@Autowired
	private TipoDeficienciaBo tipoDeficienciaBo;

	@Autowired
	private CargoInteresseBo cargoInteresseBo;

	@Autowired
	private TipoGrauBo tipoGrauBo;

	@Autowired
	private CurriculoBo curriculoBo;

	@Autowired
	private ExperienciaProfissionalBo experienciaBo;
	
	@Autowired
	private FormacaoAcademicaBo formacaoBo;

	@Override
	public List<PaisDTO> listarPais() {

		return this.paisBo.listarPaisOrdenado();
	}

	@Override
	public Pais obterPaisPorId(final Long idPais) {

		return this.paisBo.consultar(idPais);
	}

	@Override
	public List<UfDTO> obterUfPorIdPais(final Long idPais) {

		return this.ufBo.obterPorIdPais(idPais);
	}

	@Override
	public List<CidadeDTO> obterCidadePorIdUf(final Long idUF) {

		return this.cidadeBo.obterCidadePorIdUf(idUF);
	}

	@Override
	public Cidade obterCidadePorId(final Long idCidade) {

		return this.cidadeBo.consultar(idCidade);
	}

	@Override
	AlunoDao getDAO() {

		return this.dao;
	}

	@Override
	public TipoTelefone obterTipoTelefonePorId(final Long idTipoTelefone) {

		return this.tipoTelefoneBo.consultar(idTipoTelefone);
	}

	@Override
	public void validarDadosPessoais(final Aluno aluno) {

		this.validarDadosPessoaisObrigatorios(aluno);

		this.validarDados(aluno);

	}

	/**
	 * 
	 * Método responsável por validar se os dados informados são válidos
	 * 
	 * @author Joffre
	 * 
	 * @param aluno
	 */
	private void validarDados(final Aluno aluno) {

		if (!CpfCnpjUtil.isValid(aluno.getNuCpf())) {
			throw new NegocioException("O CPF " + aluno.getNuCpf() + " é inválido");
		}
	}

	/**
	 * 
	 * Método responsável por validar se os dados obrigatórios foram preenchidos
	 * 
	 * @author Joffre
	 * 
	 * @param aluno
	 */
	private void validarDadosPessoaisObrigatorios(final Aluno aluno) {

		final MensagemNegocioUtils msgUtils = new MensagemNegocioUtils();

		msgUtils.validar(aluno.getDsNome(), "O campo Nome é obrigatório.");

		msgUtils.validar(aluno.getDtNascimento(), "O campo Dt. Nascimento é obrigatório.");

		msgUtils.validar(aluno.getTpSexo(), "O campo Sexo é obrigatório.");

		if (!aluno.getEndereco().isTodosPreenchidos()) {

			msgUtils.add("Dados obrigatórios de endereço não informados.");

		}

		if (msgUtils.validarEntidade(aluno.getEscolaridade(), "O campo Escolaridade é obrigatório.")) {

			msgUtils.validar(aluno.getEscolaridade().getIdEscolaridade(), "O campo Escolaridade é obrigatório.");

		}

		msgUtils.validar(aluno.getNuCpf(), "O campo CPF é obrigatório.");

		msgUtils.validar(aluno.getTelefones(), "O Campo telefone é obrigatório");

		msgUtils.execute();

	}

	public void incluir(final Aluno aluno) {

		this.validarDadosPessoaisObrigatorios(aluno);

		this.validarDados(aluno);

		this.incluir(aluno);
	}

	@Override
	public Aluno obterAlunoPorloginSenha(final String login, String senha) {

		this.validarDadosLogin(login, senha);

		senha = this.criptografarSenha(senha);

		final Aluno aluno = this.getDAO().obterAlunoPorLoginSenha(login, senha);

		if (ValidatorUtil.isNull(aluno)) {

			throw new NegocioException("Usuario e/ou Senha inválido(s)");

		}

		return aluno;
	}

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param login
	 * @param senha
	 */
	private void validarDadosLogin(final String login, final String senha) {

		if (ValidatorUtil.isBlank(login) || ValidatorUtil.isBlank(senha)) {

			throw new NegocioException("Usuario e Senha obrigatório");

		}
	}

	/**
	 * Método responsável por criotografar senha
	 * 
	 * @author Silvânio
	 * 
	 * @param senha
	 * 
	 * @return <code>String</code>
	 */
	private String criptografarSenha(final String senha) {

		return DigestUtils.md5Hex(senha);
	}

	/**
	 * Método responsável por listar os estados civil.
	 * 
	 * @author Silvânio
	 * 
	 * @return <code>List></code>
	 */
	@Override
	public List<EstadoCivilDTO> listarEstadoCivil() {

		return this.estadoCivilBo.listarEstadoCivil();
	}

	@Override
	public List<TelefoneDTO> listarTelefonePorIdAluno(final Long idAluno) {

		return this.telefoneBo.listarPorIdAluno(idAluno);
	}

	/**
	 * Método responsável por listar escolaridade.
	 * 
	 * @author Silvânio
	 * 
	 * @return <code>List</code>
	 */
	@Override
	public List<EscolaridadeDTO> listarEscolatidade() {

		return this.escolaridadeBo.listarEscolaridade();
	}

	@Override
	public List<TipoTelefoneDTO> listarTipoTelefone() {

		return this.tipoTelefoneBo.listarTipoTelefone();
	}

	@Override
	public List<TipoDeficienciaDTO> listarTIpoDeficiencia() {

		return this.tipoDeficienciaBo.listarTipoDeficiencia();
	}

	@Override
	public List<CargoInteresseDTO> listarCargoInteresse() {

		return this.cargoInteresseBo.listarCargoInteresse();
	}

	@Override
	public TipoDeficiencia obterTipoDeficienciaPorIdAluno(final Long idAluno) {

		return this.tipoDeficienciaBo.obterTipoDeficienciaPorIdAluno(idAluno);
	}

	@Override
	public EstadoCivil obterEstadoCivilPorId(final Long idEstadoCivil) {

		return this.estadoCivilBo.consultar(idEstadoCivil);
	}

	@Override
	public Escolaridade obterEscolaridadePorId(final Long idEscolaridade) {

		return this.escolaridadeBo.consultar(idEscolaridade);
	}

	@Override
	public TipoDeficiencia obterTipoDeficienciaPorId(final Long idTipoDeficiencia) {

		if (ValidatorUtil.isNull(idTipoDeficiencia)) {

			return null;

		}

		return this.tipoDeficienciaBo.consultar(idTipoDeficiencia);
	}

	@Override
	public CargoInteresse obterCargoInteressePorId(final Long idCargoInteresse) {

		if (ValidatorUtil.isNull(idCargoInteresse)) {

			return null;

		}

		return this.cargoInteresseBo.consultar(idCargoInteresse);
	}

	@Override
	public List<TipoGrauDTO> listarTipoGrau() {

		return this.tipoGrauBo.listarTipograu();
	}

	@Override
	public TipoGrau obterTipoGrauPorid(final Long idGrau) {

		return this.tipoGrauBo.consultar(idGrau);
	}

	@Override
	public CargoInteresse obterCargoInteressePorIdAluno(final Long idAluno) {

		if (ValidatorUtil.isNull(idAluno)) {

			return null;

		}
		return this.cargoInteresseBo.obterCargoInteressePorIdAluno(idAluno);
	}

	@Override
	public void salvarCurriculo(final Aluno aluno, final List<FormacaoAcademicaDTO> formacaoList, final List<ExperienciaProfissionalDTO> experienciaList) {

		Curriculo curriculo = this.curriculoBo.obterPorIdAluno(aluno.getIdAluno());

		if (ValidatorUtil.isNull(curriculo)) {

			curriculo = new Curriculo();

		}
		
		aluno.setIsAtivo(true);
		
		curriculo.setAluno(aluno);

		experienciaBo.removerTodosPorIdAluno(aluno.getIdAluno());
		
		formacaoBo.removerTodosPorIdAluno(aluno.getIdAluno());
		
		telefoneBo.removerTodostelefonesPorIdAluno(aluno.getIdAluno());
		
		this.adicionarFormacaoAcademica(curriculo, formacaoList);

		this.adicionarExperienciaProfissional(curriculo, experienciaList);
		
		aluno.setNuCpf(aluno.getNuCpf().replace(".", "").replace("-", ""));
		
		this.getDAO().alterar(aluno);

		this.curriculoBo.alterar(curriculo);

	}

	/**
	 * Método responsável por adicionar experiencias profissionais no curriculo.
	 * 
	 * @author Silvânio
	 * 
	 * @param curriculo
	 * 
	 * @param experienciaList
	 */
	private void adicionarExperienciaProfissional(final Curriculo curriculo, final List<ExperienciaProfissionalDTO> experienciaList) {

		curriculo.setExperienciaProfissionals(new ArrayList<ExperienciaProfissional>());

		for (final ExperienciaProfissionalDTO dto : experienciaList) {

			final ExperienciaProfissional experiencia = new ExperienciaProfissional();

			experiencia.setIdExperienciaProfissional(dto.getIdDTO());

			experiencia.setDsCargo(dto.getDsCargo());

			experiencia.setDsEmpresa(dto.getDsEmpresaDTO());

			experiencia.setDsFuncao(dto.getDsFuncaoDTO());

			experiencia.setDtDataEntrada(DataUtil.obterData(dto.getDataEntradaStr()));

			experiencia.setDtDataSaida(DataUtil.obterData(dto.getDataSaidaStr()));

			experiencia.setCurriculo(curriculo);

			curriculo.getExperienciaProfissionals().add(experiencia);

		}

	}

	/**
	 * Método responsável por adicionar a lista de formação academica na entidade currículo.
	 * 
	 * @author Silvânio
	 * 
	 * @param aluno
	 * 
	 * @param formacaoList
	 */
	private void adicionarFormacaoAcademica(final Curriculo curriculo, final List<FormacaoAcademicaDTO> formacaoList) {

		curriculo.setFormacaoAcademicas(new ArrayList<FormacaoAcademica>());

		for (final FormacaoAcademicaDTO dto : formacaoList) {

			final FormacaoAcademica formacao = new FormacaoAcademica();

			formacao.setIdFormacaoAcademica(dto.getIdDTO());

			formacao.setDsCurso(dto.getCurso());

			formacao.setDsInstituicao(dto.getInstituicao());

			formacao.setDtFimCurso(DataUtil.obterData(dto.getDtFim()));

			formacao.setDtInicioCurso(DataUtil.obterData(dto.getDtInicio()));

			final TipoGrau tipoGrau = this.obterTipoGrauPorid(dto.getIdGrau());

			formacao.setTipoGrau(tipoGrau);

			formacao.setCurriculo(curriculo);

			curriculo.getFormacaoAcademicas().add(formacao);

		}

	}

	@Override
	public List<FormacaoAcademicaDTO> listarFormacaoPorIdAluno(Long idAluno) {

		return formacaoBo.listarFormacaoPorIdAluno(idAluno);
	}

	@Override
	public List<ExperienciaProfissionalDTO> listarExperienciaPorIdAluno(Long idAluno) {

		return experienciaBo.listarExperienciaPorIdAluno(idAluno);
	}

}
