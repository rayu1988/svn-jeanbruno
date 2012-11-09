package br.com.barganhas.business.entities;

import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class CityTO extends TransferObject {

	@IdField
	@PropertyField
	private Long			id;
	
	@PropertyField(notNull=true, allowEmpty=false)
	private String			name;

	@PropertyField(notNull=true)
	private Key				keyState;
	private StateTO			state;
	
	public CityTO() {
		super(null);
	}

	public CityTO(Key key) {
		super(key);
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public Long getId() {
		return id;
	}
	
	// GETTERS AND SETTERS //
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Key getKeyState() {
		return keyState;
	}

	public void setKeyState(Key keyState) {
		this.keyState = keyState;
	}

	public StateTO getState() {
		return state;
	}

	public void setState(StateTO state) {
		this.state = state;
	}

}
