package br.gov.go.sectec.portalemprego.negocio.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.gov.go.sectec.portalemprego.comum.interfacebo.ControleEmpregoEfetivoBo;
import br.gov.go.sectec.portalemprego.comum.interfacedao.ControleEmpregoEfetivoDao;
import br.gov.go.sectec.portalemprego.core.entidade.ControleEmpregoEfetivo;

@Service("controleEmpregoEfetivo")
public class ControleEmpregoEfetivoBoImpl implements ControleEmpregoEfetivoBo {

	@Autowired
	private ControleEmpregoEfetivoDao controleEmpregoEfetivoDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public ControleEmpregoEfetivo consultar(Integer id) {
		return controleEmpregoEfetivoDao.consultar(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<ControleEmpregoEfetivo> listar() {
		return controleEmpregoEfetivoDao.listar();
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void incluir(ControleEmpregoEfetivo controleEmpregoEfetivo) {
		controleEmpregoEfetivoDao.incluir(controleEmpregoEfetivo);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public ControleEmpregoEfetivo alterar(ControleEmpregoEfetivo controleEmpregoEfetivo) {
		return controleEmpregoEfetivoDao.alterar(controleEmpregoEfetivo);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void excluir(ControleEmpregoEfetivo controleEmpregoEfetivo) {
		controleEmpregoEfetivoDao.excluir(controleEmpregoEfetivo);
	}

}
