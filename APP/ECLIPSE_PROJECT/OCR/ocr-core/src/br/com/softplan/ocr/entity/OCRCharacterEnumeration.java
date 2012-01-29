/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;

import br.com.softplan.ocr.common.OCRUtil;

/**
 * @author jean.villete
 *
 */
public class OCRCharacterEnumeration implements Enumeration<Character> {
	
	private char[] 			characters;
	private int 			counter=0;
	
	public OCRCharacterEnumeration(File characterFile) throws IOException {
		Scanner systemScanner = new Scanner(OCRUtil.getInstanceReaderUTF8(characterFile));
		StringBuffer setCharacter = new StringBuffer();
		while (systemScanner.hasNextLine()) {
			setCharacter.append(systemScanner.nextLine().replace(" ", ""));
		}
		systemScanner.close();
		this.characters = new char[setCharacter.length()];
		setCharacter.getChars(0, setCharacter.length(), this.characters, 0);
	}
	
	@Override
	public boolean hasMoreElements() {
		return this.counter < this.characters.length;
	}
	@Override
	public Character nextElement() {
		Character character = this.characters[this.counter];
		this.counter++;
		return character;
	}
	
	public Character currentElement() {
		return this.characters[(this.counter > 0 ? this.counter - 1 : this.counter)];
	}
	
	public int size() {
		return this.characters.length;
	}
}
