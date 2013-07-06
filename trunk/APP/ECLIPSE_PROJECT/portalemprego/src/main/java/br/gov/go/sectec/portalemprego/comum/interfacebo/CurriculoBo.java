package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;
import java.util.Map;

import br.gov.go.sectec.portalemprego.core.entidade.Curriculo;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.AreaInteresseDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CurriculoAlunoDTO;

/**
 * 
 * <p>
 * <b>Title:</b> CurriculoBo.java
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
 * @author Joffre
 * 
 * @version 1.0.0
 */
public interface CurriculoBo extends NegocioGenerico<Curriculo, Long> {

	public List<CidadeDTO> obterCidadePorSiglaUf(String siglaUf);

	public List<AreaInteresseDTO> ListarAreaInteresse();

	/**
	 * M�todo respons�vel por respons�vel por obter o aluno pelo identificador.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idAluno
	 * 
	 * @return Curriculo.
	 */
	public Curriculo obterPorIdAluno(Long idAluno);

	/**
	 * M�todo respons�vel por listar o totalizador de Area para a visualiza��o do curriculo.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idCidadeSelecao
	 * 
	 * @param idTipoSexo
	 * 
	 * @param idTipoFaixaEtaria
	 * 
	 * @param idAreaInteresSelecao
	 * 
	 * @return <code>List</code>
	 */
	public Map<String, Object> listarCurriculoArea(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idAreaInteresSelecao);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idAreaInteresse
	 * @return
	 */
	public Long obterQtdCurriculoPorArea(Long idAreaInteresse);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idCidadeSelecao
	 * 
	 * @param idTipoSexo
	 * 
	 * @param idTipoFaixaEtaria
	 * 
	 * @param idArea
	 * 
	 * @return
	 */
	public Map<String, Object> listarCurriculoCargo(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idArea);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idCidadeSelecao
	 * 
	 * @param idTipoSexo
	 * 
	 * @param idTipoFaixaEtaria
	 * 
	 * @param idCargo
	 * 
	 * @return
	 */
	public List<CurriculoAlunoDTO> listarCurriculoAluno(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idCargo);

}
