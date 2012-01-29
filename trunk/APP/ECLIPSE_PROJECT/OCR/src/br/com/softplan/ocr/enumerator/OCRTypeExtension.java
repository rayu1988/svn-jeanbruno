/**
 * 
 */
package br.com.softplan.ocr.enumerator;

/**
 * Enum to holds all the knowledge extensions that the Softplan OCR can runs to and from.
 * @author jean.villete
 *
 */
public enum OCRTypeExtension {
	// mime-type extractd from web.xml (tomcat)
	
	/**
	 * In the ocr context, it represents a PDF Searchable, that is an image base with its text searchable and selectable.
	 */
	PDF("application/pdf"),
	
	/**
	 * In the ocr context, it represents a clear text file containing the extracted text.
	 */
	TXT("text/plain"),
	
	/**
	 * In the ocr context, it represents an html file with hOCR results from extracting of ocr engine.
	 */
	HOCR("text/html");
	
	private String mimeType;
	
	OCRTypeExtension(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getMimeType() {
		return this.mimeType;
	}
}
