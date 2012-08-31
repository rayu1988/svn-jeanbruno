package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public abstract class User extends TransferObject {

	@IdField
	@PropertyField
	private Long			id;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			nickname;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			fullname;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			email;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String 			password;
	
	public User(Key key) {
		super(key);
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	// GETTERS AND SETTERS //
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}
}
