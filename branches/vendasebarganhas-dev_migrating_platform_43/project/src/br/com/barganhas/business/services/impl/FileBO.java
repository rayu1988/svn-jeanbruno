package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.persistencies.FilePO;

@Service("fileBO")
public class FileBO implements File {

	public static final String						BEAN_ALIAS = "fileBO";

	@Autowired
	private FilePO									persistencyLayer;
	
	@Override
	@Transactional(readOnly = true)
	public List<FileTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public FileTO insert(FileTO file) {
		return this.persistencyLayer.insert(file);
	}
	
	@Override
	@Transactional(readOnly = true)
	public FileTO consult(FileTO file) {
		return this.persistencyLayer.consult(file);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public FileTO save(FileTO file) {
		return this.persistencyLayer.save(file);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(FileTO file) {
		this.persistencyLayer.delete(file);
	}
	
}
