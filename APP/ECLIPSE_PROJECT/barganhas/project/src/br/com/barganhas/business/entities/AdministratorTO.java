package br.com.barganhas.business.entities;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class AdministratorTO extends UserTO {
	
	public AdministratorTO() {
		super(null);
	}
	
	public AdministratorTO(Key key) {
		super(key);
	}
	
}
