package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Sales;
import br.com.barganhas.business.services.persistencies.SalesPO;

import com.google.appengine.api.datastore.Transaction;

@Service("salesBO")
public class SalesBO implements Sales {

	public static final String						BEAN_ALIAS = "salesBO";

	@Autowired
	private SalesPO									persistencyLayer;
	
	@Override
	public List<SalesTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<SalesTO> listReturn = this.persistencyLayer.list();
			
			transaction.commit();
			return listReturn;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public SalesTO insert(SalesTO sales) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			sales = this.persistencyLayer.insert(sales);
			
			transaction.commit();
			return sales;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public SalesTO consult(SalesTO sales) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			sales = this.persistencyLayer.consult(sales);
			
			transaction.commit();
			return sales;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public SalesTO save(SalesTO sales) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			sales = this.persistencyLayer.save(sales);
			
			transaction.commit();
			return sales;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void delete(SalesTO sales) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			this.persistencyLayer.delete(sales);
			
			transaction.commit();
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public SalesTO consultBySalesCode(String salesCode) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			SalesTO sales = this.persistencyLayer.consultBySalesCode(salesCode);
			transaction.commit();
			
			return sales;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
}
