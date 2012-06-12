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
public class Tuple {

	private TupleId 					tupleId;
	private List<TupleField> 			tupleFields = new ArrayList<TupleField>();

	public Tuple(TupleId tupleId) {
		if (tupleId == null) {
			throw new IllegalArgumentException("tupleId parameter can't be null");
		}
		this.tupleId = tupleId;
	}

	public void addTupleField(TupleField tupleField) {
		if (tupleField == null) {
			throw new IllegalArgumentException("tupleField parameter can't be null");
		}
		this.tupleFields.add(tupleField);
	}
	
	@Override
	public int hashCode() {
		return this.tupleId.hashCode();
	}
	
	public int hashTuple() {
		StringBuffer str = new StringBuffer(this.tupleId.getStringValue());
		for (TupleField tupleField : this.tupleFields) {
			str.append(tupleField.getStringValue());
		}
		return str.toString().hashCode();
	}

	public TupleId getTupleId() {
		return tupleId;
	}

	public List<TupleField> getTupleFields() {
		return tupleFields;
	}
}
