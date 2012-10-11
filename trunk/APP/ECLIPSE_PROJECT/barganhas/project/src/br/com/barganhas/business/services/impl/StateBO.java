package br.com.barganhas.business.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.services.State;
import br.com.barganhas.business.services.persistencies.StatePO;

import com.google.appengine.api.datastore.Transaction;

@Service("stateBO")
public class StateBO implements State {

	public static final String						BEAN_ALIAS = "stateBO";

	@Autowired
	private StatePO									persistencyLayer;
	
	@Override
	public StateTO insert(StateTO state) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			state = this.persistencyLayer.insert(state);
			
			transaction.commit();
			return state;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public StateTO consultAcronym(String acronym) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			StateTO state = this.persistencyLayer.consultAcronym(acronym);
			transaction.commit();
			return state;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public boolean alreadyExists() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			boolean exists = this.persistencyLayer.alreadyExists();
			transaction.commit();
			return exists;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public void removeAll() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			this.persistencyLayer.removeAll();
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
}
