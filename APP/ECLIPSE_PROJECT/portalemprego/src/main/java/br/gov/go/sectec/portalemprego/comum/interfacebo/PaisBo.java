package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Pais;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.PaisDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface PaisBo extends NegocioGenerico<Pais,Long>{
	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @return
	 */
	public List<PaisDTO> listarPaisOrdenado();
}
