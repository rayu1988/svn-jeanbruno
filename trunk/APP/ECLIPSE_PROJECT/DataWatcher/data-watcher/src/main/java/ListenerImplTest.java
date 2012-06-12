/**
 * 
 */


import br.com.datawatcher.entity.Tuple;
import br.com.datawatcher.entity.TupleField;
import br.com.datawatcher.interfaces.TableChangeable;

/**
 * @author carrefour
 *
 */
public class ListenerImplTest implements TableChangeable {

	@Override
	public void insert(Tuple newTuple) {
		System.out.println("\ninsert;");
		this.printTuple(newTuple);
	}

	@Override
	public void update(Tuple oldTuple, Tuple newTuple) {
		System.out.println("\nupdate;");
		System.out.println("newTuple");
		this.printTuple(newTuple);
		System.out.println("oldTuple");
		this.printTuple(oldTuple);
	}

	@Override
	public void delete(Tuple oldTuple) {
		System.out.println("\ndelete;");
		this.printTuple(oldTuple);
	}

	private void printTuple(Tuple tuple) {
		System.out.println(tuple.getTupleId().getColumnName() + " : " + tuple.getTupleId().getValue());
		for (TupleField tupleField : tuple.getTupleFields()) {
			System.out.println(tupleField.getColumnName() + " : " + tupleField.getValue());
		}
	}
}
