package br.com.barganhas.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;
import br.com.barganhas.web.validators.EmailValidator;

@ManagedBean
@RequestScoped
public class AdvertisementBean extends AppManagedBean {
	
	private AdvertisementTO						advertisement;
	private DataModel<Object>					dataModel;
	
	private List<SelectItem>					listAdvertisementType;
	private String								keyAdvertisementType;
	
	public String list() {
		Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
		this.advertisement = new AdvertisementTO();
		List<AdvertisementTO> list = service.list();
		this.dataModel = new CustomDataModel(list);
		return "advertisementList";
	}
	
	public String prepareNew() {
		this.advertisement = new AdvertisementTO();
		this.prepareListAdvertisementType();
		
		return "advertisementPrepareNew";
	}
	
	private void prepareListAdvertisementType() {
		if (!Util.isCollectionOk(this.listAdvertisementType)) {
			AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
			List<AdvertisementTypeTO> listTemp = service.list();
			this.listAdvertisementType = Util.createListSelectItens(listTemp, "keyAsString", "title");
		}
	}
	
	public String insert() {
//		List<RequestMessage> messagesValidate = this.validate();
//		if (Util.isCollectionOk(messagesValidate)) {
//			this.setRequestMessages(messagesValidate);
//			return null;
//		}
//		
//		Administrator service = this.getServiceBusinessFactory().getAdministrator();
//		service.insert(this.administrator);
//		
//		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.list();
	}
	
	public String edit() {
//		Administrator service = this.getServiceBusinessFactory().getAdministrator();
//		this.administrator = service.consult(this.administrator);
		
		return "advertisementEdit";
	}
	
	public String save() {
//		List<RequestMessage> messagesValidate = this.validate();
//		if (Util.isCollectionOk(messagesValidate)) {
//			this.setRequestMessages(messagesValidate);
//			return null;
//		}
//		
//		Administrator service = this.getServiceBusinessFactory().getAdministrator();
//		service.save(this.administrator);
//		
//		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.consult();
	}
	
	private List<RequestMessage> validate() {
		List<RequestMessage> messages = new ArrayList<RequestMessage>();
//		if (!Util.isStringOk(this.administrator.getFullname())) {
//			messages.add(new RequestMessage("administratorRequiredFieldFullname", SeverityMessage.WARNING));
//		}
//		if (!Util.isStringOk(this.administrator.getEmail())) {
//			messages.add(new RequestMessage("administratorRequiredFieldEmail", SeverityMessage.WARNING));
//		} else if (!EmailValidator.validatingEmail(this.administrator.getEmail())) {
//			messages.add(new RequestMessage("wrongEmailAddress", SeverityMessage.WARNING));
//		}
//		if (!Util.isStringOk(this.administrator.getNickname())) {
//			messages.add(new RequestMessage("administratorRequiredFieldNickname", SeverityMessage.WARNING));
//		}
		return messages;
	}
	
	public String consult() {
//		Administrator service = this.getServiceBusinessFactory().getAdministrator();
//		this.administrator = service.consult(this.administrator);
		
		return "administratorConsult";
	}

	public String delete() {
//		Administrator service = this.getServiceBusinessFactory().getAdministrator();
//		service.delete(this.administrator);
//		
//		this.setRequestMessage(new RequestMessage("registerDeletedSuccessfully", SeverityMessage.SUCCESS));
		return this.list();
	}
	
	// GETTERS AND SETTERS //
	public DataModel<Object> getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel<Object> dataModel) {
		this.dataModel = dataModel;
	}

	public AdvertisementTO getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(AdvertisementTO advertisement) {
		this.advertisement = advertisement;
	}

	public List<SelectItem> getListAdvertisementType() {
		return listAdvertisementType;
	}

	public void setListAdvertisementType(List<SelectItem> listAdvertisementType) {
		this.listAdvertisementType = listAdvertisementType;
	}

	public String getKeyAdvertisementType() {
		return keyAdvertisementType;
	}

	public void setKeyAdvertisementType(String keyAdvertisementType) {
		this.keyAdvertisementType = keyAdvertisementType;
	}
}
