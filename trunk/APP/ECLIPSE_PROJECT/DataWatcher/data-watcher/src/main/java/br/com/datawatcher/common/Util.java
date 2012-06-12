package br.com.datawatcher.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Properties;

public class Util {
	
	public static boolean isStringOk(String str) {
		return str != null && !str.trim().isEmpty();
	}
	
	public static boolean isCollectionOk(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}
	
	public static boolean isBooleanOk(String str) {
		return isStringOk(str) && (str.equals("true") || str.equals("1"));
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
	
	/**
	 * 	
	 * @param loadFrom
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static final Properties getLoadedProperties(File loadFrom) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(Util.getInstanceReaderUTF8(loadFrom));
		return properties;
	}
}
