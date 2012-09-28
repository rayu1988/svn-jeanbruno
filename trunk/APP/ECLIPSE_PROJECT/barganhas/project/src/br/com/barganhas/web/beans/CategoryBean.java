package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class CategoryBean extends AppManagedBean {
	
	private CategoryTO							category;
	private DataModel<Object>					dataModel;
	
	public String list() {
		try {
			Category service = this.getServiceBusinessFactory().getCategory();
			this.category = new CategoryTO();
			List<CategoryTO> list = service.list();
			this.dataModel = new CustomDataModel(list);
			return "categoryList";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String prepareNew() {
		try {
			this.category = new CategoryTO();
			
			return "categoryPrepareNew";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String insert() {
		try {
			Category service = this.getServiceBusinessFactory().getCategory();
			service.insert(this.category);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.list();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String edit() {
		try {
			Category service = this.getServiceBusinessFactory().getCategory();
			this.category = service.consult(this.category);
			
			return "categoryEdit";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String save() {
		try {
			Category service = this.getServiceBusinessFactory().getCategory();
			service.save(this.category);
			
			this.setRequestMessage(new RequestMessage("registerSaveSuccessfully", SeverityMessage.SUCCESS));
			return this.consult();
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String consult() {
		try {
			Category service = this.getServiceBusinessFactory().getCategory();
			this.category = service.consult(this.category);
			
			return "categoryConsult";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}

	public String delete() {
		Category service = this.getServiceBusinessFactory().getCategory();
		service.delete(this.category);
		
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

	public CategoryTO getCategory() {
		return category;
	}

	public void setCategory(CategoryTO category) {
		this.category = category;
	}
}
