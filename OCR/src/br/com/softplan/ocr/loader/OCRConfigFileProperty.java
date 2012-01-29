/**
 * 
 */
package br.com.softplan.ocr.loader;

import java.io.File;
import java.io.IOException;

import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.exception.OCRLoaderException;

/**
 * Implements to loads OCRSoftplan and OCREngine with a valid file property that contains the settings.
 * 
 * @author jean.villete
 *
 */
public class OCRConfigFileProperty extends OCRConfigsProp {

	/**
	 * A constructor defining explicitly the property file with settings.
	 * Sample Usage;
	 * <br/>
	 * <code>
	 * java.io.File fileProp = new java.io.File("/usr/local/share/sp-configs/sp_ocr_configs.properties");
	 * <br/>
	 * OCRFileProperty(fileProp);
	 * </code>
	 * 
	 * @param fileProperty
	 * @throws OCRLoaderException 
	 */
	public OCRConfigFileProperty(File fileProperty) throws OCRLoaderException {
		if (fileProperty == null || !fileProperty.exists()) {
			throw new OCRLoaderException("Argument fileProperty can not be null and must exists.");
		}
		try {
			this.properties = OCRUtil.getLoadedProperties(fileProperty);
		} catch (IOException e) {
			throw new OCRLoaderException(e, "Problems while trying load file: " + fileProperty.getName());
		}
	}
}
