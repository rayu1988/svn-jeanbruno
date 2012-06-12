/**
 * 
 */
package br.com.datawatcher.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author carrefour
 *
 */
public class DataWatcherRuntimeException extends RuntimeException {

	private static final long 			serialVersionUID = -2698236571768025391L;
	
	private List<String>                messages = new ArrayList<String>();
	
	public DataWatcherRuntimeException() {
	}
	
	public DataWatcherRuntimeException(String message) {
		this.appendMessage(message);
	}
	
	public DataWatcherRuntimeException(Exception e) {
		super(e.getMessage(), e);
	}
	
	public void appendMessage(String message) {
        this.messages.add(message);
	}
	
	public String getMessage() {
        StringBuffer mensagem = new StringBuffer();
		Iterator<String> iter = this.messages.iterator();
		while(iter.hasNext()){
	        String msg = iter.next();
	        mensagem.append(msg);
	        if(iter.hasNext()){
                mensagem.append("\n");                          
	        }
		}
		return mensagem.toString();
	}
}
