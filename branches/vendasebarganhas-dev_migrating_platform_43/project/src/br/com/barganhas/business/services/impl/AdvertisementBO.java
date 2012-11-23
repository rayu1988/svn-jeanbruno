package br.com.barganhas.business.services.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.com.tatu.helper.parameter.ParameterWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.City;
import br.com.barganhas.business.services.persistencies.AdvertisementPO;
import br.com.barganhas.commons.SearchingRequest;
import br.com.barganhas.commons.SearchingResponse;
import br.com.barganhas.enums.AdvertisementStatus;

@Service("advertisementBO")
public class AdvertisementBO implements Advertisement {

	public static final String						BEAN_ALIAS = "advertisementBO";

	@Autowired
	private AdvertisementPO							persistencyLayer;
	
	@Autowired
	private Category								serviceCategory;
	
	@Autowired
	private City									serviceCity;

	@Override
	@Transactional(readOnly = true)
	public List<AdvertisementTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AdvertisementTO> list(UserAccountTO userAccount) {
		return this.persistencyLayer.list(userAccount);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AdvertisementTO> myLastAdvertisements(UserAccountTO userAccount) {
		return this.persistencyLayer.myLastAdvertisements(userAccount);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AdvertisementTO> lastAdvertisements() {
		return this.persistencyLayer.lastAdvertisements();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AdvertisementTO> mostViewed() {
		return this.persistencyLayer.mostViewed();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AdvertisementTO> userAccountLastAdvertisements(UserAccountTO userAccount) {
		return this.persistencyLayer.userAccountLastAdvertisements(userAccount);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdvertisementTO insert(AdvertisementTO advertisement) {
		if (advertisement.getSheetPicture() == null) {
			throw new AppException("advertisementAtLeastOnePictureIsRequired");
		}
		// TODO when set production, set AdvertisementStatus.PENDING
		advertisement.setStatus(AdvertisementStatus.ENABLED);
		advertisement.setSinceDate(new Date());
		advertisement.setCountView(0l);
		
		return this.persistencyLayer.insert(advertisement);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdvertisementTO adminConsult(AdvertisementTO advertisement) {
		return this.persistencyLayer.consult(advertisement);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AdvertisementTO consult(AdvertisementTO advertisement) {
		return this.persistencyLayer.consult(advertisement);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdvertisementTO lock(AdvertisementTO advertisement) {
		advertisement = this.consult(advertisement);
		advertisement.setStatus(AdvertisementStatus.DISABLED);
		return this.persistencyLayer.save(advertisement);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdvertisementTO unlock(AdvertisementTO advertisement) {
		advertisement = this.consult(advertisement);
		advertisement.setStatus(AdvertisementStatus.ENABLED);
		return this.persistencyLayer.save(advertisement);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdvertisementTO publicConsult(AdvertisementTO advertisement) {
		advertisement = this.persistencyLayer.publicConsult(advertisement);
		
		this.persistencyLayer.getHibernateDao().update(advertisement, ParameterWrapper.instance("countView", (advertisement.getCountView() + 1) ));
		
		return advertisement;
	}
	
	@Override
	@Transactional(readOnly = true)
	public SearchingResponse publicSearch(SearchingRequest searchingRequest) {
		SearchingResponse searchingResponse = new SearchingResponse();
		
		// start getting advertisement
		searchingResponse.setListAdvertisement(this.persistencyLayer.listEntitiesPublicSearch(searchingRequest));
		if (searchingRequest.getCategory() == null) {
			searchingResponse.setListCategory(this.serviceCategory.listFiter(searchingRequest));
		}
		if (searchingRequest.getCity() == null) {
			searchingResponse.setListCities(this.serviceCity.listFiter(searchingRequest));
		}
		
		return searchingResponse;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AdvertisementTO save(AdvertisementTO advertisement) {
		if (advertisement.getSheetPicture() == null) {
			throw new AppException("advertisementAtLeastOnePictureIsRequired");
		}
		
		return this.persistencyLayer.save(advertisement);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(AdvertisementTO advertisement) {
		this.persistencyLayer.delete(advertisement);
	}

	/**
	 * Method to delete every UserAccount's advertisement.
	 * @param owner
	 * @throws EntityNotFoundException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(UserAccountTO owner) {
		this.persistencyLayer.delete(owner);
	}

}
