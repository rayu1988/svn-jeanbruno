package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;

@SuppressWarnings("serial")
@Repository
public class CategoryPO extends AppPersistency {

	public List<CategoryTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(CategoryTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<CategoryTO> listReturn = new ArrayList<CategoryTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(CategoryTO.class, entity));
		}
		
		return listReturn;
	}
	
	public CategoryTO insert(CategoryTO category) {
		return this.persist(category);
	}
	
	public CategoryTO save(CategoryTO category) {
		return this.persist(category);
	}

	public CategoryTO consult(CategoryTO category) throws EntityNotFoundException {
		return this.consultByKey(category);
	}

	public void delete(CategoryTO category) {
		this.deleteEntity(category);
	}
}
