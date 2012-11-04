package br.com.barganhas.commons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.CityTO;

public class SearchingResponse {

	private List<AdvertisementTO>				listAdvertisement;
	private HashSet<CityTO>						listCities;
	private HashSet<CategoryTO>					listCategory;
	private Integer								totalCriteriaSize;
	
	public SearchingResponse() {
		this.listAdvertisement = new ArrayList<AdvertisementTO>();
		this.listCities = new HashSet<CityTO>();
		this.listCategory = new HashSet<CategoryTO>();
		this.totalCriteriaSize = 1;
	}
	
	public List<Integer> buildPageNumbers(int totalItensPerPage) {
		int result = (int) this.totalCriteriaSize / (int) totalItensPerPage;
		boolean isInteger = (double) this.totalCriteriaSize % (double) totalItensPerPage == 0;
		result += isInteger ? 0 : 1;
		
		Integer[] arrayItens = org.com.tatu.arrays.Arrays.createIntegerArray(1, result);
		return java.util.Arrays.asList(arrayItens);
	}
	
	// GETTERS AND SETTERS //
	public List<AdvertisementTO> getListAdvertisement() {
		return listAdvertisement;
	}

	public void setListAdvertisement(List<AdvertisementTO> listAdvertisement) {
		this.listAdvertisement = listAdvertisement;
	}

	public HashSet<CategoryTO> getListCategory() {
		return listCategory;
	}

	public void setListCategory(HashSet<CategoryTO> listCategory) {
		this.listCategory = listCategory;
	}

	public Integer getTotalCriteriaSize() {
		return totalCriteriaSize;
	}

	public void setTotalCriteriaSize(Integer totalCriteriaSize) {
		this.totalCriteriaSize = totalCriteriaSize;
	}

	public HashSet<CityTO> getListCities() {
		return listCities;
	}

	public void setListCities(HashSet<CityTO> listCities) {
		this.listCities = listCities;
	}

}
