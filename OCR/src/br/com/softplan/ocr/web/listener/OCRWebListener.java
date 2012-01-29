package br.com.softplan.ocr.web.listener;

import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.softplan.ocr.common.OCRConstant;
import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.exception.OCRLoaderException;
import br.com.softplan.ocr.web.OCRWebConfigFactory;

public class OCRWebListener implements ServletContextListener {

	public static Log log = LogFactory.getLog(OCRWebListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		this.beginsImageScanPlugins();
		this.loadConfigs(servletContextEvent);
	}

	private void loadConfigs(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		
		String configsValue = servletContext.getInitParameter(OCRConstant.CONFIGS_PROPERTIES);
		
		if (!OCRUtil.isStringOk(configsValue)) {
			log.error(new OCRLoaderException("The Configs to Softplan OCR is not correct! While load OCR configs as servlet config, is mandatory the param: <param-name>sp_configs</param-name> !"));
			return;
		}
		
		OCRWebConfigFactory configFactory = OCRWebConfigFactory.getInstance();
		InputStream inputStream = servletContext.getResourceAsStream(configsValue);
		try {
			if (inputStream != null) {
				configFactory.loadProperties(inputStream);
			} else {
				throw new OCRLoaderException("The Configs to Softplan OCR is not correct! There is no valid resource called as: " + configsValue);
			}
		} catch (OCRLoaderException e) {
			log.error(e);
			return;
		}
	}
	
	private void beginsImageScanPlugins() {
		ImageIO.scanForPlugins();
		
		ImageIO.getImageReadersByFormatName("pnm").next();
		ImageIO.getImageReadersByFormatName("jpeg2000").next();
		ImageIO.getImageReadersByFormatName("jpg").next();
		ImageIO.getImageReadersByFormatName("jpeg").next();
		ImageIO.getImageReadersByFormatName("tif").next();
		ImageIO.getImageReadersByFormatName("tiff").next();
	}
}
