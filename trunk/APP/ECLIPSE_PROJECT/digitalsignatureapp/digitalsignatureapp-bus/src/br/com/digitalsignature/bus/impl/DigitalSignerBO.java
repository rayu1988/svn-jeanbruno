package br.com.digitalsignature.bus.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.util.Enumeration;

import br.com.digitalsignature.bus.DigitalSigner;
import br.com.digitalsignature.entity.KeyStoreTO;
import br.com.digitalsignature.entity.MessageTO;
import br.com.digitalsignature.entity.SignatureTO;
import br.com.digitalsignature.exception.NoneMessageException;

/**
 * 
 * @author jean
 *
 */
public class DigitalSignerBO implements DigitalSigner {
	
	public static final String PKCS12_KEYSTORE_TYPE					= "PKCS12";
	public static final String DIGITAL_SIGNATURE_ALGORITHM_NAME		= "SHA1withRSA";
	
	private PrivateKey 			privateKey;
	private Certificate[] 		certificationChain;

	private MessageTO 			message;
	
	/**
	 * 
	 */
	@Override
	public SignatureTO sign(KeyStoreTO keyStoreTO) throws NoneMessageException {
		try {
			KeyStore keyStore = keyStoreTO.getKeyStore();
			this.setPrivateKeyAndCertChain(keyStore, keyStoreTO);
			Signature signatureAlgorithm = Signature.getInstance(DIGITAL_SIGNATURE_ALGORITHM_NAME);
	        signatureAlgorithm.initSign(this.privateKey);
	        signatureAlgorithm.update(this.message.getCodedMessage());
	        byte[] digitalSignature = signatureAlgorithm.sign();
	        
			SignatureTO signature = new SignatureTO(digitalSignature, this.certificationChain);
			
			return signature;
		} catch (SignatureException e) {
			e.printStackTrace();
			throw new NoneMessageException("Problems while was trying sign the message.");
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new NoneMessageException("Problems while was trying sign the message.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new NoneMessageException("Problems while was trying sign the message.");
		}
	}

    /**
     * @note from the original NakovDocumentSigner
     * 
     * @return private key and certification chain corresponding to it, extracted from
     * given keystore using given password to access the keystore and the same password
     * to access the private key in it. The keystore is considered to have only one
     * entry that contains both certification chain and the corresponding private key.
     * If the certificate has no entries, an exception is trown. It the keystore has
     * several entries, the first is used.
     */
    @SuppressWarnings("unchecked")
	private void setPrivateKeyAndCertChain(KeyStore keystore, KeyStoreTO keystoreTO) throws GeneralSecurityException {
        char[] password = keystoreTO.getPassword().toCharArray();
        Enumeration aliasesEnum = keystore.aliases();
        if (aliasesEnum.hasMoreElements()) {
            String alias = (String) aliasesEnum.nextElement();
            this.certificationChain = keystore.getCertificateChain(alias);
            this.privateKey = (PrivateKey) keystore.getKey(alias, password);
        } else {
            throw new KeyStoreException("The keystore is empty!");
        }
    }
	
	// GETTERS AND SETTERS //
	public MessageTO getMessage() {
		return message;
	}

	public void setMessage(MessageTO message) {
		this.message = message;
	}

}
