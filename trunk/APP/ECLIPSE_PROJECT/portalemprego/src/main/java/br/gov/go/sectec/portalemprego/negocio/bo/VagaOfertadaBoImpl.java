package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.VagaOfertadaBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.VagaOfertadaDao;
import br.gov.go.sectec.portalemprego.core.entidade.VagaOfertada;

@Service("vagaOfertadaBo")
public class VagaOfertadaBoImpl implements VagaOfertadaBo {

	@Autowired
	private VagaOfertadaDao vagaOfertadaDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public VagaOfertada consultar(Integer id) {
		return vagaOfertadaDao.consultar(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<VagaOfertada> listar() {
		return vagaOfertadaDao.listar();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void incluir(VagaOfertada vagaOfertada) {
		vagaOfertadaDao.incluir(vagaOfertada);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public VagaOfertada alterar(VagaOfertada vagaOfertada) {
		return vagaOfertadaDao.alterar(vagaOfertada);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluir(VagaOfertada vagaOfertada) {
		vagaOfertadaDao.excluir(vagaOfertada);
	}

}
