package br.com.barganhas.business.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.City;
import br.com.barganhas.business.services.Sales;
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
	private City									serviceCity;
	
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
	public List<AdvertisementTO> userAccountLastAdvertisements(UserAccountTO userAccount) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AdvertisementTO> listReturn = this.persistencyLayer.userAccountLastAdvertisements(userAccount);
			
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
			
			AdvertisementTypeTO advertisementType = advertisement.getAdvertisementType();
			advertisement.setKeyAdvertisementType(advertisementType.getKey());
			advertisement.setScore(advertisementType.getAdvertisementScore());
			
			advertisement.setKeyCategory(advertisement.getCategory().getKey());

			// TODO when set production, set AdvertisementStatus.PENDING
			advertisement.setStatus(AdvertisementStatus.ENABLED);
			advertisement.setSinceDate(new Date());
			
			AdvertisementPictureTO sheetPicture = this.serviceAdvertisementPicture.insert(advertisementSheet);
			advertisement.setKeySheetPicture(sheetPicture.getKey());

			advertisement.setPictures(this.buildListKeyPictures(listAdvertisementPictures));
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
	
	private List<Key> buildListKeyPictures(List<AdvertisementPictureTO> listAdvertisementPictures) throws EntityNotFoundException {
		List<Key> listKeyAdvertisementPictures = new ArrayList<Key>();
		if (GeneralsHelper.isCollectionOk(listAdvertisementPictures)) {
			for (AdvertisementPictureTO advertisementPicture : listAdvertisementPictures) {
				if (advertisementPicture.getKey() == null) {
					advertisementPicture = this.serviceAdvertisementPicture.insert(advertisementPicture);
				}
				listKeyAdvertisementPictures.add(advertisementPicture.getKey());
			}
		}
		return listKeyAdvertisementPictures;
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
	
	private boolean isEmptySearch(SearchingRequest searchingRequest) {
		boolean isValidSarchText = !GeneralsHelper.isStringOk(searchingRequest.getText());
		boolean existsCategory = searchingRequest.getCategory() == null;
		boolean existsCity = searchingRequest.getCity() == null;
		boolean existsCurrencyValueFrom = searchingRequest.getFilterCurrencyFrom() == null;
		boolean existsCurrencyValueUpTo = searchingRequest.getFilterCurrencyUpTo() == null;
				
		return isValidSarchText && existsCategory && existsCity && existsCurrencyValueFrom && existsCurrencyValueUpTo;
	}
	
	@Override
	public SearchingResponse publicSearch(SearchingRequest searchingRequest) throws EntityNotFoundException {
		SearchingResponse searchingResponse = new SearchingResponse();
		
		// start getting advertisement
		List<AdvertisementTO> listAdvertisement = null;
		if (isEmptySearch(searchingRequest)) {
			return searchingResponse;
		} else {
			listAdvertisement = this.persistencyLayer.listEntitiesPublicSearch(searchingRequest);
			
			// sorting
			if (!searchingRequest.getSearchOrdering().equals(SearchingRequest.SearchOrdering.MOST_RELEVANT)) {
				if (searchingRequest.getSearchOrdering().equals(SearchingRequest.SearchOrdering.LOWER_PRICE)) {
					Collections.sort(listAdvertisement, AdvertisementTO.LowerPrice());
				} else {
					Collections.sort(listAdvertisement, AdvertisementTO.HigherPrice());
				}
			}
			
			// filter selecting and removing
			for (int i = 0; i < listAdvertisement.size() ; i++) {
				AdvertisementTO advertisement = listAdvertisement.get(i);
				
				UserAccountTO userAccount = this.serviceUserAccount.consult(new UserAccountTO(advertisement.getKeyUserAccount()));
				CityTO currentCity = new CityTO(userAccount.getKeyCity());
				
				// stats checking remove undesired elements
				if (searchingRequest.getCity() != null && !searchingRequest.getCity().equals(currentCity)) {
					listAdvertisement.remove(i--);
					continue;
				}
				if (GeneralsHelper.isStringOk(searchingRequest.getText()) &&
						!advertisement.getTitle().trim().toLowerCase().contains(searchingRequest.getText().trim().toLowerCase())) {
					listAdvertisement.remove(i--);
					continue;
				}
				if (searchingRequest.getFilterCurrencyFrom() != null && advertisement.getValue() < searchingRequest.getFilterCurrencyFrom()) {
					listAdvertisement.remove(i--);
					continue;
				}
				if (searchingRequest.getFilterCurrencyUpTo() != null && advertisement.getValue() > searchingRequest.getFilterCurrencyUpTo()) {
					listAdvertisement.remove(i--);
					continue;
				}
				// ends checking remove undesired elements
				
				// starts setting filtering
				if (!searchingResponse.getListCities().contains(currentCity)) {
					searchingResponse.getListCities().add(this.serviceCity.consult(currentCity));
				}
				
				CategoryTO category = new CategoryTO(advertisement.getKeyCategory());
				if (!searchingResponse.getListCategory().contains(category)) {
					searchingResponse.getListCategory().add(this.serviceCategory.consult(category));
				}
				// ends setting filtering
			}
			
			int currentListSize = listAdvertisement.size();
			int totalItensPerPage = searchingRequest.getTotalItensPerPage();
			int currentPage = searchingRequest.getCurrentPage();
			
			int starts = (currentPage - 1) * totalItensPerPage;
			int ends = (totalItensPerPage * currentPage) - 1;
			ends = ends >= currentListSize ? currentListSize - 1 : ends;
			
			// consulting the advertisement picture's thumbnails
			List<AdvertisementTO> finalSearchList = new ArrayList<AdvertisementTO>();
			for (int i = starts; i <= ends; i++ ) {
				AdvertisementTO advertisement = listAdvertisement.get(i);
				AdvertisementPictureTO advertisementPicture = this.serviceAdvertisementPicture.consult(new AdvertisementPictureTO(advertisement.getKeySheetPicture()));
				advertisement.setSheetPicture(advertisementPicture);
				
				finalSearchList.add(advertisement);
			}
			listAdvertisement = finalSearchList;
			searchingResponse.setTotalCriteriaSize(currentListSize);
		}
		searchingResponse.setListAdvertisement(listAdvertisement);
		// ends getting advertisement
		
		return searchingResponse;
	}
	
	@Override
	public AdvertisementTO save(AdvertisementTO advertisement) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			
			List<AdvertisementPictureTO> listAdvertisementPictures = advertisement.getListAdvertisementPictures();
			advertisement.setPictures(this.buildListKeyPictures(listAdvertisementPictures));
			
			AdvertisementPictureTO advertisementSheet = advertisement.getSheetPicture();
			if (advertisementSheet.getKey() == null) {
				advertisementSheet = this.serviceAdvertisementPicture.insert(advertisementSheet);
			}
			advertisement.setKeySheetPicture(advertisementSheet.getKey());
			
			advertisement.setKeyCategory(advertisement.getCategory().getKey());
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
