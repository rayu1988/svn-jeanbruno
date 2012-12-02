package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.FileTO;


public interface AdvertisementPicture {

	int 	MAX_WIDTH_THUMBNAIL = 110;
	int 	MAX_HEIGHT_THUMBNAIL = 83;
	int 	MAX_WIDTH_PICTURE = 588;
	int 	MAX_HEIGHT_PICTURE = 441;
	
	List<AdvertisementPictureTO> list();
	
	AdvertisementPictureTO newAdvertisementPicture(FileTO imageBase);

	AdvertisementPictureTO insert(AdvertisementPictureTO advertisementPictureBase) throws EntityNotFoundException;
	
	AdvertisementPictureTO consult(AdvertisementPictureTO advertisementPicture) throws EntityNotFoundException;
	
	void delete(AdvertisementPictureTO advertisementPicture) throws EntityNotFoundException;
}
