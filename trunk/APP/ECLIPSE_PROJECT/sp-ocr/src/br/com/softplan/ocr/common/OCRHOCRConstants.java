/**
 * 
 */
package br.com.softplan.ocr.common;

import java.util.regex.Pattern;

/**
 * @author jean.villete
 *
 */
public interface OCRHOCRConstants {
	// In order to place text behind the recognised text snippets we are interested in the bbox property		
	Pattern 				B_BOX_PATTERN = Pattern.compile("bbox(\\s+\\d+){4}");
	// This pattern separates the coordinates of the bbox property
	Pattern 				B_BOX_COORDINATE_PATTERN = Pattern.compile("(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)");
	
	String 					HOCR_CLASS_PAGE = "ocr_page";
	String 					HOCR_CLASS_LINE = "ocr_line";
	String 					HOCR_CLASS_WORD = "ocr_word";
	String 					HOCR_ATTRIBUTE_TITLE = "title";
}
