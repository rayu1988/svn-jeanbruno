/**
 * 
 */
package org.simplestructruedata.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author carrefour
 *
 */
public class SSDSetCharacter {

	private List<Character> string = null;
	
	public SSDSetCharacter() {
		this.clear();
	}
	
	public void add(Character character) {
		this.string.add(character);
	}
	
	public void clear() {
		this.string = new ArrayList<Character>();
	}
	
	public String getString() {
		char[] string = new char[this.string.size()];
		int i = 0;
		for (char character : this.string) {
			string[i++] = character;
		}
		return new String(string);
	}
	
}
