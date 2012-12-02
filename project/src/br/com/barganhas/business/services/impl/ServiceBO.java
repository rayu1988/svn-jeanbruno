package br.com.barganhas.business.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.TransferObject;
import br.com.barganhas.business.services.persistencies.ServicePO;

@Service("serviceBO")
public class ServiceBO implements br.com.barganhas.business.services.Service {

	public static final String							BEAN_ALIAS = "serviceBO";

	@Autowired
	private ServicePO									persistencyLayer;

	@Override
	public <T extends TransferObject> int count(Class<T> targetTO) {
		return this.persistencyLayer.count(targetTO);
	}
	
	
}
