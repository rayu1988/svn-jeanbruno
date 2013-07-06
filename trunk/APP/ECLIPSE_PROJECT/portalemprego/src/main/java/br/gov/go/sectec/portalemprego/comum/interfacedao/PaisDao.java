package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Pais;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.PaisDTO;

public interface PaisDao extends PersistenciaGenerica<Pais, Long> {

	public Pais consultar(Long id);

	public List<Pais> listar();

	public void incluir(Pais pais);

	public Pais alterar(Pais pais);

	public void excluir(Pais pais);

	/**
	 * Método responsável por listar pais ordenado pelo nome.
	 * 
	 * @author Silvânio
	 * 
	 * @return Lista de paises.
	 */
	public List<PaisDTO> listarPaisOrdenado();
}
