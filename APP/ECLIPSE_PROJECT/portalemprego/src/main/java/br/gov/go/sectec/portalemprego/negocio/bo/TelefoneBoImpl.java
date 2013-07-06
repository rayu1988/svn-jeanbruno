package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.TelefoneBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PersistenciaGenerica;
import br.gov.go.sectec.portalemprego.comum.interfacedao.TelefoneDao;
import br.gov.go.sectec.portalemprego.core.entidade.Telefone;
import br.gov.go.sectec.portalemprego.core.entidade.DTO.TelefoneDTO;

@Service("telefoneBo")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
public class TelefoneBoImpl extends PremiumBOImpl<Telefone, Long> implements TelefoneBo {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TelefoneDao telefoneDao;

	@Override
	public List<TelefoneDTO> listarPorIdAluno(Long idAluno) {

		return getDAO().listarPorIdAluno(idAluno);
	}

	@Override
	TelefoneDao getDAO() {

		return telefoneDao;
	}

	@Override
	public void removerTodostelefonesPorIdAluno(Long idAluno) {

		telefoneDao.removerTodostelefonesPorIdAluno(idAluno);
		
	}

	
}
