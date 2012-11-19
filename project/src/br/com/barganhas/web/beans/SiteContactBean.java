package br.com.barganhas.web.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.com.tatu.helper.GeneralsHelper;

import br.com.barganhas.business.entities.SiteContactTO;
import br.com.barganhas.business.services.Mail;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.enums.SeverityMessage;

@ManagedBean
@RequestScoped
@SuppressWarnings("serial")
public class SiteContactBean extends AppManagedBean {
	
	private SiteContactTO		siteContact;
	
	public String goToSiteContact() {
		try {
			this.siteContact = new SiteContactTO();
			
			return "siteContact";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	public String sendMessage() {
		try {
			// starts block validating
			List<RequestMessage> messages = this.commonValidate();
			if (GeneralsHelper.isCollectionOk(messages)) {
				this.setRequestMessages(messages);
				return null;
			}
			// ends block validating
			
			Mail service = this.getServiceBusinessFactory().getMail();
			service.sendContactMessage(this.siteContact);
			
			this.setRequestMessage(new RequestMessage("siteContactMsgSentTitle", SeverityMessage.SUCCESS));
			return "siteContactMessageSent";
		} catch (Exception e) {
			return this.manageException(e);
		}
	}
	
	private List<RequestMessage> commonValidate() {
		List<RequestMessage> messages = new ArrayList<RequestMessage>();
		if (!GeneralsHelper.isStringOk(this.siteContact.getName())) {
			messages.add(new RequestMessage("siteContactMsgNameRequired", SeverityMessage.ERROR));
		}
		if (!GeneralsHelper.isStringOk(this.siteContact.getEmail())) {
			messages.add(new RequestMessage("siteContactMsgEmailRequired", SeverityMessage.ERROR));
		} else if (!GeneralsHelper.isEmailOk(this.siteContact.getEmail())) {
			messages.add(new RequestMessage("siteContactMsgEmailInvalid", SeverityMessage.ERROR));
		}
		if (!GeneralsHelper.isStringOk(this.siteContact.getMessage())) {
			messages.add(new RequestMessage("siteContactMsgMessageRequired", SeverityMessage.ERROR));
		}
		return messages;
	}

	// GETTERS AND SETTERS //
	public SiteContactTO getSiteContact() {
		return siteContact;
	}

	public void setSiteContact(SiteContactTO siteContact) {
		this.siteContact = siteContact;
	}
	
}
