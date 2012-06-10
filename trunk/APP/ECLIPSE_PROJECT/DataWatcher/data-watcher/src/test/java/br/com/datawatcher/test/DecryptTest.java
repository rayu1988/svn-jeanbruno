package br.com.datawatcher.test;

import br.com.datawatcher.common.Decryptable;

public class DecryptTest implements Decryptable {

	@Override
	public String decrypt(String encryptedString) {
		return encryptedString.replaceAll("1", "");
	}

}
