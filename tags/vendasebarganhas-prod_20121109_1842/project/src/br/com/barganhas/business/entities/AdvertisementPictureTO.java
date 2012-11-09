package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class AdvertisementPictureTO extends TransferObject {

	@IdField
	@PropertyField
	private Long				id;
	
	@PropertyField(notNull=true)
	private Key					keyThumbnail;
	private FileTO				thumbnail;
	
	@PropertyField(notNull=true)
	private Key					keyPicture;
	private FileTO				picture;
	
	public AdvertisementPictureTO() {
		super(null);
	}
	
	public AdvertisementPictureTO(Key key) {
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
	public Key getKeyThumbnail() {
		return keyThumbnail;
	}

	public void setKeyThumbnail(Key keyThumbnail) {
		this.keyThumbnail = keyThumbnail;
	}

	public Key getKeyPicture() {
		return keyPicture;
	}

	public void setKeyPicture(Key keyPicture) {
		this.keyPicture = keyPicture;
	}

	public FileTO getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(FileTO thumbnail) {
		this.thumbnail = thumbnail;
	}

	public FileTO getPicture() {
		return picture;
	}

	public void setPicture(FileTO picture) {
		this.picture = picture;
	}
}
