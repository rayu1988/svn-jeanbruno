package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.business.services.persistencies.AdministratorPO;

@Service("administratorBO")
public class AdministratorBO implements Administrator {

	public static final String						BEAN_ALIAS = "administratorBO";

	@Autowired
	private AdministratorPO							persistencyLayer;
	
	@Override
	@Transactional(readOnly = true)
	public List<AdministratorTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AdministratorTO> filter(AdministratorTO administrator) {
		return this.persistencyLayer.filter(administrator);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdministratorTO insert(AdministratorTO administrator) {
		return this.persistencyLayer.insert(administrator);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdministratorTO consult(AdministratorTO administrator) {
		return this.persistencyLayer.consult(administrator);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdministratorTO save(AdministratorTO administrator) {
		return this.persistencyLayer.save(administrator);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(AdministratorTO administrator) {
		this.persistencyLayer.delete(administrator);
	}

	@Override
	@Transactional(readOnly = true)
	public AdministratorTO validateLogin(AdministratorTO administrator) {
		return this.persistencyLayer.validateLogin(administrator);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void registerFirstAdministrator() {
		long totalAdministrators = this.persistencyLayer.countAdvertisements();
		
		if (totalAdministrators <= 0) {
			AdministratorTO administrator = new AdministratorTO();
			administrator.setFullname("Administrator Master");
			administrator.setEmail("admin@mail.com");
			administrator.setNickname("admin");
			administrator.setPassword("admin");
			
			this.insert(administrator);
		}
	}
}
