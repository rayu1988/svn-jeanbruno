package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.querylanguage.QLWhereClause;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

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
	
	public List<CategoryTO> listFiter(SearchingRequest searchingRequest) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct category.id_category, category.name from category ");
		sql.append(" join advertisement on category.id_category = advertisement.id_category ");
		
		QLWhereClause where = new QLWhereClause();
		
		if (GeneralsHelper.isStringOk(searchingRequest.getText())) {
			where.and(" advertisement.title like '%" + searchingRequest.getText() + "%' ");
		}
		if (GeneralsHelper.isStringOk(searchingRequest.getFilterCurrencyFrom())) {
			where.and(" advertisement.value >= " + searchingRequest.getFilterCurrencyFrom());
		}
		if (GeneralsHelper.isStringOk(searchingRequest.getFilterCurrencyUpTo())) {
			where.and(" advertisement.value <= " + searchingRequest.getFilterCurrencyUpTo());
		}
		if (searchingRequest.getCity() != null) {
			sql.append(" left join user_account on advertisement.id_user_account = user_account.id_user ");
			where.and(" user_account.id_city = " + searchingRequest.getCity().getId());
		}
		
		sql.append(where.toString());
		
		SQLQuery query = this.getHibernateDao().createSQLQuery(sql.toString());
		List<CategoryTO> returning = new ArrayList<CategoryTO>();
		for (Object result : query.list()) {
			Object[] array = (Object[])result;
			CategoryTO category = new CategoryTO(((Number)array[0]).longValue());
			category.setName((String)array[1]);
			returning.add(category);
		}
		
		return returning;
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
