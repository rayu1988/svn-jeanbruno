package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.Curriculo;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CurriculoAlunoDTO;

public interface CurriculoDao extends PersistenciaGenerica<Curriculo, Long> {

	public Integer obterQtdCurriculoPorCargo_Area(Integer idCargo, Integer idAreaInteresse);

	/**
	 * M�todo respons�vel por obter o curriculo pelo identificador do aluno.
	 * 
	 * @author Silv�nio
	 * 
	 * @param idAluno
	 * 
	 * @return Curriculo.
	 */
	public Curriculo obterPorIdAluno(Long idAluno);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idAreaInteresse
	 * @return
	 */
	public Long obterQtdCurriculoPorArea(Long idAreaInteresse);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idCargo
	 * @return
	 */
	public Long obterQtdCurriculoPorCargo(Long idCargo);

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idCidadeSelecao
	 * 
	 * @param idTipoSexo
	 * 
	 * @param idTipoFaixaEtaria
	 * 
	 * @param idCargo
	 * 
	 * @return
	 */
	public List<CurriculoAlunoDTO> listarCurriculoAluno(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idCargo);
}
