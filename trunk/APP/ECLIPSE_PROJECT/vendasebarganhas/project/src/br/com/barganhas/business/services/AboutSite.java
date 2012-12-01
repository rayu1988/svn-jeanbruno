package br.com.barganhas.business.services;

import java.util.List;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.barganhas.business.entities.AboutSiteTO;

public interface AboutSite {

	List<AboutSiteTO> list();
	
	AboutSiteTO insert(AboutSiteTO aboutSite);

	AboutSiteTO consult(AboutSiteTO aboutSite) throws EntityNotFoundException;

	AboutSiteTO getDefault() throws EntityNotFoundException;

	AboutSiteTO turnDefault(AboutSiteTO aboutSite) throws EntityNotFoundException;
	
	AboutSiteTO save(AboutSiteTO aboutSite);

	void delete(AboutSiteTO aboutSite) throws EntityNotFoundException;
}
