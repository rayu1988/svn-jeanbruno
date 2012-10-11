package br.com.barganhas.business.services;

import br.com.barganhas.business.entities.CityTO;

public interface City {

	CityTO insert(CityTO state);

	boolean alreadyExists();

	void removeAll();
	
}
