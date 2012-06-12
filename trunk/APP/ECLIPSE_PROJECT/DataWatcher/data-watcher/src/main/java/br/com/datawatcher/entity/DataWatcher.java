/**
 * 
 */
package br.com.datawatcher.entity;

import java.util.ArrayList;
import java.util.List;

import br.com.datawatcher.exception.DataWatcherException;

/**
 * @author carrefour
 *
 */
public class DataWatcher {

	private List<DataMapping> mappings = new ArrayList<DataMapping>();
	
	public DataWatcher() { }

	public DataWatcher addMapping(DataMapping dataMapping) {
		if (dataMapping != null) {
			this.mappings.add(dataMapping);
			return this;
		} else throw new IllegalArgumentException("parameter can not be null");
	}
	
	public void start() throws DataWatcherException {
		for (DataMapping dataMapping : this.mappings) {
			dataMapping.startup();
		}
	}
}
