package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.querylanguage.QLWhereClause;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;
import br.com.barganhas.commons.SearchingRequest;

@SuppressWarnings("serial")
@Repository
public class CategoryPO extends AppPersistencyManagement {

	@SuppressWarnings("unchecked")
	public List<CategoryTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select CATEGORY.id, CATEGORY.name, CATEGORY.description from ").append(CategoryTO.class.getName()).append(" CATEGORY ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<CategoryTO> listFiter(SearchingRequest searchingRequest) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct CATEGORY.id, CATEGORY.name from ").append(AdvertisementTO.class.getName()).append(" ADVERTISEMENT ");
		hql.append(" join ADVERTISEMENT.category CATEGORY ");
		hql.append(" join ADVERTISEMENT.userAccount USER_ACCOUNT ");
		
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
		if (searchingRequest.getCity() != null) {
			where.and(" USER_ACCOUNT.city = " + searchingRequest.getCity().getId());
		}
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString(), CategoryTO.class, "category");
		return query.list();
	}
	
	public CategoryTO insert(CategoryTO category) {
		this.getHibernateDao().insert(category);
		return category;
	}
	
	public CategoryTO save(CategoryTO category) {
		this.getHibernateDao().update(category);
		return category;
	}

	public CategoryTO consult(CategoryTO category) {
		return this.getHibernateDao().consult(category);
	}

	public void delete(CategoryTO category) {
		this.getHibernateDao().delete(category);
	}
}
