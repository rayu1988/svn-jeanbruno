package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.AdministratorTO;

public interface Administrator {

	List<AdministratorTO> list();

	List<AdministratorTO> filter(AdministratorTO administrator);
	
	AdministratorTO insert(AdministratorTO administrator);

	AdministratorTO consult(AdministratorTO administrator);
	
	AdministratorTO save(AdministratorTO administrator);

	void delete(AdministratorTO administrator);
	
	AdministratorTO validateLogin(AdministratorTO administrator);
	
	void registerFirstAdministrator();
}
