package br.com.digitalsignature.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import br.com.digitalsignature.exception.NoneMessageException;
import br.com.digitalsignature.utils.Util;

/**
 * 
 * @author jean
 *
 */
public class MessageTO {
	public static final String charSetISO88591 = "ISO-8859-1";
	public static final String charSetUTF8 = "UTF-8";
	
	private File 		file;
	private String 		string;
	private byte[] 		codedMsg;
	
	/**
	 * 
	 * @param file
	 */
	public MessageTO(File file) {
		this.file = file;
	}
	
	/**
	 * 
	 * @param string
	 */
	public MessageTO(String string) {
		this.string = string;
	}
	
	/**
	 * 
	 * @param codedMsg
	 */
	public MessageTO(byte[] codedMsg) {
		this.codedMsg = codedMsg;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws NoneMessageException 
	 */
	public byte[] getCodedMessage() throws IOException, NoneMessageException {
		if (file != null)
			return this.readFile(file);
		else if (Util.stringOk(this.string))
			return this.string.getBytes(charSetUTF8);
		else if (this.codedMsg != null)
			return this.codedMsg;
		else
			throw new NoneMessageException("None valid message into.");
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private byte[] readFile(File file) throws IOException {
		FileInputStream fileStream = new FileInputStream(file);
        try {
            int fileSize = (int) file.length();
            byte[] data = new byte[fileSize];
            int bytesRead = 0;
            while (bytesRead < fileSize) {
                bytesRead += fileStream.read(data, bytesRead, fileSize-bytesRead);
            }
            return data;
        } finally {
            fileStream.close();
        }
	}
}