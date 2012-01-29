/**
 * 
 */
package br.com.softplan.ocr.exception;

/**
 * @author jean.villete
 *
 */
public class OCRLoaderException extends OCRException {

	private static final long serialVersionUID = -5622867523955677084L;

	public OCRLoaderException() {
		super();
	}
	
	public OCRLoaderException(Exception e) {
		super(e);
	}
	
	public OCRLoaderException(String strPlus) {
		super();
		this.appendMessage(strPlus);
	}
	
	public OCRLoaderException(Exception e, String strPlus) {
		super(e);
		this.appendMessage(strPlus);
	}

}
