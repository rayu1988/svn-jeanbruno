package br.com.barganhas.business.services.persistencies.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.com.tatu.helper.parameter.ParameterWrapper;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
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
	
	public void clear() {
		this.getHibernateTemplate().clear();
	}
	
	public void flush() {
		this.getHibernateTemplate().flush();
	}
	
	public void refresh(TransferObject to) {
		this.getHibernateTemplate().refresh(to);
	}
	
	public void initialize(TransferObject to) {
		this.getHibernateTemplate().initialize(to);
	}
	
	public void evict(TransferObject to) {
		this.getHibernateTemplate().evict(to);
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
	
	public SQLQuery createSQLQuery(String sql) {
		return this.getSession().createSQLQuery(sql);
	}
	
	public Query createQuery(String hql) {
		return this.getSession().createQuery(hql);
	}
	
	public Query createQueryTransform(String hql) {
		Query query = this.getSession().createQuery(hql);
		query.setResultTransformer(new AppHbnResultTransformer(hql, false));
		return query;
	}
	
	public Query createQueryTransform(String hql, boolean up) {
		Query query = this.getSession().createQuery(hql);
		query.setResultTransformer(new AppHbnResultTransformer(hql, up));
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
	
	public <T extends TransferObject> void update(T to, ParameterWrapper param) {
		if (to.getKey() == null) {
			throw new IllegalStateException("O TransferObject passado como parâmetro não tem o id populado, impedindo assim a atualização do mesmo.");
		}
		if (param.getEntryList().isEmpty()) {
			throw new IllegalStateException("Não existe parâmetros para poder montar a cláusula set da atualização.");
		}
		
		String						nameBinario = "binario";
		String						nameData = "data";
		List<Date> 					listaDatas = new ArrayList<Date>();
		List<byte[]> 				listaBinarios = new ArrayList<byte[]>();
		StringBuilder hql 			= new StringBuilder();
		
//		TransferObject toOk = Util.getProperlyTransferObject(to);
		TransferObject toOk = to;
		
		hql.append(" update from ").append(toOk.getClass().getName()).append(" TO set ");
		for (ParameterWrapper.Entry entrada : param.getEntryList()) {
			hql.append(entrada.getKey());
			
			if (entrada.getValue() instanceof String){
				hql.append(" = '" + entrada.getValue() + "', ");
			}else if(entrada.getValue() instanceof Date){
				hql.append(" = :" + nameData + listaDatas.size() + ", ");
				listaDatas.add((Date) entrada.getValue());
			}else if(entrada.getValue() instanceof byte[]) {
				hql.append(" = :" + nameBinario + listaBinarios.size() + ", ");
				listaBinarios.add((byte[]) entrada.getValue());
			}else{
				hql.append(" = " + entrada.getValue() + ", ");
			}
		}
		hql.delete(hql.length() - 2, hql.length());
		hql.append(" where TO = " + to.getKey());
		
		Query query = this.createQuery(hql.toString());
		for (int i = 0; i<listaDatas.size(); i ++) {
			query.setTimestamp(nameData+i, listaDatas.get(i));
		}
		for (int i = 0; i<listaBinarios.size(); i++) {
			query.setBinary(nameBinario+i, listaBinarios.get(i));
		}
		
		query.executeUpdate();
		
	}
}