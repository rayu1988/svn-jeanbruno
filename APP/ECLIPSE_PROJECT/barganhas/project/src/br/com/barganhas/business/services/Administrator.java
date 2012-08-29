package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.AdministratorTO;

public interface Administrator {

	List<AdministratorTO> list(AdministratorTO administrator);
	
	void insert(AdministratorTO administrator);

	AdministratorTO consult(AdministratorTO administrator);
	
	void save(AdministratorTO administrator);

	void delete(AdministratorTO administrator);
	
	AdministratorTO validateLogin(AdministratorTO administrator);
	
	void registerFirstAdministrator();
}
