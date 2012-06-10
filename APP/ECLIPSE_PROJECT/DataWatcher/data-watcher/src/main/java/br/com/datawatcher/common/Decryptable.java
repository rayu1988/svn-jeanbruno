/**
 * 
 */
package br.com.datawatcher.common;

/**
 * @author carrefour
 *
 */
public interface Decryptable {

	/**
	 * Used to decryt a String.
	 * @param encryptedString
	 * @return
	 */
	String decrypt(String encryptedString);
}
