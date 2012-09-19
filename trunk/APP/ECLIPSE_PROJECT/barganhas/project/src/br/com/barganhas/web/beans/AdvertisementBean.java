package br.com.barganhas.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

import org.omnifaces.util.selectitems.SelectItemsBuilder;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

import com.google.appengine.api.datastore.Blob;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class AdvertisementBean extends AppManagedBean {
	
	private AdvertisementTO						advertisement;
	private DataModel<Object>					dataModel;
	
	private List<SelectItem>					listAdvertisementType;
	private AdvertisementTypeTO					selectedAdvertisementType;
	private List<SelectItem>					listCategories;
	private CategoryTO							selectedCategory;
	
	private List<SelectItem>		listAdvertisementPictures;
	private AdvertisementPictureTO				selectedSheetPicture;
	
	public String list() {
		UserAccountTO loggedUserAccount = this.getUserAccountLogged();
		Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
		this.advertisement = new AdvertisementTO();
		List<AdvertisementTO> list = service.list(loggedUserAccount);
		this.dataModel = new CustomDataModel(list);
		return "advertisementList";
	}
	
	public String prepareNewStepOne() {
		this.advertisement = new AdvertisementTO();
		this.advertisement.setContacts(this.getUserAccountLogged().getContacts());
		this.prepareListAdvertisementType();
		this.prepareListCategories();
		
		return "advertisementPrepareNewStepOne";
	}
	
	public String prepareNewStepTwo() {
		// start validate block
		List<RequestMessage> messages = new ArrayList<RequestMessage>();
		if (this.selectedAdvertisementType == null) {
			messages.add(new RequestMessage("advertisementAdvertisementTypeRequiredField", SeverityMessage.ERROR));
		}
		if (this.selectedCategory == null) {
			messages.add(new RequestMessage("advertisementCategoryRequiredField", SeverityMessage.ERROR));
		}
		if (!Util.isStringOk(this.advertisement.getTitle())) {
			messages.add(new RequestMessage("advertisementTitleRequiredField", SeverityMessage.ERROR));
		}
		if (!Util.isStringOk(this.advertisement.getValue())) {
			messages.add(new RequestMessage("advertisementValueRequiredField", SeverityMessage.ERROR));
		}
		if (Util.isCollectionOk(messages)) {
			this.setRequestMessages(messages);
			return null;
		}
		// ends validate block
		
		return "advertisementPrepareNewStepTwo";
	}
	
	public String prepareNewStepThree() {
		// start validate block
		List<RequestMessage> messages = new ArrayList<RequestMessage>();
		if (!Util.isCollectionOk(this.listAdvertisementPictures)) {
			messages.add(new RequestMessage("advertisementAtLeastOnePictureIsRequired", SeverityMessage.ERROR));
		}
		if (Util.isCollectionOk(messages)) {
			this.setRequestMessages(messages);
			return null;
		}
		// ends validate block
		
		
		
		return "advertisementPrepareNewStepThree";
	}
	
	private void prepareListAdvertisementType() {
		if (!Util.isCollectionOk(this.listAdvertisementType)) {
			AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
			List<AdvertisementTypeTO> listTemp = service.list();
			SelectItemsBuilder selectItemsBuilder = new SelectItemsBuilder();
			for (AdvertisementTypeTO advertisementType : listTemp) {
				selectItemsBuilder.add(advertisementType, advertisementType.getTitle());
			}
			this.listAdvertisementType = selectItemsBuilder.buildList();
		}
	}
	
	private void prepareListCategories() {
		if (!Util.isCollectionOk(this.listCategories)) {
			Category service = this.getServiceBusinessFactory().getCategory();
			List<CategoryTO> listTemp = service.list();
			SelectItemsBuilder selectItemsBuilder = new SelectItemsBuilder();
			for (CategoryTO category : listTemp) {
				selectItemsBuilder.add(category, category.getName());
			}
			this.listCategories = selectItemsBuilder.buildList();
		}
	}
	
	public void uploadFile(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getUploadedFile();
		if (uploadedFile != null) {
			byte[] bytes = uploadedFile.getData();
			
			FileTO picture = new FileTO();
			picture.setData(new Blob(bytes));
			picture.setContentType(uploadedFile.getContentType());
			picture.setFileName(uploadedFile.getName());
			
			AdvertisementPicture service = this.getServiceBusinessFactory().getAdvertisementPicture();
			AdvertisementPictureTO advertisementPicture = service.newAdvertisementPicture(picture);
			
			SelectItemsBuilder selectItemsBuilder = new SelectItemsBuilder();
			for (SelectItem selectItem : this.listAdvertisementPictures) {
				AdvertisementPictureTO currentPicture = (AdvertisementPictureTO) selectItem.getValue();
				selectItemsBuilder.add(currentPicture, currentPicture.getThumbnail().getFileName());
			}
			selectItemsBuilder.add(advertisementPicture, advertisementPicture.getThumbnail().getFileName());
			
			this.listAdvertisementPictures = selectItemsBuilder.buildList();
		}
	}
	
	public String insert() {
		Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
		
		UserAccountTO userAccountLogged = this.getUserAccountLogged();
		
		this.advertisement.setKeyUserAccount(userAccountLogged.getKey());
		this.advertisement.setKeyCategory(this.selectedCategory.getKey());
		this.advertisement.setKeyAdvertisementType(this.selectedAdvertisementType.getKey());
		this.advertisement = service.insert(this.advertisement, userAccountLogged);
		
		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.list();
	}
	
	public String edit() {
//		Administrator service = this.getServiceBusinessFactory().getAdministrator();
//		this.administrator = service.consult(this.administrator);
		
		return "advertisementEdit";
	}
	
	public String save() {
//		Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
//		service.insert(this.advertisement);
		
//		
//		Administrator service = this.getServiceBusinessFactory().getAdministrator();
//		service.save(this.administrator);
//		
//		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.consult();
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

	public List<SelectItem> getListAdvertisementPictures() {
		if (listAdvertisementPictures == null) {
			listAdvertisementPictures = new ArrayList<SelectItem>();
		}
		return listAdvertisementPictures;
	}

	public void setListAdvertisementPictures(List<SelectItem> listAdvertisementPictures) {
		this.listAdvertisementPictures = listAdvertisementPictures;
	}

	public AdvertisementTypeTO getSelectedAdvertisementType() {
		return selectedAdvertisementType;
	}

	public void setSelectedAdvertisementType(
			AdvertisementTypeTO selectedAdvertisementType) {
		this.selectedAdvertisementType = selectedAdvertisementType;
	}

	public List<SelectItem> getListCategories() {
		return listCategories;
	}

	public void setListCategories(List<SelectItem> listCategories) {
		this.listCategories = listCategories;
	}

	public CategoryTO getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(CategoryTO selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public AdvertisementPictureTO getSelectedSheetPicture() {
		return selectedSheetPicture;
	}

	public void setSelectedSheetPicture(AdvertisementPictureTO selectedSheetPicture) {
		this.selectedSheetPicture = selectedSheetPicture;
	}
}
