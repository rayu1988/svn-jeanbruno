package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.tatu.cypher.XORCryption;
import org.com.tatu.helper.checking.Parameter;

import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.Mail;
import br.com.barganhas.business.services.UserAccount;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.AppManagedBean;

import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class CheckUserMailServlet extends HttpServlet {
	
	private static final Logger 	logger = Logger.getLogger(CheckUserMailServlet.class.getCanonicalName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		this.checkUserMail(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		this.checkUserMail(req, resp);
	}
	
	private void checkUserMail(HttpServletRequest req, HttpServletResponse resp) {
		String query = req.getParameter("q");
		try {
			logger.log(Level.INFO, "Checking new user.");
			Parameter.check(query).notEmpty();
			AppManagedBean managedBean = new AppManagedBean();
			UserAccount service = managedBean.getServiceBusinessFactory().getUserAccount();
			XORCryption decoder = new XORCryption(Mail.MAIL_KEY);
			query = decoder.decodeHexString(query);
			UserAccountTO userAccount = new UserAccountTO(KeyFactory.stringToKey(query));
			userAccount = service.activate(userAccount);
			logger.log(Level.INFO, "User activated successfully: nickname " + userAccount.getNickname() + ", email " + userAccount.getEmail());
			
			managedBean.setRequestMessage(req, new RequestMessage("userAccountActivatedSuccessfully", SeverityMessage.SUCCESS));
			resp.sendRedirect("/xhtml/account/login.jsf");
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			try {
				resp.sendRedirect("/xhtml/exceptions/pageNotFoundException.jsf");
			} catch (IOException ioException) { }
		}
	}
}
