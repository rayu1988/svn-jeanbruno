package br.com.digitalsignature.bus;

import br.com.digitalsignature.entity.KeyStoreTO;
import br.com.digitalsignature.entity.MessageTO;
import br.com.digitalsignature.entity.SignatureTO;
import br.com.digitalsignature.exception.NoneMessageException;
import br.com.digitalsignature.exception.SignatureException;

public interface DigitalSigner {
	SignatureTO sign(KeyStoreTO keyStoreTO) throws NoneMessageException;
	boolean verify(SignatureTO signature) throws SignatureException;
	void setMessage(MessageTO message);
}
