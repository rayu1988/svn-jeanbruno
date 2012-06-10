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
	
	// GETTERS AND SETTERS //
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
