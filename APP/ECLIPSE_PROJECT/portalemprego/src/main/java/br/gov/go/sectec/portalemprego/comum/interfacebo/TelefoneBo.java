package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Telefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TelefoneDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface TelefoneBo extends NegocioGenerico<Telefone,Long>{
	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idAluno
	 * @return
	 */
	public List<TelefoneDTO> listarPorIdAluno(Long idAluno);

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idAluno
	 */
	public void removerTodostelefonesPorIdAluno(Long idAluno);
}
