/**
 * 
 */
package br.com.datawatcher.entity;

/**
 * @author carrefour
 *
 */
public class JdbcConnection {

	private String				url;
	private String				driverClassName;
	private UserLogging			userLogging;
	private PasswordLogging		passwordLogging;
	
	// GETTERS AND SETTERS //
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public UserLogging getUserLogging() {
		return userLogging;
	}
	public void setUserLogging(UserLogging userLogging) {
		this.userLogging = userLogging;
	}
	public PasswordLogging getPasswordLogging() {
		return passwordLogging;
	}
	public void setPasswordLogging(PasswordLogging passwordLogging) {
		this.passwordLogging = passwordLogging;
	}
}
