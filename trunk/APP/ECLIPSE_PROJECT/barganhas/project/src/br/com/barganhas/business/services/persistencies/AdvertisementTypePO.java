package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
@Repository
public class AdvertisementTypePO extends AppPersistency {

	public List<AdvertisementTypeTO> list() {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			List<Entity> entities = this.getSimplePreparedQuery(AdvertisementTypeTO.class).asList(FetchOptions.Builder.withDefaults());
			
			List<AdvertisementTypeTO> listReturn = new ArrayList<AdvertisementTypeTO>();
			for (Entity entity : entities) {
				listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTypeTO.class, entity));
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
	
	public void insert(AdvertisementTypeTO advertisementType) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.persist(advertisementType);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	public void save(AdvertisementTypeTO advertisementType) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.persist(advertisementType);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public AdvertisementTypeTO consult(AdvertisementTypeTO advertisementType) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			advertisementType = this.consultEntityById(advertisementType);
			
			transaction.commit();
			return advertisementType;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	public void delete(AdvertisementTypeTO advertisementType) {
		Transaction transaction = this.getDataStoreService().beginTransaction();
		try {
			this.deleteEntity(advertisementType);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
}
