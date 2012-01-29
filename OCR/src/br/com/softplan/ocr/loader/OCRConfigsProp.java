/**
 * 
 */
package br.com.softplan.ocr.loader;

import java.util.Properties;

/**
 * Public interface to defines a way that load Property Settings to OCREngine.
 * @author jean.villete
 *
 */
public abstract class OCRConfigsProp {

	protected Properties				properties;
	
	/**
	 * Method to get the file properties with settings to OCREngine.
	 * @return
	 */
	public Properties getFileProperties() {
		return this.properties;
	}
}
