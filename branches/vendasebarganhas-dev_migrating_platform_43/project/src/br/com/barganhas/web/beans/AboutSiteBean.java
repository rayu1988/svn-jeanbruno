package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.AboutSiteTO;
import br.com.barganhas.business.services.AboutSite;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class AboutSiteBean extends AppManagedBean {
	
	private AboutSiteTO							aboutSite;
	private DataModel<Object>					dataModel;
	
	public String aboutSite() {
		try {
			AboutSite service = this.getServiceBusinessFactory().getAboutSite();
			this.aboutSite = service.getDefault();
			
			this.aboutSite = this.service.load(new AboutSiteTO(this.aboutSite.getId()));
			
			return "aboutSite";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String list() {
		try {
			AboutSite service = this.getServiceBusinessFactory().getAboutSite();
			this.aboutSite = new AboutSiteTO();
			List<AboutSiteTO> list = service.list();
			this.dataModel = new CustomDataModel(list);
			return "aboutSiteList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String prepareNew() {
		try {
			this.aboutSite = new AboutSiteTO();
			
			return "aboutSitePrepareNew";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String insert() {
		try {
			AboutSite service = this.getServiceBusinessFactory().getAboutSite();
			service.insert(this.aboutSite);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String edit() {
		try {
			AboutSite service = this.getServiceBusinessFactory().getAboutSite();
			this.aboutSite = service.consult(this.aboutSite);
			
			return "aboutSiteEdit";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String save() {
		try {
			AboutSite service = this.getServiceBusinessFactory().getAboutSite();
			this.aboutSite = service.save(this.aboutSite);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String turnDefault() {
		try {
			AboutSite service = this.getServiceBusinessFactory().getAboutSite();
			this.aboutSite = service.turnDefault(this.aboutSite);
			
			this.setRequestMessage(new RequestMessage("aboutSiteHadBeenUnlocked", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consult() {
		try {
			AboutSite service = this.getServiceBusinessFactory().getAboutSite();
			this.aboutSite = service.consult(this.aboutSite);
			
			return "aboutSiteConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	public String delete() {
		try{	
			AboutSite service = this.getServiceBusinessFactory().getAboutSite();
			service.delete(this.aboutSite);
			
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

	public AboutSiteTO getAboutSite() {
		return aboutSite;
	}

	public void setAboutSite(AboutSiteTO aboutSite) {
		this.aboutSite = aboutSite;
	}

}
