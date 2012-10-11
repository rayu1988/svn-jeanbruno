package br.com.barganhas.business.services.persistencies;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import br.com.barganhas.business.entities.CityTO;

@SuppressWarnings("serial")
@Repository
public class CityPO extends AppPersistency {

	public CityTO insert(CityTO category) {
		return this.persist(category);
	}
	
	public boolean alreadyExists() {
		return this.getSimplePreparedQuery(CityTO.class).countEntities(FetchOptions.Builder.withDefaults()) > 0;
	}
	
	public void removeAll() {
		Query query = this.getQuery(CityTO.class);
		query.setKeysOnly();
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		for (Entity entity : preparedQuery.asList(FetchOptions.Builder.withDefaults())) {
			this.getDataStoreService().delete(entity.getKey());
		}
	}
}
