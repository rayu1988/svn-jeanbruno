/**
 * 
 */
package br.com.softplan.ocr.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Super class to controll exception launched by OCR System.
 * @author jean.villete
 *
 */
public class OCRException extends Exception {

	private List<String> 			messages = new ArrayList<String>();
	
	private static final long 		serialVersionUID = 8156382381077519957L;

	public OCRException() { }
	
	public OCRException(Exception e) {
		super(e.getMessage(), e);
	}
	
	public void appendMessage(String message) {
		this.messages.add(message);
	}
	
	 @Override
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
