/**
 * 
 */
package org.com.tatu.helper;

/**
 * @author Jean Bruno
 *
 */
public class StringHelper {

	/**
	 * Method to replace all key variable to it's properly value.
	 * Understand key variable as ${some_env_variable} inside a string text.
	 * @param stringBase
	 * @return
	 */
	public static String replaceEnvironmentVariable(String stringBase) {
		StringBuffer string = new StringBuffer();
		for (int i = 0; i < stringBase.length(); i++) {
			if (stringBase.charAt(i) == '$' && stringBase.charAt(++i) == '{') {
				StringBuffer key = new StringBuffer();
				try {
					for (++i; stringBase.charAt(i) != '}'; i++) {
						key.append(stringBase.charAt(i));
					}
					string.append(System.getProperty(key.toString()));
					i++;
				} catch (StringIndexOutOfBoundsException e) {
					throw new IllegalStateException("It has started read the variable " + key.toString() + " but the end limiter was not found.");
				}
				if (stringBase.length() == i) break;
			}
			string.append(stringBase.charAt(i));
		}
		return string.toString();
	}
	
}
