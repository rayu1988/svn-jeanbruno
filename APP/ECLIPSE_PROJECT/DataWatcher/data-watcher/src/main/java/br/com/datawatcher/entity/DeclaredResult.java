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
public class DeclaredResult {

	private List<Procedure>				procedures = new ArrayList<Procedure>();
	private Query						query;

	public DeclaredResult() { }
	
	public DeclaredResult(Query query) {
		if (query == null) {
			throw new IllegalArgumentException("parameter query can not be null");
		}
		this.query = query;
	}

	public DeclaredResult addProcedure(Procedure procedure) {
		if (procedure != null) {
			this.procedures.add(procedure);
			return this;
		} else throw new IllegalArgumentException("parameter procedure can not be null");
	}
	
	// GETTERS AND SETTERS //
	public List<Procedure> getProcedures() {
		if (procedures == null) procedures = new ArrayList<Procedure>();
		return procedures;
	}
	public Query getQuery() {
		return query;
	}
}
