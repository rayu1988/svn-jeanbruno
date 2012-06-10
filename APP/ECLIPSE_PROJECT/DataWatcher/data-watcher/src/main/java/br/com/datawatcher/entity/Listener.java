package br.com.datawatcher.entity;

public class Listener {
	
	private String			classname;

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
}
