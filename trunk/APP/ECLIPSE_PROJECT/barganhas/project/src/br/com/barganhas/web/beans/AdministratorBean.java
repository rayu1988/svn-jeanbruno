package br.com.barganhas.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import org.com.tatu.helper.GeneralsHelper;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;
import br.com.barganhas.web.validators.EmailValidator;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class AdministratorBean extends AppManagedBean {
	
	private AdministratorTO						administrator;
	private DataModel<Object>					dataModel;
	
	public String list() {
		try {
			Administrator service = this.getServiceBusinessFactory().getAdministrator();
			this.administrator = new AdministratorTO();
			List<AdministratorTO> list = service.list();
			this.dataModel = new CustomDataModel(list);
			return "administratorList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String filter() {
		try {
			Administrator service = this.getServiceBusinessFactory().getAdministrator();
			List<AdministratorTO> list = service.filter(this.administrator);
			
			this.dataModel = new CustomDataModel(list);
			return "administratorList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String prepareNew() {
		try {
			this.administrator = new AdministratorTO();
			
			return "administratorPrepareNew";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String insert() {
		try {
			this.validate();
			
			Administrator service = this.getServiceBusinessFactory().getAdministrator();
			service.insert(this.administrator);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String edit() {
		try {
			Administrator service = this.getServiceBusinessFactory().getAdministrator();
			this.administrator = service.consult(this.administrator);
			
			return "administratorEdit";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String save() {
		try {
			this.validate();
			
			Administrator service = this.getServiceBusinessFactory().getAdministrator();
			service.save(this.administrator);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	private void validate() {
		List<RequestMessage> messages = new ArrayList<RequestMessage>();
		if (!GeneralsHelper.isStringOk(this.administrator.getFullname())) {
			messages.add(new RequestMessage("administratorRequiredFieldFullname", SeverityMessage.WARNING));
		}
		if (!GeneralsHelper.isStringOk(this.administrator.getEmail())) {
			messages.add(new RequestMessage("administratorRequiredFieldEmail", SeverityMessage.WARNING));
		} else if (!EmailValidator.validatingEmail(this.administrator.getEmail())) {
			messages.add(new RequestMessage("wrongEmailAddress", SeverityMessage.WARNING));
		}
		if (!GeneralsHelper.isStringOk(this.administrator.getNickname())) {
			messages.add(new RequestMessage("administratorRequiredFieldNickname", SeverityMessage.WARNING));
		}
		
		if (GeneralsHelper.isCollectionOk(messages)) {
			throw new AppException(messages);
		}
	}
	
	public String consult() {
		try {
			Administrator service = this.getServiceBusinessFactory().getAdministrator();
			this.administrator = service.consult(this.administrator);
			
			return "administratorConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	public String delete() {
		try {
			Administrator service = this.getServiceBusinessFactory().getAdministrator();
			service.delete(this.administrator);
			
			this.setRequestMessage(new RequestMessage("registerDeletedSuccessfully", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
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
