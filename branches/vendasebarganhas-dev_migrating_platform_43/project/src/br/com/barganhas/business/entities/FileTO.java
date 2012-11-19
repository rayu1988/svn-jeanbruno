package br.com.barganhas.business.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.barganhas.business.entities.management.TransferObject;

@SuppressWarnings("serial")
@Entity
@Table(name="FILE")
public class FileTO extends TransferObject {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_file")
	private Long			id;
	
	@Column(name="data", nullable=true)
	private byte[]			data;
	
	@Column(name = "content_type", nullable = false)
	private String			contentType;
	
	@Column(name = "file_name", nullable = false)
	private String			fileName;
	
	@Column(name = "since_date", nullable = false)
	private Date			sinceDate;
	
	public FileTO() {
	}
	
	public FileTO(Long id) {
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
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getSinceDate() {
		return sinceDate;
	}

	public void setSinceDate(Date sinceDate) {
		this.sinceDate = sinceDate;
	}
}
