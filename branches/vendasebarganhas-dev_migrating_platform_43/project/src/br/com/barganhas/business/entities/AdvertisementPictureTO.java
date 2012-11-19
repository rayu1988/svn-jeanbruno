package br.com.barganhas.business.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.barganhas.business.entities.management.TransferObject;

@SuppressWarnings("serial")
@Entity
@Table(name="ADVERTISEMENT_PICTURE")
public class AdvertisementPictureTO extends TransferObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_advertisement_picture")
	private Long				id;
	
	@OneToOne
	@JoinColumn(name = "id_thumbnail", nullable = false)
	private FileTO				thumbnail;
	
	@OneToOne
	@JoinColumn(name = "id_picture", nullable = false)
	private FileTO				picture;
	
	public AdvertisementPictureTO() {
	}
	
	public AdvertisementPictureTO(Long id) {
		this.id = id;
	}

	@Override
	public void setKey(Serializable id) {
		this.id = (Long)id;
	}

	@Override
	public Serializable getKey() {
		return getId();
	}

	// GETTERS AND SETTERS //
	public Long getId() {
		return id;
	}
	
	public FileTO getThumbnail() {
		return thumbnail;
	}
	
	public FileTO getPicture() {
		return picture;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setThumbnail(FileTO thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public void setPicture(FileTO picture) {
		this.picture = picture;
	}
	
}
