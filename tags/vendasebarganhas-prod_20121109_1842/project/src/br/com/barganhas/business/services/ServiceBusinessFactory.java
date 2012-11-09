package br.com.barganhas.business.services;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;

import br.com.barganhas.business.services.impl.AboutSiteBO;
import br.com.barganhas.business.services.impl.AdministratorBO;
import br.com.barganhas.business.services.impl.AdvertisementBO;
import br.com.barganhas.business.services.impl.AdvertisementPictureBO;
import br.com.barganhas.business.services.impl.AdvertisementTypeBO;
import br.com.barganhas.business.services.impl.CategoryBO;
import br.com.barganhas.business.services.impl.CityBO;
import br.com.barganhas.business.services.impl.FileBO;
import br.com.barganhas.business.services.impl.FileTempBO;
import br.com.barganhas.business.services.impl.MailBO;
import br.com.barganhas.business.services.impl.SalesBO;
import br.com.barganhas.business.services.impl.StateBO;
import br.com.barganhas.business.services.impl.UseTermBO;
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
	
	public State getState() {
		return (State) this.applicationContext.getBean(StateBO.BEAN_ALIAS);
	}
	
	public City getCity() {
		return (City) this.applicationContext.getBean(CityBO.BEAN_ALIAS);
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
	
	public File getFile() {
		return (File) this.applicationContext.getBean(FileBO.BEAN_ALIAS);
	}
	
	public FileTemp getFileTemp() {
		return (FileTemp) this.applicationContext.getBean(FileTempBO.BEAN_ALIAS);
	}
	
	public Advertisement getAdvertisement() {
		return (Advertisement) this.applicationContext.getBean(AdvertisementBO.BEAN_ALIAS);
	}
	
	public AdvertisementPicture getAdvertisementPicture() {
		return (AdvertisementPicture) this.applicationContext.getBean(AdvertisementPictureBO.BEAN_ALIAS);
	}
	
	public Mail getMail() {
		return (Mail) this.applicationContext.getBean(MailBO.BEAN_ALIAS);
	}
	
	public UseTerm getUseTerm() {
		return (UseTerm) this.applicationContext.getBean(UseTermBO.BEAN_ALIAS);
	}
	
	public AboutSite getAboutSite() {
		return (AboutSite) this.applicationContext.getBean(AboutSiteBO.BEAN_ALIAS);
	}
	
}
