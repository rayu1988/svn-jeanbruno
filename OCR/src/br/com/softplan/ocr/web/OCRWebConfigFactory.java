/**
 * 
 */
package br.com.softplan.ocr.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.softplan.ocr.exception.OCRLoaderException;

/**
 * Factory or can be called as a Singleton to load a Properties configs to Softpaln OCR.
 * @author jean.villete
 *
 */
public class OCRWebConfigFactory {
	
	private Properties properties = new Properties();
	
	private static OCRWebConfigFactory factory;
	
	private OCRWebConfigFactory() {}
	
	public static final OCRWebConfigFactory getInstance() {
		if (factory == null) {
			factory = new OCRWebConfigFactory();
		}
		return factory;
	}
	
	public void loadProperties(InputStream fileProperties) throws OCRLoaderException {
		try {
			this.properties.clear();
			this.properties.load(fileProperties);
		} catch (IOException e) {
			throw new OCRLoaderException(e);
		}
	}

	public Properties getProperties() {
		return properties;
	}
}
