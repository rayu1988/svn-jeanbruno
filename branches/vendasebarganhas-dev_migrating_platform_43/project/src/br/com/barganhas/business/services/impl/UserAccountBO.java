package br.com.barganhas.business.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.com.tatu.helper.GeneralsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Mail;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.business.services.persistencies.UserAccountPO;
import br.com.barganhas.enums.UserAccountStatus;

@Service("userAccountBO")
public class UserAccountBO implements UserAccount {
	
	public static final String						BEAN_ALIAS = "userAccountBO";

	@Autowired
	private UserAccountPO							persistencyLayer;
	
	@Autowired
	private Mail									mailService;
	
	@Override
	@Transactional(readOnly = true)
	public List<UserAccountTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserAccountTO> filter(UserAccountTO userAccount) {
		return this.persistencyLayer.filter(userAccount);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserAccountTO insert(UserAccountTO userAccount) {
		this.beforePersist(userAccount);
		return this.persistencyLayer.insert(userAccount);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserAccountTO consult(UserAccountTO userAccount) {
		return this.persistencyLayer.consult(userAccount);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserAccountTO> listHighlightedUsers() {
		return this.persistencyLayer.listHighlightedUsers();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void lock(UserAccountTO userAccount) {
		this.persistencyLayer.lock(userAccount);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void unlock(UserAccountTO userAccount) {
		this.persistencyLayer.activate(userAccount);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void activate(UserAccountTO userAccount) {
		userAccount = this.consult(userAccount);
		if (userAccount == null || !userAccount.getStatus().equals(UserAccountStatus.PENDING)) {
			throw new AppException("userAccountActivatedProblem");
		}
		
		this.persistencyLayer.activate(userAccount);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserAccountTO save(UserAccountTO userAccount) {
		UserAccountTO syncronized = this.consult(userAccount);
		if (!GeneralsHelper.isStringOk(userAccount.getPassword())) {
			userAccount.setPassword(syncronized.getPassword());
		}
		
		this.beforePersist(userAccount);
		return this.persistencyLayer.save(userAccount);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(UserAccountTO userAccount) {
		this.persistencyLayer.delete(userAccount);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean nicknameAlreadyExist(UserAccountTO userAccount) {
		return this.persistencyLayer.nicknameAlreadyExist(userAccount);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean emailAlreadyExist(UserAccountTO userAccount) {
		return this.persistencyLayer.emailAlreadyExist(userAccount);
	}

	@Override
	@Transactional(readOnly = true)
	public UserAccountTO validateLogin(UserAccountTO userAccount) {
		return this.persistencyLayer.validateLogin(userAccount);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void registerNewUser(UserAccountTO userAccount) throws UnsupportedEncodingException, MessagingException {
		userAccount.setSinceDate(new Date());
		userAccount.setStatus(UserAccountStatus.PENDING);
		userAccount = this.persistencyLayer.insert(userAccount);
		
		this.mailService.mailNewUser(userAccount);
	}
	
	private void beforePersist(UserAccountTO userAccount) {
		if (userAccount.getPassword().length() < 8) {
			throw new AppException("passphraseMinMessage");
		}
	}

}
