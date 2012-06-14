/**
 * 
 */
package br.com.datawatcher.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.datawatcher.common.Util;
import br.com.datawatcher.exception.DataWatcherException;
import br.com.datawatcher.exception.DataWatcherRuntimeException;
import br.com.datawatcher.exception.InterfaceNotImplemented;
import br.com.datawatcher.interfaces.TableChangeable;

/**
 * @author carrefour
 *
 */
public class TableMapping extends DataMapping {

	private String				name;
	private Id					id;
	private List<Column>		columns = new ArrayList<Column>();
	private JdbcConnection		jdbcConnection;
	private DeclaredResult		declaredResult;
	// key = tuple id
	// value = tuple hash code
	private Set<Tuple> 			tableState;
	private boolean				started=false;
	
	public TableMapping addColumn(Column column) {
		if (column != null) {
			columns.add(column);
			return this;
		} else throw new IllegalArgumentException("parameter column can not be null");
	}
	
	private ResultSet processSelect() throws InterfaceNotImplemented, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		br.com.datawatcher.common.JdbcConnection jdbcConnection = new br.com.datawatcher.common.JdbcConnection(this.jdbcConnection);

		ResultSet resultSet = null;
		if (this.declaredResult != null) {
			for (Procedure procedure : this.declaredResult.getProcedures()) {
				jdbcConnection.execute(procedure.getStatement());
			}
			resultSet = jdbcConnection.select(this.declaredResult.getQuery().getStatement());
		} else {
			StringBuffer sqlStatement = new StringBuffer();
			sqlStatement.append(this.id.getColumnName() + ", ");
			for (int i = 0; i < this.columns.size(); i++) {
				sqlStatement.append(this.columns.get(i).getColumnName());
				if ((i+1) < this.columns.size()) {
					sqlStatement.append(", ");
				}
			}
			resultSet = jdbcConnection.select("select " + sqlStatement.toString() + " from " + this.name);
		}
		if (resultSet != null) {
			return resultSet;
		} else throw new IllegalStateException("Problems with table mapping: " + this.name + ". result set is null.");
	}

	private Set<Tuple> buildResult(ResultSet resultSet) throws SQLException, ClassNotFoundException {
		Set<Tuple> tableState = new HashSet<Tuple>();
		while (resultSet.next()) {
			TupleId tupleId = new TupleId(this.id, this.getStringResultColumn(this.id, resultSet));
			Tuple tuple = new Tuple(tupleId);
			for (Column column : this.columns) {
				TupleField tupleField = new TupleField(column, this.getStringResultColumn(column, resultSet));
				tuple.addTupleField(tupleField);
			}
			tableState.add(tuple);
		}
		return tableState;
	}
	
	private Object getStringResultColumn(Column column, ResultSet resultSet) throws ClassNotFoundException, SQLException {
		if (column.getColumnClass().equals(java.lang.String.class)) {
			return resultSet.getString(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Character.class)) {
			return resultSet.getString(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Long.class)) {
			return resultSet.getLong(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Integer.class)) {
			return resultSet.getInt(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Byte.class)) {
			return resultSet.getByte(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Double.class)) {
			return resultSet.getDouble(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Float.class)) {
			return resultSet.getFloat(column.getColumnName());
		} else if (column.getColumnClass().equals(java.util.Date.class)) {
			return resultSet.getDate(column.getColumnName());
		} else throw new IllegalStateException("Unknow type column: " + column.getColumnName());
	}
	
	@Override
	public void startup() throws DataWatcherException {
		if (this.id != null && Util.isCollectionOk(this.columns)) {
			try {
				ResultSet resultSet = this.processSelect();
				this.tableState = this.buildResult(resultSet);
				this.started = true;
			} catch (Exception e) {
				throw new DataWatcherException(e);
			}
		} else throw new DataWatcherException("Problems while starting up the table mapping: " + this.name);
	}
	
	@Override
	public void checkChange() throws DataWatcherException {
		if (this.started) {
			try {
				ResultSet resultSet = this.processSelect();
				Set<Tuple> newTuples = this.buildResult(resultSet);
				new CompareTuples(newTuples).processComparing();
			} catch (Exception e) {
				throw new DataWatcherException(e);
			}
		} else throw new DataWatcherException("Table mapping was not started. (startup not invoked)");
	}
	
	// GETTERS AND SETTERS //
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		this.id = id;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public JdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}
	public void setJdbcConnection(JdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
	public DeclaredResult getDeclaredResult() {
		return declaredResult;
	}
	public void setDeclaredResult(DeclaredResult declaredResult) {
		this.declaredResult = declaredResult;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private class CompareTuples {
		
		private Map<Integer, CoupleTuple> tuples = new HashMap<Integer, CoupleTuple>();
		
		protected CompareTuples(Set<Tuple> newState) {
			if (newState == null) {
				throw new IllegalArgumentException("param can't be null");
			}
			this.turnUpCurrentState();
			this.turnUpNewState(newState);
		}
		
		private void turnUpCurrentState() {
			Iterator<Tuple> iterator = tableState.iterator();
			while (iterator.hasNext()) {
				Tuple tuple = iterator.next();
				
				CoupleTuple coupleTuple = this.tuples.get(tuple.hashCode());
				if (coupleTuple == null) {
					coupleTuple = new CoupleTuple();
				}
				coupleTuple.addCurrentTuple(tuple);
				this.tuples.put(tuple.hashCode(), coupleTuple);
			}
		}
		
		private void turnUpNewState(Set<Tuple> newState) {
			Iterator<Tuple> iterator = newState.iterator();
			while (iterator.hasNext()) {
				Tuple tuple = iterator.next();
				
				CoupleTuple coupleTuple = this.tuples.get(tuple.hashCode());
				if (coupleTuple == null) {
					coupleTuple = new CoupleTuple();
				}
				coupleTuple.addNewTuple(tuple);
				this.tuples.put(tuple.hashCode(), coupleTuple);
			}
		}
		
		protected void processComparing() throws DataWatcherException {
			for (CoupleTuple coupleTuple : this.tuples.values())
				for (Listener listener : getListeners())
					new ProcessListener(listener, coupleTuple).process();
		}
	}
	
	private class ProcessListener implements Runnable {

		private Listener listener;
		private CoupleTuple coupleTuple;
		
		protected ProcessListener(Listener listener, CoupleTuple coupleTuple) {
			if (listener == null || coupleTuple == null) {
				throw new IllegalArgumentException("param can't be null");
			}
			this.listener = listener;
			this.coupleTuple = coupleTuple;
		}
		
		protected void process() throws DataWatcherException {
			if (Util.isBooleanOk(this.listener.getAsynchronous())) {
				new Thread(this).start();
			} else {
				this.run();
			}
		}
		
		@Override
		public void run() {
			try {
				if (this.coupleTuple.isUpdate()) {
					tableState.remove(this.coupleTuple.getCurrentTuple());
					tableState.add(this.coupleTuple.getNewTuple());
					((TableChangeable)Class.forName(this.listener.getClassname()).newInstance()).update(this.coupleTuple.getCurrentTuple(), this.coupleTuple.getNewTuple());
				} else if (this.coupleTuple.isDelete()) {
					tableState.remove(this.coupleTuple.getCurrentTuple());
					((TableChangeable)Class.forName(this.listener.getClassname()).newInstance()).delete(this.coupleTuple.getCurrentTuple());
				} else if (this.coupleTuple.isInsert()) {
					tableState.add(this.coupleTuple.getNewTuple());
					((TableChangeable)Class.forName(this.listener.getClassname()).newInstance()).insert(this.coupleTuple.getNewTuple());
				}
			} catch (Exception e) {
				throw new DataWatcherRuntimeException(e);
			}
		}
		
	}
}
