package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.commons.SearchingRequest;
import br.com.barganhas.commons.SearchingResponse;

public interface Advertisement {

	List<AdvertisementTO> list();

	List<AdvertisementTO> list(UserAccountTO userAccount);
	
	List<AdvertisementTO> myLastAdvertisements(UserAccountTO userAccount);

	List<AdvertisementTO> lastAdvertisements();

	List<AdvertisementTO> mostViewed();

	List<AdvertisementTO> userAccountLastAdvertisements(UserAccountTO userAccount);

	AdvertisementTO insert(AdvertisementTO advertisement);

	AdvertisementTO adminConsult(AdvertisementTO advertisement);
	
	AdvertisementTO consult(AdvertisementTO advertisement);
	
	AdvertisementTO lock(AdvertisementTO advertisement);

	AdvertisementTO unlock(AdvertisementTO advertisement);

	AdvertisementTO publicConsult(AdvertisementTO advertisement);
	
	SearchingResponse publicSearch(SearchingRequest searchingRequest);
	
	AdvertisementTO save(AdvertisementTO advertisement);

	void delete(AdvertisementTO advertisement);

	/**
	 * Method to delete every UserAccount's advertisement.
	 * @param owner
	 * @throws EntityNotFoundException
	 */
	void delete(UserAccountTO owner);
}
