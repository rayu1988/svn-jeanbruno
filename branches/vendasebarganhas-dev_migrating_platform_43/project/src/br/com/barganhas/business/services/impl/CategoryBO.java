package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.persistencies.CategoryPO;
import br.com.barganhas.commons.SearchingRequest;

@Service("categoryBO")
public class CategoryBO implements Category {

	public static final String						BEAN_ALIAS = "categoryBO";

	@Autowired
	private CategoryPO								persistencyLayer;
	
	@Override
	@Transactional(readOnly = true)
	public List<CategoryTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CategoryTO> listFiter(SearchingRequest searchingRequest) {
		return this.persistencyLayer.listFiter(searchingRequest);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CategoryTO insert(CategoryTO category) {
		return this.persistencyLayer.insert(category);
	}
	
	@Override
	@Transactional(readOnly = true)
	public CategoryTO consult(CategoryTO category) {
		return this.persistencyLayer.consult(category);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public CategoryTO save(CategoryTO category) {
		return this.persistencyLayer.save(category);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(CategoryTO category) {
		this.persistencyLayer.delete(category);
	}
}
