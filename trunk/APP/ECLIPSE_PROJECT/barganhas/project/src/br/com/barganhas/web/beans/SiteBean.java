package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.commons.Util;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class SiteBean extends AppManagedBean {
	
	private DataModel<Object>					dataModel;

	private List<CategoryTO>					listCategories;
	
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
	
	// GETTERS AND SETTERS //
	public DataModel<Object> getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel<Object> dataModel) {
		this.dataModel = dataModel;
	}

	public List<CategoryTO> getListCategories() {
		return listCategories;
	}

	public void setListCategories(List<CategoryTO> listCategories) {
		this.listCategories = listCategories;
	}
}
