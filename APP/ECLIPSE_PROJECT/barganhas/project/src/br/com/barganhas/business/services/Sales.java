package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.SalesTO;

public interface Sales {

	List<SalesTO> list();
	
	SalesTO insert(SalesTO sales);

	SalesTO consult(SalesTO sales);
	
	SalesTO consultBySalesCode(String salesCode);
	
	SalesTO save(SalesTO sales);

	void delete(SalesTO sales);
}
