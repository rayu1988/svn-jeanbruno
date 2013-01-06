/**
 * 
 */
package org.simplestructruedata.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * @author carrefour
 *
 */
public class SSDObjectNode extends SSDObject {
	
	/**
	 * String : 	attribute's identifier
	 * SSDObject: 	the attribute itself
	 */
	private Map<String, SSDObject>			attributes;

	public SSDObjectNode(String identifier) {
		super(identifier);
		this.attributes = new HashMap<String, SSDObject>();
	}
	
	public void addAttribute(SSDObject ssdObject) {
		this.attributes.put(ssdObject.getIdentifier(), ssdObject);
	}
	
	public SSDObject get(String objectIdentifier) {
		return this.attributes.get(objectIdentifier);
	}
}
