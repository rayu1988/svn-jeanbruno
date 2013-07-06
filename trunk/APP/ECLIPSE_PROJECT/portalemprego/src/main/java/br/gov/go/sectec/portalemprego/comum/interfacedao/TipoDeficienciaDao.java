package br.gov.go.sectec.portalemprego.comum.interfacedao;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.TipoDeficiencia;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoDeficienciaDTO;

public interface TipoDeficienciaDao extends PersistenciaGenerica<TipoDeficiencia, Long> {
	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @return
	 */
	public List<TipoDeficienciaDTO> listarTipoDeficiencia();

	/**
	 * M�todo respons�vel por
	 *
	 * @author Silv�nio
	 *
	 * @param idAluno
	 * @return
	 */
	public TipoDeficiencia obterTipoDeficienciaPorIdAluno(Long idAluno);

}
