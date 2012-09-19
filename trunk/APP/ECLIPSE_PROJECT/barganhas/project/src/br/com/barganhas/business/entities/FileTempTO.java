package br.com.barganhas.business.entities;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class FileTempTO extends FileTO {

	public FileTempTO() {
		super(null);
	}
	
	public FileTempTO(Key key) {
		super(key);
	}

}
