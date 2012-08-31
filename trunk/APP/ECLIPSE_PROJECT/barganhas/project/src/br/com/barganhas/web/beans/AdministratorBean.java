package br.com.barganhas.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;
import br.com.barganhas.web.validators.EmailValidator;

@ManagedBean
@RequestScoped
public class AdministratorBean extends AppManagedBean {
	
	private AdministratorTO						administrator;
	private DataModel<Object>					dataModel;
	
	public String list() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		this.administrator = new AdministratorTO();
		List<AdministratorTO> list = service.list(this.administrator);
		this.dataModel = new CustomDataModel(list);
		return "administratorList";
	}
	
	public String filter() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		List<AdministratorTO> list = service.filter(this.administrator);
		
		this.dataModel = new CustomDataModel(list);
		return "administratorList";
	}
	
	public String prepareNew() {
		this.administrator = new AdministratorTO();
		
		return "administratorPrepareNew";
	}
	
	public String insert() {
		List<RequestMessage> messagesValidate = this.validate();
		if (Util.isCollectionOk(messagesValidate)) {
			this.setRequestMessages(messagesValidate);
			return null;
		}
		
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		service.insert(this.administrator);
		
		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.list();
	}
	
	public String edit() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		this.administrator = service.consult(this.administrator);
		
		return "administratorEdit";
	}
	
	public String save() {
		List<RequestMessage> messagesValidate = this.validate();
		if (Util.isCollectionOk(messagesValidate)) {
			this.setRequestMessages(messagesValidate);
			return null;
		}
		
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		service.save(this.administrator);
		
		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.consult();
	}
	
	private List<RequestMessage> validate() {
		List<RequestMessage> messages = new ArrayList<RequestMessage>();
		if (!Util.isStringOk(this.administrator.getFullname())) {
			messages.add(new RequestMessage("administratorRequiredFieldFullname", SeverityMessage.WARNING));
		}
		if (!Util.isStringOk(this.administrator.getEmail())) {
			messages.add(new RequestMessage("administratorRequiredFieldEmail", SeverityMessage.WARNING));
		} else if (!EmailValidator.validaEmail(this.administrator.getEmail())) {
			messages.add(new RequestMessage("wrongEmailAddress", SeverityMessage.WARNING));
		}
		if (!Util.isStringOk(this.administrator.getNickname())) {
			messages.add(new RequestMessage("administratorRequiredFieldNickname", SeverityMessage.WARNING));
		}
		return messages;
	}
	
	public String consult() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		this.administrator = service.consult(this.administrator);
		
		return "administratorConsult";
	}

	public String delete() {
		Administrator service = this.getServiceBusinessFactory().getAdministrator();
		service.delete(this.administrator);
		
		this.setRequestMessage(new RequestMessage("registerDeletedSuccessfully", SeverityMessage.SUCCESS));
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
