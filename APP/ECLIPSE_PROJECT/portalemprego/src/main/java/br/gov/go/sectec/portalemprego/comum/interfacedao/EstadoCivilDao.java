package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.EstadoCivil;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EstadoCivilDTO;

public interface EstadoCivilDao extends PersistenciaGenerica<EstadoCivil, Long> {

	/**
	 * M�todo respons�vel por listar os estados civil.
	 * 
	 * @author Silv�nio
	 * 
	 * @return <code>List></code>
	 */
	public List<EstadoCivilDTO> listarEstadoCivil();
}
