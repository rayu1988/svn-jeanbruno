package org.com.tatu.helper.parameter;

import java.util.ArrayList;
import java.util.List;

public class ParameterWrapper {

private List<Entry> entryList;
	
	private ParameterWrapper(String key, Object obj){
		super();
		this.entryList = new ArrayList<Entry>();
		this.entryList.add(new Entry(key, obj));
	}
	
	public static ParameterWrapper instance(String key, Object obj) {
		ParameterWrapper param = new ParameterWrapper(key, obj);
		return param;
	}

	public ParameterWrapper add(String key, Object obj) {
		this.entryList.add(new Entry(key, obj));
		return this;
	}
	
	public List<Entry> getEntryList() {
		return this.entryList;
	}
	
	public class Entry {
		private String 							key;
		private Object							value;
		
		protected Entry(String key, Object value) {
			this.key = key;
			this.value = value;
		}
		
		public String getKey() {
			return this.key;
		}
		public Object getValue() {
			return this.value;
		}
	}
	
}
