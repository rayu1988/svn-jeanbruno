package br.com.barganhas.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class SiteBean extends AppManagedBean {
	
	private DataModel<Object>					dataModel;

	public String goToIndex() {
		return "siteIndex";
	}
	
	// GETTERS AND SETTERS //
	public DataModel<Object> getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel<Object> dataModel) {
		this.dataModel = dataModel;
	}
}
