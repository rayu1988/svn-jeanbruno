package br.com.barganhas.business.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="ADMINISTRATOR")
public class AdministratorTO extends UserTO {
	
	public AdministratorTO() {
	}
	
	public AdministratorTO(Long id) {
		this.setId(id);
	}

}
