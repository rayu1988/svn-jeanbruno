package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
public class AdvertisementTypeBean extends AppManagedBean {
	
	private AdvertisementTypeTO					advertisementType;
	private DataModel<Object>					dataModel;
	
	public String list() {
		AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
		List<AdvertisementTypeTO> list = service.list(new AdvertisementTypeTO());
		
		this.dataModel = new CustomDataModel(list);
		this.advertisementType = new AdvertisementTypeTO();
		
		return "advertisementTypeList";
	}
	
	public String prepareNew() {
		this.advertisementType = new AdvertisementTypeTO();
		
		return "advertisementTypePrepareNew";
	}
	
	public String insert() {
		AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
		service.insert(this.advertisementType);
		
		return this.list();
	}
	
	public String edit() {
		AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
		this.advertisementType = service.consult(this.advertisementType);
		
		return "advertisementTypeEdit";
	}
	
	public String save() {
		AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
		service.save(this.advertisementType);
		
		return this.consult();
	}
	
	public String consult() {
		AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
		this.advertisementType = service.consult(this.advertisementType);
		
		return "advertisementTypeConsult";
	}

	public String delete() {
		AdvertisementType service = this.getServiceBusinessFactory().getAdvertisementType();
		service.delete(this.advertisementType);
		
		return this.list();
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
