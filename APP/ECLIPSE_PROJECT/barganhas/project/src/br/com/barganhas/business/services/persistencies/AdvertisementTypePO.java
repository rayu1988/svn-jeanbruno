package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;

@SuppressWarnings("serial")
@Repository
public class AdvertisementTypePO extends AppPersistency {

	public List<AdvertisementTypeTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(AdvertisementTypeTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<AdvertisementTypeTO> listReturn = new ArrayList<AdvertisementTypeTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTypeTO.class, entity));
		}
		
		return listReturn;
	}
	
	public AdvertisementTypeTO insert(AdvertisementTypeTO advertisementType) {
		return this.persist(advertisementType);
	}
	
	public AdvertisementTypeTO save(AdvertisementTypeTO advertisementType) {
		return this.persist(advertisementType);
	}

	public AdvertisementTypeTO consult(AdvertisementTypeTO advertisementType) {
		return this.consultEntityById(advertisementType);
	}

	public void delete(AdvertisementTypeTO advertisementType) {
		this.deleteEntity(advertisementType);
	}
}
