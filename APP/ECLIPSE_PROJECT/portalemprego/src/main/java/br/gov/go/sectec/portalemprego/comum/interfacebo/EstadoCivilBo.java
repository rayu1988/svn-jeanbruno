package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.EstadoCivil;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EstadoCivilDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface EstadoCivilBo extends NegocioGenerico<EstadoCivil,Long>{
	/**
	 * M�todo respons�vel por listar os estados civil.
	 *
	 * @author Silv�nio
	 *
	 * @return <code>List></code>
	 */
	public List<EstadoCivilDTO> listarEstadoCivil();
}
