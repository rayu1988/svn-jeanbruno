package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Uf;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.UfDTO;

public interface UfDao extends PersistenciaGenerica<Uf, Long> {
	/**
	 * Método responsável por retornar lista de ufs.
	 * 
	 * @author Silvânio
	 * 
	 * @param idPais
	 *            identificador do pais.
	 * 
	 * @return lista de uf.
	 */
	public List<UfDTO> obterPorIdPais(Long idPais);

}
