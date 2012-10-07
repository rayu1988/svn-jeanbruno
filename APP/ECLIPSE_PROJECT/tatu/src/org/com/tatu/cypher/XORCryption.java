package org.com.tatu.cypher;

import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.checking.Parameter;

/**
 * Got from: http://forum.x86labs.org/index.php?topic=6406.0
 * @author carrefour
 *
 */
public class XORCryption {

	private String		key;
	
	public XORCryption(String key) {
		Parameter.check(key).notNull().notEmpty();
		this.key = key;
	}
	
	/**
	 * Encodes the message to an hex string message.
	 * @param message
	 * @return
	 */
	public String encodeToHexString(String message) {
		Parameter.check(message).notEmpty();
		message = this.xorEncode(message);
		StringBuffer charEncoded = new StringBuffer();
		for(int i = 0; i < message.length(); i++) {
			String hexString = Integer.toHexString((int)message.charAt(i));
			charEncoded.append(hexString.length()+hexString);
		}
		return charEncoded.toString();
	}
	
	/**
		Encodes a string
		@param data Data to encode
		@param key Key to encode with
	*/
	public String xorEncode(String data) {
		byte m_cData[] = data.getBytes();
		byte m_cKey [] = this.key.getBytes();

		int keyPointer = 0;
		for(int i = 0; i < m_cData.length; i++) {
			m_cData[i] ^= m_cKey[keyPointer];
			keyPointer += m_cData[i];
			keyPointer %= m_cKey.length;
		}

		return new String(m_cData);
	}

	/**
	 * Encodes the message to an hex string message.
	 * @param message
	 * @return
	 */
	public String decodeHexString(String message) {
		Parameter.check(message).notEmpty();
		List<String> characters = new ArrayList<String>();
		StringBuffer charDecoded = new StringBuffer();
		for (int i = 0 ; i < message.length() ; ) {
			int sizeNextChar = Integer.parseInt(message.substring(i, (++i)));
			int currentPosition = i;
			Integer character = Integer.parseInt(message.substring(currentPosition, currentPosition + sizeNextChar), 16);
			
			charDecoded.append(Character.toChars(character)[0]);
			
			characters.add(character.toString());
			i = i + sizeNextChar;
		}
		
		return this.xorDecode(charDecoded.toString());
	}
	
	/**
		Decodes a string
		@param data Data to decode
		@param key Key to decode with
	*/
	public String xorDecode(String data) {
		byte m_cData[] = data.getBytes();
		byte m_cKey [] = this.key.getBytes();

		// This was a little interesting to code, because by the time
		// we increase the keyPointer, what we have to increase it by
		// is already destroyed by the line above it. Therefore, we
		// have to set keyPointerAdd before we decrypt the byte that
		// holds what's added to the pointer.
		int keyPointer = 0;
		byte keyPointerAdd = 0;
		for(int i = 0; i < m_cData.length; i++) {
			keyPointerAdd = m_cData[i];
			m_cData[i] ^= m_cKey[keyPointer];
			keyPointer += keyPointerAdd;
			keyPointer %= m_cKey.length;
		}

		return new String(m_cData);
	}

}