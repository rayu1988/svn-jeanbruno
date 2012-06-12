/**
 * 
 */
package br.com.datawatcher.entity;

/**
 * @author carrefour
 *
 */
public class TupleId extends TupleField {

	public TupleId(Id id, Object value) {
		super(id, value);
	}

	@Override
	public int hashCode() {
		return this.getStringValue().hashCode();
	}
}
