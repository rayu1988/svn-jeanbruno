package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Transaction;

import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.business.services.persistencies.AdvertisementTypePO;

@Service("advertisementTypeBO")
public class AdvertisementTypeBO implements AdvertisementType {

	public static final String						BEAN_ALIAS = "advertisementTypeBO";

	@Autowired
	private AdvertisementTypePO						persistencyLayer;
	
	@Override
	public List<AdvertisementTypeTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdvertisementTypeTO> listReturn = this.persistencyLayer.list();
			
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
	public AdvertisementTypeTO insert(AdvertisementTypeTO advertisementType) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisementType = this.persistencyLayer.insert(advertisementType);
			
			transaction.commit();
			return advertisementType;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AdvertisementTypeTO consult(AdvertisementTypeTO advertisementType) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisementType = this.persistencyLayer.consult(advertisementType);
			
			transaction.commit();
			return advertisementType;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AdvertisementTypeTO save(AdvertisementTypeTO advertisementType) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisementType = this.persistencyLayer.save(advertisementType);
			
			transaction.commit();
			return advertisementType;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void delete(AdvertisementTypeTO advertisementType) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			this.persistencyLayer.delete(advertisementType);
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
