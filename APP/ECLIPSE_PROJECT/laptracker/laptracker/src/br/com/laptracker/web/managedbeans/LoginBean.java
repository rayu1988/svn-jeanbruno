package br.com.laptracker.web.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpServletResponse;

import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.service.User;
import br.com.laptracker.commons.RequestMessage;
import br.com.laptracker.commons.enums.SeverityMessage;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class LoginBean extends AppManagedBean {

	private String								nickname;
	private String								password;
	
	public String login() {
		try {
			User service = this.getServiceBusinessFactory().getUser();
			UserTO user = new UserTO();
			user.setNickname(this.nickname);
			user.setPassword(this.password);
			user = service.validateLogin(user);
			if (user != null) {
				AppSessionBean sessionBean = this.getManagedBean(AppSessionBean.class);
				sessionBean.setUserAccount(user);
				
				this.setRequestMessage(new RequestMessage("loginSuccessfully", SeverityMessage.SUCCESS));
				return this.goToDashboard();
			} else {
				this.setRequestMessage(new RequestMessage("loginErrorUserNotFound", SeverityMessage.ERROR));
				return this.goToLogin();
			}
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String logoff() {
		try {
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
			
			return this.goToLogin();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String goToLogin() {
		try {
			return "login";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	// GETTERS AND SETTERS //
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
