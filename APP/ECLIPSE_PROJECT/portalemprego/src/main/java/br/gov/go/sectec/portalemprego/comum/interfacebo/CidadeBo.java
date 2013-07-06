package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Cidade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CidadeDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface CidadeBo extends NegocioGenerico<Cidade, Long> {

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idUF
	 * @return
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
