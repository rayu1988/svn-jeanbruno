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
public class DataWatcher {

	private List<DataMapping> mappings = new ArrayList<DataMapping>();
	
	public DataWatcher() { }

	public DataWatcher addMapping(DataMapping dataMapping) {
		if (dataMapping != null) {
			this.mappings.add(dataMapping);
			return this;
		} else throw new IllegalArgumentException("parameter can not be null");
	}
}
