package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class AdministratorTO extends TransferObject {

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
	
	public AdministratorTO() {
		super(null);
	}
	
	public AdministratorTO(Key key) {
		super(key);
	}

	// GETTERS AND SETTERS //
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
