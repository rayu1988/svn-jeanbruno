package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
public class SiteContactTO extends TransferObject {

	@IdField
	@PropertyField
	private Long			id;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			name;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			email;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private Text			message;
	
	public SiteContactTO() {
		super(null);
	}

	public SiteContactTO(Key key) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Text getMessage() {
		return message;
	}

	public void setMessage(Text message) {
		this.message = message;
	}
	
	public String getStringMessage() {
		return message != null ? message.getValue() : "";
	}
	
	public void setStringMessage(String message) {
		this.message = message != null ? new Text(message) : null;
	}
}