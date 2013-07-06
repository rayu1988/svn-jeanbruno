package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.RamoAtividade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.RamoAtividadeDTO;
/**
 * <p>
 * <b>Title:</b> RamoAtividadeBo.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> interface de negocio para RamoAtividade
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
public interface RamoAtividadeBo extends NegocioGenerico<RamoAtividade,Long>{

	/**
	 * Método responsável por listar os dados do ramo ordenado.
	 *
	 * @author Silvânio
	 *
	 * @return lista de ramo atividade.
	 */
	List<RamoAtividadeDTO> listarOrdenado();
}
