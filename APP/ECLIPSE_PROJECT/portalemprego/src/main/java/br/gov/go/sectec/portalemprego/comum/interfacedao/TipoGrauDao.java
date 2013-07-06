package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.TipoGrau;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoGrauDTO;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;

public interface TipoGrauDao extends PersistenciaGenerica<TipoGrau, Long> {
	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @return
	 */
	public List<TipoGrauDTO> listarTipograu();

}
