package br.com.barganhas.web.beans;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.AdministratorTO;

@ManagedBean
@RequestScoped
public class AdministratorBean extends AppManagedBean {
	
	private AdministratorTO						administrator;
	private DataModel<Object>					dataModel;
	
	public String list() {
		System.out.println("administratorList");
		return "administratorList";
	}
	
	public String consult() {
		return null;
	}

	// GETTERS AND SETTERS //
	public AdministratorTO getAdministrator() {
		return administrator;
	}

	public void setAdministrator(AdministratorTO administrator) {
		this.administrator = administrator;
	}

	public DataModel<Object> getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel<Object> dataModel) {
		this.dataModel = dataModel;
	}
}
