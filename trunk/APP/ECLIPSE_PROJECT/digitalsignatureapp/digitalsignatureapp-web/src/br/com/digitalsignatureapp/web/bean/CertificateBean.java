package br.com.digitalsignatureapp.web.bean;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import br.com.digitalsignatureapp.control.ControlManagedBean;

public class CertificateBean extends ControlManagedBean {
	
	private String				idTypeMessage;
	private String				freeMessage;
	private UploadedFile		digitalFile;
	private String				certificationChain;
	private String				messageDigest; // DIGITAL SIGNATURE
	
	private void initialize() {
		this.idTypeMessage = "f";
	}
	
	/**
	 * 
	 * @return
	 */
	public String goToCertificating() {
		this.initialize();
		return "certificating";
	}
	
	/**
	 * 
	 * @return
	 */
	public String goToVerifyCertificate() {
		this.initialize();
		return "verifyCertificate";
	}

	// GETTERS AND SETTERS //
	public String getIdTypeMessage() {
		return idTypeMessage;
	}

	public void setIdTypeMessage(String idTypeMessage) {
		this.idTypeMessage = idTypeMessage;
	}

	public String getFreeMessage() {
		return freeMessage;
	}

	public void setFreeMessage(String freeMessage) {
		this.freeMessage = freeMessage;
	}

	public UploadedFile getDigitalFile() {
		return digitalFile;
	}

	public void setDigitalFile(UploadedFile digitalFile) {
		this.digitalFile = digitalFile;
	}

	public String getCertificationChain() {
		return certificationChain;
	}

	public void setCertificationChain(String certificationChain) {
		this.certificationChain = certificationChain;
	}

	public String getMessageDigest() {
		return messageDigest;
	}

	public void setMessageDigest(String messageDigest) {
		this.messageDigest = messageDigest;
	}
	
}
