package br.com.softplan.ocr.entity;

import java.io.Serializable;

import br.com.softplan.ocr.common.OCRUtil;

/**
 * Entity class to represent a binary (pixels) image and its scale/size.
 * 
 * @author jean.villete
 *
 */
public class OCRImage implements Serializable {
	
	private static final long 		serialVersionUID = -1517685449581750662L;
	
	/**
	 * (pt.BR {literal: relação de aspecto} proporção de aparência)
	 */
	private float 					aspectRatio;
	private int[] 					binaries;
	private OCRImageScale			imageScale;
	
	public OCRImage(int[] binaries, OCRImageScale imageScale) {
		if (binaries == null || binaries.length <= 0 || imageScale == null) {
			throw new IllegalArgumentException();
		}
		if (binaries.length != imageScale.getSize()) {
			throw new IllegalStateException("the size binaries do not corresponding with imageScale's size");
		}
		
		this.binaries = binaries;
		this.imageScale = imageScale;
		this.aspectRatio = ((float) imageScale.getWidth()) / ((float) imageScale.getHeight());
	}

	/**
	 * Returns a specified binary from the binary image map as a matrix coordinates (x and y).
	 * @param x
	 * @param y
	 * @return
	 */
	public int getBinaryAt(int x, int y) {
		return this.binaries[OCRUtil.matrixIndexToArrayIndex(x, y, this.imageScale.getWidth())];
	}
	
	// GETTERS AND SETTERS //
	public int[] getBinaries() {
		return binaries;
	}
	public OCRImageScale getImageScale() {
		return imageScale;
	}
	public float getAspectRatio() {
		return aspectRatio;
	}
}
