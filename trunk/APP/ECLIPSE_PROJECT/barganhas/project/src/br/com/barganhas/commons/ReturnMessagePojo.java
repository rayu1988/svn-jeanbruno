package br.com.barganhas.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ReturnMessagePojo implements Serializable {

	private Boolean				success;
	private Boolean				inUse = false;
	private List<String>		messages = new ArrayList<String>();
	
	public ReturnMessagePojo() {
		super();
	}
	
	public ReturnMessagePojo(Boolean success) {
		super();
		this.success = success;
	}
	
	public boolean hasMessage() {
		return Util.isCollectionOk(this.messages);
	}
	
	public ReturnMessagePojo addMessage(String message) {
		if (Util.isStringOk(message)) {
			this.messages.add(message);
			this.inUse = true;
		}
		return this;
	}
	
	public String getMessage() {
		StringBuffer strBf = new StringBuffer();
		for (String str : this.messages) {
			strBf.append(str);
		}
		return strBf.toString();
	}
	
	// GETTERS AND SETTERS //
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<String> getMessages() {
		return messages;
	}

	public Boolean getInUse() {
		return inUse;
	}
}
