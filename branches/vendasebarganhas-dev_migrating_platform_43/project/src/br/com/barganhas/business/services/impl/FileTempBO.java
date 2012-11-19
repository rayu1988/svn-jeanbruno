package br.com.barganhas.business.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.services.FileTemp;
import br.com.barganhas.business.services.persistencies.FileTempPO;

@Service("fileTempBO")
public class FileTempBO implements FileTemp {

	public static final String						BEAN_ALIAS = "fileTempBO";

	@Autowired
	private FileTempPO								persistencyLayer;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void clearTempFiles() {
		this.persistencyLayer.clearTempFiles();
	}
	
}
