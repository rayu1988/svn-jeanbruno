package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
@Repository
public class CityPO extends AppPersistency {

	public List<CityTO> list(StateTO state) {
		Query query = this.getQuery(CityTO.class);
		query.setFilter(new Query.FilterPredicate("keyState", Query.FilterOperator.EQUAL, state.getKey()));
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		
		List<CityTO> listReturn = new ArrayList<CityTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(CityTO.class, entity));
		}
		
		return listReturn;
	}
	
	public CityTO consult(CityTO city) throws EntityNotFoundException {
		return this.consultByKey(city);
	}
	
	public CityTO insert(CityTO city) {
		return this.persist(city);
	}
	
	public boolean alreadyExists() {
		return this.getSimplePreparedQuery(CityTO.class).countEntities(FetchOptions.Builder.withLimit(1)) > 0;
	}
	
	public void removeAll() {
		Query query = this.getQuery(CityTO.class);
		query.setKeysOnly();
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		for (Entity entity : preparedQuery.asList(FetchOptions.Builder.withLimit(500))) {
			this.getDataStoreService().delete(entity.getKey());
		}
	}
}
