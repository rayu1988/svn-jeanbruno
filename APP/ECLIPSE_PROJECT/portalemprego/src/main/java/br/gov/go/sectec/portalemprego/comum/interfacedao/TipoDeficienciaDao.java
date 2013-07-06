package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.TipoDeficiencia;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoDeficienciaDTO;

public interface TipoDeficienciaDao extends PersistenciaGenerica<TipoDeficiencia, Long> {
	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @return
	 */
	public List<TipoDeficienciaDTO> listarTipoDeficiencia();

	/**
	 * Método responsável por
	 *
	 * @author Silvânio
	 *
	 * @param idAluno
	 * @return
	 */
	public TipoDeficiencia obterTipoDeficienciaPorIdAluno(Long idAluno);

}
