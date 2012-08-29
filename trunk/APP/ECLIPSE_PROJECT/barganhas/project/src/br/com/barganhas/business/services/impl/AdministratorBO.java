package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.business.services.persistencies.AdministratorPO;

@Service("administratorBO")
public class AdministratorBO implements Administrator {

	public static final String						BEAN_ALIAS = "administratorBO";

	@Autowired
	private AdministratorPO							persistencyLayer;
	
	@Override
	public List<AdministratorTO> list(AdministratorTO administrator) {
		return this.persistencyLayer.list(administrator);
	}
	
	@Override
	public void insert(AdministratorTO administrator) {
		this.persistencyLayer.insert(administrator);
	}
	
	@Override
	public AdministratorTO consult(AdministratorTO administrator) {
		return this.persistencyLayer.consult(administrator);
	}
	
	@Override
	public void save(AdministratorTO administrator) {
		this.persistencyLayer.save(administrator);
	}

	@Override
	public void delete(AdministratorTO administrator) {
		this.persistencyLayer.delete(administrator);
	}

	@Override
	public AdministratorTO validateLogin(AdministratorTO administrator) {
		return this.persistencyLayer.validateLogin(administrator);
	}
}