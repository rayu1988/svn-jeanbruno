package br.com.barganhas.business.entities;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public abstract class TransferObject implements Serializable {

	protected String					key;

	public TransferObject(Key key) {
		if (key != null) {
			this.key = KeyFactory.keyToString(key);
		}
	}
	
	public String getKey() {
		return key;
	}

	public abstract void setId(Long id);
}
