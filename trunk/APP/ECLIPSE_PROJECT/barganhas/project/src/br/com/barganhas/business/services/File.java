package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.FileTO;

public interface File {

	List<FileTO> list();

	void insert(FileTO file);

	FileTO consult(FileTO file);
	
	void save(FileTO file);

	void delete(FileTO file);

}
