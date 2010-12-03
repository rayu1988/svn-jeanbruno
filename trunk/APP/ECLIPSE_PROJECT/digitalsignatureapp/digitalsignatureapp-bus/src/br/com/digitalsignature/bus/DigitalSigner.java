package br.com.digitalsignature.bus;

import br.com.digitalsignature.entity.KeyStoreTO;
import br.com.digitalsignature.entity.MessageTO;
import br.com.digitalsignature.entity.SignatureTO;
import br.com.digitalsignature.exception.NoneMessageException;

public interface DigitalSigner {
	SignatureTO sign(KeyStoreTO keyStoreTO) throws NoneMessageException;
	void setMessage(MessageTO message);
}
