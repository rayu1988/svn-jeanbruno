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
	 * Método responsável por responsável por obter o aluno pelo identificador.
	 * 
	 * @author Silvânio
	 * 
	 * @param idAluno
	 * 
	 * @return Curriculo.
	 */
	public Curriculo obterPorIdAluno(Long idAluno);

	/**
	 * Método responsável por listar o totalizador de Area para a visualização do curriculo.
	 * 
	 * @author Silvânio
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
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idAreaInteresse
	 * @return
	 */
	public Long obterQtdCurriculoPorArea(Long idAreaInteresse);

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
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
	 * Método responsável por
	 * 
	 * @author Silvânio
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
