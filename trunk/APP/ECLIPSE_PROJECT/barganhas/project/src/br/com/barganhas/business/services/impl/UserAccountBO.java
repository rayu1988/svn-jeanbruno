package br.com.barganhas.business.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.business.services.persistencies.UserAccountPO;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.UserAccountStatus;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Transaction;

@Service("userAccountBO")
public class UserAccountBO implements UserAccount {
	
	public static final String						BEAN_ALIAS = "userAccountBO";

	@Autowired
	private UserAccountPO							persistencyLayer;
	
	@Autowired
	private File									fileService;
	
	@Override
	public List<UserAccountTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<UserAccountTO> listReturn = this.persistencyLayer.list();
			
			transaction.commit();
			return listReturn;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public List<UserAccountTO> filter(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<UserAccountTO> listReturn = this.persistencyLayer.filter(userAccount);
			
			transaction.commit();
			return listReturn;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public UserAccountTO insert(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			userAccount = this.persistencyLayer.insert(userAccount);
			
			transaction.commit();
			return userAccount;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public UserAccountTO consult(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			userAccount = this.persistencyLayer.consult(userAccount);
			
			transaction.commit();
			return userAccount;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public UserAccountTO save(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			UserAccountTO syncronized = this.consult(userAccount);
			if (!Util.isStringOk(userAccount.getPassword())) {
				userAccount.setPassword(syncronized.getPassword());
			}
			userAccount = this.persistencyLayer.save(userAccount);
			
			transaction.commit();
			return userAccount;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public UserAccountTO save(UserAccountTO userAccount, FileTO fileImage) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
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
			userAccount = this.save(userAccount);
			
			transaction.commit();
			return userAccount;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void delete(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			this.persistencyLayer.delete(userAccount);
			transaction.commit();
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public boolean nicknameAlreadyExist(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			boolean checking = this.persistencyLayer.nicknameAlreadyExist(userAccount);
			
			transaction.commit();
			return checking;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public boolean emailAlreadyExist(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			boolean checking = this.persistencyLayer.emailAlreadyExist(userAccount);
			
			transaction.commit();
			return checking;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public UserAccountTO validateLogin(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			userAccount = this.persistencyLayer.validateLogin(userAccount);
			
			transaction.commit();
			return userAccount;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void registerNewUser(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			userAccount.setSinceDate(new Date());
			//TODO replace the ACTIVE value to PENDING because when the user is registered he must confirm his register by email, and so will be ACTIVE
			userAccount.setStatus(UserAccountStatus.ACTIVE);
			userAccount = this.persistencyLayer.insert(userAccount);
			
			transaction.commit();
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

}
