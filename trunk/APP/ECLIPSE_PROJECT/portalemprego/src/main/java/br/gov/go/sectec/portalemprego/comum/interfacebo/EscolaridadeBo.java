package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Escolaridade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EscolaridadeDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface EscolaridadeBo extends NegocioGenerico<Escolaridade,Long>{
	/**
	 * Método responsável por listar escolaridade.
	 *
	 * @author Silvânio
	 *
	 * @return <code>List</code>
	 */
	public List<EscolaridadeDTO> listarEscolaridade();
}
