package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.TipoGrauBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.TipoGrauDao;
import br.gov.go.sectec.portalemprego.core.entidade.TipoGrau;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoGrauDTO;

@Service("tipoGrauBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class TipoGrauBoImpl extends PremiumBOImpl<TipoGrau,Long> implements TipoGrauBo {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TipoGrauDao tipoGrauDao;

	@Override
	public List<TipoGrauDTO> listarTipograu() {

		return tipoGrauDao.listarTipograu();
	}

	@Override
	TipoGrauDao  getDAO() {

		return tipoGrauDao;
	}
	

}
