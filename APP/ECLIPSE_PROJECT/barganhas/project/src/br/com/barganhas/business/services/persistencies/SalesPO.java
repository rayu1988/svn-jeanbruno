package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

@SuppressWarnings("serial")
@Repository
public class SalesPO extends AppPersistency {

	public List<SalesTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(SalesTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<SalesTO> listReturn = new ArrayList<SalesTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(SalesTO.class, entity));
		}
		
		return listReturn;
	}
	
	public SalesTO insert(SalesTO sales) {
		return this.persist(sales);
	}
	
	public SalesTO save(SalesTO sales) {
		return this.persist(sales);
	}

	public SalesTO consult(SalesTO sales) {
		return this.consultEntityById(sales);
	}

	public void delete(SalesTO sales) {
		this.deleteEntity(sales);
	}
	
	public SalesTO consultBySalesCode(String salesCode) {
		Query query = this.getQuery(SalesTO.class);
		query.setFilter(new Query.FilterPredicate("salesCode", FilterOperator.EQUAL, salesCode.trim()));
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		Entity entity = preparedQuery.asSingleEntity();
		
		if (entity == null) {
			throw new AppException("advertisementInvalidSalesCode");
		}
		
		return (SalesTO) AnnotationUtils.getTransferObjectFromEntity(SalesTO.class, entity);
	}
}
