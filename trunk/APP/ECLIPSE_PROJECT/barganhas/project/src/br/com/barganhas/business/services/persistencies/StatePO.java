package br.com.barganhas.business.services.persistencies;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
@Repository
public class StatePO extends AppPersistency {

	public boolean alreadyExists() {
		return this.getSimplePreparedQuery(StateTO.class).countEntities(FetchOptions.Builder.withDefaults()) > 0;
	}
	
	public StateTO insert(StateTO category) {
		return this.persist(category);
	}

	public StateTO consultAcronym(String acronym) {
		Query query = this.getQuery(StateTO.class);
		query.setFilter(new Query.FilterPredicate("acronym", Query.FilterOperator.EQUAL, acronym));
		
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		Entity entity = preparedQuery.asSingleEntity();
		
		if (entity == null) {
			throw new IllegalStateException();
		}
		
		return AnnotationUtils.getTransferObjectFromEntity(StateTO.class, entity);
	}
	
	public void removeAll() {
		Query query = this.getQuery(StateTO.class);
		query.setKeysOnly();
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		for (Entity entity : preparedQuery.asList(FetchOptions.Builder.withDefaults())) {
			this.getDataStoreService().delete(entity.getKey());
		}
	}
	
}
