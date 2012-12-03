package br.com.barganhas.commons;

public class UNHEX {

	private String value;
	
	public UNHEX(String value) {
		this.value = value;
	}
	
	public String getUNHEXValue() {
		return "UNHEX('" + this.value + "')";
	}
}
