package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.business.services.persistencies.AdvertisementTypePO;

@Service("advertisementTypeBO")
public class AdvertisementTypeBO implements AdvertisementType {

	public static final String						BEAN_ALIAS = "advertisementTypeBO";

	@Autowired
	private AdvertisementTypePO						persistencyLayer;
	
	@Override
	@Transactional(readOnly = true)
	public List<AdvertisementTypeTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdvertisementTypeTO insert(AdvertisementTypeTO advertisementType) {
		return this.persistencyLayer.insert(advertisementType);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdvertisementTypeTO consult(AdvertisementTypeTO advertisementType) {
		return this.persistencyLayer.consult(advertisementType);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdvertisementTypeTO save(AdvertisementTypeTO advertisementType) {
		return this.persistencyLayer.save(advertisementType);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(AdvertisementTypeTO advertisementType) {
		this.persistencyLayer.delete(advertisementType);
	}
}
