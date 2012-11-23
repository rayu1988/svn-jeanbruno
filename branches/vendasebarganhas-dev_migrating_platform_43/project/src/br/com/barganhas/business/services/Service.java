package br.com.barganhas.business.services;

import br.com.barganhas.business.entities.management.TransferObject;

public interface Service {

	public void evict(TransferObject to);
	
	public void initialize(TransferObject to);
	
	public void refresh(TransferObject to);
	
	public void clear();
	
	public void flush();
	
	public <T extends TransferObject> T load(T to);
	
	public <T extends TransferObject> T get(T to);
	
	public void delete(TransferObject to);
	
}
