package br.com.digitalsignature.exception;

public class KeyStoreException extends Exception {
	public KeyStoreException(String message) {
		super(message);
	}
	
	public KeyStoreException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
