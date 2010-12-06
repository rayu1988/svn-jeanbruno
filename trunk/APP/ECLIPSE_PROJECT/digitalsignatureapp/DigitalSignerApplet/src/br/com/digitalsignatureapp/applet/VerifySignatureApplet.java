package br.com.digitalsignatureapp.applet;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.cert.CertificateException;

import netscape.javascript.JSObject;
import br.com.digitalsignature.bus.DigitalSigner;
import br.com.digitalsignature.bus.impl.DigitalSignerBO;
import br.com.digitalsignature.entity.MessageTO;
import br.com.digitalsignature.entity.SignatureTO;
import br.com.digitalsignature.exception.SignatureException;
import br.com.digitalsignature.utils.Util;
import br.com.digitalsignatureapp.ControledApplet;
import br.com.digitalsignatureapp.DigitalSignature;

public class VerifySignatureApplet extends ControledApplet {

	public static final String PARAM_VERIFY_BUTTON_CAPTION 			= "verifyButtonLabel";
	public static final String PARAM_MSG_VALID_SIGNATURE 			= "msgValidSignature";
	public static final String PARAM_MSG_INVALID_SIGNATURE 			= "msgInvalidSignature";

	@Override
	public void init() {
		this.browserWindow = JSObject.getWindow(this);
		
		Button button = new Button(this.getParameter(PARAM_VERIFY_BUTTON_CAPTION));
		button.setLocation(0, 0);
		button.setSize(this.getSize());
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verify();
			}
		});
		this.setLayout(null);
		this.add(button);
	}
	
	private void verify() {
		Character typeMessage = ((String) ((JSObject) this.browserWindow.call("getCurrentTypeMessage", null)).getMember("value")).toCharArray()[0];
		MessageTO message = null;
		
		if (typeMessage.equals('f')) {
    		message = new MessageTO(this.getValue(DigitalSignature.ID_FIELD_FREE_MESSAGE));
    	} else if (typeMessage.equals('d')) {
    		message = new MessageTO(new File(this.getValue(DigitalSignature.ID_FIEDL_DIGITAL_FILE)));
    	} else throw new IllegalStateException("The variable called messageType it's wrong.");

		DigitalSigner dsBusiness = new DigitalSignerBO();
    	dsBusiness.setMessage(message);
		
		String certificationChainCoded = this.getValue(DigitalSignature.ID_FIEDL_CERTIFICATION_CHAIN);
		String signatureCoded = this.getValue(DigitalSignature.ID_FIEDL_DIGITAL_SIGNATURE);
		
		SignatureTO signature = new SignatureTO();
		try {
			signature.setCertificationChainDecoded(certificationChainCoded);
			signature.setMessageDigestDecoded(signatureCoded);
			
			if (Util.isBooleanTrue(dsBusiness.verify(signature))){
				this.alertJS(this.getParameter(PARAM_MSG_VALID_SIGNATURE));
			} else {
				this.alertJS(this.getParameter(PARAM_MSG_INVALID_SIGNATURE));
			}
		} catch (CertificateException e) {
			this.responseException(e);
		} catch (SignatureException e) {
			this.responseException(e);
		}
	}
}