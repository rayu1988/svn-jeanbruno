package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.FormacaoAcademica;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.FormacaoAcademicaDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface FormacaoAcademicaBo extends NegocioGenerico<FormacaoAcademica,Long>{
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
