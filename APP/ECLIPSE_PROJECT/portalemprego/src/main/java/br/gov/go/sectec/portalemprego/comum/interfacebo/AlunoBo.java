package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Aluno;
import br.gov.go.sectec.portalemprego.core.entidade.CargoInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.Escolaridade;
import br.gov.go.sectec.portalemprego.core.entidade.EstadoCivil;
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

public interface AlunoBo extends NegocioGenerico<Aluno, Long> {

	/**
	 * 
	 * Método responsável por listar os paises do BD
	 * 
	 * @author Joffre
	 * 
	 * @return
	 */
	public List<PaisDTO> listarPais();

	/**
	 * 
	 * Método responsável por retornar o país do ID passado como parametro
	 * 
	 * @author Joffre
	 * 
	 * @param idPais
	 *            identificador do pais
	 * @return
	 */
	public Pais obterPaisPorId(Long idPais);

	/**
	 * 
	 * Método responsável por retornar as UFs do país passado por parametro
	 * 
	 * @author Joffre
	 * 
	 * @param idPais
	 *            identificador do país
	 * @return
	 */
	public List<UfDTO> obterUfPorIdPais(Long idPais);

	/**
	 * 
	 * Método responsável por retornar a lista de cidades da UF passada por parametro
	 * 
	 * @author Joffre
	 * 
	 * @param idUF
	 *            identificador da Unidade Federativa
	 * @return
	 */
	public List<CidadeDTO> obterCidadePorIdUf(Long idUF);

	/**
	 * 
	 * Método responsável por retornar a cidade do ID passado por parametro
	 * 
	 * @author Joffre
	 * 
	 * @param idCidade
	 *            identificador da cidade
	 * @return
	 */
	public Cidade obterCidadePorId(Long idCidade);

	/**
	 * 
	 * Método responsável por obter o Tipo de Telefone pelo ID
	 * 
	 * @author Joffre
	 * 
	 * @param idTipoTelefone
	 *            identificador do tipo de telefone
	 * @return
	 */
	public TipoTelefone obterTipoTelefonePorId(Long idTipoTelefone);

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param login
	 * 
	 * @param senha
	 * 
	 * @return
	 */
	public Aluno obterAlunoPorloginSenha(String login, String senha);

	/**
	 * Método responsável por listar os estados civil.
	 * 
	 * @author Silvânio
	 * 
	 * @return <code>List></code>
	 */
	public List<EstadoCivilDTO> listarEstadoCivil();

	/**
	 * Método responsável por listar escolaridade.
	 * 
	 * @author Silvânio
	 * 
	 * @return <code>List</code>
	 */
	public List<EscolaridadeDTO> listarEscolatidade();

	/**
	 * Método responsável por listar os tipos de telefone.
	 * 
	 * @author Silvânio
	 * 
	 * @return <code>List</code>
	 */
	public List<TipoTelefoneDTO> listarTipoTelefone();

	/**
	 * Método responsável por listar telefone pelo id do aluno.
	 * 
	 * @author Silvânio
	 * 
	 * @param idAluno
	 * 
	 * @return
	 */
	public List<TelefoneDTO> listarTelefonePorIdAluno(Long idAluno);

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @return
	 */
	public List<TipoDeficienciaDTO> listarTIpoDeficiencia();

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @return
	 */
	public List<CargoInteresseDTO> listarCargoInteresse();

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idAluno
	 * @return
	 */
	public TipoDeficiencia obterTipoDeficienciaPorIdAluno(Long idAluno);

	/**
	 * Método responsável por obter o estado civil pelo identificador.
	 * 
	 * @author Silvânio
	 * 
	 * @param idEstadoCivil
	 * 
	 * @return
	 */
	public EstadoCivil obterEstadoCivilPorId(Long idEstadoCivil);

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idEscolaridade
	 * @return
	 */
	public Escolaridade obterEscolaridadePorId(Long idEscolaridade);

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idTipoDeficiencia
	 * @return
	 */
	public TipoDeficiencia obterTipoDeficienciaPorId(Long idTipoDeficiencia);

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idCargoInteresse
	 * 
	 * @return
	 */
	public CargoInteresse obterCargoInteressePorId(Long idCargoInteresse);

	/**
	 * Método responsável por validar os dados pessoais do aluno.
	 * 
	 * @author Silvânio
	 * 
	 * @param aluno
	 */
	public void validarDadosPessoais(Aluno aluno);

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @return
	 */
	public List<TipoGrauDTO> listarTipoGrau();

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idGrau
	 * @return
	 */
	public TipoGrau obterTipoGrauPorid(Long idGrau);

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idAluno
	 * @return
	 */
	public CargoInteresse obterCargoInteressePorIdAluno(Long idAluno);

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param aluno
	 * @param formacaoList
	 * @param experienciaList
	 */
	public void salvarCurriculo(Aluno aluno, List<FormacaoAcademicaDTO> formacaoList, List<ExperienciaProfissionalDTO> experienciaList);

	/**
	 * Método responsável por obter lista de formacao academia por Id aluno.
	 * 
	 * @author Silvânio
	 * 
	 * @param idAluno
	 * 
	 * @return lista FormacaoAcademica.
	 */
	public List<FormacaoAcademicaDTO> listarFormacaoPorIdAluno(Long idAluno);

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idAluno
	 * 
	 * @return
	 */
	public List<ExperienciaProfissionalDTO> listarExperienciaPorIdAluno(Long idAluno);


}
