package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.SalesTO;

public interface Sales {

	List<SalesTO> list();
	
	void insert(SalesTO sales);

	SalesTO consult(SalesTO sales);
	
	void save(SalesTO sales);

	void delete(SalesTO sales);
}
