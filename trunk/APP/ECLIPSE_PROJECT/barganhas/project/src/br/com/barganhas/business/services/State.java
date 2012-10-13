package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.StateTO;

public interface State {

	List<StateTO> list();
	
	StateTO insert(StateTO state);
	
	StateTO consult(StateTO state) throws EntityNotFoundException;
	
	StateTO consultAcronym(String acronym);

	boolean alreadyExists();

	void removeAll();
}
