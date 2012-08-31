package br.com.barganhas.business.entities;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class UserAccountTO extends User {

	private Blob			profileImage;
	
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
	
}
