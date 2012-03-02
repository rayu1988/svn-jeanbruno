/**
 * 
 */
package br.com.db.gatekeeperbr.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.db.gatekeeperbr.common.Util;
import br.com.db.gatekeeperbr.exception.GatekeeperBRException;
import br.com.db.gatekeeperbr.exception.ParameterRequiredException;

/**
 * @author JNVE
 *
 */
public class JDBCConnection {
	
	private String driverClass;
	private String url;
	private String userName;
	private String password;
	private Connection conn = null;
	
	public JDBCConnection(String driverClass, String url, String userName, String password) {
		if (!Util.isStringOk(driverClass) || !Util.isStringOk(url) || !Util.isStringOk(userName) || !Util.isStringOk(password)) {
			throw new ParameterRequiredException("driverClass", "url", "userName", "password");
		}
		
		this.driverClass = driverClass;
		this.url = url;
		this.userName = userName;
		this.password = password;
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws GatekeeperBRException {
		try {
			return this.getConnection().prepareStatement(sql);
		} catch (SQLException e) {
			throw new GatekeeperBRException(e);
		}
	}
	
	private Connection getConnection() throws GatekeeperBRException {
		if (this.conn != null) {
			return conn;
		}
		try {
			Class.forName(this.driverClass);
			this.conn = DriverManager.getConnection(this.url, this.userName, this.password);
			return conn;
		} catch (ClassNotFoundException e) {
			throw new GatekeeperBRException(e);
		} catch (SQLException e) {
			throw new GatekeeperBRException(e);
		}
	}
	
	public void close() throws SQLException {
		if (this.conn != null) {
			this.conn.close();
		}
	}
	
	// getters and setters //
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
}