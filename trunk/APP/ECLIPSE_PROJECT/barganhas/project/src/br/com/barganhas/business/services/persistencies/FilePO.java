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

@SuppressWarnings("serial")
@Repository
public class FilePO extends AppPersistency {

	public List<FileTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(FileTO.class).asList(FetchOptions.Builder.withDefaults());
		
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
		Entity entity = this.getDataStoreService().get(file.getKey());
		return AnnotationUtils.getTransferObjectFromEntity(FileTO.class, entity);
	}

	public void delete(FileTO file) {
		this.deleteEntity(file);
	}
}
