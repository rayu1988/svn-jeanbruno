package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.TipoTelefoneBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.TipoTelefoneDao;
import br.gov.go.sectec.portalemprego.core.entidade.TipoTelefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TipoTelefoneDTO;

/**
 * <p>
 * <b>Title:</b> TipoTelefoneBoImpl.java
 * </p>
 * 
 * <p>
 * <b>Description:</b> Classe de negocio de TipoTelefone.
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Silvânio
 * 
 * @version 1.0.0
 */
@Service("tipoTelefoneBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TipoTelefoneBoImpl extends PremiumBOImpl<TipoTelefone, Long> implements TipoTelefoneBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private TipoTelefoneDao tipoTelefoneDao;

	@Override
	public List<TipoTelefoneDTO> listarTipoTelefone() {

		return this.getDAO().listarTipoTelefone();
	}

	@Override
	TipoTelefoneDao getDAO() {

		return this.tipoTelefoneDao;
	}

}
