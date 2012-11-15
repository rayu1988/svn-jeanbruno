package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.UseTerm;
import br.com.barganhas.business.services.persistencies.UseTermPO;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Transaction;

@Service("useTermBO")
public class UseTermBO implements UseTerm {

	public static final String						BEAN_ALIAS = "useTermBO";

	@Autowired
	private UseTermPO								persistencyLayer;
	
	@Override
	public List<UseTermTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<UseTermTO> listReturn = this.persistencyLayer.list();
			
			transaction.commit();
			return listReturn;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public UseTermTO insert(UseTermTO useTerm) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			useTerm = this.persistencyLayer.insert(useTerm);
			
			transaction.commit();
			return useTerm;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public UseTermTO consult(UseTermTO useTerm) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			useTerm = this.persistencyLayer.consult(useTerm);
			transaction.commit();
			return useTerm;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public UseTermTO getDefaultUseTerm() throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			UseTermTO useTerm = this.persistencyLayer.getDefaultUseTerm();
			transaction.commit();
			return useTerm;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public UseTermTO turnUseTermDefault(UseTermTO useTerm) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			try {
				UseTermTO currentDefaultUseTerm = this.persistencyLayer.getDefaultUseTerm();
				currentDefaultUseTerm.setDefaultUseTerm(false);
				this.save(currentDefaultUseTerm);
			} catch (AppException e) { } 
			
			useTerm.setDefaultUseTerm(true);
			useTerm = this.save(useTerm);
			
			transaction.commit();
			return useTerm;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public UseTermTO save(UseTermTO useTerm) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			useTerm = this.persistencyLayer.save(useTerm);
			
			transaction.commit();
			return useTerm;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void delete(UseTermTO useTerm) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			useTerm = this.consult(useTerm);
			
			if (useTerm.getDefaultUseTerm()) {
				throw new AppException("useTermDefaultCantBeDeleted");
			}
			
			this.persistencyLayer.delete(useTerm);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
}
