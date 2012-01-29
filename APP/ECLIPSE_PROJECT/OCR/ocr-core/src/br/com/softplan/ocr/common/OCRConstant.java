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
	
    /**
     * This is the maximum variance of aspect ratio between a training image
     * and the actual image segment to be decoded.  It is expressed as a
     * fraction of the calculated aspect ratios.  Any training image which
     * varies by more than this fraction from the aspect ratio of the character
     * to be decoded, is not used to decode that character.
     */
    public static final float 					ASPECT_RATIO_TOLERANCE = 0.3f;
    
    /**
     * This is the maximum variance between the source character's top white
     * space fraction and the training image's top white space fraction.  Any
     * training image which varies from the source character by more than this
     * tolerance, will not be considered a candidate.
     */
    public static final float 					TOP_WHITE_SPACE_FRACTION_TOLERANCE = 0.3f;
    
    /**
     * This is the maximum variance between the source character's bottom white
     * space fraction and the training image's bottom white space fraction.  Any
     * training image which varies from the source character by more than this
     * tolerance, will not be considered a candidate.
     */
    public static final float 					BOTTOM_WHITE_SPACE_FRACTION_TOLERANCE = 0.3f;
}
