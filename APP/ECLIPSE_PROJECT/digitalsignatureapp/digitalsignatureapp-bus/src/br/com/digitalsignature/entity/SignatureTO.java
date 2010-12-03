package br.com.digitalsignature.entity;

import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.List;

import br.com.digitalsignature.bus.impl.DigitalSignerBO;
import br.com.digitalsignature.utils.Base64;

/**
 * 
 * @author jean
 *
 */
public class SignatureTO {
	private String				messageDigestDecoded;
	private String				certificationChainDecoded;
	private byte[]				messageDigestCoded;
	private Certificate[]		certificationChainCoded;
	
	/**
	 * 
	 */
	public SignatureTO() {}
	
	/**
	 * 
	 * @param messageDigest
	 * @param certificationChain
	 */
	public SignatureTO(byte[] messageDigestCoded, Certificate[] certificationChainCoded) {
		this.setMessageDigestCoded(messageDigestCoded);
		this.setCertificationChainCoded(certificationChainCoded);
	}

	// GETTERS AND SETTERS //
	public String getMessageDigestDecoded() {
		return messageDigestDecoded;
	}

	public String getCertificationChainDecoded() {
		return certificationChainDecoded;
	}

	public byte[] getMessageDigestCoded() {
		return messageDigestCoded;
	}

	public void setMessageDigestCoded(byte[] messageDigestCoded) {
		this.messageDigestDecoded = Base64.base64Encode(messageDigestCoded);
		this.messageDigestCoded = messageDigestCoded;
	}

	public Certificate[] getCertificationChainCoded() {
		return certificationChainCoded;
	}

	public void setCertificationChainCoded(Certificate[] certificationChainCoded) {
		try {
			List<Certificate> certList = Arrays.asList(certificationChainCoded);
	        CertificateFactory certFactory = CertificateFactory.getInstance(DigitalSignerBO.X509_CERTIFICATE_TYPE);
	        CertPath certPath = certFactory.generateCertPath(certList);
	        byte[] certPathEncoded = certPath.getEncoded(DigitalSignerBO.CERTIFICATION_CHAIN_ENCODING);
	        this.certificationChainDecoded = Base64.base64Encode(certPathEncoded);
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		this.certificationChainCoded = certificationChainCoded;
	}
}