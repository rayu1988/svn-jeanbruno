package br.com.barganhas.business.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.barganhas.business.entities.management.TransferObject;
import br.com.barganhas.enums.AdvertisementStatus;

@SuppressWarnings("serial")
@Entity
@Table(name="ADVERTISEMENT")
public class AdvertisementTO extends TransferObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_advertisement")
	private Long								id;
	
	@Column(name = "since_date", nullable = false)
	private Date								sinceDate;

	@Column(name = "title", nullable = false, length = 100)
	private String								title;
	
	@Column(name = "description", nullable = false, length = 2500)
	private String								description;
	
	@Column(name = "value", nullable = false)
	private Double								value;

	@Column(name = "contact_phone_number_one", nullable = true, length = 50)
	private String								contactPhoneNumberOne;
	
	@Column(name = "contact_phone_number_two", nullable = true, length = 50)
	private String								contactPhoneNumberTwo;
	
	@Column(name = "contact_email", nullable = true, length = 50)
	private String								contactEmail;
	
	@Column(name = "status", nullable = false)
	private AdvertisementStatus					status;
	
	@Column(name = "exchange_by", nullable = false, length = 250)
	private String								exchangeBy;
	
	@Column(name = "is_new_product", nullable = false)
	private Boolean								isNewProduct;
	
	@Column(name = "count_view", nullable = false)
	private Long								countView;

	@ManyToOne
	@JoinColumn(name = "id_advertisement_type", nullable = false)
	private AdvertisementTypeTO					advertisementType;
	
	@ManyToOne
	@JoinColumn(name = "id_user_account", nullable = false)
	private UserAccountTO						userAccount;
	
	@ManyToOne
	@JoinColumn(name = "id_category", nullable = false)
	private CategoryTO							category;
	
	@ManyToOne
	@JoinColumn(name = "id_sales", nullable = true)
	private SalesTO								sales;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name="id_advertisement")
	private Set<AdvertisementPictureTO> 		listAdvertisementPictures;

	@OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_sheet_picture", nullable = false)
	private AdvertisementPictureTO				sheetPicture;

	@ManyToOne
	@JoinColumn(name = "id_use_term", nullable = false)
	private UseTermTO							useTerm;
	
	public AdvertisementTO() {
	}

	public AdvertisementTO(Long id) {
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
	
	public Date getSinceDate() {
		return sinceDate;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Double getValue() {
		return value;
	}
	
	public AdvertisementStatus getStatus() {
		return status;
	}
	
	public String getExchangeBy() {
		return exchangeBy;
	}
	
	public Boolean getIsNewProduct() {
		return isNewProduct;
	}
	
	public Long getCountView() {
		return countView;
	}
	
	public AdvertisementTypeTO getAdvertisementType() {
		return advertisementType;
	}
	
	public UserAccountTO getUserAccount() {
		return userAccount;
	}
	
	public CategoryTO getCategory() {
		return category;
	}
	
	public SalesTO getSales() {
		return sales;
	}
	
	public Set<AdvertisementPictureTO> getListAdvertisementPictures() {
		return listAdvertisementPictures;
	}
	
	public AdvertisementPictureTO getSheetPicture() {
		return sheetPicture;
	}
	
	public UseTermTO getUseTerm() {
		return useTerm;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSinceDate(Date sinceDate) {
		this.sinceDate = sinceDate;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public void setStatus(AdvertisementStatus status) {
		this.status = status;
	}
	
	public void setExchangeBy(String exchangeBy) {
		this.exchangeBy = exchangeBy;
	}
	
	public void setIsNewProduct(Boolean isNewProduct) {
		this.isNewProduct = isNewProduct;
	}
	
	public void setCountView(Long countView) {
		this.countView = countView;
	}
	
	public void setAdvertisementType(AdvertisementTypeTO advertisementType) {
		this.advertisementType = advertisementType;
	}
	
	public void setUserAccount(UserAccountTO userAccount) {
		this.userAccount = userAccount;
	}
	
	public void setCategory(CategoryTO category) {
		this.category = category;
	}
	
	public void setSales(SalesTO sales) {
		this.sales = sales;
	}
	
	public void setListAdvertisementPictures(
			Set<AdvertisementPictureTO> listAdvertisementPictures) {
		this.listAdvertisementPictures = listAdvertisementPictures;
	}
	
	public void setSheetPicture(AdvertisementPictureTO sheetPicture) {
		this.sheetPicture = sheetPicture;
	}
	
	public void setUseTerm(UseTermTO useTerm) {
		this.useTerm = useTerm;
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

	public void setContactPhoneNumberOne(String contactPhoneNumberOne) {
		this.contactPhoneNumberOne = contactPhoneNumberOne;
	}

	public void setContactPhoneNumberTwo(String contactPhoneNumberTwo) {
		this.contactPhoneNumberTwo = contactPhoneNumberTwo;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

}
