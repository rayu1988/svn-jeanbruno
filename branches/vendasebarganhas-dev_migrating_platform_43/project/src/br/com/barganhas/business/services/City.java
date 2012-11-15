package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.StateTO;

public interface City {

	List<CityTO> list(StateTO state);
	
	CityTO insert(CityTO state);

	CityTO consult(CityTO city) throws EntityNotFoundException;
	
	boolean alreadyExists();

	void removeAll();
	
}
