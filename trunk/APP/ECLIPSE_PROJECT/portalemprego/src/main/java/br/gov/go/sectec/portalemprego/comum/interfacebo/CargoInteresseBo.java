package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.CargoInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.CargoInteresseDTO;

public interface CargoInteresseBo extends NegocioGenerico<CargoInteresse, Long> {

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @return
	 */
	public List<CargoInteresseDTO> listarCargoInteresse();

	/**
	 * Método responsável por
	 * 
	 * @author Silvânio
	 * 
	 * @param idAluno
	 * @return
	 */
	public CargoInteresse obterCargoInteressePorIdAluno(Long idAluno);

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
	public List<CargoInteresseDTO> listarCurriculoCargo(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idArea);
}
