/**
 * 
 */
package br.com.datawatcher.entity;


/**
 * @author carrefour
 *
 */
public class CoupleTuple {

	private Tuple				currentTuple;
	private Tuple				newTuple;
	
	protected CoupleTuple() {
	}

	public boolean isInsert() {
		return currentTuple == null && newTuple != null;
	}
	
	public boolean isDelete() {
		return currentTuple != null && newTuple == null;
	}
	
	public boolean isUpdate() {
		return (currentTuple != null && newTuple != null) && (currentTuple.hashTuple() != newTuple.hashTuple());
	}
	
	public void addCurrentTuple(Tuple currentTuple) {
		if (currentTuple == null) {
			throw new IllegalArgumentException("param currentTuple can't be null");
		}
		this.currentTuple = currentTuple;
	}

	public void addNewTuple(Tuple newTuple) {
		if (newTuple == null) {
			throw new IllegalArgumentException("param newTuple can't be null");
		}
		this.newTuple = newTuple;
	}

	public Tuple getCurrentTuple() {
		return currentTuple;
	}

	public Tuple getNewTuple() {
		return newTuple;
	}
	
}
