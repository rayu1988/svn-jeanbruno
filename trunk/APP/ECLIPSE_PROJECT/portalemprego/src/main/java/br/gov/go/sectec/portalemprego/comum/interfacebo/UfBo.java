package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Uf;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.UfDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface UfBo extends NegocioGenerico<Uf,Long>{
	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idPais
	 * @return
	 */
	public List<UfDTO> obterPorIdPais(Long idPais);
}
