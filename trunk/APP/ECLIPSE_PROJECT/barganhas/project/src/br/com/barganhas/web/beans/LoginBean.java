package br.com.barganhas.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.services.Administrator;

@ManagedBean
@RequestScoped
public class LoginBean extends AppManagedBean {
	
	private AdministratorTO						administrator;
	private String								user;
	private String								password;
	
	
	public String login() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		this.administrator = new AdministratorTO();
		this.administrator.setNickname(this.user);
		this.administrator.setPassword(this.password);
		AdministratorTO administrator = service.validateLogin(this.administrator);
		if (administrator != null) {
			AppSessionBean sessionBean = this.getManagedBean(AppSessionBean.class);
			sessionBean.setAdministrator(administrator);
		}
		
		return this.goToIndex();
	}
	
	public String logoff() {
		this.destroyCurrentSession();
		
		HttpServletResponse response = this.getHttpServletResponse();
		response.setHeader("Pragma", "no-Cache");
		response.setHeader("Cache-Control", "no-cache,");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Cache-Control", "private");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Cache-Control", "max-stale=0");
		response.setHeader("Cache-Control", "max-age=0");
		response.setDateHeader("Expires", 1);
		
		return "login";
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AdministratorTO getAdministrator() {
		return administrator;
	}

	public void setAdministrator(AdministratorTO administrator) {
		this.administrator = administrator;
	}
}
