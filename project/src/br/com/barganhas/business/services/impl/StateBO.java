package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.services.State;
import br.com.barganhas.business.services.persistencies.StatePO;

@Service("stateBO")
public class StateBO implements State {

	public static final String						BEAN_ALIAS = "stateBO";

	@Autowired
	private StatePO									persistencyLayer;
	
	@Override
	@Transactional(readOnly = true)
	public List<StateTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public StateTO insert(StateTO state) {
		return this.persistencyLayer.insert(state);
	}
	
	@Override
	@Transactional(readOnly = true)
	public StateTO consult(StateTO state) {
		return this.persistencyLayer.consult(state);
	}
	
	@Override
	@Transactional(readOnly = true)
	public StateTO consultAcronym(String acronym) {
		return this.persistencyLayer.consultAcronym(acronym);
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
