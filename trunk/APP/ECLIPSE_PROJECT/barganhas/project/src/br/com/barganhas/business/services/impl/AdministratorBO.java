package br.com.barganhas.business.services.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Transaction;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.business.services.persistencies.AdministratorPO;

@Service("administratorBO")
public class AdministratorBO implements Administrator {

	private static final Logger 					logger = Logger.getLogger(AdministratorBO.class.getCanonicalName());
	public static final String						BEAN_ALIAS = "administratorBO";

	@Autowired
	private AdministratorPO							persistencyLayer;
	
	@Override
	public List<AdministratorTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdministratorTO> listReturn = this.persistencyLayer.list();
			
			transaction.commit();
			return listReturn;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public List<AdministratorTO> filter(AdministratorTO administrator) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdministratorTO> listReturn = this.persistencyLayer.filter(administrator);
			
			transaction.commit();
			return listReturn;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AdministratorTO insert(AdministratorTO administrator) {
		logger.log(Level.INFO, "Initializing the insert (persist) task.");

		logger.log(Level.INFO, "Begining the transaction.");
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			logger.log(Level.INFO, "Registering the instance in the database.");
			administrator = this.persistencyLayer.insert(administrator);
			
			logger.log(Level.INFO, "Invoking the commit.");
			transaction.commit();
			return administrator;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AdministratorTO consult(AdministratorTO administrator) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			administrator = this.persistencyLayer.consult(administrator);
			
			transaction.commit();
			return administrator;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AdministratorTO save(AdministratorTO administrator) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			administrator = this.persistencyLayer.save(administrator);
			
			transaction.commit();
			return administrator;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void delete(AdministratorTO administrator) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			this.persistencyLayer.delete(administrator);
			transaction.commit();
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public AdministratorTO validateLogin(AdministratorTO administrator) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			administrator = this.persistencyLayer.validateLogin(administrator);
			transaction.commit();
			return administrator;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void registerFirstAdministrator() {
		logger.log(Level.INFO, "Starting execution inside AdministratorBO, registering first AdministratorTO.");

		logger.log(Level.INFO, "Begining a transaction.");
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			logger.log(Level.INFO, "Doing the count of Administrators (TO) inside the database.");
			int totalAdministrators = this.persistencyLayer.count(AdministratorTO.class);
			logger.log(Level.INFO, "The count found was:" + totalAdministrators + " .");
			
			if (totalAdministrators <= 0) {
				logger.log(Level.INFO, "As there's none Administrator inside the database let's register one.");
				
				AdministratorTO administrator = new AdministratorTO();
				administrator.setFullname("Administrator Master");
				administrator.setEmail("admin@mail.com");
				administrator.setNickname("admin");
				administrator.setPassword("admin");
				
				logger.log(Level.INFO, "The Administrator's object instance is complete, let's save it:" + administrator.getFullname());
				this.insert(administrator);
				logger.log(Level.INFO, "The persistency task was completed successfully to this first Administrator:" + administrator.getFullname());
			}
			
			logger.log(Level.INFO, "Invoking the commit.");
			transaction.commit();
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
}
