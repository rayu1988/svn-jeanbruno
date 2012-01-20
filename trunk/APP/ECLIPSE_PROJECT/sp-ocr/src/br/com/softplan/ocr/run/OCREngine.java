/**
 * 
 */
package br.com.softplan.ocr.run;

import java.io.File;
import java.util.List;
import java.util.Properties;

import br.com.softplan.ocr.exception.OCRExtractingException;

/**
 * Utility interface to set contract to some ocr engine that returns a hOCR result.
 * 
 * @author jean.villete
 *
 */
public interface OCREngine {
	
	/**
	 * Method to load some settings to current OCR Engine.
	 * @param configsProp
	 */
	void load(Properties configsProp);
	
	/**
	 * Method to invoke the tesseract engine to extract text contained at the image passed as parameter.
     * The return is a list of string extracted and also formated as hOCR standard.
     * 
	 * @param imageFiles
	 * @return To each imageFile passed as parameter, will there a String hOCR in the same index at the List returned. A list of hOCR String.
	 * @throws OCRExtractingException
	 */
	List<String> run(final List<File> imageFiles) throws OCRExtractingException;
	
	/**
	 * Set complete String command to invoke the ocr engine.
	 * @param appInvoking
	 */
	void setAppInvoking(String appInvoking);
}
