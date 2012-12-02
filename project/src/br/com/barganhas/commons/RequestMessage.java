package br.com.barganhas.commons;

import java.io.Serializable;

import org.com.tatu.helper.parameter.Parameter;

import br.com.barganhas.enums.SeverityMessage;

@SuppressWarnings("serial")
public class RequestMessage implements Serializable {

	private String 					textMessage;
	private SeverityMessage 		severityMessage;
	
	public RequestMessage(String textMessage, SeverityMessage severityMessage) {
		Parameter.check(textMessage, severityMessage).notNull();
		this.setSeverityMessage(severityMessage);
	}
	
	// GETTERS AND SETTERS //
	public String getTextMessage() {
		return textMessage;
	}
	public SeverityMessage getSeverityMessage() {
		return severityMessage;
	}
	public void setSeverityMessage(SeverityMessage severityMessage) {
		this.severityMessage = severityMessage;
	}
}
