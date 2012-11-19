package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.StateTO;

public interface State {

	List<StateTO> list();
	
	StateTO insert(StateTO state);
	
	StateTO consult(StateTO state);
	
	StateTO consultAcronym(String acronym);

	boolean alreadyExists();

	void removeAll();
}
