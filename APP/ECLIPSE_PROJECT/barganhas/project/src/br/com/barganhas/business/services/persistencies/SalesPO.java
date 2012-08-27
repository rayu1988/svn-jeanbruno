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

	public List<SalesTO> list(SalesTO sales) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			List<Entity> entities = this.getSimplePreparedQuery(sales).asList(FetchOptions.Builder.withDefaults());
			
			List<SalesTO> listReturn = new ArrayList<SalesTO>();
			for (Entity entity : entities) {
				listReturn.add(AnnotationUtils.getTransferObjectFromEntity(new SalesTO(entity.getKey()), entity));
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
	
	public void insert(SalesTO sales) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.persist(sales);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public void save(SalesTO sales) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.persist(sales);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public SalesTO consult(SalesTO sales) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			Entity entity = this.getEntity(sales);
			sales = AnnotationUtils.getTransferObjectFromEntity(new SalesTO(entity.getKey()), entity);
			
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