package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AboutSiteTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
@Repository
public class AboutSitePO extends AppPersistency {

	public List<AboutSiteTO> list() {
		List<Entity> entities = this.getSimplePreparedQuery(AboutSiteTO.class).asList(FetchOptions.Builder.withDefaults());
		
		List<AboutSiteTO> listReturn = new ArrayList<AboutSiteTO>();
		for (Entity entity : entities) {
			listReturn.add(AnnotationUtils.getTransferObjectFromEntity(AboutSiteTO.class, entity));
		}
		
		return listReturn;
	}
	
	public AboutSiteTO insert(AboutSiteTO aboutSite) {
		boolean alreadyExists = GeneralsHelper.isCollectionOk(this.getSimplePreparedQuery(AboutSiteTO.class).asList(FetchOptions.Builder.withLimit(1)));
		aboutSite.setDefaultAboutSite(!alreadyExists);
		return this.persist(aboutSite);
	}
	
	public AboutSiteTO save(AboutSiteTO aboutSite) {
		return this.persist(aboutSite);
	}

	public AboutSiteTO consult(AboutSiteTO aboutSite) throws EntityNotFoundException {
		return this.consultByKey(aboutSite);
	}

	public AboutSiteTO getDefault() throws EntityNotFoundException {
		Query query = this.getQuery(AboutSiteTO.class);
		query.setFilter(new Query.FilterPredicate("defaultAboutSite", Query.FilterOperator.EQUAL, true));
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		Entity entity = preparedQuery.asSingleEntity();
		
		if (entity == null) {
			throw new AppException("aboutSiteDefaultNotFound");
		}
		
		return AnnotationUtils.getTransferObjectFromEntity(AboutSiteTO.class, entity);
	}
	
	public void delete(AboutSiteTO aboutSite) {
		this.deleteEntity(aboutSite);
	}
}
