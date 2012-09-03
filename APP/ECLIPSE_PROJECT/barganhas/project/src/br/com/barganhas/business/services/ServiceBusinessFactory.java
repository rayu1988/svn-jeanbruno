package br.com.barganhas.business.services;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;

import br.com.barganhas.business.services.impl.AdministratorBO;
import br.com.barganhas.business.services.impl.AdvertisementTypeBO;
import br.com.barganhas.business.services.impl.CategoryBO;
import br.com.barganhas.business.services.impl.SalesBO;
import br.com.barganhas.business.services.impl.UserAccountBO;

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
	
	public AdvertisementType getAdvertisementType() {
		return (AdvertisementType) this.applicationContext.getBean(AdvertisementTypeBO.BEAN_ALIAS);
	}
	
	public Sales getSales() {
		return (Sales) this.applicationContext.getBean(SalesBO.BEAN_ALIAS);
	}
	
	public Category getCategory() {
		return (Category) this.applicationContext.getBean(CategoryBO.BEAN_ALIAS);
	}
	
	public UserAccount getUserAccount() {
		return (UserAccount) this.applicationContext.getBean(UserAccountBO.BEAN_ALIAS);
	}
	
}
