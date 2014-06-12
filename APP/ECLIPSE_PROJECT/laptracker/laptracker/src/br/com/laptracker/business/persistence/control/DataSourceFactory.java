/**
 * 
 */
package br.com.laptracker.business.persistence.control;

/**
 * @author villjea
 *
 */
public class DataSourceFactory {

	private static DataSourceFactory 		INSTANCE;
	private String							url;
	private String							user;
	private String							password;
	private String							driverClassName;
	
	private DataSourceFactory() {}
	
	public static DataSourceFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataSourceFactory();
		}
		return INSTANCE;
	}

	// GETTERS AND SETTERS //
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	
}
