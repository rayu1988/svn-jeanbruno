package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.AreaInteresseBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.AreaInteresseDao;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.core.entidade.AreaInteresse;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.AreaInteresseDTO;

/**
 * 
 * <p>
 * <b>Title:</b> AreaInteresseBoImpl.java
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
 * @author Joffre
 * 
 * @version 1.0.0
 */
@Service("areaInteresseBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AreaInteresseBoImpl extends PremiumBOImpl<AreaInteresse, Long> implements AreaInteresseBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AreaInteresseDao dao;

	@Override
	PersistenciaGenerica<AreaInteresse, Long> getDAO() {

		return dao;
	}

	@Override
	public List<AreaInteresseDTO> listarAreaInteresseOrdenada() {

		return this.dao.listarAreaInteresseOrdenado();
	}

	@Override
	public List<AreaInteresseDTO> ListarAreaInteresse(Long idCidadeSelecao, Integer idTipoSexo, Integer idTipoFaixaEtaria, Long idAreaInteresSelecao) {

		return dao.ListarAreaInteresse(idCidadeSelecao, idTipoSexo, idTipoFaixaEtaria,idAreaInteresSelecao);
	}

}
