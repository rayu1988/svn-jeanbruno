package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.querylanguage.QLWhereClause;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;
import br.com.barganhas.commons.SearchingRequest;
import br.com.barganhas.enums.AdvertisementStatus;

@SuppressWarnings("serial")
@Repository
public class AdvertisementPO extends AppPersistencyManagement {

	/**
	 * Method used in;
	 * 		admin List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AdvertisementTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADVERTISEMENT from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	/**
	 * Method used in;
	 * 		public search in the public site
	 * 
	 * @param searchText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AdvertisementTO> listEntitiesPublicSearch(SearchingRequest searchingRequest) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADVERTISEMENT.id, ADVERTISEMENT.title, ADVERTISEMENT.description, ADVERTISEMENT.value, ADVERTISEMENT.exchangeBy ");
		hql.append(" from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" left join ADVERTISEMENT.advertisementType ADVERTISEMENT_TYPE ");
		hql.append(" left join ADVERTISEMENT.userAccount USER_ACCOUNT ");
		
		QLWhereClause where = new QLWhereClause();
		where.and(" ADVERTISEMENT.status = " + AdvertisementStatus.ENABLED);
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
		if (searchingRequest.getCity() != null) {
			where.and(" USER_ACCOUNT.city = " + searchingRequest.getCity().getId());
		}
		hql.append(where.toString());
		
		if (searchingRequest.getSearchOrdering().equals(SearchingRequest.SearchOrdering.MOST_RELEVANT)) {
			hql.append(" order by ADVERTISEMENT_TYPE.score DESC ");
		} else {
			if (searchingRequest.getSearchOrdering().equals(SearchingRequest.SearchOrdering.LOWER_PRICE)) {
				hql.append(" order by ADVERTISEMENT.value ASC ");
			} else {
				hql.append(" order by ADVERTISEMENT.value DESC ");
			}
		}
		
		int limit = searchingRequest.getTotalItensPerPage();
		int offset = (searchingRequest.getCurrentPage() - 1) * limit;
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		query.setMaxResults(limit);
		query.setFirstResult(offset);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdvertisementTO> list(UserAccountTO userAccount) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADVERTISEMENT from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" where ADVERTISEMENT.userAccount = ").append(userAccount.getId());
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdvertisementTO> myLastAdvertisements(UserAccountTO userAccount) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADVERTISEMENT from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" where ADVERTISEMENT.userAccount = ").append(userAccount.getId());
		hql.append(" order by ADVERTISEMENT.id DESC ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		query.setMaxResults(10);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdvertisementTO> lastAdvertisements() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADVERTISEMENT from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" where ADVERTISEMENT.status = ").append(AdvertisementStatus.ENABLED);
		hql.append(" order by ADVERTISEMENT.id DESC ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		query.setMaxResults(12);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdvertisementTO> mostViewed() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADVERTISEMENT from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" where ADVERTISEMENT.status = ").append(AdvertisementStatus.ENABLED);
		hql.append(" order by ADVERTISEMENT.countView DESC ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		query.setMaxResults(12);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdvertisementTO> userAccountLastAdvertisements(UserAccountTO userAccount) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADVERTISEMENT from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" where ADVERTISEMENT.status = ").append(AdvertisementStatus.ENABLED);
		hql.append(" and ADVERTISEMENT.userAccount = ").append(userAccount.getId());
		hql.append(" order by ADVERTISEMENT.id DESC ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		query.setMaxResults(12);
		return query.list();
	}
	
	public AdvertisementTO insert(AdvertisementTO advertisement) {
		this.getHibernateDao().insert(advertisement);
		return advertisement;
	}
	
	public AdvertisementTO save(AdvertisementTO advertisement) {
		this.getHibernateDao().update(advertisement);
		return advertisement;
	}

	public AdvertisementTO consult(AdvertisementTO advertisement) {
		return this.getHibernateDao().consult(advertisement);
	}
	
	private void incrementCountView(AdvertisementTO advertisement) {
		StringBuffer hql = new StringBuffer();
		hql.append(" update ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" set ADVERTISEMENT.countView = ").append(advertisement.getCountView() + 1);
		hql.append(" where ADVERTISEMENT = ").append(advertisement.getId());
		
		this.getHibernateDao().createQuery(hql.toString()).executeUpdate();
	}
	
	public AdvertisementTO publicConsult(AdvertisementTO advertisement) {
		advertisement = this.consult(advertisement);
		
		this.incrementCountView(advertisement);
		
		return advertisement;
	}

	public void delete(AdvertisementTO advertisement) {
		this.getHibernateDao().delete(advertisement);
	}
	
	public void delete(UserAccountTO owner) {
		StringBuffer hql = new StringBuffer();
		hql.append(" delete from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" where ADVERTISEMENT.userAccount = ").append(owner.getId());
	}
}
