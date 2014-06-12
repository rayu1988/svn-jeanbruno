package br.com.laptracker.business.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.GeneralsHelper;

import br.com.laptracker.commons.RequestMessage;
import br.com.laptracker.commons.enums.SeverityMessage;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = -5741753119427401589L;
	
	private List<RequestMessage> messages = new ArrayList<RequestMessage>();
	
	public AppException(Exception exception) {
		super(exception.getMessage(), exception);
		if (exception instanceof AppException) {
			this.messages = ((AppException) exception).getMessages();
		} else if (GeneralsHelper.isStringOk(exception.getMessage())) {
			this.addErrorMessage(exception.getMessage());
		}
	}
	
	public AppException(String message) {
		super(message);
		this.addErrorMessage(message);
	}

	public AppException(List<RequestMessage> messages) {
		super(GeneralsHelper.isCollectionOk(messages) ? messages.toString() : "list messages");
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
