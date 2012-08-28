package br.com.barganhas.web.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.web.beans.datamodel.CustomDataModel;

@ManagedBean
@RequestScoped
public class CategoryBean extends AppManagedBean {
	
	private CategoryTO							category;
	private DataModel<Object>					dataModel;
	
	public String list() {
		Category service = this.getServiceBusinessFactory().getCategory();
		List<CategoryTO> list = service.list(new CategoryTO());
		
		this.dataModel = new CustomDataModel(list);
		this.category = new CategoryTO();
		
		return "categoryList";
	}
	
	public String prepareNew() {
		this.category = new CategoryTO();
		
		return "categoryPrepareNew";
	}
	
	public String insert() {
		Category service = this.getServiceBusinessFactory().getCategory();
		service.insert(this.category);
		
		return this.list();
	}
	
	public String edit() {
		Category service = this.getServiceBusinessFactory().getCategory();
		this.category = service.consult(this.category);
		
		return "categoryEdit";
	}
	
	public String save() {
		Category service = this.getServiceBusinessFactory().getCategory();
		service.save(this.category);
		
		return this.consult();
	}
	
	public String consult() {
		Category service = this.getServiceBusinessFactory().getCategory();
		this.category = service.consult(this.category);
		
		return "categoryConsult";
	}

	public String delete() {
		Category service = this.getServiceBusinessFactory().getCategory();
		service.delete(this.category);
		
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
