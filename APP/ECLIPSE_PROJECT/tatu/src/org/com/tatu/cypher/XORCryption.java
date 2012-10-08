package org.com.tatu.cypher;

import org.com.tatu.helper.checking.Parameter;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

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
	
	public String encodeToBase64(String message) {
		return Base64.encode(this.xorEncode(message).getBytes());
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

	public String decodeFromBase64(String message) {
		byte[] arrayMessage = Base64.decode(message);
		return this.xorDecode(new String(arrayMessage));
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