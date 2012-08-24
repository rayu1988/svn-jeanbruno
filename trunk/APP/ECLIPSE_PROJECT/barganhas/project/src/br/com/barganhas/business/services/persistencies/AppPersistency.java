package br.com.barganhas.business.services.persistencies;

import java.io.Serializable;
import java.util.List;

import br.com.barganhas.business.entities.TransferObject;
import br.com.barganhas.business.entities.annotations.IdFieldComparable;
import br.com.barganhas.commons.AppException;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public abstract class AppPersistency implements Serializable {

	protected DatastoreService getDataStoreService() {
		return DatastoreServiceFactory.getDatastoreService();
	}
	
	protected <T extends TransferObject> Key getKey(T transferObject) {
		try {
			if (transferObject == null) {
				throw new IllegalArgumentException("the param cann't be null");
			}
			
			StringBuilder sb = new StringBuilder();
			List<IdFieldComparable> listIdField = Util.getIdFields(transferObject);
			for (int i = 0 ; i < listIdField.size(); i++) {
				IdFieldComparable idFieldComparable = listIdField.get(i);
				sb.append(idFieldComparable.getField().get(transferObject) + (i+1 > listIdField.size() ? ":" : ""));
			}
			
			return KeyFactory.createKey(transferObject.getClass().getName(), sb.toString());
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	protected Entity getEntity(TransferObject transferObject) {
		try {
			Key key = this.getKey(transferObject);
			return this.getDataStoreService().get(key);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
