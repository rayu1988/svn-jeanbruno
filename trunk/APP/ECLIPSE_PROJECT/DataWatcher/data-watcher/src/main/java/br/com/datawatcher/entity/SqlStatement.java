/**
 * 
 */
package br.com.datawatcher.entity;

/**
 * @author carrefour
 *
 */
public abstract class SqlStatement {
	
	public SqlStatement() { }
	
	public SqlStatement(String statement) {
		this.statement = statement;
	}

	private String 				statement;

	// GETTERS AND SETTERS //
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
}
