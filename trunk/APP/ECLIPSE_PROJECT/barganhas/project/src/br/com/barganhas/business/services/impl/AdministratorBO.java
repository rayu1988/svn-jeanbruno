package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Transaction;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.business.services.persistencies.AdministratorPO;

@Service("administratorBO")
public class AdministratorBO implements Administrator {

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
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			administrator = this.persistencyLayer.insert(administrator);
			
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
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			int totalAdministrators = this.persistencyLayer.count(AdministratorTO.class);
			if (totalAdministrators <= 0) {
				AdministratorTO administrator = new AdministratorTO();
				administrator.setFullname("Administrator Master");
				administrator.setEmail("admin@mail.com");
				administrator.setNickname("admin");
				administrator.setPassword("admin");
				
				this.insert(administrator);
			}
			
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
