package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
@Repository
public class AdministratorPO extends AppPersistency {

	public List<AdministratorTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(AdministratorTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<AdministratorTO> listReturn = new ArrayList<AdministratorTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdministratorTO.class, entity));
		}
		
		return listReturn;
	}
	
	public List<AdministratorTO> filter(AdministratorTO administrator) {
		Query query = this.getQuery(AdministratorTO.class);
		
		List<Filter> filters = new ArrayList<Query.Filter>();
		if (Util.isStringOk(administrator.getFullname())) {
			filters.add(new FilterPredicate("fullname", FilterOperator.EQUAL, administrator.getFullname()));
		}
		if (Util.isStringOk(administrator.getNickname())) {
			filters.add(new FilterPredicate("nickname", FilterOperator.EQUAL, administrator.getNickname()));
		}
		if (Util.isStringOk(administrator.getEmail())) {
			filters.add(new FilterPredicate("email", FilterOperator.EQUAL, administrator.getEmail()));
		}
		
		if (!Util.isCollectionOk(filters)) {
			return this.list();
		} else if (filters.size() == 1) {
			query.setFilter(filters.get(0));
		} else if (filters.size() > 1) {
			query.setFilter(new Query.CompositeFilter(Query.CompositeFilterOperator.AND, filters));
		}
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		
		List<Entity> entities = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		List<AdministratorTO> listReturn = new ArrayList<AdministratorTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdministratorTO.class, entity));
		}
		
		return listReturn;
	}
	
	public AdministratorTO insert(AdministratorTO administrator) {
		return this.persist(administrator);
	}
	
	public AdministratorTO save(AdministratorTO administrator) throws EntityNotFoundException {
		if (!Util.isStringOk(administrator.getPassword())) {
			AdministratorTO syncronizedTO = this.consult(administrator);
			administrator.setPassword(syncronizedTO.getPassword());
		}
		
		return this.persist(administrator);
	}

	public AdministratorTO consult(AdministratorTO administrator) throws EntityNotFoundException {
		return this.consultByKey(administrator);
	}

	public void delete(AdministratorTO administrator) {
		this.deleteEntity(administrator);
	}
	
	public AdministratorTO validateLogin(AdministratorTO administrator) {
		Query query = this.getQuery(AdministratorTO.class);
		query.setFilter(CompositeFilterOperator.and(
				new FilterPredicate("nickname", Query.FilterOperator.EQUAL, administrator.getNickname()),
				new FilterPredicate("password", Query.FilterOperator.EQUAL, administrator.getPassword())
				));
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		Entity entity = preparedQuery.asSingleEntity();
		
		if (entity == null) {
			throw new AppException("loginErrorUserNotFound");
		}

		return AnnotationUtils.getTransferObjectFromEntity(AdministratorTO.class, entity);
	}
}
