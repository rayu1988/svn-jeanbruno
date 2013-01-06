/**
 * 
 */
package org.simplestructruedata.entities;

/**
 * @author carrefour
 *
 */
public class SSDObjectLeaf extends SSDObject {

	private String					value;
	
	public SSDObjectLeaf(String identifier) {
		super(identifier);
	}

	// GETTERS AND SETTERS //
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
