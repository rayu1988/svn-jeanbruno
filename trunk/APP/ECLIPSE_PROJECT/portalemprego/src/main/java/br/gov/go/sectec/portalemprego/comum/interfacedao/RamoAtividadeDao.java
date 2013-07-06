package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.RamoAtividade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.RamoAtividadeDTO;

public interface RamoAtividadeDao extends PersistenciaGenerica<RamoAtividade, Long> {

	/**
	 * M�todo respons�vel por lsitar os ramos ordenados
	 *
	 * @author Silv�nio
	 *
	 * @return lista de RamoAtividadeDTO.
	 */
	List<RamoAtividadeDTO> listarOrdenado();
}
