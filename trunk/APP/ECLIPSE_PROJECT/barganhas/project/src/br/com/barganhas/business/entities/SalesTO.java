package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class SalesTO extends TransferObject {

	@IdField
	@PropertyField
	private Long			id;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			title;
	
	@PropertyField(notNull=true)
	private String			description;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			salesCode;
	
	public SalesTO() {
		super(null);
	}
	
	public SalesTO(Key key) {
		super(key);
	}

	// GETTERS AND SETTERS //
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}
}
