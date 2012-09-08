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
	public UserAccountTO insert(UserAccountTO userAccount) {
		return this.persistencyLayer.insert(userAccount);
	}
	
	@Override
	public UserAccountTO consult(UserAccountTO userAccount) {
		return this.persistencyLayer.consult(userAccount);
	}
	
	@Override
	public UserAccountTO save(UserAccountTO userAccount) {
		UserAccountTO syncronized = this.consult(userAccount);
		if (!Util.isStringOk(userAccount.getPassword())) {
			userAccount.setPassword(syncronized.getPassword());
		}
		
		return this.persistencyLayer.save(userAccount);
	}
	
	@Override
	public UserAccountTO save(UserAccountTO userAccount, FileTO fileImage) {
		if (fileImage != null) {
			Blob profileImage = Util.transformBlobImage(fileImage.getData(), MAX_HEIGHT_IMG_PROFILE, MAX_WIDTH_IMG_PROFILE);
			if (userAccount.getKeyProfileImage() == null) {
				fileImage.setData(profileImage);
				fileImage = this.fileService.insert(fileImage, userAccount);
				userAccount.setKeyProfileImage(fileImage.getKey());
			} else {
				FileTO synchronizedProfileImage = this.fileService.consult(new FileTO(userAccount.getKeyProfileImage()));
				synchronizedProfileImage.setContentType(fileImage.getContentType());
				synchronizedProfileImage.setFileName(fileImage.getFileName());
				synchronizedProfileImage.setData(profileImage);
				this.fileService.save(synchronizedProfileImage);
			}
		}
		
		return this.save(userAccount);
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
