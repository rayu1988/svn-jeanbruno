package br.com.softplan.ocr.common;

/**
 * 
 * @author jean.villete
 *
 */
public interface OCRConstant {

	/**
	 * Acronym to OCR.
	 */
	public static final String 					OCR_ACRONYM_TO = "Optical Character Recognation";
	
	public static final String					FILE_SEPARATOR = System.getProperty("file.separator");
	
	/**
	 * Used to define a valid Temp Dir to Optical Character Recognation System.
	 */
	public static final String 					OCR_TO_TMP_DIR = FILE_SEPARATOR + "OpticalCharacterRecognationTempDir";

	public static final String 					END_OF_LINE = "\n";
}
