/**
 * 
 */
package br.com.softplan.ocr.run;

import java.io.File;
import java.util.List;

import br.com.softplan.ocr.exception.OCRExtractingException;

/**
 * Utility interface to set contract to some ocr engine that returns a hOCR result.
 * 
 * @author jean.villete
 *
 */
public interface OCREngine {
	
	/**
	 * Method to invoke the tesseract engine to extract text contained at the image passed as parameter.
     * The return is the string extracted formated as hOCR standard.
     * 
	 * @param imageFiles
	 * @return A hOCR String
	 * @throws OCRExtractingException
	 */
	String run(final List<File> imageFiles) throws OCRExtractingException;
}
