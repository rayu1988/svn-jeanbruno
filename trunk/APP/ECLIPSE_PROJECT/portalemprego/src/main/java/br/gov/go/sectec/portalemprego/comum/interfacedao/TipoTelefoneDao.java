package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.TipoTelefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoTelefoneDTO;

public interface TipoTelefoneDao extends PersistenciaGenerica<TipoTelefone, Long> {

	public TipoTelefone consultar(Long id);

	public List<TipoTelefone> listar();

	public void incluir(TipoTelefone tipoTelefone);

	public TipoTelefone alterar(TipoTelefone tipoTelefone);

	public void excluir(TipoTelefone tipoTelefone);

	/**
	 * Método responsável por listar Tipos de telefone.
	 * 
	 * @author Silvânio
	 * 
	 * @return lista de telefone.
	 */
	public List<TipoTelefoneDTO> listarTipoTelefone();
}
