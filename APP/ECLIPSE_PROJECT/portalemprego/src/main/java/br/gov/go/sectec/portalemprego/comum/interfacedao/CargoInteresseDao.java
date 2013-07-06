package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.CargoInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CargoInteresseDTO;

public interface CargoInteresseDao extends PersistenciaGenerica<CargoInteresse, Long> {

	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @return
	 */
	List<CargoInteresseDTO> listarCargoInteresse();

	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @param idAluno
	 * @return
	 */
	CargoInteresse obterCargoInteressePorIdAluno(Long idAluno);

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
	 * @param idArea
	 * 
	 * @return
	 */
	List<CargoInteresseDTO> listarCurriculoCargo(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idArea);
}
