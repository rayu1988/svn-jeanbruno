package org.com.tatu.helper;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralsHelper {

	public static boolean isBooleanTrue(Boolean b) {
		if(b != null && b){
		   return true;
		}
		else return false;
	}
	
	public static boolean isBooleanTrue(String str) {
		return isStringOk(str) && (str.equals("true") || str.equals("1") || str.equals("yes") || str.equals("y"));
	}
	
	public static boolean isStringOk(String str) {
        return str != null && !str.trim().isEmpty();
	}

	public static boolean isCollectionOk(Collection<?> collection) {
	        return collection != null && !collection.isEmpty();
	}
	
	public static boolean isEmailOk(String email) {
		Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = pattern.matcher(email);
		return m.matches();
	}

}
