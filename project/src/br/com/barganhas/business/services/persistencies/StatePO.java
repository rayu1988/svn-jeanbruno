package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class StatePO extends AppPersistencyManagement {

	@SuppressWarnings("unchecked")
	public List<StateTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select STATE.id, STATE.name, STATE.acronym from ").append(StateTO.class.getName()).append(" STATE ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	public boolean alreadyExists() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(STATE) from ").append(StateTO.class.getName()).append(" STATE ");
		
		return this.getHibernateDao().queryCount(hql.toString()) > 0;
	}
	
	public StateTO insert(StateTO state) {
		this.getHibernateDao().insert(state);
		return state;
	}
	
	public StateTO consult(StateTO state) {
		return this.getHibernateDao().consult(state);
	}

	public StateTO consultAcronym(String acronym) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select STATE.id, STATE.name, STATE.acronym from ").append(StateTO.class.getName()).append(" STATE ");
		hql.append(" where STATE.acronym = :acronym ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		query.setString("acronym", acronym);
		
		StateTO state = (StateTO) query.uniqueResult();
		
		if (state == null) {
			throw new IllegalStateException();
		}
		
		return state;
	}
	
	public void removeAll() {
		StringBuffer hql = new StringBuffer();
		hql.append(" delete from ").append(StateTO.class.getName());

		Query query = this.getHibernateDao().createQuery(hql.toString());
		query.executeUpdate();
	}
	
}
