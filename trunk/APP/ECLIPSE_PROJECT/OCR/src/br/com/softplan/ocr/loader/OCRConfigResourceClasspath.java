/**
 * 
 */
package br.com.softplan.ocr.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.exception.OCRLoaderException;

/**
 * Implementation to loads OCRSoftplan and OCREngine with a valid resource at current JVM that should be a valid file property containing the settings.
 * 
 * @author jean.villete
 *
 */
public class OCRConfigResourceClasspath extends OCRConfigsProp {
	
	/**
	 * A constructor defining explicitly a resource at current JVM that is a property file with settings.
	 * <br/>
	 * Sample Usage...
	 * <br/>
	 * <code>
	 * OCRResourceClasspath("sp_ocr_configs.properties");
	 * </code>
	 * <br/>
	 * ...that sp_ocr_configs.properties must be a file within current classpath (JVM)
	 * 
	 * @param resourceName
	 * @throws OCRLoaderException 
	 */
	public OCRConfigResourceClasspath(String resourceName) throws OCRLoaderException {
		if (!OCRUtil.isStringOk(resourceName)) {
			throw new IllegalArgumentException("Argument resourceName can not be null nor empty!");
		}
		
		URL urlFileProp = this.getClass().getClassLoader().getResource(resourceName);
		if (urlFileProp != null) {
			File fileProperty = new File(urlFileProp.getFile());
			try {
				this.properties = OCRUtil.getLoadedProperties(fileProperty);
			} catch (IOException e) {
				throw new OCRLoaderException(e, "Problems while trying load file with resource. This should points to file: " + fileProperty.getAbsolutePath());
			}
		} else {
			throw new OCRLoaderException("Problems while trying load file with resource name: " + resourceName + ".");
		}
	}
}
