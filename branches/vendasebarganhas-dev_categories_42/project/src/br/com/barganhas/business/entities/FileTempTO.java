package br.com.barganhas.business.entities;

import java.util.Date;

import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class FileTempTO extends FileTO {
	
	@PropertyField
	private Date			persistedDate;
	
	public FileTempTO() {
		super(null);
	}
	
	public FileTempTO(Key key) {
		super(key);
	}

	// GETTERS AND SETTERS //
	public Date getPersistedDate() {
		return persistedDate;
	}

	public void setPersistedDate(Date persistedDate) {
		this.persistedDate = persistedDate;
	}

}
