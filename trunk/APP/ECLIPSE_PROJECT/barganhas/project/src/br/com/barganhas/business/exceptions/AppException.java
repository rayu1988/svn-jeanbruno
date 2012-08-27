package br.com.barganhas.business.exceptions;

@SuppressWarnings("serial")
public class AppException extends RuntimeException {

	public AppException() {
		super();
	}
	
	public AppException(Exception exception) {
		super(exception);
	}
}
