package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.UserAccountTO;

public interface Advertisement {

	List<AdvertisementTO> list();

	List<AdvertisementTO> list(UserAccountTO userAccount);
	
	List<AdvertisementTO> myLastAdvertisements(UserAccountTO userAccount);

	List<AdvertisementTO> lastAdvertisements();

	List<AdvertisementTO> lastMostViewed();

	AdvertisementTO insert(AdvertisementTO advertisement);

	AdvertisementTO consult(AdvertisementTO advertisement);
	
	AdvertisementTO save(AdvertisementTO advertisement);

	void delete(AdvertisementTO advertisement);
}
