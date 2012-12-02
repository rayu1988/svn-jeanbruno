package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.TransferObject;

public interface File {

	List<FileTO> list(Integer startFrom);

	FileTO insert(FileTO file);
	
	FileTO insert(FileTO file, TransferObject ancestorTO);

	FileTO consult(FileTO file) throws EntityNotFoundException;

	/**
	 * Currently the implementation returns a FileTO without Blob data content.
	 * @param file
	 * @return
	 * @throws EntityNotFoundException
	 */
	FileTO consultProjection(FileTO file) throws EntityNotFoundException;
	
	FileTO save(FileTO file);

	void delete(FileTO file);

}
