package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.AdvertisementTypeTO;

public interface AdvertisementType {

	List<AdvertisementTypeTO> list();
	
	AdvertisementTypeTO insert(AdvertisementTypeTO advertisementType);

	AdvertisementTypeTO consult(AdvertisementTypeTO advertisementType) throws EntityNotFoundException;
	
	AdvertisementTypeTO save(AdvertisementTypeTO advertisementType);

	void delete(AdvertisementTypeTO advertisementType);
}
