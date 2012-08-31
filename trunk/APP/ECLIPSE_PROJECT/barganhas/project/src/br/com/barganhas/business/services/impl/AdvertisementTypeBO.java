package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.business.services.persistencies.AdvertisementTypePO;

@Service("advertisementTypeBO")
public class AdvertisementTypeBO implements AdvertisementType {

	public static final String						BEAN_ALIAS = "advertisementTypeBO";

	@Autowired
	private AdvertisementTypePO						persistencyLayer;
	
	@Override
	public List<AdvertisementTypeTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	public void insert(AdvertisementTypeTO advertisementType) {
		this.persistencyLayer.insert(advertisementType);
	}
	
	@Override
	public AdvertisementTypeTO consult(AdvertisementTypeTO advertisementType) {
		return this.persistencyLayer.consult(advertisementType);
	}
	
	@Override
	public void save(AdvertisementTypeTO advertisementType) {
		this.persistencyLayer.save(advertisementType);
	}

	@Override
	public void delete(AdvertisementTypeTO advertisementType) {
		this.persistencyLayer.delete(advertisementType);
	}
}
