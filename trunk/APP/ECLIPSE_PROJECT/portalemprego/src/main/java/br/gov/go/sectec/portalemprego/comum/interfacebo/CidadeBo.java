package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface CidadeBo extends NegocioGenerico<Cidade, Long> {

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idUF
	 * @return
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
