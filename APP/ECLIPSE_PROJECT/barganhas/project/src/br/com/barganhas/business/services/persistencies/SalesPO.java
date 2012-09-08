package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
@Repository
public class SalesPO extends AppPersistency {

	public List<SalesTO> list() {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			List<Entity> entities = this.getSimplePreparedQuery(SalesTO.class).asList(FetchOptions.Builder.withDefaults());
			
			List<SalesTO> listReturn = new ArrayList<SalesTO>();
			for (Entity entity : entities) {
				listReturn.add(AnnotationUtils.getTransferObjectFromEntity(SalesTO.class, entity));
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
	
	public SalesTO insert(SalesTO sales) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			transaction.commit();
			return this.persist(sales);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public SalesTO save(SalesTO sales) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			transaction.commit();
			return this.persist(sales);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public SalesTO consult(SalesTO sales) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			sales = this.consultEntityById(sales);
			
			transaction.commit();
			return sales;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public void delete(SalesTO sales) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.deleteEntity(sales);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
}
