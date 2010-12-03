package br.com.digitalsignature.exception;

public class SignatureException extends Exception {
	public SignatureException(String message) {
		super(message);
	}
	
	public SignatureException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
