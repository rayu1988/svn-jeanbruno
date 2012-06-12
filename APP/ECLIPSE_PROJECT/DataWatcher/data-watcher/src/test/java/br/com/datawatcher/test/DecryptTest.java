package br.com.datawatcher.test;

import br.com.datawatcher.interfaces.Decryptable;

public class DecryptTest implements Decryptable {

	@Override
	public String decrypt(String encryptedString) {
		return encryptedString.replaceAll("1", "");
	}

}
