package br.com.barganhas.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

import org.com.tatu.helper.GeneralsHelper;
import org.omnifaces.util.selectitems.SelectItemsBuilder;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.Sales;
import br.com.barganhas.business.services.UseTerm;
import br.com.barganhas.commons.RequestMessage;
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
	
	private List<SelectItem>					listAdvertisementPictures;
	private AdvertisementPictureTO				selectedSheetPicture;
	
	private String								salesCode;
	private UseTermTO							useTerm;
	private Boolean								agreeTerms;
	
	public String adminListAdvertisements() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			List<AdvertisementTO> list = service.list();
			this.dataModel = new CustomDataModel(list);
			return "advertisementAdminList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String list() {
		try {
			UserAccountTO loggedUserAccount = this.getUserAccountLogged();
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = new AdvertisementTO();
			List<AdvertisementTO> list = service.list(loggedUserAccount);
			this.dataModel = new CustomDataModel(list);
			return "advertisementList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	private List<RequestMessage> commonValidate() {
		List<RequestMessage> messages = new ArrayList<RequestMessage>();
		if (this.selectedCategory == null) {
			messages.add(new RequestMessage("advertisementCategoryRequiredField", SeverityMessage.ERROR));
		}
		if (!GeneralsHelper.isStringOk(this.advertisement.getTitle())) {
			messages.add(new RequestMessage("advertisementTitleRequiredField", SeverityMessage.ERROR));
		}
		if (!GeneralsHelper.isStringOk(this.advertisement.getValue())) {
			messages.add(new RequestMessage("advertisementValueRequiredField", SeverityMessage.ERROR));
		}
		return messages;
	}
	
	public String prepareNewStepOne() {
		try {
			this.advertisement = new AdvertisementTO();
			this.advertisement.setContacts(this.getUserAccountLogged().getContacts());
			this.prepareListAdvertisementType();
			this.prepareListCategories();
			
			return "advertisementPrepareNewStepOne";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String backToStepOne() {
		try {
			this.prepareListAdvertisementType();
			this.prepareListCategories();
			
			return "advertisementPrepareNewStepOne";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	public String prepareNewStepTwo() {
		try {
			// start validate block
			List<RequestMessage> messages = this.commonValidate();
			if (this.selectedAdvertisementType == null) {
				messages.add(new RequestMessage("advertisementAdvertisementTypeRequiredField", SeverityMessage.ERROR));
			}
			if (GeneralsHelper.isCollectionOk(messages)) {
				this.setRequestMessages(messages);
				return null;
			}
			// ends validate block
			
			return "advertisementPrepareNewStepTwo";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	public String backToStepTwo() {
		try {
			this.listAdvertisementPictures = new ArrayList<SelectItem>();
			return "advertisementPrepareNewStepTwo";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String prepareNewStepThree() {
		try {
			// start validate block
			if (!GeneralsHelper.isCollectionOk(this.listAdvertisementPictures)) {
				this.setRequestMessage(new RequestMessage("advertisementAtLeastOnePictureIsRequired", SeverityMessage.ERROR));
				return null;
			}
			if (this.listAdvertisementPictures.size() > this.selectedAdvertisementType.getTotalPictures()) {
				this.listAdvertisementPictures = this.listAdvertisementPictures.subList(0, this.selectedAdvertisementType.getTotalPictures().intValue() - 1);
			}
			// ends validate block
			
			this.selectedSheetPicture = (AdvertisementPictureTO) this.listAdvertisementPictures.get(0).getValue();
			
			UseTerm useTermService = this.getServiceBusinessFactory().getUseTerm();
			this.useTerm = useTermService.getDefaultUseTerm();
			
			return "advertisementPrepareNewStepThree";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	private void prepareListAdvertisementType() {
		if (!GeneralsHelper.isCollectionOk(this.listAdvertisementType)) {
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
		if (!GeneralsHelper.isCollectionOk(this.listCategories)) {
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
		try {
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
		} catch (Exception e) {
			this.manageException(e);
		}
	}
	
	public String insert() {
		try {
			if (!GeneralsHelper.isBooleanTrue(this.agreeTerms)) {
				throw new AppException("advertisementAgreeIsRequired");
			}
			this.advertisement.setKeyUseTerm(this.useTerm.getKey());
			
			if (GeneralsHelper.isStringOk(this.salesCode)) {
				Sales serviceSales = this.getServiceBusinessFactory().getSales();
				SalesTO sales = serviceSales.consultBySalesCode(this.salesCode);
				this.advertisement.setSales(sales);
			}
			
			// remove the sheet picture from list pictures
			List<AdvertisementPictureTO> listAdvertisementPictures = new ArrayList<AdvertisementPictureTO>();
			for (SelectItem selectItem : this.listAdvertisementPictures) {
				AdvertisementPictureTO advertisementPicture = (AdvertisementPictureTO) selectItem.getValue();
				if (!advertisementPicture.getThumbnail().equals(this.selectedSheetPicture.getThumbnail())) {
					listAdvertisementPictures.add(advertisementPicture);
				}
			}
			this.advertisement.setListAdvertisementPictures(listAdvertisementPictures);
			this.advertisement.setSheetPicture(this.selectedSheetPicture);
			
			UserAccountTO userAccountLogged = this.getUserAccountLogged();
			this.advertisement.setUserAccount(userAccountLogged);
			
			this.advertisement.setAdvertisementType(this.selectedAdvertisementType);
			this.advertisement.setCategory(this.selectedCategory);
			
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.insert(this.advertisement);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String edit() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.consult(this.advertisement);
			this.selectedCategory = this.advertisement.getCategory();
			this.prepareListCategories();
			
			return "advertisementEdit";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String adminUnlockAdvertisement() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.unlock(this.advertisement);
			this.setRequestMessage(new RequestMessage("advertisementAccountHadBeenUnlocked", SeverityMessage.SUCCESS));
			return this.adminConsultAdvertisement();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String adminLockAdvertisement() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.lock(this.advertisement);
			this.setRequestMessage(new RequestMessage("advertisementAccountHadBeenLocked", SeverityMessage.SUCCESS));
			return this.adminConsultAdvertisement();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String save() {
		try {
			List<RequestMessage> messages = this.commonValidate();
			if (GeneralsHelper.isCollectionOk(messages)) {
				this.setRequestMessages(messages);
				return null;
			}
			
			this.advertisement.setCategory(this.selectedCategory);
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.save(this.advertisement);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String adminConsultAdvertisement() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.adminConsult(this.advertisement);
			return "advertisementAdminConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consult() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.consult(this.advertisement);
			
			return "advertisementConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	public String adminDeleteAdvertisement() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			service.delete(this.advertisement);
			this.setRequestMessage(new RequestMessage("registerDeletedSuccessfully", SeverityMessage.SUCCESS));
			return this.adminListAdvertisements();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String delete() {
		try {
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			service.delete(this.advertisement);
			
			this.setRequestMessage(new RequestMessage("registerDeletedSuccessfully", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
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

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}

	public UseTermTO getUseTerm() {
		return useTerm;
	}

	public void setUseTerm(UseTermTO useTerm) {
		this.useTerm = useTerm;
	}

	public Boolean getAgreeTerms() {
		return agreeTerms;
	}

	public void setAgreeTerms(Boolean agreeTerms) {
		this.agreeTerms = agreeTerms;
	}

}
