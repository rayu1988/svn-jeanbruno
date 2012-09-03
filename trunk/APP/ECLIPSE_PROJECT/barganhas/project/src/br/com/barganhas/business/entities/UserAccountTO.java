package br.com.barganhas.business.entities;

import java.util.Date;

import br.com.barganhas.business.entities.annotations.PropertyField;
import br.com.barganhas.enums.UserAccountStatus;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class UserAccountTO extends User {

	@PropertyField
	private Date					sinceDate;
	
	@PropertyField
	private UserAccountStatus 		status;
	
	@PropertyField
	private String					contacts;
	
	@PropertyField
	private Blob					profileImage;
	
	public UserAccountTO() {
		super(null);
	}
	
	public UserAccountTO(Key key) {
		super(key);
	}

	public Blob getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(Blob profileImage) {
		this.profileImage = profileImage;
	}

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
	
}
