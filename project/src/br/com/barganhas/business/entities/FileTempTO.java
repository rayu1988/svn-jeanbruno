package br.com.barganhas.business.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="FILE_TEMP")
public class FileTempTO extends FileTO {
	
	public FileTempTO() {
	}
	
	public FileTempTO(Long id) {
		this.setId(id);
	}

}
