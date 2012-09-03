package br.com.barganhas.commons;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import br.com.barganhas.business.entities.TransferObject;
import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.PropertyField;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class AnnotationUtils {

	/**
	 * This method get the Field IdField annotated in the class passed as parameter, but as well validate if it has one and nothing more than one IdField annotated
	 * @param transferObject
	 * @return
	 */
	public static <T extends TransferObject> Field getIdField(Class<T> transferObject) {
		Field fieldToReturn = null;
		
		Class<?> currentClass = null;
		do {
			currentClass = currentClass == null ? transferObject : currentClass.getSuperclass();
			Field[] allFields = currentClass.getDeclaredFields();
			for (Field field : allFields) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(IdField.class)) {
					if (fieldToReturn != null) {
						throw new IllegalStateException("The class " + currentClass.getName() + " has more than 1 IdField, and itsn't allowed");
					} else if (!field.getType().equals(Long.class)) {
						throw new IllegalStateException("The IdField must be as Long type in the class " + currentClass.getName());
					}
					fieldToReturn = field;
				}
			}
		} while (!currentClass.equals(TransferObject.class));
		
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
		Class<?> currentClass = null;
		do {
			currentClass = currentClass == null ? transferObject.getClass() : currentClass.getSuperclass();
			Field[] allFields = currentClass.getDeclaredFields();
			for (Field field : allFields) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(PropertyField.class)) {
					PropertyField propertyField = field.getAnnotation(PropertyField.class);
					Object propertyValue = field.get(transferObject);
					if (propertyField.notNull() && propertyValue == null) {
						throw new IllegalStateException("The field " + field.getName() + "in the class " + transferObject.getClass().getName() +
								" is null and its annotation indicates that this cann't be null");
					}
					if (!propertyField.allowEmpty()) {
						if (propertyValue instanceof String && !Util.isStringOk((String)propertyValue)) {
							throw new IllegalStateException("The field " + field.getName() + " of the type java.lang.String cann't be empty");
						}
					}
					if (field.getType().isEnum()) { //if the current field is an enumerator, let's save the value as String
						propertyValue = propertyValue.toString();
					}
					listToReturn.add(new EntityPropertyPojo(field.getName(), propertyValue, propertyField.unindexed()));
				}
			}
		} while (!currentClass.equals(TransferObject.class));
		return listToReturn;
	}
	
	/**
	 * Method to build an TransferObject from an entity.
	 * 
	 * @param targetTO
	 * @param entity
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends TransferObject> T getTransferObjectFromEntity(Class<T> targetTO, Entity entity) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, SecurityException, NoSuchMethodException {
		Util.validateParameterNull(targetTO, entity);
		Constructor<T> constructor = targetTO.getConstructor(Key.class);
		T transferObject = constructor.newInstance(entity.getKey());
		Class<?> currentClass = null;
		do {
			currentClass = currentClass == null ? currentClass = targetTO : currentClass.getSuperclass();
			Field[] allFields = currentClass.getDeclaredFields();
			for (Field field : allFields) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(PropertyField.class)) {
					Object propertyValue = entity.getProperty(field.getName());
					if (propertyValue != null && field.getType().isEnum()) { //if the current field is an enumerator, let's get the Enum value from a String
						propertyValue = Enum.valueOf((Class<Enum>) field.getType(), propertyValue.toString());
					}
					field.set(transferObject, propertyValue);
				}
			}
		} while (!currentClass.equals(TransferObject.class));
		return transferObject;
	}
}
