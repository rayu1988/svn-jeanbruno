package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.TransferObject;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
@Repository
public class FilePO extends AppPersistency {

	public List<FileTO> list(Integer startFrom) {
		List<Entity> entities = this.getSimplePreparedQuery(FileTO.class).asList(FetchOptions.Builder.withOffset(startFrom).limit(50));
		
		List<FileTO> listReturn = new ArrayList<FileTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(FileTO.class, entity));
		}
		
		return listReturn;
	}
	
	public FileTO insert(FileTO file) {
		return this.persist(file);
	}
	
	public FileTO insert(FileTO file, TransferObject ancestorTO) {
		return this.persist(file, ancestorTO);
	}
	
	public FileTO save(FileTO file) {
		return this.persist(file);
	}

	public FileTO consult(FileTO file) throws EntityNotFoundException {
		return this.consultByKey(file);
	}
	
	public FileTO consultProjection(FileTO file) {
		Query query = this.getQuery(FileTO.class);
		query.addProjection(new PropertyProjection("fileName", String.class));
		query.setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, file.getKey().getId()));
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		Entity entity = preparedQuery.asSingleEntity();
		return AnnotationUtils.getTransferObjectFromEntity(FileTO.class, entity);
	}

	public void delete(FileTO file) {
		this.deleteEntity(file);
	}
}
