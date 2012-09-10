package br.com.barganhas.business.entities;

import java.util.Date;
import java.util.List;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;
import br.com.barganhas.enums.AdvertisementStatus;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class AdvertisementTO extends TransferObject {

	@IdField
	@PropertyField
	private Long						id;
	
	@PropertyField(notNull=true)
	private Date						sinceDate;

	@PropertyField(notNull=true, allowEmpty=false)
	private String						title;
	
	@PropertyField
	private String						description;
	
	@PropertyField
	private String						contacts;
	
	@PropertyField
	private String						value;

	@PropertyField(notNull=true)
	private AdvertisementStatus			status;
	
	@PropertyField(notNull=true)
	private Key							keyAdvertisementType;
	private AdvertisementTypeTO			advertisementType;
	
	@PropertyField(notNull=true)
	private Key							keyUserAccount;
	private UserAccountTO				userAccount;
	
	@PropertyField
	private String						listExchangeBy;
	
	@PropertyField
	private List<Key>					pictures;
	
	public AdvertisementTO() {
		super(null);
	}

	public AdvertisementTO(Long id) {
		super(null);
		this.setId(id);
	}
	
	public AdvertisementTO(Key key) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public AdvertisementStatus getStatus() {
		return status;
	}

	public void setStatus(AdvertisementStatus status) {
		this.status = status;
	}

	public String getListExchangeBy() {
		return listExchangeBy;
	}

	public void setListExchangeBy(String listExchangeBy) {
		this.listExchangeBy = listExchangeBy;
	}

	public List<Key> getPictures() {
		return pictures;
	}

	public void setPictures(List<Key> pictures) {
		this.pictures = pictures;
	}

	public Key getKeyAdvertisementType() {
		return keyAdvertisementType;
	}

	public void setKeyAdvertisementType(Key keyAdvertisementType) {
		this.keyAdvertisementType = keyAdvertisementType;
	}

	public AdvertisementTypeTO getAdvertisementType() {
		return advertisementType;
	}

	public void setAdvertisementType(AdvertisementTypeTO advertisementType) {
		this.advertisementType = advertisementType;
	}

	public Key getKeyUserAccount() {
		return keyUserAccount;
	}

	public void setKeyUserAccount(Key keyUserAccount) {
		this.keyUserAccount = keyUserAccount;
	}

	public UserAccountTO getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccountTO userAccount) {
		this.userAccount = userAccount;
	}

	public Date getSinceDate() {
		return sinceDate;
	}

	public void setSinceDate(Date sinceDate) {
		this.sinceDate = sinceDate;
	}
}
