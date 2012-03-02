/**
 * 
 */
package br.com.db.gatekeeperbr.entity;

import java.util.ArrayList;
import java.util.List;

import br.com.db.gatekeeperbr.common.Util;
import br.com.db.gatekeeperbr.exception.ParameterRequiredException;

/**
 * @author JNVE
 *
 */
public class Email {
	
	private String			host;
	private String			mailFrom;
	private List<String>	emailAdress;
	
	public Email(String host, String mailFrom, String emailAddress) {
		if (!Util.isStringOk(host) || !Util.isStringOk(mailFrom) || !Util.isStringOk(emailAddress)) {
			throw new ParameterRequiredException("host", "mailFrom", "emailAddress");
		}
		this.host = host.trim();
		this.mailFrom = mailFrom.trim();
		this.emailAdress = new ArrayList<String>();
		
		for (String str : emailAddress.split(",")) {
			this.emailAdress.add(str.trim());
		}
	}
	
	// getters and setters //
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public List<String> getEmailAdress() {
		return emailAdress;
	}
	public void setEmailAdress(List<String> emailAdress) {
		this.emailAdress = emailAdress;
	}
}
