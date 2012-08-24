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
	@Transactional(readOnly=true)
	public List<AdministratorTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void insert(AdministratorTO administrator) {
		this.persistencyLayer.insert(administrator);
	}
	
	@Override
	@Transactional(readOnly=true)
	public AdministratorTO consult(AdministratorTO administrator) {
		return this.persistencyLayer.consult(administrator);
	}
	
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void save(AdministratorTO administrator) {
		this.persistencyLayer.save(administrator);
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void delete(AdministratorTO administrator) {
		this.persistencyLayer.delete(administrator);
	}
}
