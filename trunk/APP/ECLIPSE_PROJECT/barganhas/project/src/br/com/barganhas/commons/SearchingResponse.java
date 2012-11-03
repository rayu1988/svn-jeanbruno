package br.com.barganhas.commons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.StateTO;

public class SearchingResponse {

	private List<AdvertisementTO>				listAdvertisement;
	private HashSet<StateTO>					listState;
	private HashSet<CategoryTO>					listCategory;
	private Integer								totalCriteriaSize;
	
	public SearchingResponse() {
		this.listAdvertisement = new ArrayList<AdvertisementTO>();
		this.listState = new HashSet<StateTO>();
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

	public HashSet<StateTO> getListState() {
		return listState;
	}

	public void setListState(HashSet<StateTO> listState) {
		this.listState = listState;
	}

	public Integer getTotalCriteriaSize() {
		return totalCriteriaSize;
	}

	public void setTotalCriteriaSize(Integer totalCriteriaSize) {
		this.totalCriteriaSize = totalCriteriaSize;
	}

}
