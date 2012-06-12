/**
 * 
 */
package br.com.datawatcher.service;

import br.com.datawatcher.common.Util;
import br.com.datawatcher.entity.CoupleTuple;
import br.com.datawatcher.entity.Listener;
import br.com.datawatcher.exception.DataWatcherException;
import br.com.datawatcher.exception.DataWatcherRuntimeException;
import br.com.datawatcher.interfaces.TableChangeable;

/**
 * @author carrefour
 *
 */
public class TableProcessListener implements Runnable {

	private Listener listener;
	private CoupleTuple coupleTuple;
	
	public TableProcessListener(Listener listener, CoupleTuple coupleTuple) {
		if (listener == null || coupleTuple == null) {
			throw new IllegalArgumentException("param can't be null");
		}
		this.listener = listener;
		this.coupleTuple = coupleTuple;
	}
	
	public void process() throws DataWatcherException {
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
				((TableChangeable)Class.forName(this.listener.getClassname()).newInstance()).update(this.coupleTuple.getCurrentTuple(), this.coupleTuple.getNewTuple());
			} else if (this.coupleTuple.isDelete()) {
				((TableChangeable)Class.forName(this.listener.getClassname()).newInstance()).delete(this.coupleTuple.getCurrentTuple());
			} else if (this.coupleTuple.isInsert()) {
				((TableChangeable)Class.forName(this.listener.getClassname()).newInstance()).insert(this.coupleTuple.getNewTuple());
			}
		} catch (Exception e) {
			throw new DataWatcherRuntimeException(e);
		}
	}
}
