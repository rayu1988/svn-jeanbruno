package br.com.barganhas.business.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.business.services.persistencies.UserAccountPO;
import br.com.barganhas.enums.UserAccountStatus;

@Service("userAccountBO")
public class UserAccountBO implements UserAccount {

	public static final String						BEAN_ALIAS = "userAccountBO";

	@Autowired
	private UserAccountPO							persistencyLayer;
	
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
		this.persistencyLayer.save(userAccount);
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
