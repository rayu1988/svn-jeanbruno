/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.io.Serializable;

/**
 * Entity Relational Class.
 * This class holds a link between a scanned character that is nothing more than a likely character and a specified data training, that really have a pointer to a valid character,
 *  thus it indicates that likely character really represent the specified character within data training.
 * 
 * @author jean.villete
 *
 */
public class OCRExtractedCharacter implements Serializable {
	
	private static final long 				serialVersionUID = -541027209512189146L;
	
	private OCRExtractedText 				extractedText;
	private OCRScannedCharacterImage 		scannedCharacterImage;
	private OCRDataTraining 				dataTraining;
	
	public OCRExtractedCharacter(OCRExtractedText extractedText, OCRScannedCharacterImage scannedCharacterImage, OCRDataTraining dataTraining) {
		if (extractedText == null || scannedCharacterImage == null || dataTraining == null) throw new IllegalArgumentException();
		
		this.extractedText = extractedText;
		this.scannedCharacterImage = scannedCharacterImage;
		this.dataTraining = dataTraining;
	}

	// GETTERS //
	public OCRExtractedText getExtractedText() {
		return extractedText;
	}
	public OCRScannedCharacterImage getScannedCharacterImage() {
		return scannedCharacterImage;
	}
	public OCRDataTraining getDataTraining() {
		return dataTraining;
	}
	
	@Override
	public String toString() {
		return "[" + this.dataTraining.getCharacter() + ", " + this.dataTraining.getFont().getName() + ", position x1: " + this.scannedCharacterImage.getCoordinateX1()
				+ ", x2: " + this.scannedCharacterImage.getCoordinateX2() + ", y1: " + this.scannedCharacterImage.getCoordinateY1() + ", y2: " + this.scannedCharacterImage.getCoordinateY2()
				+ "]";
	}
}
