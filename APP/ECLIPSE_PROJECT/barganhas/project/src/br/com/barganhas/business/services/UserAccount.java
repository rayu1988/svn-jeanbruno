package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.UserAccountTO;

public interface UserAccount {

	int 	MAX_WIDTH_IMG_PROFILE = 128;
	int 	MAX_HEIGHT_IMG_PROFILE = 128;
	
	List<UserAccountTO> list();

	List<UserAccountTO> filter(UserAccountTO userAccount);
	
	UserAccountTO insert(UserAccountTO userAccount);

	UserAccountTO consult(UserAccountTO userAccount);
	
	UserAccountTO save(UserAccountTO userAccount);

	UserAccountTO save(UserAccountTO userAccount, FileTO fileImage) throws EntityNotFoundException;

	void delete(UserAccountTO userAccount);

	boolean nicknameAlreadyExist(UserAccountTO userAccount);
	
	boolean emailAlreadyExist(UserAccountTO userAccount);
	
	void registerNewUser(UserAccountTO userAccount);
	
	UserAccountTO validateLogin(UserAccountTO userAccount);
}
