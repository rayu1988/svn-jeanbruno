package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.persistencies.CategoryPO;

@Service("categoryBO")
public class CategoryBO implements Category {

	public static final String						BEAN_ALIAS = "categoryBO";

	@Autowired
	private CategoryPO								persistencyLayer;
	
	@Override
	public List<CategoryTO> list(CategoryTO category) {
		return this.persistencyLayer.list(category);
	}
	
	@Override
	public void insert(CategoryTO category) {
		this.persistencyLayer.insert(category);
	}
	
	@Override
	public CategoryTO consult(CategoryTO category) {
		return this.persistencyLayer.consult(category);
	}
	
	@Override
	public void save(CategoryTO category) {
		this.persistencyLayer.save(category);
	}

	@Override
	public void delete(CategoryTO category) {
		this.persistencyLayer.delete(category);
	}
}
