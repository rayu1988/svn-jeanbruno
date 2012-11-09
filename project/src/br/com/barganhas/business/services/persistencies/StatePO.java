package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
@Repository
public class StatePO extends AppPersistency {

	public List<StateTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(StateTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<StateTO> listReturn = new ArrayList<StateTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(StateTO.class, entity));
		}
		
		return listReturn;
	}
	
	public boolean alreadyExists() {
		return this.getSimplePreparedQuery(StateTO.class).countEntities(FetchOptions.Builder.withDefaults()) > 0;
	}
	
	public StateTO insert(StateTO state) {
		return this.persist(state);
	}
	
	public StateTO consult(StateTO state) throws EntityNotFoundException {
		return this.consultByKey(state);
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
