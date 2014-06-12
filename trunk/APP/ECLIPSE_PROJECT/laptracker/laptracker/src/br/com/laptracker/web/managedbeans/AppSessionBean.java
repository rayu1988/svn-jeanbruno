package br.com.laptracker.web.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.laptracker.business.entities.UserTO;

@ManagedBean
@SessionScoped
@SuppressWarnings("serial")
public class AppSessionBean extends AppManagedBean {
	
	private UserTO						userAccount=null;

	// GETTERS AND SETTERS //
	public UserTO getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserTO userAccount) {
		this.userAccount = userAccount;
	}	
}
