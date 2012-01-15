/**
 * 
 */
package br.com.softplan.ocr.exception;


/**
 * @author jean.villete
 *
 */
public class OCRNoneCharacterFoundException extends OCRException {

	private static final long serialVersionUID = -7474361930605907666L;

	public OCRNoneCharacterFoundException() {
		this.appendMessage("The system passed by a likely character not recognized.");
	}
}
