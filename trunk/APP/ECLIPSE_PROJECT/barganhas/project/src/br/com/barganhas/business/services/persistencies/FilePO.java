package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.TransferObject;
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
	
	public FileTO insert(FileTO file, TransferObject ancestorTO) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			transaction.commit();
			return this.persist(file, ancestorTO);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public FileTO save(FileTO file) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			transaction.commit();
			return this.persist(file);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public FileTO consult(FileTO file) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			Entity entity = this.getDataStoreService().get(file.getKey());
			transaction.commit();
			return AnnotationUtils.getTransferObjectFromEntity(FileTO.class, entity);
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
