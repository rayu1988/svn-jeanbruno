package br.com.barganhas.business.entities;

import java.util.Date;

import br.com.barganhas.business.entities.annotations.PropertyField;
import br.com.barganhas.enums.UserAccountStatus;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class UserAccountTO extends UserTO {

	@PropertyField
	private Date					sinceDate;
	
	@PropertyField
	private UserAccountStatus 		status;
	
	@PropertyField
	private String					contacts;
	
	@PropertyField
	private Long					profileImage;
	
	public UserAccountTO() {
		super(null);
	}
	
	public UserAccountTO(Long id) {
		super(null);
		this.setId(id);
	}
	
	public UserAccountTO(Key key) {
		super(key);
	}

	// GETTERS AND SETTERS //
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public UserAccountStatus getStatus() {
		return status;
	}

	public void setStatus(UserAccountStatus status) {
		this.status = status;
	}

	public Date getSinceDate() {
		return sinceDate;
	}

	public void setSinceDate(Date sinceDate) {
		this.sinceDate = sinceDate;
	}

	public Long getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(Long profileImage) {
		this.profileImage = profileImage;
	}
	
}
