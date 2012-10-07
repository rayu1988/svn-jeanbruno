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
import org.springframework.stereotype.Service;

import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.Mail;
import br.com.barganhas.commons.Util;

@Service("mailBO")
public class MailBO implements Mail {

	public static final String						BEAN_ALIAS = "mailBO";

	@Override
	public void mailNewUser(UserAccountTO userAccount) throws MessagingException, UnsupportedEncodingException {
		Util.validateParameterNull(userAccount);
		
		Properties properties = new Properties();
		Session session = Session.getDefaultInstance(properties, null);
		
		Message message = new MimeMessage(session);
		
		Multipart multipart = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		multipart.addBodyPart(htmlPart);
		htmlPart.setContent(this.getEmailNewUser(userAccount), "text/html");
		
		message.setFrom(new InternetAddress("vendasebarganhas@gmail.com", "Vendas & Barganhas"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(userAccount.getEmail(), userAccount.getFullname()));
		message.setSubject("Vendas & Barganhas - Ativar da Conta");
		
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
			" 			<a href=\"http://vendasebarganhas.appspot.com/\" target=\"_blank\">" +
			" 				<img src=\"http://vendasebarganhas.appspot.com/images/logo/logo2-rascunho2.png\">" +
			" 			</a>" +
			" 		</div> " +
				this.twoLines() +
			" 		<div> " +
			" 			Recebemos a solicitação de inclusão de um novo usuário no sistema de anúncio e busca " + this.getLink() + " referente ao email " + 
			" 			<b>" + userAccount.getEmail() + "</b>. " + 
			" 		</div> " +
				this.twoLines() +
			" 		<div> " +
			" 			Se realmente a solicitação é válida e parte do seu desejo verdadeiro, clique no link abaixo ou copie e cole o endereço no seu browser favorito " +
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
			" 			O Grupo " + this.getLink() + " agradece muito a sua atenção! " +
			" 		</div> " +
			
			" 	</body> " +
			" </html> ";
	}
	
	private String getLink() {
		return 
			"	<a href=\"http://vendasebarganhas.appspot.com/\" target=\"_blank\">" +
			"		Vendas & Barganhas " +
			"	</a>";
	}
	
	private String twoLines() {
		return "<br><br>";
	}

	private String getCheckingLink(UserAccountTO userAccount) {
		XORCryption encoder = new XORCryption(MAIL_KEY);
		String encodedQuery = encoder.encodeToHexString(userAccount.getKeyAsString());
		String checkingLink = "http://vendasebarganhas.appspot.com" + CHECK_ADDRESS + "?q=" + encodedQuery;
		return "<a href=\"" + checkingLink + "\" target=\"_blank\">" + checkingLink + "</a>";
	}
}
