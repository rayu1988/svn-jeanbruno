package br.com.barganhas.business.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import br.com.barganhas.business.entities.UserAccountTO;

public interface UserAccount {

	int 	MAX_WIDTH_IMG_PROFILE = 128;
	int 	MAX_HEIGHT_IMG_PROFILE = 128;
	
	List<UserAccountTO> list();

	List<UserAccountTO> filter(UserAccountTO userAccount);
	
	UserAccountTO insert(UserAccountTO userAccount);
	
	List<UserAccountTO> listHighlightedUsers();
	
	UserAccountTO consult(UserAccountTO userAccount);
	
	void lock(UserAccountTO userAccount);

	void unlock(UserAccountTO userAccount);

	void activate(UserAccountTO userAccount);
	
	UserAccountTO save(UserAccountTO userAccount);

	void delete(UserAccountTO userAccount);

	boolean nicknameAlreadyExist(UserAccountTO userAccount);
	
	boolean emailAlreadyExist(UserAccountTO userAccount);
	
	void registerNewUser(UserAccountTO userAccount) throws UnsupportedEncodingException, MessagingException;
	
	UserAccountTO validateLogin(UserAccountTO userAccount);
}
