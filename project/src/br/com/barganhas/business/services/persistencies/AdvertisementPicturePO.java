package br.com.barganhas.business.services.persistencies;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class AdvertisementPicturePO extends AppPersistencyManagement {

	public AdvertisementPictureTO insert(AdvertisementPictureTO advertisementPicture) {
		this.getHibernateDao().insert(advertisementPicture);
		return advertisementPicture;
	}
	
	public AdvertisementPictureTO save(AdvertisementPictureTO advertisementPicture) {
		this.getHibernateDao().update(advertisementPicture);
		return advertisementPicture;
	}
	
	public AdvertisementPictureTO consult(AdvertisementPictureTO advertisementPicture) {
		return this.getHibernateDao().consult(advertisementPicture);
	}
	
	public void delete(AdvertisementPictureTO advertisementPicture) {
		this.getHibernateDao().delete(advertisementPicture);
	}
}
