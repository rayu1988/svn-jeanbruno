package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.TransferObject;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.persistencies.FilePO;

@Service("fileBO")
public class FileBO implements File {

	public static final String						BEAN_ALIAS = "fileBO";

	@Autowired
	private FilePO									persistencyLayer;
	
	@Override
	public List<FileTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	public FileTO insert(FileTO file, TransferObject ancestorTO) {
		return this.persistencyLayer.insert(file, ancestorTO);
	}
	
	@Override
	public FileTO consult(FileTO file) {
		return this.persistencyLayer.consult(file);
	}
	
	@Override
	public FileTO save(FileTO file) {
		return this.persistencyLayer.save(file);
	}
	
	@Override
	public void delete(FileTO file) {
		this.persistencyLayer.delete(file);
	}
	
}
