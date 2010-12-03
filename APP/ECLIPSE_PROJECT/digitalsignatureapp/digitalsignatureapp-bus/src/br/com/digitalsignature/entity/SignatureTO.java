package br.com.digitalsignature.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.List;

import br.com.digitalsignature.exception.SignatureException;
import br.com.digitalsignature.utils.Base64;

/**
 * 
 * @author jean
 *
 */
public class SignatureTO {
	public static final String X509_CERTIFICATE_TYPE 				= "X.509";
	public static final String CERTIFICATION_CHAIN_ENCODING			= "PkiPath";
	
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
	
	/**
     * Loads a certification chain from given Base64-encoded string, containing
     * ASN.1 DER formatted chain, stored with PkiPath encoding.
     */
    private void loadCertPathFromBase64String(String certificationChainCoded) throws CertificateException {
        byte[] certChainEncoded = Base64.base64Decode(certificationChainCoded);
        CertificateFactory cf = CertificateFactory.getInstance(X509_CERTIFICATE_TYPE);
        InputStream certChainStream = new ByteArrayInputStream(certChainEncoded);
        try {
        	CertPath certPath = cf.generateCertPath(certChainStream, CERTIFICATION_CHAIN_ENCODING);
            this.certificationChainCoded = certPath.getCertificates().toArray(new Certificate[0]);
        } finally {
            try {
				certChainStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

	// GETTERS AND SETTERS //
	public String getMessageDigestDecoded() {
		return messageDigestDecoded;
	}

	public void setMessageDigestDecoded(String messageDigestDecoded) {
		this.messageDigestDecoded = messageDigestDecoded;
		this.messageDigestCoded = Base64.base64Decode(messageDigestDecoded);
	}
	
	public String getCertificationChainDecoded() {
		return certificationChainDecoded;
	}

	public void setCertificationChainDecoded(String certificationChainDecoded) throws CertificateException {
		this.certificationChainDecoded = certificationChainDecoded;
		this.loadCertPathFromBase64String(certificationChainDecoded);
	}
	
	public byte[] getMessageDigestCoded() {
		return messageDigestCoded;
	}

	/**
	 * 
	 * @param messageDigestCoded
	 */
	public void setMessageDigestCoded(byte[] messageDigestCoded) {
		this.messageDigestDecoded = Base64.base64Encode(messageDigestCoded);
		this.messageDigestCoded = messageDigestCoded;
	}

	public Certificate[] getCertificationChainCoded() {
		return certificationChainCoded;
	}

	/**
	 * 
	 * @return
	 * @throws SignatureException
	 */
	public Certificate getCertificate() throws SignatureException {
		if (this.certificationChainCoded != null)
			return this.certificationChainCoded[0];
		else
			throw new SignatureException("None signature to return.");
	}
	
	/**
	 * At current version, the certificate is a X509Certificate format (object). 
	 * @return
	 * @throws SignatureException
	 */
	public PublicKey getPublicKey() throws SignatureException {
		try {
			return getCertificate().getPublicKey();
		} catch (SignatureException e) {
			e.printStackTrace();
			throw new SignatureException("Problems when has trying access a current certificate, so don't have none PublicKey to return.");
		}
	}
	
	/**
	 * 
	 * @param certificationChainCoded
	 */
	public void setCertificationChainCoded(Certificate[] certificationChainCoded) {
		try {
			List<Certificate> certList = Arrays.asList(certificationChainCoded);
	        CertificateFactory certFactory = CertificateFactory.getInstance(X509_CERTIFICATE_TYPE);
	        CertPath certPath = certFactory.generateCertPath(certList);
	        byte[] certPathEncoded = certPath.getEncoded(CERTIFICATION_CHAIN_ENCODING);
	        this.certificationChainDecoded = Base64.base64Encode(certPathEncoded);
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		this.certificationChainCoded = certificationChainCoded;
	}
}