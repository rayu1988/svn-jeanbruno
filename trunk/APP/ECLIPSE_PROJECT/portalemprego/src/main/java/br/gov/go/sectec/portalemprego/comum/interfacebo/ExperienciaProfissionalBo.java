package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.ExperienciaProfissional;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.ExperienciaProfissionalDTO;

public interface ExperienciaProfissionalBo extends NegocioGenerico<ExperienciaProfissional, Long> {

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idAluno
	 * @return
	 */
	List<ExperienciaProfissionalDTO> listarExperienciaPorIdAluno(Long idAluno);

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idAluno
	 */
	void removerTodosPorIdAluno(Long idAluno);
}
