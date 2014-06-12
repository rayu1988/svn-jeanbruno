package br.com.laptracker.business.service.impl;

import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.persistence.UserPO;
import br.com.laptracker.business.service.User;

public class UserBO implements User {

	@Override
	public UserTO validateLogin(UserTO user) {
		return new UserPO().validateLogin(user);
	}

}
