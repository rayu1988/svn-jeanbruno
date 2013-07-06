package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.FormacaoAcademica;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.FormacaoAcademicaDTO;

public interface FormacaoAcademicaDao extends PersistenciaGenerica<FormacaoAcademica, Long> {

	public List<FormacaoAcademicaDTO> listarFormacaoAcademica();

	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @param idAluno
	 * @return
	 */
	public List<FormacaoAcademicaDTO> listarFormacaoPorIdAluno(Long idAluno);

	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @param idAluno
	 */
	public void removerTodosPorIdAluno(Long idAluno);
}
