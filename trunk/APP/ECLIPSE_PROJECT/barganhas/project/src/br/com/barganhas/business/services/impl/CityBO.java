package br.com.barganhas.business.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.services.City;
import br.com.barganhas.business.services.persistencies.CityPO;

import com.google.appengine.api.datastore.Transaction;

@Service("cityBO")
public class CityBO implements City {

	public static final String						BEAN_ALIAS = "cityBO";

	@Autowired
	private CityPO									persistencyLayer;
	
	@Override
	public CityTO insert(CityTO city) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			city = this.persistencyLayer.insert(city);
			
			transaction.commit();
			return city;
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
