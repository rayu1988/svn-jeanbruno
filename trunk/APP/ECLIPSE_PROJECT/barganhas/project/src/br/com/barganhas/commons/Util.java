package br.com.barganhas.commons;

import java.util.Collection;

public class Util {

	public static boolean isStringOk(String str) {
		return str != null && !str.isEmpty();
	}
	
	public static boolean isCollectionOk(Collection<?> list){
		return ((list != null) && (!list.isEmpty()));
	}
	
	public static void validateParameterNull(Object... parameters) {
		for (Object param : parameters) {
			if (param == null) {
				throw new IllegalArgumentException("parameter " + parameters.getClass().getName() + " cann't be null");
			}
		}
	}
	
	public static String getNameAsJavaBean(Class<?> classe){
		String name = classe.getSimpleName();
		String firstLatter = name.substring(0, 1);
		String restOfTheName = name.substring(1, name.length());
		return firstLatter.toLowerCase() + restOfTheName;
	}
}
