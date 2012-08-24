package br.com.barganhas.commons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.barganhas.business.entities.TransferObject;
import br.com.barganhas.business.entities.annotations.IdField;
import br.com.barganhas.business.entities.annotations.IdFieldComparable;

public class Util {

	public static boolean isStringOk(String str) {
		return str != null && !str.isEmpty();
	}
	
	public static List<IdFieldComparable> getIdFields(TransferObject transferObject) {
		List<IdFieldComparable> fieldsToReturn = new ArrayList<IdFieldComparable>();
		Field[] allFields = transferObject.getClass().getFields();
		for (Field field : allFields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(IdField.class)) {
				IdField idField = field.getAnnotation(IdField.class);
				int sorting = idField.sorting();
				
				if (fieldsToReturn.get(sorting) != null) {
					throw new IllegalStateException("be sure the fields are sorted properly, it seems have more the on sort to value :" + sorting);
				}
				
				fieldsToReturn.add(sorting, new IdFieldComparable(sorting, field));
			}
		}
		Collections.sort(fieldsToReturn);
		return fieldsToReturn;
	}
	
}
