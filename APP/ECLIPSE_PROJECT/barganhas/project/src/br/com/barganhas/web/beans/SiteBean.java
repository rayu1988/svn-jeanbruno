package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.commons.Util;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class SiteBean extends AppManagedBean {
	
	private List<CategoryTO>					listCategories;
	private List<AdvertisementTO>				myLastAdvertisements;
	private List<AdvertisementTO>				lastAdvertisements;
	private List<AdvertisementTO>				mostViewed;
	
	public String goToIndex() {
		this.prepareListCategories();
		
		return "siteIndex";
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
		if (!Util.isCollectionOk(this.lastAdvertisements)) {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.lastAdvertisements = service.lastAdvertisements();
		}
	}
	
	private void prepareListMostViewed() {
		if (!Util.isCollectionOk(this.mostViewed)) {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.mostViewed = service.lastAdvertisements();
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
}
