package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;

@SuppressWarnings("serial")
@Repository
public class AdvertisementPO extends AppPersistency {

	public List<AdvertisementTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(AdvertisementTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<AdvertisementTO> listReturn = new ArrayList<AdvertisementTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementTO.class, entity));
		}
		
		return listReturn;
	}
	
	public AdvertisementTO insert(AdvertisementTO advertisement) {
		return this.persist(advertisement);
	}
	
	public AdvertisementTO save(AdvertisementTO advertisement) {
		return this.persist(advertisement);
	}

	public AdvertisementTO consult(AdvertisementTO advertisement) {
		advertisement = this.consultEntityById(advertisement);
		return advertisement;
	}

	public void delete(AdvertisementTO advertisement) {
		this.deleteEntity(advertisement);
	}
}
