package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.ExperienciaProfissional;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.ExperienciaProfissionalDTO;

public interface ExperienciaProfissionalDao extends PersistenciaGenerica<ExperienciaProfissional, Long> {

	public List<ExperienciaProfissionalDTO> listarExperienciaProfissional();

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idAluno
	 * 
	 * @return
	 */
	public List<ExperienciaProfissionalDTO> listarExperienciaPorIdAluno(Long idAluno);

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idAluno
	 * @return
	 */
	public void removerTodosPorIdAluno(Long idAluno);
}
