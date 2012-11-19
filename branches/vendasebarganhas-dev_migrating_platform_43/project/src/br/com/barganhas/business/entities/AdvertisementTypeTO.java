package br.com.barganhas.business.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.barganhas.business.entities.management.TransferObject;

@SuppressWarnings("serial")
@Entity
@Table(name="ADVERTISEMENT_TYPE")
public class AdvertisementTypeTO extends TransferObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_advertisement_type")
	private Long			id;
	
	@Column(name = "title", nullable = false, length = 50)
	private String			title;
	
	@Column(name = "description", nullable = false, length = 500)
	private String			description;
	
	@Column(name = "value", nullable = false)
	private Double			value;
	
	@Column(name = "score", nullable = false)
	private Byte			score;
	
	@Column(name = "total_pictures", nullable = false)
	private Byte			totalPictures;
	
	@Column(name = "days_to_expire", nullable = false)
	private Integer			daysToExpire;
	
	public AdvertisementTypeTO() {
	}

	public AdvertisementTypeTO(Long id) {
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
	
	public void setId(Long id) {
		this.id = id;
	}
	
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
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public Byte getScore() {
		return score;
	}
	
	public void setScore(Byte score) {
		this.score = score;
	}
	
	public Byte getTotalPictures() {
		return totalPictures;
	}
	
	public void setTotalPictures(Byte totalPictures) {
		this.totalPictures = totalPictures;
	}
	
	public Integer getDaysToExpire() {
		return daysToExpire;
	}
	
	public void setDaysToExpire(Integer daysToExpire) {
		this.daysToExpire = daysToExpire;
	}
	

}
