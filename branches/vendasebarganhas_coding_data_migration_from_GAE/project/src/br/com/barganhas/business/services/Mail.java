package br.com.barganhas.business.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import br.com.barganhas.business.entities.SiteContactTO;
import br.com.barganhas.business.entities.UserAccountTO;


public interface Mail {

	String MAIL_KEY = "7";
	String CHECK_ADDRESS = "/checking/checkusermailservlet";
	String SITE_CONTACT_EMAIL = "contato@vendasebarganhas.com.br";
	String SITE_CONTACT_EMAIL_USER_FROM = "vendasebarganhas@gmail.com";
	String SITE_CONTACT_EMAIL_PASSWORD = "ContatoVB123!";

	void mailNewUser(UserAccountTO userAccount) throws MessagingException, UnsupportedEncodingException;
	
	void sendContactMessage(SiteContactTO siteContact) throws MessagingException, UnsupportedEncodingException;
}
