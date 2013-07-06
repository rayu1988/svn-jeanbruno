package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.CargoInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CargoInteresseDTO;

public interface CargoInteresseBo extends NegocioGenerico<CargoInteresse, Long> {

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @return
	 */
	public List<CargoInteresseDTO> listarCargoInteresse();

	/**
	 * M�todo respons�vel por
	 * 
	 * @author Silv�nio
	 * 
	 * @param idAluno
	 * @return
	 */
	public CargoInteresse obterCargoInteressePorIdAluno(Long idAluno);

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
	public List<CargoInteresseDTO> listarCurriculoCargo(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idArea);
}
