/**
 * 
 */
package br.com.softplan.ocr.loader;

import br.com.softplan.ocr.exception.OCRLoaderException;
import br.com.softplan.ocr.web.OCRWebConfigFactory;

/**
 * Implements to loads OCRSoftplan and OCREngine with a valid file property that contains the settings.
 * 
 * @author jean.villete
 *
 */
public class OCRConfigWebContext extends OCRConfigsProp {

	/**
	 * A constructor according a Web Context.
	 * 
	 * @throws OCRLoaderException 
	 */
	public OCRConfigWebContext() throws OCRLoaderException {
		this.properties = OCRWebConfigFactory.getInstance().getProperties();
		if (this.properties == null) {
			throw new OCRLoaderException("None instance was loaded to ocr web factory.");
		}
	}
}
