package br.com.digitalsignature.utils;

public class Util {
	public static final String PARAMETER_STRING_NOT_OK = "The parameter string is illegal. Can't be empty or null";
	
	/**
	 * Método usado testa se a String é nula ou vazia, se for retorna false
	 * se não true
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean stringOk(String str){
		if(str != null && !str.trim().isEmpty()){
			return true;
		}
		else return false;
	}
}
