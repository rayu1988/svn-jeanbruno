package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Transaction;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.TransferObject;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.persistencies.FilePO;

@Service("fileBO")
public class FileBO implements File {

	public static final String						BEAN_ALIAS = "fileBO";

	@Autowired
	private FilePO									persistencyLayer;
	
	@Override
	public List<FileTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<FileTO> listReturn = this.persistencyLayer.list();
			
			transaction.commit();
			return listReturn;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public FileTO insert(FileTO file) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			file = this.persistencyLayer.insert(file);
			
			transaction.commit();
			return file;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public FileTO insert(FileTO file, TransferObject ancestorTO) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			file = this.persistencyLayer.insert(file, ancestorTO);
			
			transaction.commit();
			return file;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public FileTO consult(FileTO file) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			file = this.persistencyLayer.consult(file);
			
			transaction.commit();
			return file;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public FileTO save(FileTO file) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			file = this.persistencyLayer.save(file);
			
			transaction.commit();
			return file;
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public void delete(FileTO file) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			this.persistencyLayer.delete(file);
			
			transaction.commit();
		} catch (Exception e) {
			throw new AppException(e);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
}
