package br.com.barganhas.business.services;

import br.com.barganhas.business.entities.StateTO;

public interface State {

	StateTO insert(StateTO state);
	
	StateTO consultAcronym(String acronym);

	boolean alreadyExists();

	void removeAll();
}
