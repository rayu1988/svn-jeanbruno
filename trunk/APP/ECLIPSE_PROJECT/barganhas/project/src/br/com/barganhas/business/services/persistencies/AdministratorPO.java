package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import br.com.barganhas.business.entities.AdministratorTO;

@SuppressWarnings("serial")
@Repository
public class AdministratorPO extends AppPersistency {

	public List<AdministratorTO> list() {
		return null;
	}
	
	public void insert(AdministratorTO administrator) {
		Entity entity = new Entity(this.getKey(administrator));
		entity.setProperty("fullname", administrator.getFullname());
		entity.setProperty("email", administrator.getEmail());
		entity.setProperty("nickname", administrator.getNickname());
		entity.setProperty("password", administrator.getPassword());
		
		this.getDataStoreService().put(entity);
	}

	public AdministratorTO consult(AdministratorTO administrator) {
		Entity entity = this.getEntity(administrator);
		return null;
	}
	
	public void save(AdministratorTO administrator) {
	}

	public void delete(AdministratorTO administrator) {
	}
}
