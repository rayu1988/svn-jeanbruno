package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.FileTO;

public interface File {

	List<FileTO> list();

	FileTO insert(FileTO file);
	
	FileTO consult(FileTO file);

	FileTO save(FileTO file);

	void delete(FileTO file);

}
