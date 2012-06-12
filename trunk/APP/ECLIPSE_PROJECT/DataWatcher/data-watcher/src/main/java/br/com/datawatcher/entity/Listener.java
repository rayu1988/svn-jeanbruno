package br.com.datawatcher.entity;

public class Listener {
	
	private String			classname;
	private String 			asynchronous;

	public Listener() { }
	
	public Listener(String classname) {
		this.classname = classname;
	}
	
	// GETTERS AND SETTERS //
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getAsynchronous() {
		return asynchronous;
	}

	public void setAsynchronous(String asynchronous) {
		this.asynchronous = asynchronous;
	}
}
