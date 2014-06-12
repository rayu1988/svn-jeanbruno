/**
 * 
 */
package br.com.laptracker.web.listeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.com.tatu.helper.GeneralsHelper;

import br.com.laptracker.business.exceptions.AppException;
import br.com.laptracker.business.persistence.control.DataSourceFactory;

/**
 * @author villjea
 *
 */
public class SettingsListener implements ServletContextListener {

	public static final String					DATA_SOURCE = "br.com.laptracker.DATA_SOURCE";
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			String dataSourceParam = event.getServletContext().getInitParameter(DATA_SOURCE);
			File dataSourceFile = null;
			if (dataSourceParam.startsWith("file:")) {
				dataSourceFile = new File(new URL(dataSourceParam).getFile());
			} else {
				dataSourceFile = new File(dataSourceParam);
			}
			Properties dataSourceProperties = new Properties();
			InputStream dataSourceIS = new FileInputStream(dataSourceFile);
			dataSourceProperties.load(dataSourceIS);
			
			DataSourceFactory dsf = DataSourceFactory.getInstance();
			String propertyValue = null;
			
			// url
			if (GeneralsHelper.isStringOk(propertyValue = dataSourceProperties.getProperty("dataSource.url"))) {
				dsf.setUrl(propertyValue);
			} else {
				throw new AppException("The property dataSource.url was not found.");
			}
			
			// user
			if (GeneralsHelper.isStringOk(propertyValue = dataSourceProperties.getProperty("dataSource.user"))) {
				dsf.setUser(propertyValue);
			} else {
				throw new AppException("The property dataSource.user was not found.");
			}
			
			// password
			if (GeneralsHelper.isStringOk(propertyValue = dataSourceProperties.getProperty("dataSource.password"))) {
				dsf.setPassword(propertyValue);
			} else {
				throw new AppException("The property dataSource.password was not found.");
			}
			
			// driverClassName
			if (GeneralsHelper.isStringOk(propertyValue = dataSourceProperties.getProperty("dataSource.driverClassName"))) {
				dsf.setDriverClassName(propertyValue);
			} else {
				throw new AppException("The property dataSource.driverClassName was not found.");
			}
		} catch (FileNotFoundException e) {
			throw new AppException(e);
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

}
