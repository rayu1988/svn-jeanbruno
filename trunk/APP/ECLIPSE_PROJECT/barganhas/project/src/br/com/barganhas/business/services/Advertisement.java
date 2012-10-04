package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.UserAccountTO;

public interface Advertisement {

	List<AdvertisementTO> list();

	List<AdvertisementTO> list(UserAccountTO userAccount);
	
	List<AdvertisementTO> myLastAdvertisements(UserAccountTO userAccount);

	List<AdvertisementTO> lastAdvertisements() throws EntityNotFoundException;

	List<AdvertisementTO> mostViewed() throws EntityNotFoundException;

	AdvertisementTO insert(AdvertisementTO advertisement) throws EntityNotFoundException;

	AdvertisementTO consult(AdvertisementTO advertisement) throws EntityNotFoundException;

	AdvertisementTO publicConsult(AdvertisementTO advertisement) throws EntityNotFoundException;
	
	List<AdvertisementTO> publicSearch(String searchText) throws EntityNotFoundException;
	
	AdvertisementTO save(AdvertisementTO advertisement);

	void delete(AdvertisementTO advertisement) throws EntityNotFoundException;
}
