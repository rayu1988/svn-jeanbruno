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
@Table(name="USE_TERM")
public class UseTermTO extends TransferObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_use_term")
	private Long			id;
	
	@Column(name = "title", nullable = false)
	private String			title;
	
	@Column(name = "text", nullable = false, length = 2500)
	private String			text;
	
	@Column(name = "is_default", nullable = false)
	private Boolean			isDefault;
	
	public UseTermTO() {
	}

	public UseTermTO(Long id) {
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
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Boolean getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
}
