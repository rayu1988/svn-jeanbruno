/**
 * 
 */
package org.simplestructruedata.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.simplestructruedata.exception.SSDException;

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
	
	public Collection<SSDObject> getAttributes() {
		return this.attributes.values();
	}
	
	public SSDObjectNode getNode(String nodeIdentifier) throws SSDException {
		SSDObjectNode objectNode;
		try {
			objectNode = (SSDObjectNode) this.get(nodeIdentifier);
		} catch (Exception e) {
			throw new SSDException("error trying get a SSDObjectNode with the identifier:" + nodeIdentifier + "\n" + e.getMessage());
		}
		return objectNode;
	}
	
	public SSDObjectLeaf getLeaf(String leafIdentifier) throws SSDException {
		SSDObjectLeaf objectLeaf;
		try {
			objectLeaf = (SSDObjectLeaf) this.get(leafIdentifier);
		} catch (Exception e) {
			throw new SSDException("error trying get a SSDObjectLeaf with the identifier:" + leafIdentifier + "\n" + e.getMessage());
		}
		return objectLeaf;
	}
}
