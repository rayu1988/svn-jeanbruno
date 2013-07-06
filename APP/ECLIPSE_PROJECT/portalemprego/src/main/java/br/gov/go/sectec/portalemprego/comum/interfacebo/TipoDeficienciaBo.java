package br.gov.go.sectec.portalemprego.comum.interfacebo;

import java.util.List;

import br.gov.go.sectec.portalemprego.core.entidade.TipoDeficiencia;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoDeficienciaDTO;
import br.gov.go.sectec.portalemprego.comum.interfacebo.NegocioGenerico;

public interface TipoDeficienciaBo extends NegocioGenerico<TipoDeficiencia,Long>{
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
