package br.com.laptracker.business.service;

import br.com.laptracker.business.entities.UserTO;

public interface User {

	UserTO validateLogin(UserTO user);
	
}
