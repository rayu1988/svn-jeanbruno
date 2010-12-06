package br.com.digitalsignatureapp;

import java.applet.Applet;

import br.com.digitalsignature.utils.Util;

import netscape.javascript.JSObject;

public abstract class ControledApplet extends Applet {
	
	/**
	 * For perfect implementation, it's ideal write like the following; <br/>
	 * 
	 *  public void init() { <br/>
     * 	   this.browserWindow = JSObject.getWindow(this); <br/>
     * 		 ... <br/>
     *	}
	 */
	public abstract void init();

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
	
	protected void alertJS(String str) {
		if (!Util.stringOk(str)) throw new IllegalArgumentException(Util.PARAMETER_STRING_NOT_OK);
		this.browserWindow.call("alert", new String[]{str});
			
	}
	
	/**
	 * 
	 * @param ex
	 */
	protected void responseException(Exception ex) {
		ex.printStackTrace();
		this.alertJS("A exception:\n" + ex.getMessage() + ".\n Give a look at console log.");
	}
}
