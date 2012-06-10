/**
 * 
 */
package br.com.datawatcher.entity;

import br.com.datawatcher.exception.InterfaceNotImplemented;

/**
 * @author carrefour
 *
 */
public class PasswordLogging extends DataLogging {

	private String				password;
	
	public PasswordLogging(String password, String decryptClass) {
		super(decryptClass);
		this.password = password;
	}
	
	public PasswordLogging(String password) {
		super(null);
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getDecryptedPassword() throws InterfaceNotImplemented, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return this.getDecryptedDataLogging(password);
	}
}
