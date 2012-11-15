package br.com.barganhas.commons;

public class EntityPropertyPojo {
	private String			key;
	private Object			value;
	private boolean			unindexed;
	
	public EntityPropertyPojo(String key, Object value, boolean unindexed) {
		this.key = key;
		this.value = value;
		this.unindexed = unindexed;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isUnindexed() {
		return unindexed;
	}

	public void setUnindexed(boolean unindexed) {
		this.unindexed = unindexed;
	}

}
