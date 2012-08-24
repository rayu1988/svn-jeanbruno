package br.com.barganhas.business.services;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;

import br.com.barganhas.business.services.impl.AdministratorBO;

@SuppressWarnings("serial")
public class ServiceBusinessFactory implements Serializable {
	
	protected ApplicationContext applicationContext;
	
	private ServiceBusinessFactory() { }
	
	private static ServiceBusinessFactory INSTANCE = new ServiceBusinessFactory();
	
	public static ServiceBusinessFactory getInstance() {
		return INSTANCE;
	}
	
	public void registerApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public Administrator getAdministrator() {
		return (Administrator) this.applicationContext.getBean(AdministratorBO.BEAN_ALIAS);
	}
	
}
