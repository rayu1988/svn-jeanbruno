package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.PaisBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PaisDao;
import br.gov.go.sectec.portalemprego.core.entidade.Pais;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.PaisDTO;
/**
 * <p>
 * <b>Title:</b> PaisBoImpl.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
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
@Service("paisBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PaisBoImpl extends PremiumBOImpl<Pais, Long> implements PaisBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PaisDao paisDao;

	@Override
	public List<PaisDTO> listarPaisOrdenado() {

		return getDAO().listarPaisOrdenado();
	}

	@Override
	PaisDao getDAO() {

		return paisDao;
	}

}
