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
	 * M�todo respons�vel por listar os paises do BD
	 * 
	 * @author Joffre
	 * 
	 * @return
	 */
	public List<PaisDTO> listarPais();

	/**
	 * 
	 * M�todo respons�vel por retornar o pa�s do ID passado como parametro
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
	 * M�todo respons�vel por retornar as UFs do pa�s passado por parametro
	 * 
	 * @author Joffre
	 * 
	 * @param idPais
	 *            identificador do pa�s
	 * @return
	 */
	public List<UfDTO> obterUfPorIdPais(Long idPais);

	/**
	 * 
	 * M�todo respons�vel por retornar a lista de cidades da UF passada por parametro
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
	 * M�todo respons�vel por retornar a cidade do ID passado por parametro
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
	 * M�todo respons�vel por obter o Tipo de Telefone pelo ID
	 * 
	 * @author Joffre
	 * 
	 * @param idTipoTelefone
	 *            identificador do tipo de telefone
	 * @return
	 */
	public TipoTelefone obterTipoTelefonePorId(Long idTipoTelefone);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param login
	 * 
	 * @param senha
	 * 
	 * @return
	 */
	public Aluno obterAlunoPorloginSenha(String login, String senha);

	/**
	 * M�todo respons�vel por listar os estados civil.
	 * 
	 * @author Silv�nio
	 * 
	 * @return <code>List></code>
	 */
	public List<EstadoCivilDTO> listarEstadoCivil();

	/**
	 * M�todo respons�vel por listar escolaridade.
	 * 
	 * @author Silv�nio
	 * 
	 * @return <code>List</code>
	 */
	public List<EscolaridadeDTO> listarEscolatidade();

	/**
	 * M�todo respons�vel por listar os tipos de telefone.
	 * 
	 * @author Silv�nio
	 * 
	 * @return <code>List</code>
	 */
	public List<TipoTelefoneDTO> listarTipoTelefone();

	/**
	 * M�todo respons�vel por listar telefone pelo id do aluno.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idAluno
	 * 
	 * @return
	 */
	public List<TelefoneDTO> listarTelefonePorIdAluno(Long idAluno);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @return
	 */
	public List<TipoDeficienciaDTO> listarTIpoDeficiencia();

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @return
	 */
	public List<CargoInteresseDTO> listarCargoInteresse();

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idAluno
	 * @return
	 */
	public TipoDeficiencia obterTipoDeficienciaPorIdAluno(Long idAluno);

	/**
	 * M�todo respons�vel por obter o estado civil pelo identificador.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idEstadoCivil
	 * 
	 * @return
	 */
	public EstadoCivil obterEstadoCivilPorId(Long idEstadoCivil);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idEscolaridade
	 * @return
	 */
	public Escolaridade obterEscolaridadePorId(Long idEscolaridade);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idTipoDeficiencia
	 * @return
	 */
	public TipoDeficiencia obterTipoDeficienciaPorId(Long idTipoDeficiencia);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idCargoInteresse
	 * 
	 * @return
	 */
	public CargoInteresse obterCargoInteressePorId(Long idCargoInteresse);

	/**
	 * M�todo respons�vel por validar os dados pessoais do aluno.
	 * 
	 * @author Silv�nio
	 * 
	 * @param aluno
	 */
	public void validarDadosPessoais(Aluno aluno);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @return
	 */
	public List<TipoGrauDTO> listarTipoGrau();

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idGrau
	 * @return
	 */
	public TipoGrau obterTipoGrauPorid(Long idGrau);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idAluno
	 * @return
	 */
	public CargoInteresse obterCargoInteressePorIdAluno(Long idAluno);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param aluno
	 * @param formacaoList
	 * @param experienciaList
	 */
	public void salvarCurriculo(Aluno aluno, List<FormacaoAcademicaDTO> formacaoList, List<ExperienciaProfissionalDTO> experienciaList);

	/**
	 * M�todo respons�vel por obter lista de formacao academia por Id aluno.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idAluno
	 * 
	 * @return lista FormacaoAcademica.
	 */
	public List<FormacaoAcademicaDTO> listarFormacaoPorIdAluno(Long idAluno);

	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @param idAluno
	 * 
	 * @return
	 */
	public List<ExperienciaProfissionalDTO> listarExperienciaPorIdAluno(Long idAluno);


}
