package br.com.softplan.ocr.entity;

import java.io.Serializable;

/**
 * Entity class to represents the image scales/dimensions/sizes.
 * @author jean.villete
 *
 */
public class OCRImageScale implements Serializable {
	
	private static final long 		serialVersionUID = -2910129793925890107L;
	
	private int 					width;
	private int						height;
	private int 					size; // width * height
	
	public OCRImageScale(int width, int height) {
		if (width < 0 || height < 0) throw new IllegalArgumentException();
		this.width = width;
		this.height = height;
		this.size = this.width * this.height;
	}
	
	// GETTERS AND SETTERS //
	public int getSize() {
		return size;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}