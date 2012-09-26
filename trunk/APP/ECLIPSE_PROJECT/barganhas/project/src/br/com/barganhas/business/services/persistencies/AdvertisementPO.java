package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
@Repository
public class AdvertisementPO extends AppPersistency {

	public List<AdvertisementTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(AdvertisementTO.class).asList(FetchOptions.Builder.withDefaults());
		
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
		
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		
		// TODO <TBD> correcty, I'm not sure that 10 is the best choice
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withLimit(10));
		
		List<AdvertisementTO> listReturn = new ArrayList<AdvertisementTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTO.class, entity));
		}
		
		return listReturn;
	}
	
	// TODO Implement a field that stores how many times an advertisement was viewed.
	public List<AdvertisementTO> lastMostViewed() {
		Query query = this.getQuery(AdvertisementTO.class);
		query.addSort("id",	SortDirection.DESCENDING);
		
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withLimit(10));
		
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

	public void delete(AdvertisementTO advertisement) {
		this.deleteEntity(advertisement);
	}
}
