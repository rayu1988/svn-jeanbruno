/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class to represents a class that has been scanned by the Optical Recognation.
 * This current class must be created just by the Optical Recognation task, and that class holds every character found (recognized).
 * 
 * @author jean.villete
 *
 */
public class OCRScannedDocumentImage implements Serializable {

	private static final long 					serialVersionUID = -5445694967301468918L;
	
	private ArrayList<Integer>					listWordDivisor = new ArrayList<Integer>();
	
	private OCRDocumentImage					documentImage;
	private List<OCRScannedCharacterImage>		scannedCharacters;
	
	public OCRScannedDocumentImage(OCRDocumentImage documentImage) throws IOException, InterruptedException {
		if (documentImage == null) throw new IllegalArgumentException();
		this.documentImage = documentImage;
		this.scannedCharacters = new ArrayList<OCRScannedCharacterImage>();
	}

	/**
	 * Add a new scanned character image to currente document image.
	 * @param scannedCharacterImage
	 */
	public void addScannedCharacterImage(OCRScannedCharacterImage scannedCharacterImage) {
		this.scannedCharacters.add(scannedCharacterImage);
	}
	
	/**
	 * Mark a pointer to word divisor.
	 */
	public void addWordDivisor() {
		this.listWordDivisor.add(this.scannedCharacters.size());
	}
	
	// GETTERS //
	public OCRDocumentImage getDocumentImage() {
		return documentImage;
	}
	public List<OCRScannedCharacterImage> getScannedCharacters() {
		return scannedCharacters;
	}

	public ArrayList<Integer> getListWordDivisor() {
		return listWordDivisor;
	}
}
