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
		
		String htmlBody = " <html> " +
				" 	<head> " +
				" 		<title></title> " +
				" 	</head> " +
				" 	<body> " +
				" 		[logo vendas e barganhas] Vendas & Barganhas " +
				" 		Recebemos a solicitação de inclusão de novo usuário no sistema de anúncio e busca Vendas & Barganhas. " +
				" 		Se realmente a solicitação é válida e parte do seu desejo verdadeiro, clique no link abaixo ou copie e cole o endereço no seu browser favorito. " +
				" 		Caso não seja do seu desejo, por favor, desconsidere o mesmo. " +
				" 		O Grupo Vendas & Barganhas agradece muito a sua atenção! " +
				" 		[logo vendas e barganhas] Vendas & Barganhas " +
				" 	</body> " +
				" </html> ";
		
		Message message = new MimeMessage(session);
		
		Multipart multipart = new MimeMultipart();
		MimeBodyPart htmlPart = new MimeBodyPart();
		multipart.addBodyPart(htmlPart);
		htmlPart.setContent(htmlBody, "text/html");
		
		message.setFrom(new InternetAddress("vendasebarganhas@gmail.com", "Vendas & Barganhas"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(userAccount.getEmail(), userAccount.getFullname()));
		message.setSubject("");
		
		message.setContent(multipart);
		
		Transport.send(message);
	}

}
