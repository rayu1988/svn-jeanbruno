package br.com.barganhas.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import org.com.tatu.helper.GeneralsHelper;
import org.omnifaces.util.selectitems.SelectItemsBuilder;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.City;
import br.com.barganhas.business.services.State;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.commons.JSFunctionTimeRunning;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.commons.ReturnMessagePojo;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;
import br.com.barganhas.web.validators.EmailValidator;

import com.google.appengine.api.datastore.Blob;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class UserAccountBean extends AppManagedBean {
	
	private UserAccountTO						userAccount=new UserAccountTO();
	
	private String								confirmEmail;
	private String								confirmPassword;
	private	ReturnMessagePojo					returnMessage;

	private String								nickname;
	private String								password;
	
	private FileTO		 						profileImage;
	
	private DataModel<Object>					dataModel;
	
	private List<SelectItem>					listStates;
	private StateTO								selectedState;
	private List<SelectItem>					listCities;
	private CityTO								selectedCity;

	public String adminDeleteUser() {
		try {
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			service.delete(this.userAccount);
			this.setRequestMessage(new RequestMessage("registerDeletedSuccessfully", SeverityMessage.SUCCESS));
			return this.adminListUsers();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String adminLockUser() {
		try {
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			this.userAccount = service.lock(this.userAccount);
			this.setRequestMessage(new RequestMessage("userAccountHadBeenLocked", SeverityMessage.SUCCESS));
			return this.adminConsultUser();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String adminUnlockUser() {
		try {
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			this.userAccount = service.unlock(this.userAccount);
			this.setRequestMessage(new RequestMessage("userAccountHadBeenUnlocked", SeverityMessage.SUCCESS));
			return this.adminConsultUser();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String adminListUsers() {
		try {
			this.userAccount = new UserAccountTO();
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			List<UserAccountTO> list = service.list();
			this.dataModel = new CustomDataModel(list);
			return "userAccountAdminList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String adminConsultUser() {
		try {
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			this.userAccount = service.consult(this.userAccount);
			return "userAccountAdminConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public void registerUser(ActionEvent event) {
		try {
			this.returnMessage = new ReturnMessagePojo(false);
			if (!GeneralsHelper.isStringOk(this.userAccount.getFullname())) {
				this.returnMessage.addMessage(Util.getMessageResourceString("requiredFieldMessage", "Nome"));
			}
			if (this.selectedState == null || this.selectedCity == null) {
				this.returnMessage.addMessage(Util.getMessageResourceString("userAccountStateAndCityAreRequired"));
			}
			if (!GeneralsHelper.isStringOk(this.userAccount.getEmail())) {
				this.returnMessage.addMessage(Util.getMessageResourceString("requiredFieldMessage", "Email"));
			} else if (!EmailValidator.validatingEmail(this.userAccount.getEmail())) {
				this.returnMessage.addMessage(Util.getMessageResourceString("wrongEmailAddress"));
			} else if (!this.userAccount.getEmail().equals(this.confirmEmail)) {
				this.returnMessage.addMessage(Util.getMessageResourceString("confirmFieldErrorMsg", "Email", "Confirma Email"));
			}
			if (!GeneralsHelper.isStringOk(this.userAccount.getNickname())) {
				this.returnMessage.addMessage(Util.getMessageResourceString("requiredFieldMessage", "Usu�rio"));
			}
			if (!GeneralsHelper.isStringOk(this.userAccount.getPassword()) || this.userAccount.getPassword().length() < 8) {
				this.returnMessage.addMessage(Util.getMessageResourceString("passwordRequiredFieldMsg"));
			} else if (!this.userAccount.getPassword().equals(this.confirmPassword)) {
				this.returnMessage.addMessage(Util.getMessageResourceString("confirmFieldErrorMsg", "Senha", "Confirma Senha"));
			}
			
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			if (service.emailAlreadyExist(this.userAccount)) {
				String msg = Util.getMessageResourceString("uniqueFieldAlredyExistAnEqualValueEmail", this.userAccount.getEmail());
				this.returnMessage.addMessage(msg);
			}
			if (service.nicknameAlreadyExist(this.userAccount)) {
				String msg = Util.getMessageResourceString("uniqueFieldAlredyExistAnEqualValueNickname", this.userAccount.getNickname());
				this.returnMessage.addMessage(msg);
			}
			
			if (!this.returnMessage.hasMessage()) {
				this.userAccount.setKeyState(this.selectedState.getKey());
				this.userAccount.setKeyCity(this.selectedCity.getKey());
				
				service.registerNewUser(this.userAccount);
				
				this.returnMessage = new ReturnMessagePojo(true);
				this.returnMessage.addMessage(Util.getMessageResourceString("registerDoneSuccessfullyConfirmEmail", this.userAccount.getEmail()));
			}
		} catch (Exception e) {
			this.returnMessage = new ReturnMessagePojo(false).addMessage(e.getMessage());
			this.manageException(e);
		}
	}
	
	public void prepareRegister(ActionEvent event) {
		this.returnMessage = new ReturnMessagePojo();
		this.callJSFunction(JSFunctionTimeRunning.onLoad(), "openPopup", "accountNewPopupPanel");
	}
	
	public String goToLogin() {
		try {
			return "userAccountLogin";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String edit() {
		try {
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			this.userAccount = service.consult(this.userAccount);
			
			this.selectedState = this.userAccount.getState();
			this.prepareListStates();
			
			this.prepareListCities(null);
			this.selectedCity = this.userAccount.getCity();
			
			return "userAccountEdit";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public void uploadFile(FileUploadEvent event) {
		try {
			UploadedFile uploadedFile = event.getUploadedFile();
			if (uploadedFile != null) {
				byte[] bytes = uploadedFile.getData();
				
				this.profileImage = new FileTO();
				this.profileImage.setData(new Blob(bytes));
				this.profileImage.setContentType(uploadedFile.getContentType());
				this.profileImage.setFileName(uploadedFile.getName());
			} else {
				this.profileImage = null;
			}
		} catch (Exception e) {
			this.manageException(e);
		}
	}
	
	public String save() {
		try {
			if (this.selectedState == null || this.selectedCity == null) {
				throw new AppException("userAccountStateAndCityAreRequired");
			}
			this.userAccount.setKeyState(this.selectedState.getKey());
			this.userAccount.setKeyCity(this.selectedCity.getKey());
			
			UserAccount service = this.getServiceBusinessFactory().getUserAccount();
			if (this.profileImage != null) {
				this.userAccount = service.save(this.userAccount, this.profileImage);
			} else {
				this.userAccount = service.save(this.userAccount);
			}
			this.getManagedBean(AppSessionBean.class).setUserAccount(this.userAccount);
			
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consult() {
		try {
			UserAccountTO userAccount = null;
			if (this.userAccount != null && this.userAccount.getId() != null) {
				userAccount = this.userAccount;
			} else {
				userAccount = this.getUserAccountLogged();
			}
			
			if (userAccount == null) {
				return this.goToLogin();
			} else {
				UserAccount service = this.getServiceBusinessFactory().getUserAccount();
				this.userAccount = service.consult(userAccount);
				return "userAccountConsult";
			}
		} catch (Exception e) {
			return this.manageException(e);
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
			
			return this.getManagedBean(SiteBean.class).goToIndex();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	private void prepareListStates() {
		if (!GeneralsHelper.isCollectionOk(this.listStates)) {
			State service = this.getServiceBusinessFactory().getState();
			List<StateTO> list = service.list();
			SelectItemsBuilder selectItemsBuilder = new SelectItemsBuilder();
			for (StateTO state : list) {
				selectItemsBuilder.add(state, state.getName() + " (" + state.getAcronym() + ")");
			}
			this.listStates = selectItemsBuilder.buildList();
		}
	}
	
	public void prepareListCities(AjaxBehaviorEvent event) {
		if (this.selectedState != null) {
			City service = this.getServiceBusinessFactory().getCity();
			List<CityTO> list = service.list(this.selectedState);
			SelectItemsBuilder selectItemsBuilder = new SelectItemsBuilder();
			for (CityTO city : list) {
				selectItemsBuilder.add(city, city.getName());
			}
			this.listCities = selectItemsBuilder.buildList();
		} else {
			this.listCities = new ArrayList<SelectItem>();
		}
		this.selectedCity = null;
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

	public FileTO getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(FileTO profileImage) {
		this.profileImage = profileImage;
	}

	public List<SelectItem> getListStates() {
		this.prepareListStates();
		return listStates;
	}

	public void setListStates(List<SelectItem> listStates) {
		this.listStates = listStates;
	}

	public StateTO getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(StateTO selectedState) {
		this.selectedState = selectedState;
	}

	public List<SelectItem> getListCities() {
		return listCities;
	}

	public void setListCities(List<SelectItem> listCities) {
		this.listCities = listCities;
	}

	public CityTO getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(CityTO selectedCity) {
		this.selectedCity = selectedCity;
	}
}
