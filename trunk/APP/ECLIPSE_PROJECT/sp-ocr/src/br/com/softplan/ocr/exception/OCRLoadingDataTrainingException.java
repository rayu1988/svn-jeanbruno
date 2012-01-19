/**
 * 
 */
package br.com.softplan.ocr.exception;

import java.awt.Font;

/**
 * Exception launched when happens some problem on loading Data Training.
 * 
 * @author jean.villete
 *
 */
public class OCRLoadingDataTrainingException extends OCRException {

	private static final long serialVersionUID = 9185021456997477273L;
	
	public OCRLoadingDataTrainingException(Exception e, Font currentFont, String messagePlus) {
		super(e);
		this.setMessage(currentFont, messagePlus);
	}
	
	public OCRLoadingDataTrainingException(Font currentFont, String messagePlus) {
		this.setMessage(currentFont, messagePlus);
	}
	
	private void setMessage(Font currentFont, String messagePlus) {
		this.appendMessage("Unsuccess on loading data training on font: " + currentFont.toString() + ".");
		this.appendMessage(messagePlus);
	}
}
