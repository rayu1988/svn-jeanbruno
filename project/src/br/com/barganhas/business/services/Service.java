package br.com.barganhas.business.services;

import br.com.barganhas.business.entities.TransferObject;

public interface Service {

	<T extends TransferObject> int count(Class<T> targetTO);
	
}
