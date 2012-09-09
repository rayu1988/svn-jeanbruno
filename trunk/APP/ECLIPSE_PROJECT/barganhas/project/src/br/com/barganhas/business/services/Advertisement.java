package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.AdvertisementTO;

public interface Advertisement {

	List<AdvertisementTO> list();

	AdvertisementTO insert(AdvertisementTO advertisement);

	AdvertisementTO consult(AdvertisementTO advertisement);
	
	AdvertisementTO save(AdvertisementTO advertisement);

	void delete(AdvertisementTO advertisement);
}
