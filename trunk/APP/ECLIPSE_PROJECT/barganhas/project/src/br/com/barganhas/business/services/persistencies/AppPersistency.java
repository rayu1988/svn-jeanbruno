package br.com.barganhas.business.services.persistencies;

import java.io.Serializable;
import java.util.List;

import br.com.barganhas.business.entities.TransferObject;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.AnnotationUtils;
import br.com.barganhas.commons.EntityPropertyPojo;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public abstract class AppPersistency implements Serializable {

	private static final Key 			TRANSFER_OBJECT_ANCESTOR = KeyFactory.createKey(TransferObject.class.getName(), TransferObject.class.getName());
	
	protected DatastoreService getDataStoreService() {
		return DatastoreServiceFactory.getDatastoreService();
	}
	
	protected <T extends TransferObject> Key getKey(T transferObject) {
		Util.validateParameterNull(transferObject);
		try {
			if (Util.isStringOk(transferObject.getKey())) { // save an edit/update
				return KeyFactory.stringToKey(transferObject.getKey());
			} else { // save an insert
				Long nextId = this.getNextId(transferObject);
				this.getDataStoreService().allocateIds(transferObject.getClass().getName(), nextId);
				return KeyFactory.createKey(TRANSFER_OBJECT_ANCESTOR, transferObject.getClass().getName(), nextId);
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	private <T extends TransferObject> Long getNextId(T transferObject) {
		Long nextId = (long) 1;
		
		Query query = this.getQuery(transferObject);
		query.addProjection(new PropertyProjection(AnnotationUtils.getIdFieldStringName(transferObject), Long.class));
		query.addSort(AnnotationUtils.getIdFieldStringName(transferObject), SortDirection.DESCENDING);
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		List<Entity> listTmp = preparedQuery.asList(FetchOptions.Builder.withLimit(1));
		if (Util.isCollectionOk(listTmp)) {
			nextId = ((Long) listTmp.get(0).getProperty(AnnotationUtils.getIdFieldStringName(transferObject))) + 1;
		}
		
		return nextId;
	}
	
	/**
	 * Method used to get an Entity instance and if the current transferObject passed as parameter already exists in the database, the returned instance is this, from database
	 * otherwise if the transferObject exist not in the database, the instance returned is an Entity prepared (with its Key) to be persisted in the database.
	 * @param transferObject
	 * @return
	 */
	protected <T extends TransferObject> Entity getEntity(T transferObject) {
		try {
			Key key = this.getKey(transferObject);
			try {
				return this.getDataStoreService().get(key);
			} catch (EntityNotFoundException e) {
				Entity entity = new Entity(key);
				transferObject.setId(key.getId());
				return entity;
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	protected <T extends TransferObject> void deleteEntity(T transferObject) {
		this.getDataStoreService().delete(this.getKey(transferObject));
	}
	
	/**
	 * Get a query object to retrieve data from database.
	 * @param transferObject
	 * @return
	 */
	protected <T extends TransferObject> Query getQuery(T transferObject) {
		Query query = new Query(transferObject.getClass().getName());
		query.setAncestor(TRANSFER_OBJECT_ANCESTOR);
		return query;
	}
	
	protected <T extends TransferObject> PreparedQuery getSimplePreparedQuery(T transferObject) {
		return this.getDataStoreService().prepare(this.getQuery(transferObject));
	}
	
	/**
	 * Method used to persist the current transferObject in the database.
	 * If the transferObject passed as parameter already exists in the database its property will be updated, otherwise the object will be inserted.
	 * 
	 * @param transferObject
	 */
	protected void persist(TransferObject transferObject) {
		Entity entity = null;
		if (Util.isStringOk(transferObject.getKey())) {
			entity = new Entity(this.getKey(transferObject));
		} else {
			entity = this.getEntity(transferObject);
		}
		
		try {
			List<EntityPropertyPojo> listProperties = AnnotationUtils.getEntityProperties(transferObject);
			for (EntityPropertyPojo entityProperty : listProperties) {
				if (entityProperty.isUnindexed()) {
					entity.setUnindexedProperty(entityProperty.getKey(), entityProperty.getValue());
				} else {
					entity.setProperty(entityProperty.getKey(), entityProperty.getValue());
				}
			}
			this.getDataStoreService().put(entity);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
}
