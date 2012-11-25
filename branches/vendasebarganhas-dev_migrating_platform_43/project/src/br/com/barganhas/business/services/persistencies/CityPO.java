package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.querylanguage.QLWhereClause;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;
import br.com.barganhas.commons.SearchingRequest;

@SuppressWarnings("serial")
@Repository
public class CityPO extends AppPersistencyManagement {

	@SuppressWarnings("unchecked")
	public List<CityTO> list(StateTO state) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select CITY.id, CITY.name, STATE.id, STATE.name from ").append(CityTO.class.getName()).append(" CITY ");
		hql.append(" join CITY.state STATE ");
		hql.append(" where CITY.state = ").append(state.getId());

		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	public List<CityTO> listFiter(SearchingRequest searchingRequest) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct CITY.id_city, CITY.name, STATE.id_state, STATE.acronym from CITY ");
		sql.append(" left join STATE on CITY.id_state = STATE.id_state ");
		sql.append(" join USER_ACCOUNT on CITY.id_city = USER_ACCOUNT.id_city ");
		sql.append(" join ADVERTISEMENT on USER_ACCOUNT.id_user = ADVERTISEMENT.id_user_account ");
		
		QLWhereClause where = new QLWhereClause();
		if (GeneralsHelper.isStringOk(searchingRequest.getText())) {
			where.and(" ADVERTISEMENT.title like '%" + searchingRequest.getText() + "%' ");
		}
		if (GeneralsHelper.isStringOk(searchingRequest.getFilterCurrencyFrom())) {
			where.and(" ADVERTISEMENT.value >= " + searchingRequest.getFilterCurrencyFrom());
		}
		if (GeneralsHelper.isStringOk(searchingRequest.getFilterCurrencyUpTo())) {
			where.and(" ADVERTISEMENT.value <= " + searchingRequest.getFilterCurrencyUpTo());
		}
		if (searchingRequest.getCategory() != null) {
			where.and(" ADVERTISEMENT.id_category = " + searchingRequest.getCategory().getId());
		}
		sql.append(where.toString());
		
		SQLQuery query = this.getHibernateDao().createSQLQuery(sql.toString());
		List<CityTO> returning = new ArrayList<CityTO>();
		for (Object result : query.list()) {
			Object[] array = (Object[]) result;
			CityTO city = new CityTO(((Number)array[0]).longValue());
			city.setName((String) array[1]);
			
			StateTO state = new StateTO(((Number)array[2]).longValue());
			state.setAcronym((String)array[3]);
			
			city.setState(state);
			returning.add(city);
		}
		
		return returning;
	}
	
	public CityTO consult(CityTO city) {
		return this.getHibernateDao().consult(city);
	}
	
	public CityTO insert(CityTO city) {
		this.getHibernateDao().insert(city);
		return city;
	}
	
	public boolean alreadyExists() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(CITY.id) from ").append(CityTO.class.getName()).append(" CITY ");
		
		return this.getHibernateDao().queryCount(hql.toString()) > 0;
	}
	
	public void removeAll() {
		StringBuffer hql = new StringBuffer();
		hql.append(" delete from ").append(CityTO.class.getName());
		
		Query query = this.getHibernateDao().createQuery(hql.toString());
		query.executeUpdate();
	}
}
