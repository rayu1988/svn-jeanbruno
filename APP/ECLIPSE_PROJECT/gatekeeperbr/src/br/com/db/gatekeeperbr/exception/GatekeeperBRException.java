/**
 * 
 */
package br.com.db.gatekeeperbr.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author JNVE
 *
 */
public class GatekeeperBRException extends Exception {

	private static final long 				serialVersionUID = 3703205367487300476L;
	private List<String>                    messages = new ArrayList<String>();
	
	public GatekeeperBRException() {
	}
	
	public GatekeeperBRException(String message) {
		this.appendMessage(message);
	}
	
	public GatekeeperBRException(Exception e) {
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
