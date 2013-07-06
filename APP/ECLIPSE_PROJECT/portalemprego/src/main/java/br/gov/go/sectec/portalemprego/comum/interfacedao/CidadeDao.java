package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;

public interface CidadeDao extends PersistenciaGenerica<Cidade, Long> {

	/**
	 * M�todo respons�vel por obter a cidade pelo id de uf.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idUF
	 *            identificador uf.
	 * 
	 * @return lida de uf.
	 */
	public List<CidadeDTO> obterCidadePorIdUf(Long idUF);

	/**
	 * M�todo respons�vel por sigla do estado
	 * 
	 * @author Silv�nio
	 * 
	 * @param string
	 * 
	 * @return List<CidadeDTO>
	 */
	public List<CidadeDTO> obterCidadePorSiglaUf(String siglaUf);
	
}
