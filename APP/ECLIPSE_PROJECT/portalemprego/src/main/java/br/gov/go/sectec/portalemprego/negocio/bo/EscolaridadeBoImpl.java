package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.EscolaridadeBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.EscolaridadeDao;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.core.entidade.Escolaridade;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.EscolaridadeDTO;

@Service("escolaridadeBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class EscolaridadeBoImpl extends PremiumBOImpl<Escolaridade,Long> implements EscolaridadeBo {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EscolaridadeDao escolaridadeDao;

	@Override
	public List<EscolaridadeDTO> listarEscolaridade() {

		return escolaridadeDao.listarEscolaridade();
	}

	@Override
	PersistenciaGenerica<Escolaridade, Long> getDAO() {

		return escolaridadeDao;
	}
	
}
