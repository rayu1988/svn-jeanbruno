package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.commons.SearchingRequest;

public interface City {

	List<CityTO> list(StateTO state);

	List<CityTO> listFiter(SearchingRequest searchingRequest);
	
	CityTO insert(CityTO state);

	CityTO consult(CityTO city);
	
	boolean alreadyExists();

	void removeAll();
	
}
