package br.com.barganhas.business.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.UserAccountTO;

public interface UserAccount {

	int 	MAX_WIDTH_IMG_PROFILE = 128;
	int 	MAX_HEIGHT_IMG_PROFILE = 128;
	
	List<UserAccountTO> list(Integer startFrom);

	UserAccountTO insert(UserAccountTO userAccount);
	
	UserAccountTO incrementCountAdvertisement(UserAccountTO userAccount) throws EntityNotFoundException;

	List<UserAccountTO> listHighlightedUsers() throws EntityNotFoundException;
	
	UserAccountTO consult(UserAccountTO userAccount) throws EntityNotFoundException;
	
	UserAccountTO lock(UserAccountTO userAccount) throws EntityNotFoundException;

	UserAccountTO unlock(UserAccountTO userAccount) throws EntityNotFoundException;

	UserAccountTO activate(UserAccountTO userAccount) throws EntityNotFoundException;
	
	UserAccountTO save(UserAccountTO userAccount) throws EntityNotFoundException;

	UserAccountTO save(UserAccountTO userAccount, FileTO fileImage) throws EntityNotFoundException;

	void delete(UserAccountTO userAccount) throws EntityNotFoundException;

	boolean nicknameAlreadyExist(UserAccountTO userAccount);
	
	boolean emailAlreadyExist(UserAccountTO userAccount);
	
	void registerNewUser(UserAccountTO userAccount) throws UnsupportedEncodingException, MessagingException;
	
	UserAccountTO validateLogin(UserAccountTO userAccount);
}
