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
	private String					contactPhoneNumberOne;
	
	@PropertyField
	private String					contactPhoneNumberTwo;
	
	@PropertyField
	private String					contactEmail;
	
	@PropertyField
	private Key						keyProfileImage;
	
	@PropertyField
	private Key						keyState;
	private StateTO					state;
	
	@PropertyField
	private Key						keyCity;
	private CityTO					city;
	
	@PropertyField
	private Long					countAdvertisement;
	
	public UserAccountTO() {
		super(null);
	}
	
	public UserAccountTO(Key key) {
		super(key);
	}
	
	// anonymous attribute : firstName
	public String getFirstName() {
		return this.getFullname() != null ? this.getFullname().split(" ")[0] : "";
	}

	// GETTERS AND SETTERS //
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

	public Key getKeyProfileImage() {
		return keyProfileImage;
	}

	public void setKeyProfileImage(Key keyProfileImage) {
		this.keyProfileImage = keyProfileImage;
	}

	public Key getKeyState() {
		return keyState;
	}

	public void setKeyState(Key keyState) {
		this.keyState = keyState;
	}

	public Key getKeyCity() {
		return keyCity;
	}

	public void setKeyCity(Key keyCity) {
		this.keyCity = keyCity;
	}

	public StateTO getState() {
		return state;
	}

	public void setState(StateTO state) {
		this.state = state;
	}

	public CityTO getCity() {
		return city;
	}

	public void setCity(CityTO city) {
		this.city = city;
	}

	public Long getCountAdvertisement() {
		return countAdvertisement;
	}

	public void setCountAdvertisement(Long countAdvertisement) {
		this.countAdvertisement = countAdvertisement;
	}

	public String getContactPhoneNumberOne() {
		return contactPhoneNumberOne;
	}

	public void setContactPhoneNumberOne(String contactPhoneNumberOne) {
		this.contactPhoneNumberOne = contactPhoneNumberOne;
	}

	public String getContactPhoneNumberTwo() {
		return contactPhoneNumberTwo;
	}

	public void setContactPhoneNumberTwo(String contactPhoneNumberTwo) {
		this.contactPhoneNumberTwo = contactPhoneNumberTwo;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
}
