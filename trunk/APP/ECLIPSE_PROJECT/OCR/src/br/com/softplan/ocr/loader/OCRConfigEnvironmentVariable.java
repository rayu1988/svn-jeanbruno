/**
 * 
 */
package br.com.softplan.ocr.loader;

import java.io.File;
import java.io.IOException;

import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.exception.OCRLoaderException;

/**
 * Implements to loads OCRSoftplan and OCREngine with a valid Environment Variable Key/Value.
 * 
 * @author jean.villete
 *
 */
public class OCRConfigEnvironmentVariable extends OCRConfigsProp {
	
	/**
	 * Constructor to define a valid Key Environment Variable that will be used to search for a specified Environment Variable that
	 * should contain the address to file properties with configs to sp ocr.
	 * <br/>
	 * This constructor hopes that exist at the environment variables one that explict the configs file.
	 * Sample Usage...
	 * <br/>
	 * <code>
	 * OCREnvironmentVariable("SP_OCR_CONFIG_PROP");
	 * </code>
	 * <br/>
	 * ...that SP_OCR_CONFIG_PROP should be something like;
	 * 	SP_OCR_CONFIG_PROP = My Address (e.g. /etc/tesseract/sp-ocr/configs.properties or C:/Programs and Files/Tesseract/sp-ocr/configs.properties)
	 * 
	 * @param keyEnvironmentVariable
	 * @throws OCRLoaderException 
	 */
	public OCRConfigEnvironmentVariable(String keyEnvironmentVariable) throws OCRLoaderException {
		if (!OCRUtil.isStringOk(keyEnvironmentVariable)) {
			throw new IllegalArgumentException("Some problem with keyEnvironmentVariable passed as parameter! It's is null or empty.");
		}
		
		String strConfigHome = System.getenv(keyEnvironmentVariable);
		if (OCRUtil.isStringOk(strConfigHome)) {
			File fileProperty = new File(strConfigHome);
			try {
				this.properties = OCRUtil.getLoadedProperties(fileProperty);
			} catch (IOException e) {
				throw new OCRLoaderException(e, "Problems while trying load file declared in \"Environment Variable\": " + keyEnvironmentVariable +
						". The address exist not to file: " + fileProperty.getAbsolutePath());
			}
		} else {
			throw new OCRLoaderException("There's no valid value for \"Environment Variable\" " + keyEnvironmentVariable + " and right now it is required.");
		}
	}
}