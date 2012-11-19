package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.UseTermTO;

public interface UseTerm {

	List<UseTermTO> list();
	
	UseTermTO insert(UseTermTO useTerm);

	UseTermTO consult(UseTermTO useTerm);

	UseTermTO getDefaultUseTerm();

	UseTermTO turnUseTermDefault(UseTermTO useTerm);
	
	UseTermTO save(UseTermTO useTerm);

	void delete(UseTermTO useTerm);
}
