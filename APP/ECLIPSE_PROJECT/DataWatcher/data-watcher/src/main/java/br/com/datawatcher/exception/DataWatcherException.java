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
public class DataWatcherException extends Exception {

	private static final long 			serialVersionUID = -4106173198145293550L;

	private List<String>                messages = new ArrayList<String>();
	
	public DataWatcherException() {
	}
	
	public DataWatcherException(String message) {
		this.appendMessage(message);
	}
	
	public DataWatcherException(Exception e) {
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
