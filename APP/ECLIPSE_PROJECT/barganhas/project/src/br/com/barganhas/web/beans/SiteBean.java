package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.Key;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class SiteBean extends AppManagedBean {
	
	private List<CategoryTO>					listCategories;
	private List<AdvertisementTO>				myLastAdvertisements;
	private List<AdvertisementTO>				lastAdvertisements;
	private List<AdvertisementTO>				mostViewed;
	
	private AdvertisementTO						advertisement;
	private Key									advertisementPicture;
	
	private List<AdvertisementTO>				listRetrievied;
	private String								searchText;
	
	public String goToIndex() {
		try {
			this.prepareListCategories();
			
			return "siteIndex";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	/**
	 * Method to make a public consult of an AdvertisementTO
	 * @return
	 */
	public String publicAdvertisementConsult() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.publicConsult(this.advertisement);
			
			this.advertisementPicture = this.advertisement.getSheetPicture().getKeyPicture();
			
			return "publicAdvertisementConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String search() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.listRetrievied = service.publicSearch(this.searchText);
			
			return "search";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	private void prepareListCategories() {
		if (!Util.isCollectionOk(this.listCategories)) {
			Category service = this.getServiceBusinessFactory().getCategory();
			this.listCategories = service.list();
		}
	}
	
	private void prepareListMyAdvertisements() {
		if (this.getUserAccountLogged() != null && !Util.isCollectionOk(this.myLastAdvertisements)) {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.myLastAdvertisements = service.myLastAdvertisements(this.getUserAccountLogged());
		}
	}
	
	private void prepareListLastAdvertisements() {
		try {
			if (!Util.isCollectionOk(this.lastAdvertisements)) {
				Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
				this.lastAdvertisements = service.lastAdvertisements();
			}
		} catch (Exception e) {
			this.manageException(e);
		}
	}
	
	private void prepareListMostViewed() {
		try {
			if (!Util.isCollectionOk(this.mostViewed)) {
				Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
				this.mostViewed = service.lastAdvertisements();
			}
		} catch (Exception e) {
			this.manageException(e);
		}
	}
	
	// GETTERS AND SETTERS //
	public List<CategoryTO> getListCategories() {
		this.prepareListCategories();
		return listCategories;
	}

	public void setListCategories(List<CategoryTO> listCategories) {
		this.listCategories = listCategories;
	}

	public List<AdvertisementTO> getMyLastAdvertisements() {
		this.prepareListMyAdvertisements();
		return myLastAdvertisements;
	}

	public void setMyLastAdvertisements(List<AdvertisementTO> myLastAdvertisements) {
		this.myLastAdvertisements = myLastAdvertisements;
	}

	public List<AdvertisementTO> getLastAdvertisements() {
		this.prepareListLastAdvertisements();
		return lastAdvertisements;
	}

	public void setLastAdvertisements(List<AdvertisementTO> lastAdvertisements) {
		this.lastAdvertisements = lastAdvertisements;
	}

	public List<AdvertisementTO> getMostViewed() {
		this.prepareListMostViewed();
		return mostViewed;
	}

	public void setMostViewed(List<AdvertisementTO> mostViewed) {
		this.mostViewed = mostViewed;
	}

	public AdvertisementTO getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(AdvertisementTO advertisement) {
		this.advertisement = advertisement;
	}

	public Key getAdvertisementPicture() {
		return advertisementPicture;
	}

	public void setAdvertisementPicture(Key advertisementPicture) {
		this.advertisementPicture = advertisementPicture;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public List<AdvertisementTO> getListRetrievied() {
		return listRetrievied;
	}

	public void setListRetrievied(List<AdvertisementTO> listRetrievied) {
		this.listRetrievied = listRetrievied;
	}
}
