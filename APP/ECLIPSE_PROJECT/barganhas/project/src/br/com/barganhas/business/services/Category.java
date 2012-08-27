package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.CategoryTO;

public interface Category {

	List<CategoryTO> list(CategoryTO category);
	
	void insert(CategoryTO category);

	CategoryTO consult(CategoryTO category);
	
	void save(CategoryTO category);

	void delete(CategoryTO category);
}
