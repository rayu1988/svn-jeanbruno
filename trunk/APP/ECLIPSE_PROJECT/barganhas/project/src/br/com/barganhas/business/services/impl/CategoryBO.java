package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Transaction;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.persistencies.CategoryPO;

@Service("categoryBO")
public class CategoryBO implements Category {

	public static final String						BEAN_ALIAS = "categoryBO";

	@Autowired
	private CategoryPO								persistencyLayer;
	
	@Override
	public List<CategoryTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<CategoryTO> listReturn = this.persistencyLayer.list();
			
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
	public CategoryTO insert(CategoryTO category) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			category = this.persistencyLayer.insert(category);
			
			transaction.commit();
			return category;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public CategoryTO consult(CategoryTO category) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			category = this.persistencyLayer.consult(category);
			
			transaction.commit();
			return category;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public CategoryTO save(CategoryTO category) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			category = this.persistencyLayer.save(category);
			
			transaction.commit();
			return category;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void delete(CategoryTO category) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			this.persistencyLayer.delete(category);
			transaction.commit();
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
}
