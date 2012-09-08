package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
@Repository
public class UserAccountPO extends AppPersistency {

	public List<UserAccountTO> list() {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			List<Entity> entities = this.getSimplePreparedQuery(UserAccountTO.class).asList(FetchOptions.Builder.withDefaults());
			
			List<UserAccountTO> listReturn = new ArrayList<UserAccountTO>();
			for (Entity entity : entities) {
				listReturn.add(AnnotationUtils.getTransferObjectFromEntity(UserAccountTO.class, entity));
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
	
	public List<UserAccountTO> filter(UserAccountTO userAccount) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			Query query = this.getQuery(UserAccountTO.class);
			
			List<Filter> filters = new ArrayList<Query.Filter>();
			if (Util.isStringOk(userAccount.getFullname())) {
				filters.add(new FilterPredicate("fullname", FilterOperator.EQUAL, userAccount.getFullname()));
			}
			if (Util.isStringOk(userAccount.getNickname())) {
				filters.add(new FilterPredicate("nickname", FilterOperator.EQUAL, userAccount.getNickname()));
			}
			if (Util.isStringOk(userAccount.getEmail())) {
				filters.add(new FilterPredicate("email", FilterOperator.EQUAL, userAccount.getEmail()));
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
			List<UserAccountTO> listReturn = new ArrayList<UserAccountTO>();
			for (Entity entity : entities) {
				listReturn.add(AnnotationUtils.getTransferObjectFromEntity(UserAccountTO.class, entity));
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
	
	public UserAccountTO insert(UserAccountTO userAccount) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			transaction.commit();
			return this.persist(userAccount);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public UserAccountTO save(UserAccountTO userAccount) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			transaction.commit();
			return this.persist(userAccount);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public UserAccountTO consult(UserAccountTO userAccount) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			userAccount = this.consultEntityById(userAccount);
			
			transaction.commit();
			return userAccount;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public void delete(UserAccountTO userAccount) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.deleteEntity(userAccount);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public boolean nicknameAlreadyExist(UserAccountTO userAccount) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			Query query = this.getQuery(UserAccountTO.class);
			query.setFilter(new FilterPredicate("nickname", FilterOperator.EQUAL, userAccount.getEmail()));
			query.addProjection(new PropertyProjection(AnnotationUtils.getIdFieldStringName(UserAccountTO.class), Long.class));
			PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
			return preparedQuery.countEntities(FetchOptions.Builder.withDefaults()) > 0;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	public boolean emailAlreadyExist(UserAccountTO userAccount) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			Query query = this.getQuery(UserAccountTO.class);
			query.setFilter(new FilterPredicate("email", FilterOperator.EQUAL, userAccount.getEmail()));
			query.addProjection(new PropertyProjection(AnnotationUtils.getIdFieldStringName(UserAccountTO.class), Long.class));
			PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
			return preparedQuery.countEntities(FetchOptions.Builder.withDefaults()) > 0;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	public UserAccountTO validateLogin(UserAccountTO userAccount) {
		try {
			Query query = this.getQuery(UserAccountTO.class);
			query.setFilter(CompositeFilterOperator.and(
					new FilterPredicate("nickname", Query.FilterOperator.EQUAL, userAccount.getNickname()),
					new FilterPredicate("password", Query.FilterOperator.EQUAL, userAccount.getPassword())
					));
			PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
			Entity entity = preparedQuery.asSingleEntity();
			
			if (entity == null) {
				throw new AppException("loginErrorUserNotFound");
			}
	
			return AnnotationUtils.getTransferObjectFromEntity(UserAccountTO.class, entity);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
