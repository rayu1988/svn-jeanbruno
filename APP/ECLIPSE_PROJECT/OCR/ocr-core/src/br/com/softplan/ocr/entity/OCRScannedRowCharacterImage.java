/**
 * 
 */
package br.com.softplan.ocr.entity;

/**
 * Entity class to represents a posioned row over the image, indicating the coordinateY1 and Y2 to this row.
 * 
 * @author jean.villete
 *
 */
public class OCRScannedRowCharacterImage {

	private int					coordinateY1;
	private int					coordinateY2;
	
	public OCRScannedRowCharacterImage(int coordinateY1, int coordinateY2) {
		if (coordinateY1 < 0 || coordinateY2 < 0) throw new IllegalArgumentException();
		
		this.coordinateY1 = coordinateY1;
		this.coordinateY2 = coordinateY2;
	}

	// GETTERS //
	public int getCoordinateY1() {
		return coordinateY1;
	}
	public int getCoordinateY2() {
		return coordinateY2;
	}
	public int getRowHeight() {
		return this.coordinateY2 - this.coordinateY1;
	}
}
