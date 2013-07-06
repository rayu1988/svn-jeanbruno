package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.ControleEncaminhamentoBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.ControleEncaminhamentoDao;
import br.gov.go.sectec.portalemprego.core.entidade.ControleEncaminhamento;

@Service("controleEncaminhamentoBo")
public class ControleEncaminhamentoBoImpl implements ControleEncaminhamentoBo {

	@Autowired
	private ControleEncaminhamentoDao controleEncaminhamentoDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public ControleEncaminhamento consultar(Integer id) {
		return controleEncaminhamentoDao.consultar(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ControleEncaminhamento> listar() {
		return controleEncaminhamentoDao.listar();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void incluir(ControleEncaminhamento controleEncaminhamento) {
		controleEncaminhamentoDao.incluir(controleEncaminhamento);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public ControleEncaminhamento alterar(ControleEncaminhamento controleEncaminhamento) {
		return controleEncaminhamentoDao.alterar(controleEncaminhamento);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluir(ControleEncaminhamento controleEncaminhamento) {
		controleEncaminhamentoDao.excluir(controleEncaminhamento);
	}

}
