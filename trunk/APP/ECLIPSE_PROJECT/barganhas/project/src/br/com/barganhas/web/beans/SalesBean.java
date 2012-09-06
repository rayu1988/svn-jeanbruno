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
public class SalesBean extends AppManagedBean {
	
	private SalesTO								sales;
	private DataModel<Object>					dataModel;
	
	public String list() {
		Sales service = this.getServiceBusinessFactory().getSales();
		this.sales = new SalesTO();
		List<SalesTO> list = service.list();
		this.dataModel = new CustomDataModel(list);
		return "salesList";
	}
	
	public String prepareNew() {
		this.sales = new SalesTO();
		this.sales.setSalesCode("$" + (System.currentTimeMillis() + "barganhas").hashCode()  );
		
		return "salesPrepareNew";
	}
	
	public String insert() {
		Sales service = this.getServiceBusinessFactory().getSales();
		service.insert(this.sales);
		
		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.list();
	}
	
	public String edit() {
		Sales service = this.getServiceBusinessFactory().getSales();
		this.sales = service.consult(this.sales);
		
		return "salesEdit";
	}
	
	public String save() {
		Sales service = this.getServiceBusinessFactory().getSales();
		service.save(this.sales);
		
		this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
		return this.consult();
	}
	
	public String consult() {
		Sales service = this.getServiceBusinessFactory().getSales();
		this.sales = service.consult(this.sales);
		
		return "salesConsult";
	}

	public String delete() {
		Sales service = this.getServiceBusinessFactory().getSales();
		service.delete(this.sales);
		
		this.setRequestMessage(new RequestMessage("registerDeletedSuccessfully", SeverityMessage.SUCCESS));
		return this.list();
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
