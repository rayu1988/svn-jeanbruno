package br.com.barganhas.business.services.impl;

import java.util.List;

import org.com.tatu.helper.parameter.ParameterWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.AboutSiteTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.AboutSite;
import br.com.barganhas.business.services.persistencies.AboutSitePO;

@Service("aboutSiteBO")
public class AboutSiteBO implements AboutSite {

	public static final String						BEAN_ALIAS = "aboutSiteBO";

	@Autowired
	private AboutSitePO								persistencyLayer;
	
	@Override
	@Transactional(readOnly = true)
	public List<AboutSiteTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AboutSiteTO insert(AboutSiteTO aboutSite) {
		return this.persistencyLayer.insert(aboutSite);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AboutSiteTO consult(AboutSiteTO aboutSite) {
		return this.persistencyLayer.consult(aboutSite);
	}
	
	@Override
	@Transactional(readOnly = true)
	public AboutSiteTO getDefault() {
		return this.persistencyLayer.getDefault();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AboutSiteTO turnDefault(AboutSiteTO aboutSite) {
		AboutSiteTO currentDefault = this.persistencyLayer.getDefault();
		this.persistencyLayer.getHibernateDao().update(currentDefault, ParameterWrapper.instance("isDefault", Boolean.FALSE));
		
		this.persistencyLayer.getHibernateDao().update(aboutSite, ParameterWrapper.instance("isDefault", Boolean.TRUE));
		
		return aboutSite;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public AboutSiteTO save(AboutSiteTO aboutSite) {
		return this.persistencyLayer.save(aboutSite);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(AboutSiteTO aboutSite) {
		aboutSite = this.consult(aboutSite);
		
		if (aboutSite.getIsDefault()) {
			throw new AppException("aboutSiteDefaultCantBeDeleted");
		}
		
		this.persistencyLayer.delete(aboutSite);
	}
}
