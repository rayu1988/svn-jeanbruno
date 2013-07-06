package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.TipoDeficienciaBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.comum.interfacedao.TipoDeficienciaDao;
import br.gov.go.sectec.portalemprego.core.entidade.TipoDeficiencia;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoDeficienciaDTO;

@Service("tipoDeficienciaBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class TipoDeficienciaBoImpl extends PremiumBOImpl<TipoDeficiencia, Long> implements TipoDeficienciaBo {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TipoDeficienciaDao tipoDeficienciaDao;

	@Override
	public List<TipoDeficienciaDTO> listarTipoDeficiencia() {

		return tipoDeficienciaDao.listarTipoDeficiencia();
	}

	@Override
	TipoDeficienciaDao getDAO() {

		return tipoDeficienciaDao;
	}

	@Override
	public TipoDeficiencia obterTipoDeficienciaPorIdAluno(Long idAluno) {

		return tipoDeficienciaDao.obterTipoDeficienciaPorIdAluno(idAluno);
	}

}
