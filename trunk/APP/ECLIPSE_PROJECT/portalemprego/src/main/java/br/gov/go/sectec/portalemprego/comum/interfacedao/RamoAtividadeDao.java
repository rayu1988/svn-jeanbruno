package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.RamoAtividade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.RamoAtividadeDTO;

public interface RamoAtividadeDao extends PersistenciaGenerica<RamoAtividade, Long> {

	/**
	 * Método responsável por lsitar os ramos ordenados
	 *
	 * @author Silvânio
	 *
	 * @return lista de RamoAtividadeDTO.
	 */
	List<RamoAtividadeDTO> listarOrdenado();
}
