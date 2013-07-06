package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.TipoTelefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoTelefoneDTO;

public interface TipoTelefoneBo extends NegocioGenerico<TipoTelefone, Long> {

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @return
	 */
	public List<TipoTelefoneDTO> listarTipoTelefone();

}
