package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
@Repository
public class FilePO extends AppPersistency {

	public List<FileTO> list() {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			List<Entity> entities = this.getSimplePreparedQuery(FileTO.class).asList(FetchOptions.Builder.withDefaults());
			
			List<FileTO> listReturn = new ArrayList<FileTO>();
			for (Entity entity : entities) {
				listReturn.add(AnnotationUtils.getTransferObjectFromEntity(FileTO.class, entity));
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
	
	public void insert(FileTO file) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.persist(file);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public void save(FileTO file) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.persist(file);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public FileTO consult(FileTO file) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			file = this.consultEntityById(file);
			
			transaction.commit();
			return file;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public void delete(FileTO file) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.deleteEntity(file);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
}
