package br.com.barganhas.business.services;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import br.com.barganhas.business.entities.SiteContactTO;
import br.com.barganhas.business.entities.UserAccountTO;


public interface Mail {

	String MAIL_KEY = "7";
	String CHECK_ADDRESS = "/checking/checkusermailservlet";
	String SITE_CONTACT_MAIL_ADDRESS = "contato@vendasebarganhas.com.br";

	void mailNewUser(UserAccountTO userAccount) throws MessagingException, UnsupportedEncodingException;
	
	void sendContactMessage(SiteContactTO siteContact) throws MessagingException, UnsupportedEncodingException;
}
