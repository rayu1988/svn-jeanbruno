package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.TipoGrau;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoGrauDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface TipoGrauBo extends NegocioGenerico<TipoGrau,Long>{
	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @return
	 */
	public List<TipoGrauDTO> listarTipograu();
}
