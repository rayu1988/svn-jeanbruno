package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.CategoryTO;

public interface Category {

	List<CategoryTO> list();
	
	CategoryTO insert(CategoryTO category);

	CategoryTO consult(CategoryTO category);
	
	CategoryTO save(CategoryTO category);

	void delete(CategoryTO category);
}
