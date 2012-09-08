package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
@Repository
public class AdministratorPO extends AppPersistency {

	public List<AdministratorTO> list() {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			List<Entity> entities = this.getSimplePreparedQuery(AdministratorTO.class).asList(FetchOptions.Builder.withDefaults());
			
			List<AdministratorTO> listReturn = new ArrayList<AdministratorTO>();
			for (Entity entity : entities) {
				listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdministratorTO.class, entity));
			}
			
			transaction.commit();
			return listReturn;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public List<AdministratorTO> filter(AdministratorTO administrator) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
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
			
			transaction.commit();
			return listReturn;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	public AdministratorTO insert(AdministratorTO administrator) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			transaction.commit();
			return this.persist(administrator);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public AdministratorTO save(AdministratorTO administrator) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			if (!Util.isStringOk(administrator.getPassword())) {
				AdministratorTO syncronizedTO = this.consult(administrator);
				administrator.setPassword(syncronizedTO.getPassword());
			}
			
			transaction.commit();
			return this.persist(administrator);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public AdministratorTO consult(AdministratorTO administrator) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			administrator = this.consultEntityById(administrator);
			
			transaction.commit();
			return administrator;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public void delete(AdministratorTO administrator) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.deleteEntity(administrator);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public AdministratorTO validateLogin(AdministratorTO administrator) {
		try {
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
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
