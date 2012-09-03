package br.com.barganhas.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.entities.UserAccountTO;

@ManagedBean
@SessionScoped
public class AppSessionBean extends AppManagedBean {
	
	private AdministratorTO						administrator=null;
	
	private UserAccountTO						userAccount=null;

	// GETTERS AND SETTERS //
	public AdministratorTO getAdministrator() {
		return administrator;
	}

	public void setAdministrator(AdministratorTO administrator) {
		this.administrator = administrator;
	}

	public UserAccountTO getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccountTO userAccount) {
		this.userAccount = userAccount;
	}	
}
