package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.AdvertisementTypeTO;

public interface AdvertisementType {

	List<AdvertisementTypeTO> list();
	
	void insert(AdvertisementTypeTO advertisementType);

	AdvertisementTypeTO consult(AdvertisementTypeTO advertisementType);
	
	void save(AdvertisementTypeTO advertisementType);

	void delete(AdvertisementTypeTO advertisementType);
}
