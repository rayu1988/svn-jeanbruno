package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
public class AdministratorBean extends AppManagedBean {
	
	private AdministratorTO						administrator;
	private DataModel<Object>					dataModel;
	
	public String list() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		List<AdministratorTO> list = service.list(new AdministratorTO());
		
		this.dataModel = new CustomDataModel(list);
		
		return "administratorList";
	}
	
	public String prepareNew() {
		this.administrator = new AdministratorTO();
		
		return "administratorPrepareNew";
	}
	
	public String insert() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		service.insert(this.administrator);
		
		return this.list();
	}
	
	public String edit() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		this.administrator = service.consult(this.administrator);
		
		return "administratorEdit";
	}
	
	public String save() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		service.save(this.administrator);
		
		return this.consult();
	}
	
	public String consult() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		this.administrator = service.consult(this.administrator);
		
		return "administratorConsult";
	}

	public String delete() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		service.delete(this.administrator);
		
		return this.list();
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
