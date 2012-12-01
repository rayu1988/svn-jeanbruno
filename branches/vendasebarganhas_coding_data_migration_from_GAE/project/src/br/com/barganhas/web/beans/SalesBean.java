package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.services.Sales;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class SalesBean extends AppManagedBean {
	
	private SalesTO								sales;
	private DataModel<Object>					dataModel;
	
	public String list() {
		try {
			Sales service = this.getServiceBusinessFactory().getSales();
			this.sales = new SalesTO();
			List<SalesTO> list = service.list();
			this.dataModel = new CustomDataModel(list);
			return "salesList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String prepareNew() {
		try {
			this.sales = new SalesTO();
			this.sales.setSalesCode("$" + Math.abs((System.currentTimeMillis() + "barganhas").hashCode()));
			
			return "salesPrepareNew";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String insert() {
		try {
			Sales service = this.getServiceBusinessFactory().getSales();
			service.insert(this.sales);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String edit() {
		try {
			Sales service = this.getServiceBusinessFactory().getSales();
			this.sales = service.consult(this.sales);
			
			return "salesEdit";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String save() {
		try {
			Sales service = this.getServiceBusinessFactory().getSales();
			service.save(this.sales);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consult() {
		try {
			Sales service = this.getServiceBusinessFactory().getSales();
			this.sales = service.consult(this.sales);
			
			return "salesConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	public String delete() {
		try {
			Sales service = this.getServiceBusinessFactory().getSales();
			service.delete(this.sales);
			
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
	public SalesTO getSales() {
		return sales;
	}
	public void setSales(SalesTO sales) {
		this.sales = sales;
	}
}
