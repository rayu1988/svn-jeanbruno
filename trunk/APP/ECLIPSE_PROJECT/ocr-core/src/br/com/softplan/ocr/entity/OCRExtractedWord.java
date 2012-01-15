/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.util.ArrayList;
import java.util.List;

import br.com.softplan.ocr.common.OCRUtil;

/**
 * Entity class to represents a word found.
 * @author jean.villete
 *
 */
public class OCRExtractedWord {
	
	private OCRExtractedText 						extractedText;
	
	private List<OCRExtractedCharacter>				extractedCharacters;
	
	protected OCRExtractedWord(OCRExtractedText extractedText) {
		if (extractedText == null) {
			throw new IllegalArgumentException();
		}
		
		this.extractedText = extractedText;
		this.extractedCharacters = new ArrayList<OCRExtractedCharacter>();
	}
	
	/**
	 * Adds a new extracted charecter that has been found.
	 * The concepts of an extracted character is a bound of a previus scanned (likely) character and a data training, that really have a valid character.
	 * 
	 * @param scannedCharacterImage
	 * @param dataTraining
	 */
	protected void addExtractedCharacter(OCRExtractedCharacter extractedCharacter) {
		this.extractedCharacters.add(extractedCharacter);
	}
	
	// GETTERS //
	public List<OCRExtractedCharacter> getExtractedCharacters() {
		return extractedCharacters;
	}
	public OCRExtractedText getExtractedText() {
		return extractedText;
	}
	public int getCoordinateX() {
		if (!OCRUtil.isCollectionOk(this.extractedCharacters)) {
			throw new IllegalStateException("There is no character to pass by.");
		}
		return this.extractedCharacters.get(0).getScannedCharacterImage().getCoordinateX1();
	}
	public int getCoordinateY() {
		if (!OCRUtil.isCollectionOk(this.extractedCharacters)) {
			throw new IllegalStateException("There is no character to pass by.");
		}
		
		int yFirstCharacter = this.extractedCharacters.get(0).getScannedCharacterImage().getCoordinateY1();
		int yLastCharacter =  this.extractedCharacters.get(this.extractedCharacters.size()-1).getScannedCharacterImage().getCoordinateY2();
		return (yFirstCharacter + yLastCharacter) / 2;
	}
	
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		for (OCRExtractedCharacter extractedCharacter : this.extractedCharacters) {
			stringBuffer.append(extractedCharacter.getDataTraining().getCharacter());
		}
		return stringBuffer.toString();
	}
}
