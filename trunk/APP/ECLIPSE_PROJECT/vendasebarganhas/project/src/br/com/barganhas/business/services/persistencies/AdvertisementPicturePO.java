package br.com.barganhas.business.services.persistencies;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;

@SuppressWarnings("serial")
@Repository
public class AdvertisementPicturePO extends AppPersistency {

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
