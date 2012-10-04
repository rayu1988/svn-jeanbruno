package br.com.barganhas.business.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import br.com.barganhas.business.entities.UserAccountTO;


public interface Mail {

	void mailNewUser(UserAccountTO userAccount) throws MessagingException, UnsupportedEncodingException;
}
