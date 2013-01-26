package br.com.barganhas.web.listeners;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.business.services.ServiceBusinessFactory;

public class AppLoadListener implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(AppLoadListener.class.getCanonicalName());

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.log(Level.INFO, "Starting initialize context to AppLoadListener.");
		
		ServletContext servletContext = event.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		logger.log(Level.INFO, "Registering the Spring Application Context.");
		ServiceBusinessFactory.getInstance().registerApplicationContext(webApplicationContext);
		logger.log(Level.INFO, "Spring Application Context registered successfully.");
		
		this.registerFirstAdministrator();
	}
	
	private void registerFirstAdministrator() {
		logger.log(Level.INFO, "Getting the Administrator service from Spring.");
		Administrator service = ServiceBusinessFactory.getInstance().getAdministrator();
		
		logger.log(Level.INFO, "Trying register the first AdministratorTO.");
		service.registerFirstAdministrator();
		logger.log(Level.INFO, "Passed by the attempt to register the first AdministratorTO successfully.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

}
