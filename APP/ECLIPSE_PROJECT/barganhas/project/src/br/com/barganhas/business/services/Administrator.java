package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.AdministratorTO;

public interface Administrator {

	List<AdministratorTO> list();

	List<AdministratorTO> filter(AdministratorTO administrator);
	
	AdministratorTO insert(AdministratorTO administrator);

	AdministratorTO consult(AdministratorTO administrator) throws EntityNotFoundException;
	
	AdministratorTO save(AdministratorTO administrator) throws EntityNotFoundException;

	void delete(AdministratorTO administrator);
	
	AdministratorTO validateLogin(AdministratorTO administrator);
	
	void registerFirstAdministrator();
}
