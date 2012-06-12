/**
 * 
 */
package br.com.datawatcher.common;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import br.com.datawatcher.exception.InterfaceNotImplemented;

/**
 * @author villjea
 *
 */
public class JdbcConnection {
	
	private Connection con = null;
	private String user;
	private String password;
	private String url;
	private String driverclass;
	private PreparedStatement stmt;
	
	/**
	 * 
	 * @param user
	 * @param password
	 * @param url
	 * @param driverclass
	 */
	public JdbcConnection(String user, String password, String url, String driverclass) {
		if (!Util.isStringOk(user) || !Util.isStringOk(password) || !Util.isStringOk(url) || !Util.isStringOk(driverclass)) {
			throw new IllegalArgumentException("The argument can't be empty or null. Parameters; user, password, url, driverclass");
		}
		try {
			this.user = user.trim();
			this.password = password.trim();
			this.url = url.trim();
			this.driverclass = driverclass.trim();
		
			Class.forName(this.driverclass);
			this.con = DriverManager.getConnection(this.url, this.user, this.password);
		} catch (Exception e) {
			throw new RuntimeException("Error while creating connection: " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param jdbcConnection
	 * @throws InterfaceNotImplemented
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public JdbcConnection(br.com.datawatcher.entity.JdbcConnection jdbcConnection)
			throws InterfaceNotImplemented, InstantiationException, IllegalAccessException, ClassNotFoundException {
		this(jdbcConnection.getUserLogging().getDecryptedUserName(),
				jdbcConnection.getPasswordLogging().getDecryptedPassword(),
				jdbcConnection.getUrl(), 
				jdbcConnection.getDriverClassName());
	}
	
	/**
	 * Used to select, procedures and others operations that has ResultSet as return.
	 * @param sqlStatement
	 * @param parameters
	 * @return
	 */
	public ResultSet select(String sqlStatement, Object... parameters) {
		try {
			this.buildParam(parameters);
			ResultSet resultSetTemp = this.newPreparedStatement(sqlStatement).executeQuery();
			return resultSetTemp;
		} catch (SQLException e) {
			throw new RuntimeException("Error while creating ResultSet: " + e.getMessage());
		}
	}
	
	/**
	 * Used to update, delete, insert, procedures and others operations that don't have a ResultSet as return.
	 * @param sqlStatement
	 * @param parameters
	 */
	public void execute(String sqlStatement, Object... parameters) {
		try {
			this.buildParam(parameters);
			this.newPreparedStatement(sqlStatement).execute();
		} catch (SQLException e) {
			throw new RuntimeException("Error while creating ResultSet: " + e.getMessage());
		}
	}
	
	private void buildParam(Object[] parameters) throws SQLException {
		for (int i = 1 ; i <= parameters.length ; i ++) {
			Object parameter = parameters[i-1];
			if (parameter != null) {
				if (parameter instanceof String) {
					this.stmt.setString(i, (String)parameter);
				} else if (parameter instanceof Character) {
					this.stmt.setString(i, ((Character)parameter).toString());
				} else if (parameter instanceof Integer) {
					this.stmt.setInt(i, (Integer)parameter);
				} else if (parameter instanceof Byte) {
					this.stmt.setByte(i, (Byte)parameter);
				} else if (parameter instanceof Long) {
					this.stmt.setLong(i, (Long)parameter);
				} else if (parameter instanceof BigDecimal) {
					this.stmt.setBigDecimal(i, (BigDecimal)parameter);
				} else if (parameter instanceof Double) {
					this.stmt.setDouble(i, (Double)parameter);
				} else if (parameter instanceof Float) {
					this.stmt.setFloat(i, (Float)parameter);
				} else if (parameter instanceof java.sql.Date) {
					this.stmt.setTimestamp(i, new Timestamp(((java.sql.Date)parameter).getTime()));
				} else if (parameter instanceof java.util.Date) {
					this.stmt.setTimestamp(i, new Timestamp(((java.util.Date)parameter).getTime()));
				} else if (parameter instanceof Timestamp) {
					this.stmt.setTimestamp(i, (Timestamp)parameter);
				} else throw new IllegalArgumentException("Unknown type.");
			} else {
				this.stmt.setNull(i, java.sql.Types.NULL);
			}
		}
	}
	
	private PreparedStatement newPreparedStatement(String sqlStatement) throws SQLException {
		this.closePreparedStatement();
		return (this.stmt = this.con.prepareStatement(sqlStatement));
	}
	
	public void closePreparedStatement() {
		try {
			if (this.stmt != null) {
				this.stmt.close();
				this.stmt = null;
			}
		} catch (SQLException e) { }
	}
	
	public void closeConnection() {
		try {
			this.closePreparedStatement();
			this.con.close();
		} catch (SQLException e) {
			throw new RuntimeException("Error while closing connection: " + e.getMessage());
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.closeConnection();
	}
}