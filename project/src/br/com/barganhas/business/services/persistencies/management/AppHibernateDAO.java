package br.com.barganhas.business.services.persistencies.management;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.barganhas.business.entities.management.TransferObject;
import br.com.barganhas.business.services.persistencies.hbn.AppHbnResultTransformer;

public class AppHibernateDAO extends HibernateDaoSupport {

	public void insert(TransferObject to) {
		this.getHibernateTemplate().save(to);
	}
	
	public void delete(TransferObject to) {
		this.getHibernateTemplate().delete(to);
	}
	
	public void update(TransferObject to) {
		this.getHibernateTemplate().update(to);
	}
	
	/**
	 * This method executes the method load of the hibernate template.
	 * @param toBase
	 * @return
	 */
	public <T extends TransferObject> T consult(T toBase) {
		return this.load(toBase);
	}
	
	public <T extends TransferObject> T load(T toBase) {
		return (T) this.getHibernateTemplate().load(toBase.getClass(), toBase.getKey());
	}
	
	public <T extends TransferObject> T get(T toBase) {
		return (T) this.getHibernateTemplate().get(toBase.getClass(), toBase.getKey());
	}
	
	public Query createQuery(String hql) {
		return this.getSession().createQuery(hql);
	}
	
	public Query createQueryTransform(String hql) {
		Query query = this.getSession().createQuery(hql);
		query.setResultTransformer(new AppHbnResultTransformer(hql, false));
		return query;
	}
	
	public Query createQueryTransform(String hql, Class<? extends TransferObject> clazz, String strCut) {
		Query query = this.getSession().createQuery(hql);
		query.setResultTransformer(new AppHbnResultTransformer(hql, clazz, strCut, false));
		return query;
	}
	
	public long queryCount(String hql) {
		Query query = this.getSession().createQuery(hql);
		Number number = (Number) query.uniqueResult();
		return number == null ? 0 : number.longValue();
	}
}