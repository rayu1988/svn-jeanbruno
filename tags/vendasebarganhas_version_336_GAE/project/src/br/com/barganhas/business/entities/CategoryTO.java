package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class CategoryTO extends TransferObject {

	@IdField
	@PropertyField
	private Long			id;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			name;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			description;
	
	public CategoryTO() {
		super(null);
	}

	public CategoryTO(Key key) {
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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
