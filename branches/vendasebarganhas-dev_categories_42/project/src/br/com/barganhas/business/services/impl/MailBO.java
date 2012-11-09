package br.com.barganhas.business.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.com.tatu.cypher.XORCryption;
import org.com.tatu.helper.parameter.Parameter;
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.SiteContactTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.Mail;

@Service("mailBO")
public class MailBO implements Mail {

	public static final String						BEAN_ALIAS = "mailBO";
	
	@Override
	public void mailNewUser(UserAccountTO userAccount) throws MessagingException, UnsupportedEncodingException {
		Parameter.check(userAccount).notNull();
		
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties, null);
		
		Message message = new MimeMessage(session);
		
		Multipart multipart = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		multipart.addBodyPart(htmlPart);
		htmlPart.setContent(this.getEmailNewUser(userAccount), "text/html");
		
		message.setFrom(new InternetAddress(Mail.SITE_CONTACT_EMAIL_USER_FROM, "Vendas & Barganhas"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(userAccount.getEmail(), userAccount.getFullname()));
		message.setSubject("Vendas & Barganhas - Ativar Conta");
		
		message.setContent(multipart);
		
		Transport.send(message);
	}
	
	private String getEmailNewUser(UserAccountTO userAccount) {
		return 
			" <html> " +
			" 	<head> " +
			" 		<title>Vendas & Bargahas</title> " +
			" 	</head> " +
			" 	<body> " +

			" 		<div> " +
			" 			<a href=\"http://www.vendasebarganhas.com.br/\" target=\"_blank\">" +
			" 				<img src=\"http://www.vendasebarganhas.com.br/images/logo/logo2-rascunho2.png\">" +
			" 			</a>" +
			" 		</div> " +
				this.twoLines() +
			" 		<div> " +
			" 			Recebemos a solicitação de inclusão de um novo usuário no " + this.getLink() + " referente ao email " + 
			" 			<b>" + userAccount.getEmail() + "</b>. " + 
			" 		</div> " +
				this.twoLines() +
			" 		<div> " +
			" 			Se a solicitação é válida clique no link abaixo ou copie e cole o endereço no seu browser favorito " +
			" 			para podermos validar e <b>ATIVAR</b> o seu usuário no sistema " + this.getLink() + "." +
			" 		</div> " +
				this.twoLines() +
				this.getCheckingLink(userAccount) +
				this.twoLines() +
			" 		<div> " +
			" 			Caso não seja do seu desejo, por favor, desconsidere este email. " +
			" 		</div> " +
				this.twoLines() +
			" 		<div> " +
			" 			Nós do " + this.getLink() + " agradecemos muito seu interesse em nossa comunidade " +
			" 		</div> " +
			
			" 	</body> " +
			" </html> ";
	}
	
	private String getLink() {
		return 
			"	<a href=\"http://www.vendasebarganhas.com.br/\" target=\"_blank\">" +
			"		Vendas & Barganhas " +
			"	</a>";
	}
	
	private String twoLines() {
		return "<br><br>";
	}

	private String getCheckingLink(UserAccountTO userAccount) {
		XORCryption encoder = new XORCryption(MAIL_KEY);
		String encodedQuery = encoder.encodeToBase64(userAccount.getKeyAsString());
		String checkingLink = "http://www.vendasebarganhas.com.br" + CHECK_ADDRESS + "?q=" + encodedQuery;
		return "<a href=\"" + checkingLink + "\" target=\"_blank\">" + checkingLink + "</a>";
	}

	@Override
	public void sendContactMessage(SiteContactTO siteContact) throws MessagingException, UnsupportedEncodingException {
		Parameter.check(siteContact).notNull();
		
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties, null);
		
		Message message = new MimeMessage(session);
		
		Multipart multipart = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		multipart.addBodyPart(htmlPart);
		htmlPart.setContent(this.getEmailContact(siteContact), "text/html");
		
		message.setFrom(new InternetAddress(Mail.SITE_CONTACT_EMAIL_USER_FROM, "Vendas & Barganhas"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(Mail.SITE_CONTACT_EMAIL, "Contato Vendas e Barganhas"));
		message.setSubject("Contato Vendas e Barganhas");
		
		message.setContent(multipart);
		
		Transport.send(message);
	}
	
	private String getEmailContact(SiteContactTO siteContact) {
		return 
				" <html> " +
				" 	<head> " +
				" 		<title>Vendas & Bargahas</title> " +
				" 	</head> " +
				" 	<body> " +
				
			" 		<div> " +
			" 			<a href=\"http://www.vendasebarganhas.com.br/\" target=\"_blank\">" +
			" 				<img src=\"http://www.vendasebarganhas.com.br/images/logo/logo2-rascunho2.png\">" +
			" 			</a>" +
			" 		</div> " +
			this.twoLines() +
			" 		<div> " +
			" 			Nova mensagem provinda da sessão de Contatos!" + 
			" 		</div> " +
			this.twoLines() +
			" 		<div> " +
			" 			O usuário que deixou a mensagem foi: " + siteContact.getName() + ", cujo o endereço de email do mesmo é: " + siteContact.getEmail() +
			" 		</div> " +
			this.twoLines() +
			" 		<div> " +
			" 			A mensagem deixada pelo usuário foi a seguinte; " +
			" 		</div> " +
			this.twoLines() +
			" 		<div> " +
			" 			" + siteContact.getStringMessage() +
			" 		</div> " +
			
			" 	</body> " +
			" </html> ";
	}
}
