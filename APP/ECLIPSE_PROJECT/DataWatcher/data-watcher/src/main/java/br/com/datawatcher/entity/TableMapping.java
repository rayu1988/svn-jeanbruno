/**
 * 
 */
package br.com.datawatcher.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author carrefour
 *
 */
public class TableMapping extends DataMapping {

	private String				name;
	private Id					id;
	private List<Column>		columns = new ArrayList<Column>();;
	private JdbcConnection		jdbcConnection;
	private DeclaredResult		declaredResult;

	public TableMapping addColumn(Column column) {
		if (column != null) {
			columns.add(column);
			return this;
		} else throw new IllegalArgumentException("parameter column can not be null");
	}
	
	// GETTERS AND SETTERS //
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		this.id = id;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public JdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}
	public void setJdbcConnection(JdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
	public DeclaredResult getDeclaredResult() {
		return declaredResult;
	}
	public void setDeclaredResult(DeclaredResult declaredResult) {
		this.declaredResult = declaredResult;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
