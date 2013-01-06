/**
 * 
 */
package org.simplestructruedata.entities;


/**
 * @author carrefour
 *
 */
public abstract class SSDObject {

	private String							identifier;
	
	
	public SSDObject(String identifier) {
		if (identifier ==  null || identifier.isEmpty()) {
			throw new IllegalArgumentException("identifier argument can't be null or empty");
		}
		this.identifier = identifier.trim();
	}
	
	// GETTERS AND SETTERS //
	public String getIdentifier() {
		return identifier;
	}
	
}
