/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.io.Serializable;

/**
 * Entity class to represents a potential (likely) character found at document image.
 * 
 * @author jean.villete
 *
 */
public class OCRScannedCharacterImage implements Serializable {
	
	private static final long 			serialVersionUID = -8048742646302456547L;
	
	private OCRScannedRowCharacterImage scannedRowCharacterImage;
	private OCRScannedDocumentImage		scannedDocumentImage;
	private int							coordinateY1;
	private int							coordinateY2;
	private int							coordinateX1;
	private int							coordinateX2;
	
	private int							width;
	private int							height;
	private float 						aspectRatio;
	private float 						topWhiteSpaceFraction;
	private float 						bottomWhiteSpaceFraction;
	
	
	public OCRScannedCharacterImage(OCRScannedDocumentImage scannedDocumentImage, OCRScannedRowCharacterImage scannedRowCharacterImage, int coordinateY1, int coordinateY2, int coordinateX1, int coordinateX2) {
		if (scannedDocumentImage == null || coordinateY1 < 0 || coordinateY2 < 0 || coordinateX1 < 0 || coordinateX2 < 0) throw new IllegalArgumentException();
		
		this.scannedDocumentImage = scannedDocumentImage;
		this.scannedRowCharacterImage = scannedRowCharacterImage;
		this.coordinateY1 = coordinateY1;
		this.coordinateY2 = coordinateY2;
		this.coordinateX1 = coordinateX1;
		this.coordinateX2 = coordinateX2;
		
		this.width = this.coordinateX2 - this.coordinateX1;
		this.height = this.coordinateY2 - this.coordinateY1;
		this.aspectRatio = ((float) this.width) / ((float) this.height);
		this.topWhiteSpaceFraction = (float) (this.coordinateY1 - this.scannedRowCharacterImage.getCoordinateY1()) / (float) this.scannedRowCharacterImage.getRowHeight();
		this.bottomWhiteSpaceFraction = (float) (this.scannedRowCharacterImage.getCoordinateY2() - this.coordinateY2) / (float) this.scannedRowCharacterImage.getRowHeight();
	}

	// GETTERS //
	public int getCoordinateY1() {
		return coordinateY1;
	}
	public int getCoordinateY2() {
		return coordinateY2;
	}
	public int getCoordinateX1() {
		return coordinateX1;
	}
	public int getCoordinateX2() {
		return coordinateX2;
	}
	public OCRScannedRowCharacterImage getScannedRowCharacterImage() {
		return scannedRowCharacterImage;
	}
	public OCRScannedDocumentImage getScannedDocumentImage() {
		return scannedDocumentImage;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public float getAspectRatio() {
		return aspectRatio;
	}
	public float getTopWhiteSpaceFraction() {
		return topWhiteSpaceFraction;
	}
	public float getBottomWhiteSpaceFraction() {
		return bottomWhiteSpaceFraction;
	}
}
