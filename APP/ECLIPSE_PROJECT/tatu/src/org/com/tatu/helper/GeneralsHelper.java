package org.com.tatu.helper;

import java.util.Collection;

public class GeneralsHelper {

	public static boolean isBooleanTrue(Boolean b){
		if(b != null && b){
		   return true;
		}
		else return false;
	}
	
	public static boolean isStringOk(String str) {
        return str != null && !str.trim().isEmpty();
	}

	public static boolean isCollectionOk(Collection<?> collection) {
	        return collection != null && !collection.isEmpty();
	}

}
