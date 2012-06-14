package br.com.datawatcher.entity;

import java.util.ArrayList;
import java.util.List;

import br.com.datawatcher.exception.DataWatcherException;

/**
 * 
 * @author carrefour
 *
 */
public abstract class DataMapping {

	private String identifier;
	private CheckChange checkChange;
	private List<Listener> listeners = new ArrayList<Listener>();
	
	public abstract void startup() throws DataWatcherException;
	public abstract void checkChange() throws DataWatcherException;
	
	public DataMapping addListeners(Listener listener) {
		if (listener != null) {
			this.listeners.add(listener);
			return this;
		} else throw new IllegalArgumentException("parameter listener can not be null");
	}
	
	// GETTERS AND SETTERS //
	public CheckChange getCheckChange() {
		return checkChange;
	}
	public void setCheckChange(CheckChange checkChange) {
		this.checkChange = checkChange;
	}
	public List<Listener> getListeners() {
		return listeners;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
