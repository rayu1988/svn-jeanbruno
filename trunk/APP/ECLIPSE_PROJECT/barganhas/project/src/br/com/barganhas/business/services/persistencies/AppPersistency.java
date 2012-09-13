package br.com.barganhas.business.services.persistencies;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.google.appengine.api.datastore.Transaction;

@SuppressWarnings("serial")
public abstract class AppPersistency implements Serializable {

	private static final Logger 		logger = Logger.getLogger(AppPersistency.class.getCanonicalName());
	private static final Key 			TRANSFER_OBJECT_ANCESTOR = KeyFactory.createKey(TransferObject.class.getName(), TransferObject.class.getName());
	
	protected DatastoreService getDataStoreService() {
		return DatastoreServiceFactory.getDatastoreService();
	}
	
	public Transaction beginTransaction() {
		return this.getDataStoreService().beginTransaction();
	}
	
	/**
	 * Method to get the correct ancestor.
	 * If the parameter passed to this method is null the defeult ancestor is returned that is the key of Transfer Object (class).
	 * Otherwise, the value returned is the key of the ancestor passed as parameter.
	 * 
	 * @param ancestor
	 * @return
	 */
	private <T extends TransferObject> Key getAncestor(T ancestor) {
		logger.log(Level.INFO, "Starting the getting key.");
		Key key = ancestor != null && Util.isStringOk(ancestor.getKeyAsString()) ? KeyFactory.stringToKey(ancestor.getKeyAsString()) : TRANSFER_OBJECT_ANCESTOR;
		logger.log(Level.INFO, "The returned key found was to Kind: " + key.getKind());
		return key;
	}
	
	/**
	 * Method to get the default key of the transferObject passed as parameter, that is the key of the Transfer Object (Class).
	 * @param transferObject
	 * @return
	 */
	protected <T extends TransferObject> Key getKey(T transferObject) {
		return this.getKey(transferObject, null);
	}
	
