package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.CityTO;

import com.google.appengine.api.datastore.EntityNotFoundException;

public interface City {

	List<CityTO> list(Integer startFrom);
	
	CityTO insert(CityTO state);

	CityTO consult(CityTO city) throws EntityNotFoundException;
	
	boolean alreadyExists();

	void removeAll();
	
}
