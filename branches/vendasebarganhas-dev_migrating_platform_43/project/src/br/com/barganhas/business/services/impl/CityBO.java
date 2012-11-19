package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.services.City;
import br.com.barganhas.business.services.persistencies.CityPO;
import br.com.barganhas.commons.SearchingRequest;

@Service("cityBO")
public class CityBO implements City {

	public static final String						BEAN_ALIAS = "cityBO";

	@Autowired
	private CityPO									persistencyLayer;
	
	@Override
	@Transactional(readOnly = true)
	public List<CityTO> list(StateTO state) {
		return this.persistencyLayer.list(state);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CityTO> listFiter(SearchingRequest searchingRequest) {
		return this.persistencyLayer.listFiter(searchingRequest);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CityTO consult(CityTO city) {
		return this.persistencyLayer.consult(city);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CityTO insert(CityTO city) {
		return this.persistencyLayer.insert(city);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean alreadyExists() {
		return this.persistencyLayer.alreadyExists();
	}
	
	@Override
	@Transactional(readOnly = true)
	public void removeAll() {
		this.persistencyLayer.removeAll();
	}
}
