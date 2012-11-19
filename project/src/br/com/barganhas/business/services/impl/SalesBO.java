package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.services.Sales;
import br.com.barganhas.business.services.persistencies.SalesPO;

@Service("salesBO")
public class SalesBO implements Sales {

	public static final String						BEAN_ALIAS = "salesBO";

	@Autowired
	private SalesPO									persistencyLayer;
	
	@Override
	@Transactional(readOnly = true)
	public List<SalesTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public SalesTO insert(SalesTO sales) {
		return this.persistencyLayer.insert(sales);
	}
	
	@Override
	@Transactional(readOnly = true)
	public SalesTO consult(SalesTO sales) {
		return this.persistencyLayer.consult(sales);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public SalesTO save(SalesTO sales) {
		return this.persistencyLayer.save(sales);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(SalesTO sales) {
		this.persistencyLayer.delete(sales);
	}

	@Override
	@Transactional(readOnly = true)
	public SalesTO consultBySalesCode(String salesCode) {
		return this.persistencyLayer.consultBySalesCode(salesCode);
	}
}
