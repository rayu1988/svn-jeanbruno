package br.com.digitalsignature.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import br.com.digitalsignature.bus.impl.DigitalSignerBO;
import br.com.digitalsignature.exception.KeyStoreException;
import br.com.digitalsignature.utils.Util;

/**
 * This class has the simple job of keeps the attributes from KeyStore. 
 * @author jean
 *
 */
public class KeyStoreTO {
	private String		password;
	private String 		pathKeystore;
	private KeyStore	keyStore;
	
	public KeyStoreTO(String password, String pathKeystore) throws KeyStoreException, GeneralSecurityException, IOException {
		if (!Util.stringOk(password) || !Util.stringOk(pathKeystore))
			throw new KeyStoreException("To keystore must has the address keystore file and it respectively password.");
		
		this.password = password;
		this.pathKeystore = pathKeystore;
		this.loadKeyStoreFromPFXFile();
	}
	
	/**
	 * @note from the original NakovDocumentSigner
	 * 
     * Loads a keystore from .PFX or .P12 file (file format should be PKCS#12)
     * using given keystore password.
     */
    private void loadKeyStoreFromPFXFile() throws GeneralSecurityException, IOException {
    	FileInputStream keyStoreStream = new FileInputStream(this.pathKeystore);
        this.keyStore = KeyStore.getInstance(DigitalSignerBO.PKCS12_KEYSTORE_TYPE);
        char[] password = this.password.toCharArray();
        this.keyStore.load(keyStoreStream, password);
    }

    // GETTERS AND SETTERS //
    public KeyStore getKeyStore() {
		return this.keyStore;
	}
    
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws GeneralSecurityException, IOException {
		this.password = password;
		this.loadKeyStoreFromPFXFile();
	}

	public String getPathKeystore() {
		return pathKeystore;
	}

	public void setPathKeystore(String pathKeystore) throws GeneralSecurityException, IOException {
		this.pathKeystore = pathKeystore;
		this.loadKeyStoreFromPFXFile();
	}
}