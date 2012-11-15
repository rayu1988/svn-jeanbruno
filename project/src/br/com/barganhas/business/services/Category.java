package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.CategoryTO;

public interface Category {

	List<CategoryTO> list();
	
	CategoryTO insert(CategoryTO category);

	CategoryTO consult(CategoryTO category) throws EntityNotFoundException;
	
	CategoryTO save(CategoryTO category);

	void delete(CategoryTO category);
}
