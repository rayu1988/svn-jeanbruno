package br.com.barganhas.business.exceptions;

import java.util.ArrayList;
import java.util.List;

import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.SeverityMessage;

@SuppressWarnings("serial")
public class AppException extends RuntimeException {

	private List<RequestMessage> messages = new ArrayList<RequestMessage>();
	
	public AppException(Exception exception) {
		super(exception.getMessage(), exception);
		if (exception instanceof AppException) {
			this.messages = ((AppException) exception).getMessages();
		} else if (Util.isStringOk(exception.getMessage())) {
			this.addErrorMessage(exception.getMessage());
		}
	}
	
	public AppException(String message) {
		super(message);
		this.addErrorMessage(message);
	}

	public AppException(List<RequestMessage> messages) {
		super(Util.isCollectionOk(messages) ? messages.toString() : "list messages");
		this.messages = messages.subList(0, messages.size());
	}

	public AppException addErrorMessage(String message) {
		this.messages.add(new RequestMessage(message, SeverityMessage.ERROR));
		return this;
	}
	
	// GETTERS AND SETTERS //
	public List<RequestMessage> getMessages() {
		return messages;
	}
}
