package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.commons.AnnotationUtils;
import br.com.barganhas.enums.AdvertisementStatus;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
@Repository
public class AdvertisementPO extends AppPersistency {

	/**
	 * Method used in;
	 * 		admin List
	 * @return
	 */
	public List<AdvertisementTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(AdvertisementTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<AdvertisementTO> listReturn = new ArrayList<AdvertisementTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTO.class, entity));
		}
		
		return listReturn;
	}
	
	/**
	 * Method used in;
	 * 		public search in the public site
	 * 
	 * @param searchText
	 * @return
	 */
	public List<AdvertisementTO> publicSearch(String searchText) {
		
		Filter filterStatus = new Query.FilterPredicate("status", Query.FilterOperator.EQUAL, AdvertisementStatus.ENABLED.toString());
		
		List<Filter> filterTitle = new ArrayList<Query.Filter>();
		filterTitle.add(new Query.FilterPredicate("title", Query.FilterOperator.GREATER_THAN_OR_EQUAL, searchText));
		filterTitle.add(new Query.FilterPredicate("title", Query.FilterOperator.LESS_THAN_OR_EQUAL, searchText));
		
		Filter filter = CompositeFilterOperator.and(filterStatus, CompositeFilterOperator.or(filterTitle));
		
		Query query = this.getQuery(AdvertisementTO.class);
		query.setFilter(filter);
		
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		List<AdvertisementTO> listReturn = new ArrayList<AdvertisementTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTO.class, entity));
		}
		
		return listReturn;
	}
	
	public List<AdvertisementTO> list(UserAccountTO userAccount) {
		Query query = this.getQuery(AdvertisementTO.class, userAccount);
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		
		List<AdvertisementTO> listReturn = new ArrayList<AdvertisementTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTO.class, entity));
		}
		
		return listReturn;
	}
	
	public List<AdvertisementTO> myLastAdvertisements(UserAccountTO userAccount) {
		Query query = this.getQuery(AdvertisementTO.class, userAccount);
		query.addSort("id",	SortDirection.DESCENDING);
		
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withLimit(10));
		
		List<AdvertisementTO> listReturn = new ArrayList<AdvertisementTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTO.class, entity));
		}
		
		return listReturn;
	}
	
	public List<AdvertisementTO> lastAdvertisements() {
		Query query = this.getQuery(AdvertisementTO.class);
		query.addSort("id",	SortDirection.DESCENDING);
		Filter filterStatus = new Query.FilterPredicate("status", Query.FilterOperator.EQUAL, AdvertisementStatus.ENABLED.toString());
		query.setFilter(filterStatus);
		
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withLimit(12));
		List<AdvertisementTO> listReturn = new ArrayList<AdvertisementTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTO.class, entity));
		}
		
		return listReturn;
	}
	
	public List<AdvertisementTO> mostViewed() {
		Query query = this.getQuery(AdvertisementTO.class);
		query.addSort("countView",	SortDirection.DESCENDING);
		Filter filterStatus = new Query.FilterPredicate("status", Query.FilterOperator.EQUAL, AdvertisementStatus.ENABLED.toString());
		query.setFilter(filterStatus);
		
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withLimit(12));
		List<AdvertisementTO> listReturn = new ArrayList<AdvertisementTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTO.class, entity));
		}
		
		return listReturn;
	}
	
	public AdvertisementTO insert(AdvertisementTO advertisement, UserAccountTO userAccount) {
		return this.persist(advertisement, userAccount);
	}
	
	public AdvertisementTO save(AdvertisementTO advertisement) {
		return this.persist(advertisement);
	}

	public AdvertisementTO consult(AdvertisementTO advertisement) throws EntityNotFoundException {
		return this.consultByKey(advertisement);
	}
	
	public AdvertisementTO publicConsult(AdvertisementTO advertisement) throws EntityNotFoundException {
		advertisement = this.consultByKey(advertisement);
		Long currentCount = advertisement.getCountView() != null ? advertisement.getCountView() + 1 : 0l;
		advertisement.setCountView(currentCount);
		return this.save(advertisement);
	}

	public void delete(AdvertisementTO advertisement) {
		this.deleteEntity(advertisement);
	}
}
