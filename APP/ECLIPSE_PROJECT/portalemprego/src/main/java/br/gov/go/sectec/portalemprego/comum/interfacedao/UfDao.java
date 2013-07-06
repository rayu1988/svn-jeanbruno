package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Uf;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.UfDTO;

public interface UfDao extends PersistenciaGenerica<Uf, Long> {
	/**
	 * M�todo respons�vel por retornar lista de ufs.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idPais
	 *            identificador do pais.
	 * 
	 * @return lista de uf.
	 */
	public List<UfDTO> obterPorIdPais(Long idPais);

}
