/**
 * 
 */
package br.com.datawatcher.entity;

import br.com.datawatcher.exception.InterfaceNotImplemented;

/**
 * @author carrefour
 *
 */
public class UserLogging extends DataLogging {

	private String				userName;

	public UserLogging(String userName, String decryptClass) {
		super(decryptClass);
		this.userName = userName;
	}
	
	public UserLogging(String userName) {
		super(null);
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getDecryptedUserName() throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterfaceNotImplemented {
		return this.getDecryptedDataLogging(this.userName);
	}
}
