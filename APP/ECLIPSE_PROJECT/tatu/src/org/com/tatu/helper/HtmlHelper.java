package org.com.tatu.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Jean Bruno
 *
 */
public class HtmlHelper {

	private static final Map<String, String> characters;
	static {
		//html escape codes retrieved from http://www.theukwebdesigncompany.com/articles/entity-escape-characters.php
		characters = new HashMap<String, String>();
		characters.put("�", "&aacute;");
		characters.put("�", "&eacute;");
		characters.put("�", "&iacute;");
		characters.put("�", "&oacute;");
		characters.put("�", "&uacute;");
		characters.put("�", "&Aacute;");
		characters.put("�", "&Eacute;");
		characters.put("�", "&Iacute;");
		characters.put("�", "&Oacute;");
		characters.put("�", "&Uacute;");
		characters.put("�", "&acirc;");
		characters.put("�", "&ecirc;");
		characters.put("�", "&icirc;");
		characters.put("�", "&ocirc;");
		characters.put("�", "&ucirc;");
		characters.put("�", "&Acirc;");
		characters.put("�", "&Ecirc;");
		characters.put("�", "&Icirc;");
		characters.put("�", "&Ocirc;");
		characters.put("�", "&Ucirc;");
		characters.put("�", "&atilde;");
		characters.put("�", "&otilde;");
		characters.put("�", "&Atilde;");
		characters.put("�", "&Otilde;");
		characters.put("�", "&ntilde;");
		characters.put("�", "&Ntilde;");
		characters.put("�", "&agrave;");
		characters.put("�", "&egrave;");
		characters.put("�", "&igrave;");
		characters.put("�", "&ograve;");
		characters.put("�", "&ugrave;");
		characters.put("�", "&Agrave;");
		characters.put("�", "&Egrave;");
		characters.put("�", "&Igrave;");
		characters.put("�", "&Ograve;");
		characters.put("�", "&Ugrave;");
		characters.put("�", "&auml;");
		characters.put("�", "&euml;");
		characters.put("�", "&iuml;");
		characters.put("�", "&ouml;");
		characters.put("�", "&uuml;");
		characters.put("�", "&Auml;");
		characters.put("�", "&Euml;");
		characters.put("�", "&Iuml;");
		characters.put("�", "&Ouml;");
		characters.put("�", "&Uuml;");
		characters.put("�", "&ccedil;");
		characters.put("�", "&Ccedil;");
		characters.put("\r\n", "<br/>");
	}

	/**
	 * Method to get a HTML String from a text that contains Special Codes, e.g. the char "�" will be replaced to "&atilde;"
	 * @param text
	 * @return
	 */
	public String toHtml(String text) {
		for (String specialChar : characters.keySet()) {
			text = text.replace(specialChar, characters.get(specialChar));
		}
		return text;
	}
}
