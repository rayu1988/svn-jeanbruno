/**
 * 
 */
package br.com.datawatcher.interfaces;

import br.com.datawatcher.entity.Tuple;

/**
 * @author carrefour
 *
 */
public interface TableChangeable extends DataChangeable {

	void insert(Tuple newTuple);
	void update(Tuple oldTuple, Tuple newTuple);
	void delete(Tuple newTuple);
	
}
