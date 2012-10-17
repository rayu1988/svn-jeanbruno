package br.com.barganhas.business.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.Sales;
import br.com.barganhas.business.services.State;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.business.services.persistencies.AdvertisementPO;
import br.com.barganhas.commons.SearchingRequest;
import br.com.barganhas.commons.SearchingResponse;
import br.com.barganhas.enums.AdvertisementStatus;

import com.google.appengine.api.datastore.EntityNotFoundException;
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
	private Category								serviceCategory;
	
	@Autowired
	private AdvertisementPicture					serviceAdvertisementPicture;
	
	@Autowired
	private Sales									serviceSales;
	
	@Autowired
	private State									serviceState;
	
	@Autowired
	private UserAccount								serviceUserAccount;
	
	@Override
	public List<AdvertisementTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdvertisementTO> listReturn = this.persistencyLayer.list();
			
			transaction.commit();
			return listReturn;
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
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public List<AdvertisementTO> myLastAdvertisements(UserAccountTO userAccount) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdvertisementTO> listReturn = this.persistencyLayer.myLastAdvertisements(userAccount);
			
			transaction.commit();
			return listReturn;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public List<AdvertisementTO> lastAdvertisements() throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdvertisementTO> listReturn = this.persistencyLayer.lastAdvertisements();
			
			for (AdvertisementTO advertisement : listReturn) {
				AdvertisementPictureTO advertisementPicture = this.serviceAdvertisementPicture.consult(new AdvertisementPictureTO(advertisement.getKeySheetPicture()));
				advertisement.setSheetPicture(advertisementPicture);
			}
			
			transaction.commit();
			return listReturn;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public List<AdvertisementTO> mostViewed() throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdvertisementTO> listReturn = this.persistencyLayer.mostViewed();
			
			for (AdvertisementTO advertisement : listReturn) {
				AdvertisementPictureTO advertisementPicture = this.serviceAdvertisementPicture.consult(new AdvertisementPictureTO(advertisement.getKeySheetPicture()));
				advertisement.setSheetPicture(advertisementPicture);
			}
			
			transaction.commit();
			return listReturn;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public AdvertisementTO insert(AdvertisementTO advertisement) throws EntityNotFoundException {
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
			UserAccountTO userAccount = advertisement.getUserAccount();
			advertisement.setKeyUserAccount(userAccount.getKey());
			advertisement.setKeyState(userAccount.getKeyState());
			
			AdvertisementTypeTO advertisementType = advertisement.getAdvertisementType();
			advertisement.setKeyAdvertisementType(advertisementType.getKey());
			advertisement.setScore(advertisementType.getAdvertisementScore());
			
			advertisement.setKeyCategory(advertisement.getCategory().getKey());

			// TODO when set production, set AdvertisementStatus.PENDING
			advertisement.setStatus(AdvertisementStatus.ENABLED);
			advertisement.setSinceDate(new Date());
			
			AdvertisementPictureTO sheetPicture = this.serviceAdvertisementPicture.insert(advertisementSheet);
			advertisement.setKeySheetPicture(sheetPicture.getKey());

			if (GeneralsHelper.isCollectionOk(listAdvertisementPictures)) {
				List<Key> listKeyAdvertisementPictures = new ArrayList<Key>();
				for (AdvertisementPictureTO advertisementPicture : listAdvertisementPictures) {
					advertisementPicture = this.serviceAdvertisementPicture.insert(advertisementPicture);
					listKeyAdvertisementPictures.add(advertisementPicture.getKey());
				}
				advertisement.setPictures(listKeyAdvertisementPictures);
			}
			
			advertisement.setCountView(0l);

			// persist and syncronize the advertisement to get its key
			advertisement = this.persistencyLayer.insert(advertisement, userAccount);
			
			transaction.commit();
			
			this.serviceUserAccount.incrementCountAdvertisement(userAccount);
			return advertisement;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AdvertisementTO adminConsult(AdvertisementTO advertisement) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisement = this.persistencyLayer.consult(advertisement);
			AdvertisementTypeTO advertisementType = this.serviceAdvertisementType.consult(new AdvertisementTypeTO(advertisement.getKeyAdvertisementType()));
			advertisement.setAdvertisementType(advertisementType);
			CategoryTO category = this.serviceCategory.consult(new CategoryTO(advertisement.getKeyCategory()));
			advertisement.setCategory(category);
			
			if (advertisement.getKeySales() != null) {
				advertisement.setSales(this.serviceSales.consult(new SalesTO(advertisement.getKeySales())));
			}
			
			transaction.commit();
			return advertisement;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public AdvertisementTO consult(AdvertisementTO advertisement) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisement = this.persistencyLayer.consult(advertisement);
			AdvertisementTypeTO advertisementType = this.serviceAdvertisementType.consult(new AdvertisementTypeTO(advertisement.getKeyAdvertisementType()));
			advertisement.setAdvertisementType(advertisementType);
			CategoryTO category = this.serviceCategory.consult(new CategoryTO(advertisement.getKeyCategory()));
			advertisement.setCategory(category);
			AdvertisementPictureTO advertisementPicture = this.serviceAdvertisementPicture.consult(new AdvertisementPictureTO(advertisement.getKeySheetPicture()));
			advertisement.setSheetPicture(advertisementPicture);
			
			if (GeneralsHelper.isCollectionOk(advertisement.getPictures())) {
				List<AdvertisementPictureTO> listAdvertisementPictures = new ArrayList<AdvertisementPictureTO>();
				for (Key key : advertisement.getPictures()) {
					listAdvertisementPictures.add(this.serviceAdvertisementPicture.consult(new AdvertisementPictureTO(key)));
				}
				advertisement.setListAdvertisementPictures(listAdvertisementPictures);
			}
			
			if (advertisement.getKeySales() != null) {
				advertisement.setSales(this.serviceSales.consult(new SalesTO(advertisement.getKeySales())));
			}
			
			transaction.commit();
			return advertisement;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AdvertisementTO lock(AdvertisementTO advertisement) throws EntityNotFoundException {
		advertisement = this.consult(advertisement);
		advertisement.setStatus(AdvertisementStatus.DISABLED);
		return this.save(advertisement);
	}
	
	@Override
	public AdvertisementTO unlock(AdvertisementTO advertisement) throws EntityNotFoundException {
		advertisement = this.consult(advertisement);
		advertisement.setStatus(AdvertisementStatus.ENABLED);
		return this.save(advertisement);
	}
	
	@Override
	public AdvertisementTO publicConsult(AdvertisementTO advertisement) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisement = this.persistencyLayer.publicConsult(advertisement);
			
			AdvertisementPictureTO advertisementPicture = this.serviceAdvertisementPicture.consult(new AdvertisementPictureTO(advertisement.getKeySheetPicture()));
			advertisement.setSheetPicture(advertisementPicture);
			
			if (GeneralsHelper.isCollectionOk(advertisement.getPictures())) {
				List<AdvertisementPictureTO> listAdvertisementPictures = new ArrayList<AdvertisementPictureTO>();
				for (Key key : advertisement.getPictures()) {
					listAdvertisementPictures.add(this.serviceAdvertisementPicture.consult(new AdvertisementPictureTO(key)));
				}
				advertisement.setListAdvertisementPictures(listAdvertisementPictures);
			}
			
			UserAccountTO userAccount = this.serviceUserAccount.consult(new UserAccountTO(advertisement.getKeyUserAccount()));
			advertisement.setUserAccount(userAccount);
			
			transaction.commit();
			return advertisement;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public SearchingResponse publicSearch(SearchingRequest searchingRequest) throws EntityNotFoundException {
		SearchingResponse searchingResponse = new SearchingResponse();
		
		// start getting advertisement
		List<AdvertisementTO> listAdvertisement = null;
		if (!GeneralsHelper.isStringOk(searchingRequest.getText()) && searchingRequest.getCategory() == null
				&& searchingRequest.getState() == null && searchingRequest.getFilterCurrencyFrom() == null
				&& searchingRequest.getFilterCurrencyUpTo() == null) {
			searchingResponse.setListAdvertisement(new ArrayList<AdvertisementTO>());
			return searchingResponse;
		} else {
			listAdvertisement = this.persistencyLayer.publicSearch(searchingRequest);
			
			for (int i = 0; i < listAdvertisement.size() ; i++) {
				AdvertisementTO advertisement = listAdvertisement.get(i);
//				if (!advertisement.getTitle().trim().toLowerCase().contains(searchingRequest.getText().trim().toLowerCase())) {
//					listAdvertisement.remove(i--);
//					continue;
//				}
				if (searchingRequest.getFilterCurrencyFrom() != null && advertisement.getValue() < searchingRequest.getFilterCurrencyFrom()) {
					listAdvertisement.remove(i--);
					continue;
				}
				if (searchingRequest.getFilterCurrencyUpTo() != null && advertisement.getValue() > searchingRequest.getFilterCurrencyUpTo()) {
					listAdvertisement.remove(i--);
					continue;
				}
				
				AdvertisementPictureTO advertisementPicture = this.serviceAdvertisementPicture.consult(new AdvertisementPictureTO(advertisement.getKeySheetPicture()));
				advertisement.setSheetPicture(advertisementPicture);
			}
		}
		searchingResponse.setListAdvertisement(listAdvertisement);
		// ends getting advertisement

		// start getting filtering
		List<AdvertisementTO> filtering = this.persistencyLayer.getFiltersToSearch(searchingRequest);
		HashSet<Key> stateKeys = new HashSet<Key>();
		HashSet<Key> categoryKeys = new HashSet<Key>();
		for (AdvertisementTO advertisementFilter : filtering) {
			if (searchingRequest.getState() == null) {
				stateKeys.add(advertisementFilter.getKeyState());
			}
			if (searchingRequest.getCategory() == null) {
				categoryKeys.add(advertisementFilter.getKeyCategory());
			}
		}
		
		List<StateTO> filterState = new ArrayList<StateTO>();
		for (Key stateKey : stateKeys) {
			filterState.add(this.serviceState.consult(new StateTO(stateKey)));
		}
		searchingResponse.setState(filterState);
		
		List<CategoryTO> filterCategory = new ArrayList<CategoryTO>();
		for (Key categoryKey : categoryKeys) {
			filterCategory.add(this.serviceCategory.consult(new CategoryTO(categoryKey)));
		}
		searchingResponse.setListCategory(filterCategory);
		// ends getting filtering
		
		return searchingResponse;
	}
	
	@Override
	public AdvertisementTO save(AdvertisementTO advertisement) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			advertisement = this.persistencyLayer.save(advertisement);
			
			transaction.commit();
			return advertisement;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void delete(AdvertisementTO advertisement) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			try {
				this.serviceAdvertisementPicture.delete(new AdvertisementPictureTO(advertisement.getKeySheetPicture()));
			} catch (EntityNotFoundException e) { }
			
			if (GeneralsHelper.isCollectionOk(advertisement.getPictures())) {
				for (Key keyAdvertisementPicture : advertisement.getPictures()) {
					try {
						this.serviceAdvertisementPicture.delete(new AdvertisementPictureTO(keyAdvertisementPicture));
					} catch (EntityNotFoundException e) { }
				}
			}
			
			this.persistencyLayer.delete(advertisement);
			
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	/**
	 * Method to delete every UserAccount's advertisement.
	 * @param owner
	 * @throws EntityNotFoundException
	 */
	@Override
	public void delete(UserAccountTO owner) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			for (AdvertisementTO advertisement : this.list(owner)) {
				this.delete(advertisement);
			}
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

}
