package br.com.barganhas.business.services;

import java.util.List;

import br.com.barganhas.business.entities.AboutSiteTO;

public interface AboutSite {

	List<AboutSiteTO> list();
	
	AboutSiteTO insert(AboutSiteTO aboutSite);

	AboutSiteTO consult(AboutSiteTO aboutSite);

	AboutSiteTO getDefault();

	AboutSiteTO turnDefault(AboutSiteTO aboutSite);
	
	AboutSiteTO save(AboutSiteTO aboutSite);

	void delete(AboutSiteTO aboutSite);
}
