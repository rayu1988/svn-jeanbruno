package br.com.barganhas.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import com.google.appengine.api.datastore.KeyFactory;

import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
public class AdvertisementBean extends AppManagedBean {
	
	private AdvertisementTO						advertisement;
	private DataModel<Object>					dataModel;
	
	private List<SelectItem>					listAdvertisementType;
	private String								keyAdvertisementType;
	private List<UploadedFile>					listAdvertisementPictures;
	
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
	
	public void uploadFile(FileUploadEvent event) {
		if (this.listAdvertisementPictures == null) {
			this.listAdvertisementPictures = new ArrayList<UploadedFile>();
		}
		this.listAdvertisementPictures.add(event.getUploadedFile());
	}
	
	public String insert() {
		List<RequestMessage> messagesValidate = this.validate();
		if (Util.isCollectionOk(messagesValidate)) {
			this.setRequestMessages(messagesValidate);
			return null;
		}
		
		Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
		
		UserAccountTO userAccountLogged = this.getManagedBean(AppSessionBean.class).getUserAccount();
		
		this.advertisement.setKeyUserAccount(userAccountLogged.getKey());
		this.advertisement.setKeyAdvertisementType(KeyFactory.stringToKey(this.keyAdvertisementType));
		this.advertisement = service.insert(this.advertisement);
		
		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.list();
	}
	
	public String edit() {
//		Administrator service = this.getServiceBusinessFactory().getAdministrator();
//		this.administrator = service.consult(this.administrator);
		
		return "advertisementEdit";
	}
	
	public String save() {
		List<RequestMessage> messagesValidate = this.validate();
		if (Util.isCollectionOk(messagesValidate)) {
			this.setRequestMessages(messagesValidate);
			return null;
		}

		Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
		service.insert(this.advertisement);
		
//		
//		Administrator service = this.getServiceBusinessFactory().getAdministrator();
//		service.save(this.administrator);
//		
//		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.consult();
	}
	
	private List<RequestMessage> validate() {
		List<RequestMessage> messages = new ArrayList<RequestMessage>();
		if (!Util.isStringOk(this.keyAdvertisementType)) {
			messages.add(new RequestMessage("advertisementAdvertisementTypeRequiredField", SeverityMessage.ERROR));
		}
		if (!Util.isStringOk(this.advertisement.getTitle())) {
			messages.add(new RequestMessage("advertisementTitleRequiredField", SeverityMessage.ERROR));
		}
		if (!Util.isStringOk(this.advertisement.getValue())) {
			messages.add(new RequestMessage("advertisementValueRequiredField", SeverityMessage.ERROR));
		}
//		if (!Util.isCollectionOk(this.listAdvertisementPictures)) {
//			messages.add(new RequestMessage("advertisementPicturesRequiredField", SeverityMessage.ERROR));
//		}
		return messages;
	}
	
	public String consult() {
		Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
		this.advertisement = service.consult(this.advertisement);
		
		return "advertisementConsult";
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

	public List<UploadedFile> getListAdvertisementPictures() {
		return listAdvertisementPictures;
	}

	public void setListAdvertisementPictures(
			List<UploadedFile> listAdvertisementPictures) {
		this.listAdvertisementPictures = listAdvertisementPictures;
	}
}