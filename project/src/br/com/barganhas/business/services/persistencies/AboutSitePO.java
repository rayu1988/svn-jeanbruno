package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AboutSiteTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class AboutSitePO extends AppPersistencyManagement {
	
	@SuppressWarnings("unchecked")
	public List<AboutSiteTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ABOUT_SITE.id, ABOUT_SITE.title, ABOUT_SITE.isDefault from ");
		hql.append(AboutSiteTO.class.getName()).append(" ABOUT_SITE ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	public AboutSiteTO insert(AboutSiteTO aboutSite) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(ABOUT_SITE.id) from ").append(AboutSiteTO.class.getName()).append(" ABOUT_SITE where ABOUT_SITE.isDefault is true ");
		
		boolean alreadyExists = GeneralsHelper.isBooleanTrue(this.getHibernateDao().queryCount(hql.toString()) > 0);
		aboutSite.setIsDefault(!alreadyExists);
		
		this.getHibernateDao().insert(aboutSite);
		
		return aboutSite;
	}
	
	public AboutSiteTO save(AboutSiteTO aboutSite) {
		this.getHibernateDao().update(aboutSite);
		return aboutSite;
	}

	public AboutSiteTO consult(AboutSiteTO aboutSite) {
		return this.getHibernateDao().consult(aboutSite);
	}

	public AboutSiteTO getDefault() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ABOUT_SITE.id, ABOUT_SITE.title, ABOUT_SITE.isDefault from ").append(AboutSiteTO.class.getName()).append(" ABOUT_SITE ");
		hql.append(" where ABOUT_SITE.isDefault is true ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		AboutSiteTO aboutSite = (AboutSiteTO) query.uniqueResult();
		
		if (aboutSite == null) {
			throw new AppException("aboutSiteDefaultNotFound");
		}
		
		return aboutSite;
	}
	
	public void delete(AboutSiteTO aboutSite) {
		this.getHibernateDao().delete(aboutSite);
	}
}
