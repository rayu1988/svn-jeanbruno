package br.com.barganhas.business.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.business.services.persistencies.AdvertisementPO;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.AdvertisementStatus;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

@Service("advertisementBO")
public class AdvertisementBO implements Advertisement {

	public static final String						BEAN_ALIAS = "advertisementBO";

	@Autowired
	private AdvertisementPO							persistencyLayer;
	
	@Autowired
	private AdvertisementType						serviceAdvertisementType;
	
	@Autowired
	private AdvertisementPicture					serviceAdvertisementPicture;
	
	@Override
	public List<AdvertisementTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdvertisementTO> listReturn = this.persistencyLayer.list();
			
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
	public List<AdvertisementTO> list(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdvertisementTO> listReturn = this.persistencyLayer.list(userAccount);
			
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
	public AdvertisementTO insert(AdvertisementTO advertisement) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			if (advertisement.getSheetPicture() == null) {
				throw new AppException("advertisementAtLeastOnePictureIsRequired");
			}
			
			AdvertisementPictureTO advertisementSheet = advertisement.getSheetPicture();
			List<AdvertisementPictureTO> listAdvertisementPictures = advertisement.getListAdvertisementPictures();
			
			// set key properties
			if (advertisement.getSales() != null) {
				advertisement.setKeySales(advertisement.getSales().getKey());
			}
			advertisement.setKeyUserAccount(advertisement.getUserAccount().getKey());
			advertisement.setKeyAdvertisementType(advertisement.getAdvertisementType().getKey());
			advertisement.setKeyCategory(advertisement.getCategory().getKey());

			// TODO when set production, set AdvertisementStatus.PENDING
			advertisement.setStatus(AdvertisementStatus.ENABLED);
			advertisement.setSinceDate(new Date());
			
			AdvertisementPictureTO sheetPicture = this.serviceAdvertisementPicture.insert(advertisementSheet);
			advertisement.setKeySheetPicture(sheetPicture.getKey());

			if (Util.isCollectionOk(listAdvertisementPictures)) {
				List<Key> listKeyAdvertisementPictures = new ArrayList<Key>();
				for (AdvertisementPictureTO advertisementPicture : listAdvertisementPictures) {
					advertisementPicture = this.serviceAdvertisementPicture.insert(advertisementPicture);
					listKeyAdvertisementPictures.add(advertisementPicture.getKey());
				}
				advertisement.setPictures(listKeyAdvertisementPictures);
			}

			// persist and syncronize the advertisement to get its key
			advertisement = this.persistencyLayer.insert(advertisement, advertisement.getUserAccount());
			
			transaction.commit();
			return advertisement;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AdvertisementTO consult(AdvertisementTO advertisement) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisement = this.persistencyLayer.consult(advertisement);
			AdvertisementTypeTO advertisementType = this.serviceAdvertisementType.consult(new AdvertisementTypeTO(advertisement.getKeyAdvertisementType()));
			advertisement.setAdvertisementType(advertisementType);
			
			transaction.commit();
			return advertisement;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AdvertisementTO save(AdvertisementTO advertisement) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisement = this.persistencyLayer.save(advertisement);
			
			transaction.commit();
			return advertisement;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void delete(AdvertisementTO advertisement) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			this.persistencyLayer.delete(advertisement);
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
