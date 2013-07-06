package br.gov.go.sectec.portalemprego.negocio.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.gov.go.sectec.portalemprego.comum.interfacedao.EmpresaDao;
import br.gov.go.sectec.portalemprego.core.entidade.Empresa;

@Repository("empresaDao")
public class EmpresaDaoImpl extends PersistenciaGenericaJpa<Empresa, Integer> implements EmpresaDao {

	@Override
	public Empresa buscarEmpresaPorCNPJ(String cnpj) {

		final Criteria criteria = this.novoCriteria();
		criteria.add(Restrictions.eq("empresa.numCNPJ", cnpj));
		return (Empresa) criteria.uniqueResult();
	}

	@Override
	public Empresa buscarEmpresaPorEmail(String email) {

		final Criteria criteria = this.novoCriteria();
		criteria.add(Restrictions.eq("empresa.emailResponsavel", email));
		return (Empresa) criteria.uniqueResult();
	}

}
