package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.FileTempTO;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
@Repository
public class FileTempPO extends AppPersistency {

	public void clearTempFiles() {
		Calendar calendar =GregorianCalendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		
		Query query = this.getQuery(FileTempTO.class);
		query.setKeysOnly();
		query.setFilter(new Query.FilterPredicate("persistedDate", Query.FilterOperator.LESS_THAN_OR_EQUAL, yesterday));
		query.addSort("persistedDate", SortDirection.ASCENDING);
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		
		List<Entity> tempFiles = preparedQuery.asList(FetchOptions.Builder.withLimit(1000));
		List<Key> keysToDelete = new ArrayList<Key>();
		for (Entity tempFile : tempFiles) {
			keysToDelete.add(tempFile.getKey());
		}
		this.getDataStoreService().delete(keysToDelete);
	}
}
