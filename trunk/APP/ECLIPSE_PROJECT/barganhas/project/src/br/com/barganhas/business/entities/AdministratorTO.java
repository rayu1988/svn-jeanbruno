package br.com.barganhas.business.entities;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class AdministratorTO extends UserTO {
	
	public AdministratorTO() {
		super(null);
	}
	
	public AdministratorTO(Long id) {
		super(null);
		this.setId(id);
	}
	
	
	public AdministratorTO(Key key) {
		super(key);
	}
	
}
