package br.com.barganhas.web.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private String								isProductNew = "true";
	private String								pictureIndexToRemove;
	private String								selectedTab;
	
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
		if (this.advertisement.getValue() == null) {
			messages.add(new RequestMessage("advertisementValueRequiredField", SeverityMessage.ERROR));
		}
		return messages;
	}
	
	public String prepareNewStepOne() {
		try {
			this.advertisement = new AdvertisementTO();
			
			this.advertisement.setContactPhoneNumberOne(this.getUserAccountLogged().getContactPhoneNumberOne());
			this.advertisement.setContactPhoneNumberTwo(this.getUserAccountLogged().getContactPhoneNumberTwo());
			this.advertisement.setContactEmail(this.getUserAccountLogged().getContactEmail());
			
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
			if (this.selectedSheetPicture == null) {
				this.setRequestMessage(new RequestMessage("advertisementSheetIsRequired", SeverityMessage.ERROR));
				return null;
			}
			// ends validate block
			
			if (this.listAdvertisementPictures.size() > this.selectedAdvertisementType.getTotalPictures()) {
				this.listAdvertisementPictures = this.listAdvertisementPictures.subList(0, this.selectedAdvertisementType.getTotalPictures().intValue() - 1);
			}
			
			UseTerm useTermService = this.getServiceBusinessFactory().getUseTerm();
			this.useTerm = useTermService.getDefaultUseTerm();
			this.useTerm = this.service.load(this.useTerm);
			
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
				picture.setData(bytes);
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
			this.advertisement.setUseTerm(this.useTerm);
			
			if (GeneralsHelper.isStringOk(this.salesCode)) {
				Sales serviceSales = this.getServiceBusinessFactory().getSales();
				SalesTO sales = serviceSales.consultBySalesCode(this.salesCode);
				this.advertisement.setSales(sales);
			}
			
			this.advertisement.setListAdvertisementPictures(this.buildListPictures(this.listAdvertisementPictures, this.selectedSheetPicture));
			this.advertisement.setSheetPicture(this.selectedSheetPicture);
			
			UserAccountTO userAccountLogged = this.getUserAccountLogged();
			this.advertisement.setUserAccount(userAccountLogged);
			
			this.advertisement.setAdvertisementType(this.selectedAdvertisementType);
			this.advertisement.setCategory(this.selectedCategory);
			
			this.advertisement.setIsNewProduct(new Boolean(this.isProductNew));
			
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
			this.advertisement = this.service.load(new AdvertisementTO(this.advertisement.getId()));
			
			this.selectedCategory = this.advertisement.getCategory();
			this.prepareListCategories();
			
			return this.editTabData();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String editTabData() {
		try {
			this.selectedTab = "dataTab";
			
			return "advertisementEditData";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String editTabPictures() {
		try {
			this.selectedTab = "picturesTab";
			
			if (!GeneralsHelper.isCollectionOk(this.listAdvertisementPictures)) {
				this.advertisement = this.service.load(new AdvertisementTO(this.advertisement.getId()));
				
				SelectItemsBuilder selectItemsBuilder = new SelectItemsBuilder();
				
				AdvertisementPictureTO sheetPicture = this.advertisement.getSheetPicture();
				sheetPicture.setThumbnail(sheetPicture.getThumbnail());
				
				selectItemsBuilder.add(sheetPicture, sheetPicture.getThumbnail().getFileName());
				this.selectedSheetPicture = sheetPicture;
				
				if (GeneralsHelper.isCollectionOk(this.advertisement.getListAdvertisementPictures())) {
					for (AdvertisementPictureTO advertisementPicture : this.advertisement.getListAdvertisementPictures()) {
						advertisementPicture.setThumbnail(advertisementPicture.getThumbnail());
						
						selectItemsBuilder.add(advertisementPicture, advertisementPicture.getThumbnail().getFileName());
					}
				}
				this.listAdvertisementPictures = selectItemsBuilder.buildList();
				
				this.selectedAdvertisementType = this.advertisement.getAdvertisementType();
			}
			
			return "advertisementEditPictures";
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
			// starts validation
			if (this.selectedSheetPicture == null) {
				this.setRequestMessage(new RequestMessage("advertisementSheetIsRequired", SeverityMessage.ERROR));
				return null;
			}
			List<RequestMessage> messages = this.commonValidate();
			if (GeneralsHelper.isCollectionOk(messages)) {
				this.setRequestMessages(messages);
				return null;
			}
			// ends validation
			
			this.advertisement.setCategory(this.selectedCategory);
			
			this.advertisement.setListAdvertisementPictures(this.buildListPictures(this.listAdvertisementPictures, this.selectedSheetPicture));
			this.advertisement.setSheetPicture(this.selectedSheetPicture);
			
			Advertisement service = this.getServiceBusinessFactory().getAdvertisement();
			this.advertisement = service.save(this.advertisement);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	private Set<AdvertisementPictureTO> buildListPictures(List<SelectItem> listSelectItensPictures, AdvertisementPictureTO selectedSheetPicture) {
		Set<AdvertisementPictureTO> listAdvertisementPictures = new HashSet<AdvertisementPictureTO>();
		for (SelectItem selectItem : listSelectItensPictures) {
			AdvertisementPictureTO advertisementPicture = (AdvertisementPictureTO) selectItem.getValue();
			if (!advertisementPicture.getThumbnail().equals(selectedSheetPicture.getThumbnail())) {
				listAdvertisementPictures.add(advertisementPicture);
			}
		}
		return listAdvertisementPictures;
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
			this.service.evict(this.advertisement);
			this.advertisement = this.service.load(new AdvertisementTO(this.advertisement.getId()));
			
			this.listAdvertisementPictures = null;
			
			return this.consultTabData();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consultTabData() {
		try {
			this.selectedTab = "dataTab";
			
			return "advertisementConsultData";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consultTabPictures() {
		try {
			this.advertisement = this.service.load(new AdvertisementTO(this.advertisement.getId()));

			this.selectedTab = "picturesTab";
			
			return "advertisementConsultPictures";
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
			this.service.evict(this.advertisement);
			this.advertisement = this.service.load(new AdvertisementTO(this.advertisement.getId()));
			this.service.delete(this.advertisement);
			
			this.setRequestMessage(new RequestMessage("registerDeletedSuccessfully", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public void removePicturePrepareAdvertisement() {
		try {
			int index = Integer.parseInt(this.pictureIndexToRemove);
			this.listAdvertisementPictures.remove(index);
		} catch (Exception e) {
			this.manageException(e);
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

	public void setSelectedAdvertisementType(AdvertisementTypeTO selectedAdvertisementType) {
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

	public String getIsProductNew() {
		return isProductNew;
	}

	public void setIsProductNew(String isProductNew) {
		this.isProductNew = isProductNew;
	}

	public String getPictureIndexToRemove() {
		return pictureIndexToRemove;
	}

	public void setPictureIndexToRemove(String pictureIndexToRemove) {
		this.pictureIndexToRemove = pictureIndexToRemove;
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}

}
