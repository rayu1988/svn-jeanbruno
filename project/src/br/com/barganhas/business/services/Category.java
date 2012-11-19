package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.commons.SearchingRequest;

public interface Category {

	List<CategoryTO> list();
	
	List<CategoryTO> listFiter(SearchingRequest searchingRequest);
	
	CategoryTO insert(CategoryTO category);

	CategoryTO consult(CategoryTO category);
	
	CategoryTO save(CategoryTO category);

	void delete(CategoryTO category);
}
