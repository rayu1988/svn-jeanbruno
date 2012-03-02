/**
 * 
 */
package br.com.db.gatekeeperbr.entity;

import java.util.List;

import br.com.db.gatekeeperbr.connection.JDBCConnection;

/**
 * @author JNVE
 *
 */
public class Application {
	
	private JDBCConnection					jdbcConnection;
	private List<String>					listProcedure;
	private String							query;
	private KeyFile							keyFile;
	
	// getters and setters //
	public JDBCConnection getJdbcConnection() {
		return jdbcConnection;
	}
	public void setJdbcConnection(JDBCConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
	public List<String> getListProcedure() {
		return listProcedure;
	}
	public void setListProcedure(List<String> listProcedure) {
		this.listProcedure = listProcedure;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public KeyFile getKeyFile() {
		return keyFile;
	}
	public void setKeyFile(KeyFile keyFile) {
		this.keyFile = keyFile;
	}
}
