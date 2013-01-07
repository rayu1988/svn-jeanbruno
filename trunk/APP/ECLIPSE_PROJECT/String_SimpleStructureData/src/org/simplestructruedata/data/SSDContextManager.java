/**
 * 
 */
package org.simplestructruedata.data;

import java.util.ArrayList;
import java.util.List;

import org.simplestructruedata.commons.SSDDefaultConstants;
import org.simplestructruedata.commons.SSDUtils;
import org.simplestructruedata.entities.SSDObject;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;
import org.simplestructruedata.entities.SSDSetCharacter;
import org.simplestructruedata.exception.SSDException;

/**
 * @author carrefour
 *
 */
public class SSDContextManager {
	
	private List<SSDObject>			heap = new ArrayList<SSDObject>();
	private int						currentReference = 0;
	
	public class SSDRootObject extends SSDObjectNode {
		public SSDRootObject() {
			super(SSDDefaultConstants.ROOT_OBJECT);
		}
	}
	
	private SSDContextManager() {
		this.addToHeap(new SSDRootObject());
	}
	
	public static SSDContextManager build() throws SSDException {
		return build("{}");
	}
	
	public static SSDContextManager build(String dataBase) throws SSDException {
		dataBase = dataBase.trim();
		
		if (dataBase.charAt(0) != SSDDefaultConstants.OPENS_BRACES) {
			throw new SSDException("The string base must starts with:" + SSDDefaultConstants.OPENS_BRACES);
		} else {
			SSDContextManager ctx = new SSDContextManager();
			
			SSDSetCharacter string = new SSDSetCharacter();
			for (int i = 1; i < dataBase.length(); i++) {
				char currentChar = dataBase.charAt(i);
				if (SSDUtils.isReservedCharacter(currentChar)) {
					if (currentChar == SSDDefaultConstants.ESCAPE) {
						currentChar = dataBase.charAt(++i);
						string.add(currentChar);
						continue;
					}
					if (currentChar == SSDDefaultConstants.ASSIGN || currentChar == SSDDefaultConstants.COMMA) {
						currentChar = dataBase.charAt(++i);
					}
					if (currentChar == SSDDefaultConstants.OPENS_BRACES) {
						ctx.addToHeap(new SSDObjectNode(string.getString()));
						string.clear();
						continue;
					}
					if (currentChar == SSDDefaultConstants.CLOSES_BRACES) {
						ctx.closeObject();
						string.clear();
						continue;
					}
					if (currentChar == SSDDefaultConstants.QUOTATION_MARKS) {
						if (ctx.getCurrentObject() instanceof SSDObjectLeaf) {
							SSDObjectLeaf objectLeaf = (SSDObjectLeaf) ctx.getCurrentObject();
							objectLeaf.setValue(string.getString());
							ctx.closeObject();
						} else {
							ctx.addToHeap(new SSDObjectLeaf(string.getString()));
						}
						string.clear();
						continue;
					}
				}
				string.add(currentChar);
			}
			return ctx;
		}
	}
	
	private void addToHeap(SSDObject ssdObject) {
		this.heap.add(this.currentReference++, ssdObject);
	}
	
	private SSDObject getCurrentObject() {
		return this.heap.get(this.currentReference-1);
	}
	
	private void closeObject() throws SSDException {
		if (this.getCurrentObject() instanceof SSDRootObject) {
			return;
		}
		SSDObject object = this.heap.get(this.currentReference-2);
		if (object instanceof SSDObjectNode) {
			SSDObjectNode nodeObject = (SSDObjectNode) object;
			nodeObject.addAttribute(this.getCurrentObject());
		} else throw new SSDException("Invalid type to this point");
		this.currentReference--;
	}
	
	public SSDRootObject getRootObject() {
		if (this.heap.size() > 1) {
			int currentSize = this.heap.size();
			for (int i = 1; i < currentSize; i++ ) {
				this.heap.remove(currentSize - i);
			}
		}
		return (SSDRootObject) this.heap.get(0);
	}
	
	public String getAsString() throws SSDException {
		return this.getAsString(this.getRootObject());
	}
	
	private String getAsString(SSDObject object) throws SSDException {
		StringBuffer returning = new StringBuffer();
		if (!(object instanceof SSDRootObject)) {
			returning.append(object.getIdentifier() + " = ");
		}
		if (object instanceof SSDObjectLeaf) {
			returning.append("\"");
			returning.append(SSDUtils.formatEscapes(((SSDObjectLeaf)object).getValue()));
			returning.append("\"");
		} else if (object instanceof SSDObjectNode || object instanceof SSDRootObject) {
			returning.append("{");
			List<SSDObject> attributes = new ArrayList<SSDObject>(((SSDObjectNode) object).getAttributes());
			int attributeSize = attributes.size();
			if (attributeSize > 0) {
				for (int i = 0; i < attributeSize; i++) {
					if (i > 0) {
						returning.append(", ");
					}
					returning.append(this.getAsString(attributes.get(i)));
				}
			}
			returning.append("}");
		} else throw new SSDException("Invalid type to this point");
		return returning.toString();
	}
}
