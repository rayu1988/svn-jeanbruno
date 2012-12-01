package br.com.barganhas.business.entities;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;
import br.com.barganhas.enums.AdvertisementStatus;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
public class AdvertisementTO extends TransferObject {

	@IdField
	@PropertyField
	private Long								id;
	
	@PropertyField(notNull=true)
	private Long								score;
	
	@PropertyField(notNull=true)
	private Date								sinceDate;

	@PropertyField(notNull=true, allowEmpty=false)
	private String								title;
	
	@PropertyField
	private Text								description;
	
	@PropertyField
	private String								contactPhoneNumberOne;
	
	@PropertyField
	private String								contactPhoneNumberTwo;
	
	@PropertyField
	private String								contactEmail;
	
	@PropertyField
	private Double								value;

	@PropertyField(notNull=true)
	private AdvertisementStatus					status;
	
	@PropertyField(notNull=true)
	private Key									keyAdvertisementType;
	private AdvertisementTypeTO					advertisementType;
	
	@PropertyField(notNull=true)
	private Key									keyUserAccount;
	private UserAccountTO						userAccount;
	
	@PropertyField(notNull=true)
	private Key									keyCategory;
	private CategoryTO							category;
	
	@PropertyField
	private Key									keySales;
	private SalesTO								sales;
	
	@PropertyField
	private String								listExchangeBy;
	
	@PropertyField
	private Boolean								isNewProduct;
	
	@PropertyField
	private Long								countView;
	
	@PropertyField
	private List<Key>							pictures;
	private List<AdvertisementPictureTO> 		listAdvertisementPictures;
	
	@PropertyField
	private Key									keySheetPicture;
	private AdvertisementPictureTO				sheetPicture;

	@PropertyField
	private Key									keyUseTerm;
	
	public AdvertisementTO() {
		super(null);
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

	public static Comparator<AdvertisementTO> LowerPrice() {
		return new Comparator<AdvertisementTO>() {
			@Override
			public int compare(AdvertisementTO firstAdvertisement, AdvertisementTO anotherAdvertisement) {
				if (anotherAdvertisement == null || firstAdvertisement.value < anotherAdvertisement.value) {
					return -1;
				} else if (firstAdvertisement.value > anotherAdvertisement.value) {
					return 1;
				}
				return 0;
			}
		};
	}
	
	public static Comparator<AdvertisementTO> HigherPrice(){
		return new Comparator<AdvertisementTO>() {
			@Override
			public int compare(AdvertisementTO firstAdvertisement, AdvertisementTO anotherAdvertisement) {
				if (anotherAdvertisement == null || firstAdvertisement.value < anotherAdvertisement.value) {
					return 1;
				} else if (firstAdvertisement.value > anotherAdvertisement.value) {
					return -1;
				}
				return 0;
			}
		};
	}
	
	// GETTERS AND SETTERS //

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description != null ? this.description.getValue() : "";
	}

	public void setDescription(String description) {
		this.description = new Text(description);
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
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

	public Key getKeyCategory() {
		return keyCategory;
	}

	public void setKeyCategory(Key keyCategory) {
		this.keyCategory = keyCategory;
	}

	public CategoryTO getCategory() {
		return category;
	}

	public void setCategory(CategoryTO category) {
		this.category = category;
	}

	public Key getKeySales() {
		return keySales;
	}

	public void setKeySales(Key keySales) {
		this.keySales = keySales;
	}

	public SalesTO getSales() {
		return sales;
	}

	public void setSales(SalesTO sales) {
		this.sales = sales;
	}

	public List<AdvertisementPictureTO> getListAdvertisementPictures() {
		return listAdvertisementPictures;
	}

	public void setListAdvertisementPictures(
			List<AdvertisementPictureTO> listAdvertisementPictures) {
		this.listAdvertisementPictures = listAdvertisementPictures;
	}

	public Key getKeySheetPicture() {
		return keySheetPicture;
	}

	public void setKeySheetPicture(Key keySheetPicture) {
		this.keySheetPicture = keySheetPicture;
	}

	public AdvertisementPictureTO getSheetPicture() {
		return sheetPicture;
	}

	public void setSheetPicture(AdvertisementPictureTO sheetPicture) {
		this.sheetPicture = sheetPicture;
	}

	public Long getCountView() {
		return countView;
	}

	public void setCountView(Long countView) {
		this.countView = countView;
	}

	public Key getKeyUseTerm() {
		return keyUseTerm;
	}

	public void setKeyUseTerm(Key keyUseTerm) {
		this.keyUseTerm = keyUseTerm;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
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

	public Boolean getIsNewProduct() {
		return isNewProduct;
	}

	public void setIsNewProduct(Boolean isNewProduct) {
		this.isNewProduct = isNewProduct;
	}
}
