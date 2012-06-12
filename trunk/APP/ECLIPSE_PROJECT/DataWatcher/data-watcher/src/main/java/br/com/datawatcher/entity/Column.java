/**
 * 
 */
package br.com.datawatcher.entity;

/**
 * @author carrefour
 *
 */
public class Column {

	private String			columnType;
	private String			columnName;
	
	public Column() { }
	
	public Column(String columnType, String columnName) {
		this.columnType = columnType;
		this.columnName = columnName;
	}
	
	@SuppressWarnings("rawtypes")
	public Class getColumnClass() throws ClassNotFoundException {
		return Class.forName(this.columnType);
	}
	
	// GETTERS AND SETTERS //
	public String getColumnName() {
		return columnName;
	}

	public String getColumnType() {
		return columnType;
	}
}
