package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.PessoaJuridicaBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.PessoaJuridicaDao;
import br.gov.go.sectec.portalemprego.core.entidade.PessoaJuridica;

@Service("pessoaJuridicaBo")
public class PessoaJuridicaBoImpl implements PessoaJuridicaBo {

	@Autowired
	private PessoaJuridicaDao pessoaJuridicaDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public PessoaJuridica consultar(Integer id) {
		return pessoaJuridicaDao.consultar(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<PessoaJuridica> listar() {
		return pessoaJuridicaDao.listar();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void incluir(PessoaJuridica pessoaJuridica) {
		pessoaJuridicaDao.incluir(pessoaJuridica);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public PessoaJuridica alterar(PessoaJuridica pessoaJuridica) {
		return pessoaJuridicaDao.alterar(pessoaJuridica);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluir(PessoaJuridica pessoaJuridica) {
		pessoaJuridicaDao.excluir(pessoaJuridica);
	}

}
