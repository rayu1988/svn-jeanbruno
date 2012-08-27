package br.com.barganhas.commons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;

import br.com.barganhas.business.entities.TransferObject;
import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

public class AnnotationUtils {

	/**
	 * This method get the Field IdField annotated in the class passed as parameter, but as well validate if it has one and nothing more than one IdField annotated
	 * @param transferObject
	 * @return
	 */
	public static <T extends TransferObject> Field getIdField(Class<T> transferObject) {
		Field fieldToReturn = null;
		Field[] allFields = transferObject.getDeclaredFields();
		for (Field field : allFields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(IdField.class)) {
				if (fieldToReturn != null) {
					throw new IllegalStateException("The class " + transferObject.getName() + " has more than 1 IdField, and itsn't allowed");
				} else if (!field.getType().equals(Long.class)) {
					throw new IllegalStateException("The IdField must be as Long type in the class " + transferObject.getName());
				}
				fieldToReturn = field;
			}
		}
		
		if (fieldToReturn == null) {
			throw new IllegalStateException("There's no IdField present in the class " + transferObject.getName() + ", and this field is required");
		}
		
		return fieldToReturn;
	}

	/**
	 * Method to get the string name of the IdField in the class passed as parameter
	 * @param transferObject Class
	 * @return
	 */
	public static <T extends TransferObject> String getIdFieldStringName(Class<T> transferObject) {
		Field field = getIdField(transferObject);
		return field.getName();
	}
	
	/**
	 * Method to get the string name of the IdField in the class passed as parameter
	 * @param transferObject Instance
	 * @return
	 */
	public static <T extends TransferObject> String getIdFieldStringName(T transferObject) {
		return getIdFieldStringName(transferObject.getClass());
	}
	
	/**
	 * Method to build a list with the properties of a transferObject.
	 * These properties will be used to build and persist an entity in the after time.
	 * 
	 * @param transferObject
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T extends TransferObject> List<EntityPropertyPojo> getEntityProperties(T transferObject) throws IllegalArgumentException, IllegalAccessException {
		List<EntityPropertyPojo> listToReturn = new ArrayList<EntityPropertyPojo>();
		Field[] allFields = transferObject.getClass().getDeclaredFields();
		for (Field field : allFields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(PropertyField.class)) {
				PropertyField propertyField = field.getAnnotation(PropertyField.class);
				Object proertyValue = field.get(transferObject);
				if (propertyField.notNull() && proertyValue == null) {
					throw new IllegalStateException("The field " + field.getName() + "in the class " + transferObject.getClass().getName() +
							" is null and its annotation indicates that this cann't be null");
				}
				if (!propertyField.allowEmpty()) {
					if (proertyValue instanceof String && !Util.isStringOk((String)proertyValue)) {
						throw new IllegalStateException("The field " + field.getName() + " of the type java.lang.String cann't be empty");
					}
				}
				listToReturn.add(new EntityPropertyPojo(field.getName(), proertyValue, propertyField.unindexed()));
			}
		}
		return listToReturn;
	}

	/**
	 * Method to build an TransferObject from an entity.
	 * 
	 * @param transferObject
	 * @param entity
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T extends TransferObject> T getTransferObjectFromEntity(T transferObject, Entity entity) throws IllegalArgumentException, IllegalAccessException {
		Util.validateParameterNull(transferObject, entity);

		Field[] allFields = transferObject.getClass().getDeclaredFields();
		for (Field field : allFields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(PropertyField.class)) {
				field.set(transferObject, entity.getProperty(field.getName()));
			}
		}
		
		return transferObject;
	}
}
