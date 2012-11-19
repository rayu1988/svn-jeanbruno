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
@Table(name="SALES")
public class SalesTO extends TransferObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_sales")
	private Long			id;
	
	@Column(name = "title", nullable = false, length = 50)
	private String			title;
	
	@Column(name = "description", nullable = false, length = 500)
	private String			description;
	
	@Column(name = "sales_code", nullable = false, length = 50)
	private String			salesCode;
	
	public SalesTO() {
	}

	public SalesTO(Long id) {
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
	
	public String getSalesCode() {
		return salesCode;
	}
	
	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}
}
