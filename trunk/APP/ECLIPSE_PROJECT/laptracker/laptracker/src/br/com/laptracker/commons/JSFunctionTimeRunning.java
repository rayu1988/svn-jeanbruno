package br.com.laptracker.commons;

/**
 * 
 * @author Jean Villete
 *
 */
public class JSFunctionTimeRunning {
	public interface JSFunctionBoxing { String unBoxing(String jsFunctionToCall);  }
	
	public static JSFunctionBoxing runAsFind() {
		return new JSFunctionBoxing() {
			@Override
			public String unBoxing(String jsFunctionToCall) {
				return jsFunctionToCall;
			}
		};
	}
	
	public static JSFunctionBoxing onLoad() {
		return new JSFunctionBoxing() {
			@Override
			public String unBoxing(String jsFunctionToCall) {
				return " jQuery(document).ready( function() { " + jsFunctionToCall + " }); ";
			}
		};
	}
}
