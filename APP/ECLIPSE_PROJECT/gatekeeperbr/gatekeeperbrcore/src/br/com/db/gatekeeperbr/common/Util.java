/**
 * 
 */
package br.com.db.gatekeeperbr.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.db.gatekeeperbr.exception.GatekeeperBRLoaderException;

/**
 * @author JNVE
 *
 */
public class Util {
	
	public static boolean isStringOk(String str) {
        return str != null && !str.trim().isEmpty();
	}

	public static boolean isCollectionOk(Collection<?> collection) {
	        return collection != null && !collection.isEmpty();
	}

	public static String removeExtension(String fileName) {
	        return fileName.substring(0, fileName.lastIndexOf("."));
	}

	public static String characterAtTheEnd(Character character, String strBase) {
		return !strBase.endsWith(character.toString()) ? strBase + character : strBase;
	}

	/**
	 * Method to create a Temporary Directory to decompress the OCR Data Training.
	 * @return
	 */
	public static final File createTmpDir(String identifierTmpDir) {
        String tmpDirPath = System.getProperty("java.io.tmpdir");
        File ocrTmpDir = new File(tmpDirPath + identifierTmpDir + Long.toString(System.nanoTime()));
        ocrTmpDir.mkdir();
        ocrTmpDir.deleteOnExit();
        return ocrTmpDir;
	}

	public static final String getValueElementDOM(Element elementBase, String tagName, boolean required) throws GatekeeperBRLoaderException {
		NodeList nodeList = elementBase.getElementsByTagName(tagName);
		if (required && (nodeList == null || nodeList.getLength() != 1)) {
			throw new GatekeeperBRLoaderException("Must declare one and just one tag: " + tagName);
		}
		Node node = nodeList.item(0);
		NodeList text = node.getChildNodes();
		return ((Node) text.item(0)).getNodeValue();
	}
	
	/**
	 * A way to standard writters utf-8, centered.
	 * @param outputFile
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static final OutputStreamWriter getInstanceWriterUTF8(File outputFile) throws UnsupportedEncodingException, FileNotFoundException {
		return new OutputStreamWriter(new FileOutputStream(outputFile) , "UTF-8");
	}
	
	/**
	 * A way to standard readers utf-8, centered.
	 * @param inputFile
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static final InputStreamReader getInstanceReaderUTF8(File inputFile) throws UnsupportedEncodingException, FileNotFoundException {
		return new InputStreamReader(new FileInputStream(inputFile), "UTF-8");
	}
}
