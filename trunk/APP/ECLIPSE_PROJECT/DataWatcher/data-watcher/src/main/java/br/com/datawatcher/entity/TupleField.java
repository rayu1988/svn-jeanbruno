/**
 * 
 */
package br.com.datawatcher.entity;

/**
 * @author carrefour
 *
 */
public class TupleField {

	private Column column;
	private Object value;
	
	public TupleField(Column column, Object value) {
		if (column == null || value == null) {
			throw new IllegalArgumentException("column and/or value parameter can't be null");
		}
		this.column = column;
		this.value = value;
	}
	
	protected String getStringValue() {
		if (this.value instanceof java.lang.String) {
			return (String) this.value;
		} else if (this.value instanceof java.lang.Character) {
			return ((Character) this.value).toString();
		} else if (this.value instanceof java.lang.Long) {
			return ((Long)this.value).toString();
		} else if (this.value instanceof java.lang.Integer) {
			return ((Integer)this.value).toString();
		} else if (this.value instanceof java.lang.Byte) {
			return ((Byte)this.value).toString();
		} else if (this.value instanceof java.lang.Double) {
			return ((Double)this.value).toString();
		} else if (this.value instanceof java.lang.Float) {
			return ((Float)this.value).toString();
		} else if (this.value instanceof java.util.Date) {
			return ((java.util.Date)this.value).toString();
		} else throw new IllegalStateException("Unknow type value");
	}
	
	public String getColumnName() {
		return this.column.getColumnName();
	}
	public String getColumnType() {
		return this.column.getColumnType();
	}
	public Object getValue() {
		return value;
	}
}
