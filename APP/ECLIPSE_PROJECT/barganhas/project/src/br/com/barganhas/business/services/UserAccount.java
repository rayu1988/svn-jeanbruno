package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.UserAccountTO;

public interface UserAccount {

	List<UserAccountTO> list();

	List<UserAccountTO> filter(UserAccountTO userAccount);
	
	void insert(UserAccountTO userAccount);

	UserAccountTO consult(UserAccountTO userAccount);
	
	void save(UserAccountTO userAccount);

	void delete(UserAccountTO userAccount);

	boolean nicknameAlreadyExist(UserAccountTO userAccount);
	
	boolean emailAlreadyExist(UserAccountTO userAccount);
	
	UserAccountTO validateLogin(UserAccountTO userAccount);
}
