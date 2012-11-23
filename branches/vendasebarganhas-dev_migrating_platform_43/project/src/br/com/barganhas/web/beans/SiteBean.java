package br.com.barganhas.web.beans;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import org.com.tatu.helper.GeneralsHelper;
import org.omnifaces.util.selectitems.SelectItemsBuilder;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.commons.SearchingRequest;
import br.com.barganhas.commons.SearchingResponse;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class SiteBean extends AppManagedBean {
	
	private List<CategoryTO>					listCategories;
	private List<AdvertisementTO>				myLastAdvertisements;
	private List<AdvertisementTO>				lastAdvertisements;
	private List<AdvertisementTO>				mostViewed;
	private List<UserAccountTO>					highlightedUsers;
	private UserAccountTO						userAccount;
	private List<AdvertisementTO>				userAccountLastAdvertisements;
	
	private AdvertisementTO						advertisement;
	private Long								idAux;
	
	// manage if the list will be shown as grid or in line
	private Boolean								listAsGrid = false;
	
	private List<AdvertisementTO>				listResultSearch;
	private String								searchText = null;
	private List<CategoryTO>					listFilterCategory;
	private CategoryTO							categoryFilter;
	private List<CityTO>						listFilterCity;
	private CityTO								cityFilter;
	private Double								filterCurrencyFrom;
	private Double								filterCurrencyUpTo;
	private List<SelectItem>					listItensPerPage;
	private Integer								totalItensPerPage = 10;
	private List<Integer>						listPageNumbers; 
	private Integer								currentPage = 1;
	private SearchingRequest.SearchOrdering		searchOrdering = SearchingRequest.SearchOrdering.MOST_RELEVANT;
	
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
				this.advertisement = new AdvertisementTO(new Long(query));
			}
			
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.publicConsult(this.advertisement);
			
			return "publicAdvertisementConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	
	/**
	 * Method to make a public consult of an AdvertisementTO
	 * @return
	 */
	public String loadUserAccountConsult() {
		try {
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			this.userAccount = service.consult(this.userAccount);

			
			Advertisement serviceAdvertisement = this.getServiceBusinessFactory().getAdvertisement();
			this.userAccountLastAdvertisements = serviceAdvertisement.userAccountLastAdvertisements(this.userAccount);
			
			return "userAccountPublicConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String search() {
		try {
			SearchingRequest searchingRequest = new SearchingRequest();
			searchingRequest.setText(this.searchText);
			searchingRequest.setCity(this.cityFilter);
			searchingRequest.setCategory(this.categoryFilter);
			searchingRequest.setFilterCurrencyFrom(this.filterCurrencyFrom);
			searchingRequest.setFilterCurrencyUpTo(this.filterCurrencyUpTo);
			searchingRequest.setSearchOrdering(this.searchOrdering);
			searchingRequest.setTotalItensPerPage(this.totalItensPerPage);
			searchingRequest.setCurrentPage(this.currentPage);
			
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			SearchingResponse searchingResponse = service.publicSearch(searchingRequest);
			
			this.listResultSearch = searchingResponse.getListAdvertisement();
			this.listFilterCategory = Arrays.asList(searchingResponse.getListCategory().toArray(new CategoryTO[]{}));
			this.listFilterCity = Arrays.asList(searchingResponse.getListCities().toArray(new CityTO[]{}));
			this.listPageNumbers = searchingResponse.buildPageNumbers(this.totalItensPerPage);
			
			return "search";
		} catch (Exception e) {
			this.manageException(e);
			return this.goToIndex();
		}
	}
	
	private void prepareListItensPerPage() {
		if (!GeneralsHelper.isCollectionOk(this.listItensPerPage)) {
			SelectItemsBuilder selectItemsBuilder = new SelectItemsBuilder();
			selectItemsBuilder.add(new Integer(10), "10");
			selectItemsBuilder.add(new Integer(20), "20");
			selectItemsBuilder.add(new Integer(50), "50");
			selectItemsBuilder.add(new Integer(100), "100");
			selectItemsBuilder.add(new Integer(500), "500");
			this.listItensPerPage = selectItemsBuilder.buildList();
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

	public SearchingRequest.SearchOrdering getSearchOrdering() {
		return searchOrdering;
	}

	public void setSearchOrdering(SearchingRequest.SearchOrdering searchOrdering) {
		this.searchOrdering = searchOrdering;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public List<SelectItem> getListItensPerPage() {
		this.prepareListItensPerPage();
		return listItensPerPage;
	}

	public void setListItensPerPage(List<SelectItem> listItensPerPage) {
		this.listItensPerPage = listItensPerPage;
	}

	public Integer getTotalItensPerPage() {
		return totalItensPerPage;
	}

	public void setTotalItensPerPage(Integer totalItensPerPage) {
		this.totalItensPerPage = totalItensPerPage;
	}

	public List<Integer> getListPageNumbers() {
		return listPageNumbers;
	}

	public void setListPageNumbers(List<Integer> listPageNumbers) {
		this.listPageNumbers = listPageNumbers;
	}

	public UserAccountTO getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccountTO userAccount) {
		this.userAccount = userAccount;
	}

	public List<CityTO> getListFilterCity() {
		return listFilterCity;
	}

	public void setListFilterCity(List<CityTO> listFilterCity) {
		this.listFilterCity = listFilterCity;
	}

	public CityTO getCityFilter() {
		return cityFilter;
	}

	public void setCityFilter(CityTO cityFilter) {
		this.cityFilter = cityFilter;
	}

	public List<AdvertisementTO> getUserAccountLastAdvertisements() {
		return userAccountLastAdvertisements;
	}

	public void setUserAccountLastAdvertisements(
			List<AdvertisementTO> userAccountLastAdvertisements) {
		this.userAccountLastAdvertisements = userAccountLastAdvertisements;
	}

	public Long getIdAux() {
		return idAux;
	}

	public void setIdAux(Long idAux) {
		this.idAux = idAux;
	}

}
