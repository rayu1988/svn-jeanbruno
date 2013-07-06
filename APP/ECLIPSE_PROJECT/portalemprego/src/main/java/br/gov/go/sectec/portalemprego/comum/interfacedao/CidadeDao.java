package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;

public interface CidadeDao extends PersistenciaGenerica<Cidade, Long> {

	/**
	 * Método responsável por obter a cidade pelo id de uf.
	 * 
	 * @author Silvânio
	 * 
	 * @param idUF
	 *            identificador uf.
	 * 
	 * @return lida de uf.
	 */
	public List<CidadeDTO> obterCidadePorIdUf(Long idUF);

	/**
	 * Método responsável por sigla do estado
	 * 
	 * @author Silvânio
	 * 
	 * @param string
	 * 
	 * @return List<CidadeDTO>
	 */
	public List<CidadeDTO> obterCidadePorSiglaUf(String siglaUf);
	
}
