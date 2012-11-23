package br.com.barganhas.business.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.barganhas.enums.UserAccountStatus;

@SuppressWarnings("serial")
@Entity
@Table(name="USER_ACCOUNT")
public class UserAccountTO extends UserTO {

	@Column(name = "since_date", nullable = false)
	private Date					sinceDate;
	
	@Column(name = "status", nullable = false)
	private UserAccountStatus 		status;
	
	@Column(name = "contact_phone_number_one", nullable = true, length = 50)
	private String					contactPhoneNumberOne;
	
	@Column(name = "contact_phone_number_two", nullable = true, length = 50)
	private String					contactPhoneNumberTwo;
	
	@Column(name = "contact_email", nullable = true, length = 50)
	private String					contactEmail;
	
	@OneToOne
	@JoinColumn(name = "id_profile_image")
	private FileTO					profileImage;
	
	@ManyToOne
	@JoinColumn(name = "id_city", nullable = false)
	private CityTO					city;
	
	@OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<AdvertisementTO>	listAdvertisement;
	
	public UserAccountTO() {
	}
	
	public UserAccountTO(Long id) {
		this.setId(id);
	}
	
	// GETTERS AND SETTERS //
	public Date getSinceDate() {
		return sinceDate;
	}
	
	public UserAccountStatus getStatus() {
		return status;
	}
	
	public String getContactPhoneNumberOne() {
		return contactPhoneNumberOne;
	}
	
	public String getContactPhoneNumberTwo() {
		return contactPhoneNumberTwo;
	}
	
	public String getContactEmail() {
		return contactEmail;
	}
	
	public FileTO getProfileImage() {
		return profileImage;
	}
	
	public CityTO getCity() {
		return city;
	}
	
	public void setSinceDate(Date sinceDate) {
		this.sinceDate = sinceDate;
	}
	
	public void setStatus(UserAccountStatus status) {
		this.status = status;
	}
	
	public void setContactPhoneNumberOne(String contactPhoneNumberOne) {
		this.contactPhoneNumberOne = contactPhoneNumberOne;
	}
	
	public void setContactPhoneNumberTwo(String contactPhoneNumberTwo) {
		this.contactPhoneNumberTwo = contactPhoneNumberTwo;
	}
	
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	public void setProfileImage(FileTO profileImage) {
		this.profileImage = profileImage;
	}
	
	public void setCity(CityTO city) {
		this.city = city;
	}

	public Set<AdvertisementTO> getListAdvertisement() {
		return listAdvertisement;
	}

	public void setListAdvertisement(Set<AdvertisementTO> listAdvertisement) {
		this.listAdvertisement = listAdvertisement;
	}
	
}
