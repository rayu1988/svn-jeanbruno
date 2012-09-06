package br.com.barganhas.business.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.business.services.persistencies.UserAccountPO;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.UserAccountStatus;

import com.google.appengine.api.datastore.Blob;

@Service("userAccountBO")
public class UserAccountBO implements UserAccount {

	public static final String						BEAN_ALIAS = "userAccountBO";

	public static final int							MAX_WIDTH_IMG_PROFILE = 128;
	public static final int							MAX_HEIGHT_IMG_PROFILE = 128;
	
	@Autowired
	private UserAccountPO							persistencyLayer;
	
	@Autowired
	private File									fileService;
	
	@Override
	public List<UserAccountTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	public List<UserAccountTO> filter(UserAccountTO userAccount) {
		return this.persistencyLayer.filter(userAccount);
	}
	
	@Override
	public void insert(UserAccountTO userAccount) {
		this.persistencyLayer.insert(userAccount);
	}
	
	@Override
	public UserAccountTO consult(UserAccountTO userAccount) {
		return this.persistencyLayer.consult(userAccount);
	}
	
	@Override
	public void save(UserAccountTO userAccount) {
		UserAccountTO syncronized = this.consult(userAccount);
		if (syncronized.getProfileImage() != null) {
			FileTO file = this.fileService.serverFile(syncronized.getProfileImage());
			this.fileService.delete(file);
		}
		if (!Util.isStringOk(userAccount.getPassword())) {
			userAccount.setPassword(syncronized.getPassword());
		}
		
		this.persistencyLayer.save(userAccount);
	}
	
	@Override
	public void save(UserAccountTO userAccount, FileTO fileImage) {
		if (fileImage != null) {
			Blob profileImage = Util.transformBlobImage(fileImage.getData(), MAX_HEIGHT_IMG_PROFILE, MAX_WIDTH_IMG_PROFILE);
			fileImage.setData(profileImage);
			this.fileService.insert(fileImage);
			userAccount.setProfileImage(fileImage.getId());
		}
		
		this.save(userAccount);
	}

	@Override
	public void delete(UserAccountTO userAccount) {
		this.persistencyLayer.delete(userAccount);
	}
	
	@Override
	public boolean nicknameAlreadyExist(UserAccountTO userAccount) {
		return this.persistencyLayer.nicknameAlreadyExist(userAccount);
	}
	
	@Override
	public boolean emailAlreadyExist(UserAccountTO userAccount) {
		return this.persistencyLayer.emailAlreadyExist(userAccount);
	}

	@Override
	public UserAccountTO validateLogin(UserAccountTO userAccount) {
		return this.persistencyLayer.validateLogin(userAccount);
	}

	@Override
	public void registerNewUser(UserAccountTO userAccount) {
		userAccount.setSinceDate(new Date());
		//TODO replace the ACTIVE value to PENDING because when the user is registered he must confirm his register by email, and so will be ACTIVE
		userAccount.setStatus(UserAccountStatus.ACTIVE);
		this.persistencyLayer.insert(userAccount);
	}

}
