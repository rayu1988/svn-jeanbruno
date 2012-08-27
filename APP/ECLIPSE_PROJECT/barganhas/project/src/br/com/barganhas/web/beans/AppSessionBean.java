package br.com.barganhas.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.barganhas.business.entities.AdministratorTO;

@ManagedBean
@SessionScoped
public class AppSessionBean extends AppManagedBean {
	
	private AdministratorTO						administrator=null;

	// GETTERS AND SETTERS //
	public AdministratorTO getAdministrator() {
		return administrator;
	}

	public void setAdministrator(AdministratorTO administrator) {
		this.administrator = administrator;
	}
	
}
