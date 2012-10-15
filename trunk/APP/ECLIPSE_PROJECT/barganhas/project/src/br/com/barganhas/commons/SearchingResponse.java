package br.com.barganhas.commons;

import java.util.List;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.StateTO;

public class SearchingResponse {

	private List<AdvertisementTO>				listAdvertisement;
	private List<StateTO>						listState;
	private List<CategoryTO>					listCategory;
	
	// GETTERS AND SETTERS //
	public List<AdvertisementTO> getListAdvertisement() {
		return listAdvertisement;
	}

	public void setListAdvertisement(List<AdvertisementTO> listAdvertisement) {
		this.listAdvertisement = listAdvertisement;
	}

	public List<StateTO> getState() {
		return listState;
	}
	
	public void setState(List<StateTO> listLocalization) {
		this.listState = listLocalization;
	}

	public List<CategoryTO> getListCategory() {
		return listCategory;
	}

	public void setListCategory(List<CategoryTO> listCategory) {
		this.listCategory = listCategory;
	}

}
