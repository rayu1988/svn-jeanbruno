package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.CargoInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CargoInteresseDTO;

public interface CargoInteresseDao extends PersistenciaGenerica<CargoInteresse, Long> {

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @return
	 */
	List<CargoInteresseDTO> listarCargoInteresse();

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idAluno
	 * @return
	 */
	CargoInteresse obterCargoInteressePorIdAluno(Long idAluno);

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idCidadeSelecao
	 * 
	 * @param idTipoSexo
	 *
	 * @param idTipoFaixaEtaria
	 * 
	 * @param idArea
	 * 
	 * @return
	 */
	List<CargoInteresseDTO> listarCurriculoCargo(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idArea);
}