	/**
	 * Method to get the key of the transferObject passed as parameter and that have the ancestor passed as parameter.
	 * The ancestor passed as parameter can be null, and if it's truth the key returned is the default key that is of the Transfer Object (Class).
	 * 
	 * @param transferObject
	 * @param ancestorTO
	 * @return
	 */
	protected <T extends TransferObject> Key getKey(T transferObject, T ancestorTO) {
		Util.validateParameterNull(transferObject);
		try {
			if (Util.isStringOk(transferObject.getKeyAsString())) { // save an edit/update
				return KeyFactory.stringToKey(transferObject.getKeyAsString());
			} else { // save an insert
				Key ancestor = this.getAncestor(ancestorTO);
				Long nextId = this.getNextId(transferObject, ancestorTO);
				this.getDataStoreService().allocateIds(ancestor, transferObject.getClass().getName(), nextId);
				return KeyFactory.createKey(ancestor, transferObject.getClass().getName(), nextId);
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	/**
	 * Method to get the next id to transferObject passed as parameter that has the ancestorTO (passed as parameter) as ancestor.
	 * 
	 * @param transferObject
	 * @param ancestorTO
	 * @return
	 */
	private <T extends TransferObject> Long getNextId(T transferObject, T ancestorTO) {
		Long nextId = (long) 1;
		
		Query query = this.getQuery(transferObject.getClass(), ancestorTO);
		query.addProjection(new PropertyProjection(AnnotationUtils.getIdFieldStringName(transferObject.getClass()), Long.class));
		query.addSort(AnnotationUtils.getIdFieldStringName(transferObject.getClass()), SortDirection.DESCENDING);
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		List<Entity> listTmp = preparedQuery.asList(FetchOptions.Builder.withLimit(1));
		if (Util.isCollectionOk(listTmp)) {
			nextId = ((Long) listTmp.get(0).getProperty(AnnotationUtils.getIdFieldStringName(transferObject.getClass()))) + 1;
		}
		
		return nextId;
	}
	
	/**
	 * Get a query object to retrieve data from database, this query object should be used to the targetTO passed as parameter.
	 * It's so important note that the query object returned by this method has the default ancestor, that is the key of Transfer Object (Class).
	 * 
	 * @param targetTO
	 * @return
	 */
	protected <T extends TransferObject> Query getQuery(Class<T> targetTO) {
		return this.getQuery(targetTO, null);
	}
	
	/**
	 * Get a query object to retrieve data from database, this query object should be used to the targetTO passed as parameter and as well 
	 * using the ancestorTO as ancestor passed as parameter.
	 * 
	 * @param targetTO
	 * @param ancestorTO
	 * @return
	 */
	protected <T extends TransferObject> Query getQuery(Class<? extends TransferObject> targetTO, T ancestorTO) {
		Query query = new Query(targetTO.getName(), this.getAncestor(ancestorTO));
		return query;
	}
	
	/**
	 * Method used to get an Entity instance and if the current transferObject passed as parameter already exists in the database will be returnd this method
	 * sycronized from database otherwise if the transferObject exist not in the database, the instance returned is an Entity prepared (with its Key) to be 
	 * persisted in the database.
	 * 
	 * @param transferObject
	 * @return
	 */
	protected <T extends TransferObject> Entity getEntity(T transferObject) {
		return this.getEntity(transferObject, null);
	}
	
	/**
	 * Method used to get an Entity instance and if the current transferObject passed as parameter already exists in the database will be returnd this method
	 * sycronized from database otherwise if the transferObject exist not in the database, the instance returned is an Entity prepared (with its Key) to be 
	 * persisted in the database.
	 * 
	 * @param transferObject
	 * @param ancestorTO
	 * @return
	 */
	protected <T extends TransferObject> Entity getEntity(T transferObject, T ancestorTO) {
		try {
			Key key = this.getKey(transferObject, ancestorTO);
			try {
				return this.getDataStoreService().get(key);
			} catch (EntityNotFoundException e) {
				//if an EntityNotFoundException was launched it means that a new entity must be created and later will be persisted.
				Entity entity = new Entity(key);
				transferObject.setId(key.getId());
				return entity;
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	/**
	 * Method used to get the count number of objects persisted of the Transfer Object's type passed as parameter.
	 * It's so important note that the count number returned by this method has the default ancestor, that is the key of Transfer Object (Class).
	 * 
	 * @param targetTO
	 * @return
	 */
	public <T extends TransferObject> int count(Class<T> targetTO) {
		return this.count(targetTO, null);
	}
	
	/**
	 * Method used to get the count number of objects persisted of the Transfer Object's type passed as parameter and as well the ancestorTO type passed as parameter.
	 * 
	 * @param targetTO
	 * @return
	 */
	public <T extends TransferObject> int count(Class<T> targetTO, T ancestorTO) {
		logger.log(Level.INFO, "Initializing the process of count to the Kind: " + targetTO.getCanonicalName());
		
		logger.log(Level.INFO, "Getting the Query object.");
		Query query = this.getQuery(targetTO, ancestorTO);
		logger.log(Level.INFO, "Adding the projection.");
		query.addProjection(new PropertyProjection(AnnotationUtils.getIdFieldStringName(targetTO), Long.class));
		logger.log(Level.INFO, "Getting the PreparedQuery.");
		PreparedQuery preparedQuery = this.getDataStoreService().prepare(query);
		logger.log(Level.INFO, "Returning the count entities.");
		return preparedQuery.countEntities(FetchOptions.Builder.withDefaults());
	}
	
	/**
	 * Get a syncronized TransferObject from the database using as base the 'id' property from the TransferObject passed as parameter. 
	 * @param transferObject
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	protected <T extends TransferObject> T consultEntityById(T transferObject) {
		return this.consultEntityById(transferObject, null);
	}

	/**
	 * Get a syncronized TransferObject from the database using as base the 'id' property from the TransferObject passed as parameter.
	 * 
	 * @param transferObject
	 * @param ancestorTO
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	protected <T extends TransferObject> T consultEntityById(T transferObject, T ancestorTO) {
		Query query = this.getQuery(transferObject.getClass(), ancestorTO);
		query.setFilter(new Query.FilterPredicate(AnnotationUtils.getIdFieldStringName(transferObject.getClass()), Query.FilterOperator.EQUAL, transferObject.getId()));
		Entity entity = this.getDataStoreService().prepare(query).asSingleEntity();
		
		return (T) AnnotationUtils.getTransferObjectFromEntity(transferObject.getClass(), entity);
	}
	
	/**
	 * Method to get a simple PreparedQuery object without filters.
	 * It's so important not that the targetTO Transfer Object passed as parameter should has as ancestor the Transfer Object (Class).
	 * 
	 * @param targetTO
	 * @return
	 */
	protected PreparedQuery getSimplePreparedQuery(Class<? extends TransferObject> targetTO) {
		return this.getSimplePreparedQuery(targetTO, null);
	}
	
	/**
	 * Method to get a simple PreparedQuery object without filters.
	 * The returned PreparedQuery is to transferTO Class passed as parameter as well to ancestorTO ancestor passed as parameter.
	 * 
	 * @param targetTO
	 * @param ancestorTO
	 * @return
	 */
	protected PreparedQuery getSimplePreparedQuery(Class<? extends TransferObject> targetTO, TransferObject ancestorTO) {
		return this.getDataStoreService().prepare(this.getQuery(targetTO, ancestorTO));
	}

	/**
	 * Method used to delete the transferObject passed as parameter.
	 * 
	 * @param transferObject
	 */
	protected <T extends TransferObject> void deleteEntity(T transferObject) {
		Util.validateParameterNull(transferObject);
		if (!Util.isStringOk(transferObject.getKeyAsString())) {
			throw new IllegalStateException("The transferObject passed as parameter must has its key property as a String valid value.");
		}
		this.getDataStoreService().delete(this.getKey(transferObject));
	}
	
	/**
	 * Method used to persist the current transferObject in the database.
	 * If the transferObject passed as parameter already exists in the database its property will be updated, otherwise the object will be inserted.
	 * 
	 * @param transferObject
	 */
	protected <T extends TransferObject> T persist(T transferObject) {
		return this.persist(transferObject, null);
	}
	
	/**
	 * Method used to persist the current transferObject in the database.
	 * 
	 * @param transferObject
	 * @param ancestorTO
	 */
	@SuppressWarnings("unchecked")
	protected <T extends TransferObject> T persist(T transferObject, TransferObject ancestorTO) {
		Entity entity = null;
		if (Util.isStringOk(transferObject.getKeyAsString())) {
			entity = new Entity(this.getKey(transferObject));
		} else {
			entity = this.getEntity(transferObject, ancestorTO);
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
			
			return (T) AnnotationUtils.getTransferObjectFromEntity(transferObject.getClass(), entity);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
}
