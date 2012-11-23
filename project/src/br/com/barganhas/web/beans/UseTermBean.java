package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.services.UseTerm;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class UseTermBean extends AppManagedBean {
	
	private UseTermTO							useTerm;
	private DataModel<Object>					dataModel;
	
	public String useTerm() {
		try {
			UseTerm service = this.getServiceBusinessFactory().getUseTerm();
			this.useTerm = service.getDefaultUseTerm();
			
			this.useTerm = this.service.load(new UseTermTO(this.useTerm.getId()));
			
			return "useTerm";
		} catch (Exception e) {
			return this.manageException(e);
		}	
	}
	
	public String list() {
		try {
			UseTerm service = this.getServiceBusinessFactory().getUseTerm();
			this.useTerm = new UseTermTO();
			List<UseTermTO> list = service.list();
			this.dataModel = new CustomDataModel(list);
			return "useTermList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String prepareNew() {
		try {
			this.useTerm = new UseTermTO();
			
			return "useTermPrepareNew";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String insert() {
		try {
			UseTerm service = this.getServiceBusinessFactory().getUseTerm();
			service.insert(this.useTerm);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String edit() {
		try {
			UseTerm service = this.getServiceBusinessFactory().getUseTerm();
			this.useTerm = service.consult(this.useTerm);
			
			return "useTermEdit";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String save() {
		try {
			UseTerm service = this.getServiceBusinessFactory().getUseTerm();
			service.save(this.useTerm);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String turnUseTermDefault() {
		try {
			UseTerm service = this.getServiceBusinessFactory().getUseTerm();
			this.useTerm = service.turnUseTermDefault(this.useTerm);
			
			this.setRequestMessage(new RequestMessage("useTermHadBeenUnlocked", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consult() {
		try {
			UseTerm service = this.getServiceBusinessFactory().getUseTerm();
			this.useTerm = service.consult(this.useTerm);
			
			return "useTermConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	public String delete() {
		try{	
			UseTerm service = this.getServiceBusinessFactory().getUseTerm();
			service.delete(this.useTerm);
			
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

	public UseTermTO getUseTerm() {
		return useTerm;
	}

	public void setUseTerm(UseTermTO useTerm) {
		this.useTerm = useTerm;
	}

}
