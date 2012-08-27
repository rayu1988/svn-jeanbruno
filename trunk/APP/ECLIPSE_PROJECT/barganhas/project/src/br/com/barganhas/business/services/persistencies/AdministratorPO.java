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
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
@Repository
public class AdministratorPO extends AppPersistency {

	public List<AdministratorTO> list(AdministratorTO administrator) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			List<Entity> entities = this.getSimplePreparedQuery(administrator).asList(FetchOptions.Builder.withDefaults());
			
			List<AdministratorTO> listReturn = new ArrayList<AdministratorTO>();
			for (Entity entity : entities) {
				listReturn.add(AnnotationUtils.getTransferObjectFromEntity(new AdministratorTO(entity.getKey()), entity));
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
	
	public void insert(AdministratorTO administrator) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.persist(administrator);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public void save(AdministratorTO administrator) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			if (!Util.isStringOk(administrator.getPassword())) {
				AdministratorTO syncronizedTO = this.consult(administrator);
				administrator.setPassword(syncronizedTO.getPassword());
			}
			
			this.persist(administrator);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public AdministratorTO consult(AdministratorTO administrator) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			Entity entity = this.getEntity(administrator);
			administrator = AnnotationUtils.getTransferObjectFromEntity(new AdministratorTO(entity.getKey()), entity);
			
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
			Query query = this.getQuery(administrator);
			query.setFilter(CompositeFilterOperator.and(
					new FilterPredicate("nickname", Query.FilterOperator.EQUAL, administrator.getNickname()),
					new FilterPredicate("password", Query.FilterOperator.EQUAL, administrator.getPassword())
					));
			PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
			Entity entity = preparedQuery.asSingleEntity();

			return AnnotationUtils.getTransferObjectFromEntity(new AdministratorTO(entity.getKey()), entity);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
