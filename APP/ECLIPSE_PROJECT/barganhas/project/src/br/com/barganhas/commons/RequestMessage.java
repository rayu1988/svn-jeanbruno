package br.com.barganhas.commons;

import java.io.Serializable;

import br.com.barganhas.enums.SeverityMessage;

@SuppressWarnings("serial")
public class RequestMessage implements Serializable {

	private String 					textMessage;
	private SeverityMessage 		severityMessage;
	
	public RequestMessage(String textMessage, SeverityMessage severityMessage) {
		Util.validateParameterNull(textMessage, severityMessage);
		this.setTextMessage(textMessage);
		this.setSeverityMessage(severityMessage);
	}
	
	// GETTERS AND SETTERS //
	public String getTextMessage() {
		return textMessage;
	}
	public void setTextMessage(String textMessage) {
		this.textMessage = Util.getMessageResourceString(textMessage);
	}
	public SeverityMessage getSeverityMessage() {
		return severityMessage;
	}
	public void setSeverityMessage(SeverityMessage severityMessage) {
		this.severityMessage = severityMessage;
	}
}
