package br.com.barganhas.business.entities;

import java.io.Serializable;

import br.com.barganhas.commons.Util;

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
	
	public final String getKeyAsString() {
		return key;
	}
	
	public final Key getKey() {
		return Util.isStringOk(this.key) ? KeyFactory.stringToKey(this.key) : null;
	}

	public abstract void setId(Long id);
	public abstract Long getId();
}