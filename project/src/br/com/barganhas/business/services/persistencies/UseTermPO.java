package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class UseTermPO extends AppPersistencyManagement {

	@SuppressWarnings("unchecked")
	public List<UseTermTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select USE_TERM from ").append(UseTermTO.class.getName()).append(" USE_TERM ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	public UseTermTO insert(UseTermTO useTerm) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select USE_TERM from ").append(UseTermTO.class.getName()).append(" USE_TERM ");
		
		boolean alreadyExists = GeneralsHelper.isBooleanTrue(this.getHibernateDao().queryCount(hql.toString()) > 0);
		
		useTerm.setIsDefault(!alreadyExists);
		
		this.getHibernateDao().insert(useTerm);
		
		return useTerm;
	}
	
	public UseTermTO save(UseTermTO useTerm) {
		this.getHibernateDao().update(useTerm);
		
		return useTerm;
	}

	public UseTermTO consult(UseTermTO useTerm) {
		return this.getHibernateDao().consult(useTerm);
	}

	public UseTermTO getDefaultUseTerm() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select USE_TERM from ").append(UseTermTO.class.getName()).append(" USE_TERM ");
		hql.append(" where USE_TERM.isDefault is true ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		UseTermTO useTerm = (UseTermTO) query.uniqueResult();
		
		if (useTerm == null) {
			throw new AppException("useTermDefaultNotFound");
		}
		
		return useTerm;
	}
	
	public void delete(UseTermTO useTerm) {
		this.getHibernateDao().delete(useTerm);
	}
}
