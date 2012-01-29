/**
 * 
 */
package br.com.softplan.ocr.exception;


/**
 * @author jean.villete
 *
 */
public class OCRExtractingException extends OCRException {
	
	private static final long serialVersionUID = -5164538186710613708L;

	public OCRExtractingException(Exception e, String currentOCREngine) {
		super(e);
		this.appendMessage(currentOCREngine);
		this.appendMessage("Problems while trying runs extracting text (hOCR).");
	}
}
