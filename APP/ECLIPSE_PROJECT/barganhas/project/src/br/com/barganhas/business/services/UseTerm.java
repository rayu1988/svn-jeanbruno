package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.UseTermTO;

public interface UseTerm {

	List<UseTermTO> list();
	
	UseTermTO insert(UseTermTO useTerm);

	UseTermTO consult(UseTermTO useTerm) throws EntityNotFoundException;

	UseTermTO getDefaultUseTerm() throws EntityNotFoundException;

	UseTermTO turnUseTermDefault(UseTermTO useTerm) throws EntityNotFoundException;
	
	UseTermTO save(UseTermTO useTerm);

	void delete(UseTermTO useTerm) throws EntityNotFoundException;
}
