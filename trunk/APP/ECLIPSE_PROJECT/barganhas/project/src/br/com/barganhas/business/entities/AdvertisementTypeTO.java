package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class AdvertisementTypeTO extends TransferObject {

	@IdField
	@PropertyField
	private Long			id;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			title;
	
	@PropertyField
	private String			description;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			value;
	
	@PropertyField(notNull=true)
	private Long			advertisementScore;
	
	@PropertyField(notNull=true)
	private Long			totalPictures;
	
	public AdvertisementTypeTO() {
		super(null);
	}

	public AdvertisementTypeTO(Key key) {
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
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getAdvertisementScore() {
		return advertisementScore;
	}

	public Long getTotalPictures() {
		return totalPictures;
	}

	public void setAdvertisementScore(Long advertisementScore) {
		this.advertisementScore = advertisementScore;
	}

	public void setTotalPictures(Long totalPictures) {
		this.totalPictures = totalPictures;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
