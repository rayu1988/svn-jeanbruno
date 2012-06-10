package br.com.datawatcher.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author carrefour
 *
 */
public class DataMapping {

	private CheckChange checkChange;
	private List<Listener> listeners = new ArrayList<Listener>();
	
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
}
