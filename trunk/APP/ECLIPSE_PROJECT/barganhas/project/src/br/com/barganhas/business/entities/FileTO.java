package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class FileTO extends TransferObject {

	@IdField
	@PropertyField
	private Long			id;
	
	@PropertyField(notNull=true)
	private Blob			data;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			contentType;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			fileName;
	
	public FileTO() {
		super(null);
	}
	
	public FileTO(Long id) {
		super(null);
		this.setId(id);
	}
	
	public FileTO(Key key) {
		super(key);
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public Long getId() {
		return id;
	}

	// GETTERS AND SETTERS //
	public Blob getData() {
		return data;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setData(Blob data) {
		this.data = data;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
