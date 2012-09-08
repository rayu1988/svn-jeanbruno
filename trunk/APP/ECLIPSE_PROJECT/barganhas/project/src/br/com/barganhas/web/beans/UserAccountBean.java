package br.com.barganhas.web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.KeyFactory;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.commons.JSFunctionTimeRunning;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.commons.ReturnMessagePojo;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.validators.EmailValidator;

@ManagedBean
@RequestScoped
public class UserAccountBean extends AppManagedBean {
	
	private UserAccountTO						userAccount=new UserAccountTO();
	
	private String								confirmEmail;
	private String								confirmPassword;
	private	ReturnMessagePojo					returnMessage;

	private String								nickname;
	private String								password;
	
	private UploadedFile 						profileImage;
	
	private DataModel<Object>					dataModel;

	public void registerUser(ActionEvent event) {
		try {
			this.returnMessage = new ReturnMessagePojo(false);
			if (!Util.isStringOk(this.userAccount.getFullname())) {
				this.returnMessage.addMessage(Util.getMessageResourceString("requiredFieldMessage", "Nome"));
			}
			if (!Util.isStringOk(this.userAccount.getEmail())) {
				this.returnMessage.addMessage(Util.getMessageResourceString("requiredFieldMessage", "Email"));
			} else if (!EmailValidator.validatingEmail(this.userAccount.getEmail())) {
				this.returnMessage.addMessage(Util.getMessageResourceString("wrongEmailAddress"));
			} else if (!this.userAccount.getEmail().equals(this.confirmEmail)) {
				this.returnMessage.addMessage(Util.getMessageResourceString("confirmFieldErrorMsg", "Email", "Confirma Email"));
			}
			if (!Util.isStringOk(this.userAccount.getNickname())) {
				this.returnMessage.addMessage(Util.getMessageResourceString("requiredFieldMessage", "Usuário"));
			}
			if (!Util.isStringOk(this.userAccount.getPassword())) {
				this.returnMessage.addMessage(Util.getMessageResourceString("requiredFieldMessage", "Senha"));
			} else if (!this.userAccount.getPassword().equals(this.confirmPassword)) {
				this.returnMessage.addMessage(Util.getMessageResourceString("confirmFieldErrorMsg", "Senha", "Confirma Senha"));
			}
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			if (service.emailAlreadyExist(this.userAccount)) {
				this.returnMessage.addMessage(Util.getMessageResourceString("uniqueFieldAlredyExistAnEqualValueEmail", this.userAccount.getEmail()));
			}
			if (service.nicknameAlreadyExist(this.userAccount)) {
				this.returnMessage.addMessage(Util.getMessageResourceString("uniqueFieldAlredyExistAnEqualValueNickname", this.userAccount.getNickname()));
			}
			
			if (!this.returnMessage.hasMessage()) {
				service.registerNewUser(this.userAccount);
				this.returnMessage = new ReturnMessagePojo(true);
				this.returnMessage.addMessage(Util.getMessageResourceString("registerDoneSuccessfullyConfirmEmail", this.userAccount.getEmail()));

				this.callJSFunction(JSFunctionTimeRunning.onLoad(), "userAccountNewRegisterSuccessfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.trateExceptionMessage(e);
		}
	}
	
	public void prepareRegister(ActionEvent event) {
		this.returnMessage = new ReturnMessagePojo();
		this.callJSFunction(JSFunctionTimeRunning.onLoad(), "openPopup", "accountNewPopupPanel");
	}
	
	public String goToLogin() {
		return "userAccountLogin";
	}
	
	public String edit() {
		UserAccount service = this.getServiceBusinessFactory().getUserAccount();
		this.userAccount = service.consult(this.userAccount);
		
		return "userAccountEdit";
	}
	
	public void uploadFile(FileUploadEvent event) {
		this.profileImage = event.getUploadedFile();
	}
	
	public String save() {
		UserAccount service = this.getServiceBusinessFactory().getUserAccount();
		if (this.profileImage != null) {
			FileTO file = new FileTO();
			file.setContentType(this.profileImage.getContentType());
			file.setFileName(this.profileImage.getName());

			byte[] bytes = this.profileImage.getData();
			file.setData(new Blob(bytes));
			
			service.save(this.userAccount, file);
		} else {
			service.save(this.userAccount);
		}
		
		return this.consult();
	}
	
	public String consult() {
		UserAccountTO userAccount = null;
		if (this.userAccount != null && this.userAccount.getId() != null) {
			userAccount = this.userAccount;
		} else {
			AppSessionBean sessionBean = this.getManagedBean(AppSessionBean.class);
			userAccount = sessionBean.getUserAccount();
		}
		
		if (userAccount == null) {
			return this.goToLogin();
		} else {
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			this.userAccount = service.consult(userAccount);
			return "userAccountConsult";
		}
	}
	
	public String login() {
		try {
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			UserAccountTO userAccount = new UserAccountTO();
			userAccount.setNickname(this.nickname);
			userAccount.setPassword(this.password);
			userAccount = service.validateLogin(userAccount);
			if (userAccount != null) {
				AppSessionBean sessionBean = this.getManagedBean(AppSessionBean.class);
				sessionBean.setUserAccount(userAccount);
			}
			
			return this.getManagedBean(SiteBean.class).goToIndex();
		} catch (AppException e) {
			this.setRequestMessage(new RequestMessage("loginErrorUserNotFound", SeverityMessage.ERROR));
			return null;
		} catch (Exception e) {
			return this.trateExceptionMessage(e);
		}
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
		
		return this.getManagedBean(SiteBean.class).goToIndex();
	}
	
	public String getCurrentKeyProfileImage() {
		if (this.userAccount != null && this.userAccount.getKeyProfileImage() != null) {
			return KeyFactory.keyToString(this.userAccount.getKeyProfileImage());
		}
		return null;
	}
	
	// GETTERS AND SETTERS //
	public DataModel<Object> getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel<Object> dataModel) {
		this.dataModel = dataModel;
	}

	public UserAccountTO getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccountTO userAccount) {
		this.userAccount = userAccount;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public ReturnMessagePojo getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(ReturnMessagePojo returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UploadedFile getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(UploadedFile profileImage) {
		this.profileImage = profileImage;
	}
}
