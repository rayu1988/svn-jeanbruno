package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
@Repository
public class UseTermPO extends AppPersistency {

	public List<UseTermTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(UseTermTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<UseTermTO> listReturn = new ArrayList<UseTermTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(UseTermTO.class, entity));
		}
		
		return listReturn;
	}
	
	public UseTermTO insert(UseTermTO useTerm) {
		boolean alreadyExists = GeneralsHelper.isCollectionOk(this.getSimplePreparedQuery(UseTermTO.class).asList(FetchOptions.Builder.withLimit(1)));
		useTerm.setDefaultUseTerm(!alreadyExists);
		return this.persist(useTerm);
	}
	
	public UseTermTO save(UseTermTO useTerm) {
		return this.persist(useTerm);
	}

	public UseTermTO consult(UseTermTO useTerm) throws EntityNotFoundException {
		return this.consultByKey(useTerm);
	}

	public UseTermTO getDefaultUseTerm() throws EntityNotFoundException {
		Query query = this.getQuery(UseTermTO.class);
		query.setFilter(new Query.FilterPredicate("defaultUseTerm", Query.FilterOperator.EQUAL, true));
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		Entity entity = preparedQuery.asSingleEntity();
		
		if (entity == null) {
			throw new AppException("useTermDefaultNotFound");
		}
		
		return AnnotationUtils.getTransferObjectFromEntity(UseTermTO.class, entity);
	}
	
	public void delete(UseTermTO useTerm) {
		this.deleteEntity(useTerm);
	}
}
