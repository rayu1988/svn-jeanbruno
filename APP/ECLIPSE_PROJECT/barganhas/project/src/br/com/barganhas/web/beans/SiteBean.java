package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.com.tatu.helper.GeneralsHelper;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.commons.SearchingRequest;
import br.com.barganhas.commons.SearchingResponse;
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
	private List<UserAccountTO>					highlightedUsers;
	
	private AdvertisementTO						advertisement;
	private Key									advertisementPicture;
	
	// manage if the list will be shown as grid or in line
	private Boolean								listAsGrid = false;
	
	private List<AdvertisementTO>				listResultSearch;
	private String								searchText = null;
	private List<CategoryTO>					listFilterCategory;
	private CategoryTO							categoryFilter;
	private List<StateTO>						listFilterState;
	private StateTO								stateFilter;
	private Double								filterCurrencyFrom;
	private Double								filterCurrencyUpTo;
	
	public String goToIndex() {
		try {
			this.searchText = null;
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
	public String loadAdvertisementConsult() {
		try {
			// check request by URL parameter
			if (this.advertisement == null) {
				String query = this.getHttpServletRequest().getParameter("q");
				this.advertisement = new AdvertisementTO(Util.getKeyFromString(query));
			}
			
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
			if (!GeneralsHelper.isStringOk(this.searchText)) {
				throw new AppException("advertisementSearchTextRequired");
			}
			
			SearchingRequest searchingRequest = new SearchingRequest();
			searchingRequest.setText(this.searchText);
			searchingRequest.setState(this.stateFilter);
			searchingRequest.setCategory(this.categoryFilter);
			searchingRequest.setFilterCurrencyFrom(this.filterCurrencyFrom);
			searchingRequest.setFilterCurrencyUpTo(this.filterCurrencyUpTo);
			
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			SearchingResponse searchingResponse = service.publicSearch(searchingRequest);
			
			this.listResultSearch = searchingResponse.getListAdvertisement();
			this.listFilterCategory = searchingResponse.getListCategory();
			this.listFilterState = searchingResponse.getState();
			
			return "search";
		} catch (Exception e) {
			this.manageException(e);
			return this.goToIndex();
		}
	}
	
	private void prepareListCategories() {
		if (!GeneralsHelper.isCollectionOk(this.listCategories)) {
			Category service = this.getServiceBusinessFactory().getCategory();
			this.listCategories = service.list();
		}
	}
	
	private void prepareListMyAdvertisements() {
		if (this.getUserAccountLogged() != null && !GeneralsHelper.isCollectionOk(this.myLastAdvertisements)) {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.myLastAdvertisements = service.myLastAdvertisements(this.getUserAccountLogged());
		}
	}
	
	private void prepareListLastAdvertisements() {
		try {
			if (!GeneralsHelper.isCollectionOk(this.lastAdvertisements)) {
				Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
				this.lastAdvertisements = service.lastAdvertisements();
			}
		} catch (Exception e) {
			this.manageException(e);
		}
	}
	
	private void prepareListMostViewed() {
		try {
			if (!GeneralsHelper.isCollectionOk(this.mostViewed)) {
				Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
				this.mostViewed = service.mostViewed();
			}
		} catch (Exception e) {
			this.manageException(e);
		}
	}
	
	private void prepareHighlightedUsers() {
		try {
			if (!GeneralsHelper.isCollectionOk(this.highlightedUsers)) {
				UserAccount service = this.getServiceBusinessFactory().getUserAccount();
				this.highlightedUsers = service.listHighlightedUsers();
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
		if (advertisement == null) {
			this.loadAdvertisementConsult();
		}
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

	public List<AdvertisementTO> getListResultSearch() {
		return listResultSearch;
	}

	public void setListResultSearch(List<AdvertisementTO> listResultSearch) {
		this.listResultSearch = listResultSearch;
	}

	public List<CategoryTO> getListFilterCategory() {
		return listFilterCategory;
	}

	public void setListFilterCategory(List<CategoryTO> listFilterCategory) {
		this.listFilterCategory = listFilterCategory;
	}

	public CategoryTO getCategoryFilter() {
		return categoryFilter;
	}

	public void setCategoryFilter(CategoryTO categoryFilter) {
		this.categoryFilter = categoryFilter;
	}

	public List<StateTO> getListFilterState() {
		return listFilterState;
	}

	public void setListFilterState(List<StateTO> listFilterState) {
		this.listFilterState = listFilterState;
	}

	public StateTO getStateFilter() {
		return stateFilter;
	}

	public void setStateFilter(StateTO stateFilter) {
		this.stateFilter = stateFilter;
	}

	public Double getFilterCurrencyFrom() {
		return filterCurrencyFrom;
	}

	public Double getFilterCurrencyUpTo() {
		return filterCurrencyUpTo;
	}

	public void setFilterCurrencyFrom(Double filterCurrencyFrom) {
		this.filterCurrencyFrom = filterCurrencyFrom;
	}

	public void setFilterCurrencyUpTo(Double filterCurrencyUpTo) {
		this.filterCurrencyUpTo = filterCurrencyUpTo;
	}

	public Boolean getListAsGrid() {
		return listAsGrid;
	}

	public void setListAsGrid(Boolean listAsGrid) {
		this.listAsGrid = listAsGrid;
	}

	public List<UserAccountTO> getHighlightedUsers() {
		this.prepareHighlightedUsers();
		return highlightedUsers;
	}

	public void setHighlightedUsers(List<UserAccountTO> highlightedUsers) {
		this.highlightedUsers = highlightedUsers;
	}

}
