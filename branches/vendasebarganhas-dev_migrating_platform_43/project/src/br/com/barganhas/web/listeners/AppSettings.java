package br.com.barganhas.web.listeners;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.com.tatu.helper.GeneralsHelper;

import br.com.barganhas.business.exceptions.AppException;

public class AppSettings implements ServletContextListener {
	
	public static final String				DATA_SOURCE_PARAM = "br.com.barganhas.settings.DATA_SOURCE";
	
	private static final Logger logger = Logger.getLogger(AppSettings.class.getCanonicalName());

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.log(Level.INFO, "Starting initialize context to AppSettings.");
		ServletContext servletContext = event.getServletContext();
		
		String dataSource = servletContext.getInitParameter(DATA_SOURCE_PARAM);
		if (!GeneralsHelper.isStringOk(dataSource)) {
			throw new AppException(new IllegalArgumentException("The initial parameter " + DATA_SOURCE_PARAM + " is mandatory!"));
		}
		if (dataSource.startsWith("file:")) {
			dataSource = dataSource.replace("file:", "");
		}
		File fileDataSource = new File(dataSource);
		if (!fileDataSource.exists() || !fileDataSource.isFile()) {
			throw new AppException(new IllegalArgumentException("The initial parameter " + DATA_SOURCE_PARAM + " is not a valid file:" + dataSource)); 
		}
		System.setProperty("dataSource.file.path", dataSource);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

}
