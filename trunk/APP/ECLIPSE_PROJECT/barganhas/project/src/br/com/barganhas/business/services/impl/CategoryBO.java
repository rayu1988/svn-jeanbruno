package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.persistencies.CategoryPO;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Transaction;

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
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public CategoryTO consult(CategoryTO category) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			category = this.persistencyLayer.consult(category);
			
			transaction.commit();
			return category;
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
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
}
