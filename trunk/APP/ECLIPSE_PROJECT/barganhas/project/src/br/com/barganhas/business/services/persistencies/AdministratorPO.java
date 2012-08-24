package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdministratorTO;

@Repository
public class AdministratorPO {

	List<AdministratorTO> listTemp = new ArrayList<AdministratorTO>();
	
	public List<AdministratorTO> list() {
		
		return listTemp;
	}
	
	public void insert(AdministratorTO administrator) {
		this.listTemp.add(administrator);
	}

	public AdministratorTO consult(AdministratorTO administrator) {
		return this.listTemp.get(this.listTemp.indexOf(administrator));
	}
	
	public void save(AdministratorTO administrator) {
		this.listTemp.set(this.listTemp.indexOf(administrator), administrator);
	}

	public void delete(AdministratorTO administrator) {
		this.listTemp.remove(this.listTemp.indexOf(administrator));
	}
}
