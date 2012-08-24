package br.com.barganhas.web.beans;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.barganhas.business.services.ServiceBusinessFactory;

public class AppLoadListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ServiceBusinessFactory.getInstance().registerApplicationContext(webApplicationContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

}
