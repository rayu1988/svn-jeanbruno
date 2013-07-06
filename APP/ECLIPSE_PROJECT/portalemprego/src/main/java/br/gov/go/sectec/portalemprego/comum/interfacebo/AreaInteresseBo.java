package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.AreaInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.AreaInteresseDTO;

/**
 * 
 * <p>
 * <b>Title:</b> AreaInteresseBo.java
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
public interface AreaInteresseBo extends NegocioGenerico<AreaInteresse, Long> {

	public List<AreaInteresseDTO> listarAreaInteresseOrdenada();

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
	 * @param idAreaInteresSelecao
	 * 
	 * @return
	 */
	public List<AreaInteresseDTO> ListarAreaInteresse(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idAreaInteresSelecao);
}
