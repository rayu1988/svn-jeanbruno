package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.services.Sales;
import br.com.barganhas.business.services.persistencies.SalesPO;

@Service("salesBO")
public class SalesBO implements Sales {

	public static final String						BEAN_ALIAS = "salesBO";

	@Autowired
	private SalesPO									persistencyLayer;
	
	@Override
	public List<SalesTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	public void insert(SalesTO sales) {
		this.persistencyLayer.insert(sales);
	}
	
	@Override
	public SalesTO consult(SalesTO sales) {
		return this.persistencyLayer.consult(sales);
	}
	
	@Override
	public void save(SalesTO sales) {
		this.persistencyLayer.save(sales);
	}

	@Override
	public void delete(SalesTO sales) {
		this.persistencyLayer.delete(sales);
	}
}
