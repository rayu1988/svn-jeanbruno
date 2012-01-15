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
	
	private OCRDocumentImage					documentImage;
	private List<OCRScannedCharacterImage>		scannedCharacters;
	
	public OCRScannedDocumentImage(OCRDocumentImage documentImage) throws IOException, InterruptedException {
		if (documentImage == null) throw new IllegalArgumentException();
		this.documentImage = documentImage;
		this.scannedCharacters = new ArrayList<OCRScannedCharacterImage>();
	}

	public void addScannedCharacterImage(OCRScannedCharacterImage scannedCharacterImage) {
		this.scannedCharacters.add(scannedCharacterImage);
	}
	
	// GETTERS //
	public OCRDocumentImage getDocumentImage() {
		return documentImage;
	}
	public List<OCRScannedCharacterImage> getScannedCharacters() {
		return scannedCharacters;
	}
}
