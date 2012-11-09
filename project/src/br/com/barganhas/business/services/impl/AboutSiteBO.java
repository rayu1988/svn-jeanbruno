package br.com.barganhas.business.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.AboutSiteTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.AboutSite;
import br.com.barganhas.business.services.persistencies.AboutSitePO;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Transaction;

@Service("aboutSiteBO")
public class AboutSiteBO implements AboutSite {

	public static final String						BEAN_ALIAS = "aboutSiteBO";

	@Autowired
	private AboutSitePO								persistencyLayer;
	
	@Override
	public List<AboutSiteTO> list() {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			List<AboutSiteTO> listReturn = this.persistencyLayer.list();
			
			transaction.commit();
			return listReturn;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AboutSiteTO insert(AboutSiteTO aboutSite) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			aboutSite = this.persistencyLayer.insert(aboutSite);
			
			transaction.commit();
			return aboutSite;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AboutSiteTO consult(AboutSiteTO aboutSite) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			aboutSite = this.persistencyLayer.consult(aboutSite);
			transaction.commit();
			return aboutSite;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
	
	@Override
	public AboutSiteTO getDefault() throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			AboutSiteTO aboutSite = this.persistencyLayer.getDefault();
			transaction.commit();
			return aboutSite;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public AboutSiteTO turnDefault(AboutSiteTO aboutSite) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			try {
				AboutSiteTO currentDefault = this.persistencyLayer.getDefault();
				currentDefault.setDefaultAboutSite(false);
				this.save(currentDefault);
			} catch (AppException e) { } 
			
			aboutSite.setDefaultAboutSite(true);
			aboutSite = this.save(aboutSite);
			
			transaction.commit();
			return aboutSite;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}
	
	@Override
	public AboutSiteTO save(AboutSiteTO aboutSite) {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			aboutSite = this.persistencyLayer.save(aboutSite);
			
			transaction.commit();
			return aboutSite;
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}

	@Override
	public void delete(AboutSiteTO aboutSite) throws EntityNotFoundException {
		Transaction transaction = this.persistencyLayer.beginTransaction();
		try {
			aboutSite = this.consult(aboutSite);
			
			if (aboutSite.getDefaultAboutSite()) {
				throw new AppException("aboutSiteDefaultCantBeDeleted");
			}
			
			this.persistencyLayer.delete(aboutSite);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
		    }
		}
	}
}
