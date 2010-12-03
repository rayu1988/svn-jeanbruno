package br.com.digitalsignatureapp.applet;

import java.applet.Applet;

import netscape.javascript.JSObject;

public class ControledApplet extends Applet {

	// the current browser window (DOM)
    protected JSObject browserWindow;
    
	protected ControledApplet() { }
    
	/**
	 * 
	 * @param idElement
	 * @param value
	 */
	protected void setValue(String idElement, String value) {
		((JSObject) this.browserWindow.call("$id", new String[]{idElement})).setMember("value", value);
	}
	
	/**
	 * 
	 * @param idElement
	 * @return
	 */
	protected String getValue(String idElement) {
		return (String) ((JSObject) this.browserWindow.call("$id", new String[]{idElement})).getMember("value");
	}
	
	/**
	 * 
	 * @param ex
	 */
	protected void responseException(Exception ex) {
		this.browserWindow.call("alert", new String[]{ex.getMessage() + ". Give a look at console log."});
	}
}
