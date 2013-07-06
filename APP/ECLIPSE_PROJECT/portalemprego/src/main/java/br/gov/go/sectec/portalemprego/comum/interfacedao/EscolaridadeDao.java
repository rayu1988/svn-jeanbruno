package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Escolaridade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EscolaridadeDTO;

public interface EscolaridadeDao extends PersistenciaGenerica<Escolaridade, Long> {
	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @return
	 */
	public List<EscolaridadeDTO> listarEscolaridade();
}
