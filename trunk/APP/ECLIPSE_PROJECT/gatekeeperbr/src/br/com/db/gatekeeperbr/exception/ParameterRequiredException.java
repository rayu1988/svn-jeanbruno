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
public class ParameterRequiredException extends IllegalArgumentException {

	private static final long 				serialVersionUID = -3992997393268500601L;
	private List<String>                    messages = new ArrayList<String>();
	
	/**
	 * @param parameters
	 */
	public ParameterRequiredException(String... parameters) {
		this.appendMessage("Parameter Required.");
		for (String str : parameters) {
			this.appendMessage(" " + str);
		}
		this.appendMessage(" can not be null or empty!");
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
