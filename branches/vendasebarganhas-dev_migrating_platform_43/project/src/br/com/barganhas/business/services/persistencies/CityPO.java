package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.querylanguage.QLWhereClause;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementTO;
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
	
	@SuppressWarnings("unchecked")
	public List<CityTO> listFiter(SearchingRequest searchingRequest) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct CITY.id, CITY.name, STATE.id, STATE.name, STATE.acronym from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" join ADVERTISEMENT.userAccount USER_ACCOUNT ");
		hql.append(" join USER_ACCOUNT.city CITY ");
		hql.append(" join CITY.state STATE ");
		
		QLWhereClause where = new QLWhereClause();
		
		if (GeneralsHelper.isStringOk(searchingRequest.getText())) {
			where.and(" ADVERTISEMENT.title like '%" + searchingRequest.getText() + "%' ");
		}
		if (searchingRequest.getFilterCurrencyFrom() != null) {
			where.and(" ADVERTISEMENT.value >= " + searchingRequest.getFilterCurrencyFrom());
		}
		if (searchingRequest.getFilterCurrencyUpTo() != null) {
			where.and(" ADVERTISEMENT.value <= " + searchingRequest.getFilterCurrencyUpTo());
		}
		if (searchingRequest.getCategory() != null) {
			where.and(" ADVERTISEMENT.category = " + searchingRequest.getCategory().getId());
		}
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString(), CityTO.class, "userAccount.city");
		return query.list();
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
