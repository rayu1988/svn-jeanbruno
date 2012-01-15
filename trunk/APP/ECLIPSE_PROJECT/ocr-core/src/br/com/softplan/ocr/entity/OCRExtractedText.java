/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class.
 * This class represents a text of a valid Document Image that already has been scanned and extracted its text from.
 * 
 * @author jean.villete
 *
 */
public class OCRExtractedText implements Serializable {

	private static final long 						serialVersionUID = 3801946997608581862L;
	
	private OCRScannedDocumentImage					scannedDocumentImage;
	private List<OCRExtractedCharacter>				extractedCharacters;
	
	public OCRExtractedText(OCRScannedDocumentImage scannedDocumentImage) {
		if (scannedDocumentImage == null) throw new IllegalArgumentException();
		
		this.scannedDocumentImage = scannedDocumentImage;
		this.extractedCharacters = new ArrayList<OCRExtractedCharacter>();
	}
	
	/**
	 * Adds a new extracted charecter that has been found.
	 * The concepts of an extracted character is a bound of a previus scanned (likely) character and a data training, that really have a valid character.
	 * 
	 * @param scannedCharacterImage
	 * @param dataTraining
	 */
	public void addExtractedCharacter(OCRExtractedCharacter extractedCharacter) {
		this.extractedCharacters.add(extractedCharacter);
	}

	// GETTERS //
	public OCRScannedDocumentImage getScannedDocumentImage() {
		return scannedDocumentImage;
	}
	public List<OCRExtractedCharacter> getExtractedCharacters() {
		return extractedCharacters;
	}
}
