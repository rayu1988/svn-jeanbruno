package br.com.digitalsignature.exception;

public class NoneMessageException extends Exception {
	public NoneMessageException(String message) {
		super(message);
	}
	
	public NoneMessageException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
