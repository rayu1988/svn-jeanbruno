package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class AdvertisementTypeBean extends AppManagedBean {
	
	private AdvertisementTypeTO					advertisementType;
	private DataModel<Object>					dataModel;
	
	public String list() {
		try {
			AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
			this.advertisementType = new AdvertisementTypeTO();
			List<AdvertisementTypeTO> list = service.list();
			this.dataModel = new CustomDataModel(list);
			return "advertisementTypeList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String prepareNew() {
		try {
			this.advertisementType = new AdvertisementTypeTO();
			
			return "advertisementTypePrepareNew";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String insert() {
		try {
			AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
			service.insert(this.advertisementType);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String edit() {
		try {
			AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
			this.advertisementType = service.consult(this.advertisementType);
			
			return "advertisementTypeEdit";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String save() {
		try {
			AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
			service.save(this.advertisementType);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consult() {
		try {
			AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
			this.advertisementType = service.consult(this.advertisementType);
			
			return "advertisementTypeConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	public String delete() {
		try {
			AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
			service.delete(this.advertisementType);
			
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
	public AdvertisementTypeTO getAdvertisementType() {
		return advertisementType;
	}
	public void setAdvertisementType(AdvertisementTypeTO advertisementType) {
		this.advertisementType = advertisementType;
	}
}
