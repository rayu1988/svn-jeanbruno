package br.gov.go.sectec.portalemprego.negocio.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.EmpresaDao;
import br.gov.go.sectec.portalemprego.core.entidade.Empresa;
import br.gov.go.sectec.portalemprego.core.entidade.Telefone;
import br.gov.go.sectec.portalemprego.core.entidade.VagaOfertada;

@Repository("empresaDao")
public class EmpresaDaoImpl extends PersistenciaGenericaJpa<Empresa, Integer> implements EmpresaDao {

	@Override
	public Empresa buscarEmpresaPorCNPJ(String cnpj) {
		final Criteria criteria = this.novoCriteria();
		criteria.add(Restrictions.eq("numCNPJ", cnpj));
		return (Empresa) criteria.uniqueResult();
	}

	@Override
	public Empresa buscarEmpresaPorEmail(String email) {
		final Criteria criteria = this.novoCriteria();
		criteria.add(Restrictions.eq("emailResponsavel", email));
		return (Empresa) criteria.uniqueResult();
	}

	@Override
	public boolean checkExistEmpresa(String cnpj) {
		final Criteria criteria = this.novoCriteria();
		criteria.add(Restrictions.eq("numCNPJ", cnpj));
		List<?> listEmpresa = criteria.list();
		return listEmpresa != null && listEmpresa.size() > 0;
	}

	@Override
	public Empresa login(String dsLogin, String dsSenha) {
		final Criteria criteria = this.novoCriteria();
		criteria.add(Restrictions.eq("dsLogin", dsLogin));
		criteria.add(Restrictions.eq("dsSenha", dsSenha));
		Empresa empresa = (Empresa) criteria.uniqueResult();
		if (empresa != null) {
			if (empresa.getTelefones() != null && empresa.getTelefones().size() > 0) {
				empresa.getTelefones().get(0);
			}
			if (empresa.getVagaOfertadas() != null && empresa.getVagaOfertadas().size() > 0) {
				empresa.getVagaOfertadas().get(0);
			}
		}
		return empresa;
	}

	@Override
	public Empresa obterEmpresaPeloId(Long id) {
		final Criteria criteria = this.novoCriteria();
		criteria.add(Restrictions.eq("idEmpresa", id));
		Empresa empresa = (Empresa) criteria.uniqueResult();
		if (empresa != null) {
			if (empresa.getTelefones() != null && empresa.getTelefones().size() > 0) {
				empresa.getTelefones().get(0);
			}
			if (empresa.getVagaOfertadas() != null && empresa.getVagaOfertadas().size() > 0) {
				empresa.getVagaOfertadas().get(0);
			}
		}
		return empresa;
	}

	@Override
	public Empresa salvar(Empresa empresa) {
		String hqlTelefone = " delete from " + Telefone.class.getName() + " TEL where TEL.empresa = " + empresa.getIdEmpresa();
		this.getEntityManager().createQuery(hqlTelefone).executeUpdate();

		String hqlVagaOfertada = " delete from " + VagaOfertada.class.getName() + " VAGA where VAGA.empresa = " + empresa.getIdEmpresa();
		this.getEntityManager().createQuery(hqlVagaOfertada).executeUpdate();
		
		this.getEntityManager().flush();
		
		empresa = super.alterar(empresa);
		this.getEntityManager().flush();
		
		return empresa;
	}
	
}
