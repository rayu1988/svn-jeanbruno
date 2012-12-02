package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.commons.AnnotationUtils;

@SuppressWarnings("serial")
@Repository
public class AdvertisementPicturePO extends AppPersistency {

	public List<AdvertisementPictureTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(AdvertisementPictureTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<AdvertisementPictureTO> listReturn = new ArrayList<AdvertisementPictureTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AdvertisementPictureTO.class, entity));
		}
		
		return listReturn;
	}
	
	public AdvertisementPictureTO insert(AdvertisementPictureTO advertisementPictureBase) {
		return this.persist(advertisementPictureBase);
	}
	
	public AdvertisementPictureTO insert(AdvertisementPictureTO advertisementPictureBase, AdvertisementTO ancestor) {
		return this.persist(advertisementPictureBase, ancestor);
	}

	public AdvertisementPictureTO save(AdvertisementPictureTO advertisementPicture) {
		return this.persist(advertisementPicture);
	}
	
	public AdvertisementPictureTO consult(AdvertisementPictureTO advertisementPicture) throws EntityNotFoundException {
		return this.consultByKey(advertisementPicture);
	}
	
	public void delete(AdvertisementPictureTO advertisementPicture) {
		this.deleteEntity(advertisementPicture);
	}
}
