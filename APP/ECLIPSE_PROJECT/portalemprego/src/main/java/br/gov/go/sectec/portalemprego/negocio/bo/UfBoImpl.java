package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.UfBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.comum.interfacedao.UfDao;
import br.gov.go.sectec.portalemprego.core.entidade.Uf;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.UfDTO;

@Service("ufBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UfBoImpl extends PremiumBOImpl<Uf, Long> implements UfBo {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UfDao ufDao;

	@Override
	public List<UfDTO> obterPorIdPais(final Long idPais) {

		return this.ufDao.obterPorIdPais(idPais);
	}

	@Override
	PersistenciaGenerica<Uf, Long> getDAO() {

		return this.ufDao;
	}

}
