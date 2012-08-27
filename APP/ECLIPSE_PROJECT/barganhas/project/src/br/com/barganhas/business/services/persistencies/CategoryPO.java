package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
@Repository
public class CategoryPO extends AppPersistency {

	public List<CategoryTO> list(CategoryTO category) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			List<Entity> entities = this.getSimplePreparedQuery(category).asList(FetchOptions.Builder.withDefaults());
			
			List<CategoryTO> listReturn = new ArrayList<CategoryTO>();
			for (Entity entity : entities) {
				listReturn.add(AnnotationUtils.getTransferObjectFromEntity(new CategoryTO(entity.getKey()), entity));
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
	
	public void insert(CategoryTO category) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.persist(category);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public void save(CategoryTO category) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.persist(category);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public CategoryTO consult(CategoryTO category) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			Entity entity = this.getEntity(category);
			category = AnnotationUtils.getTransferObjectFromEntity(new CategoryTO(entity.getKey()), entity);
			
			transaction.commit();
			return category;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public void delete(CategoryTO category) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.deleteEntity(category);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
}
