package org.com.tatu.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author villjea
 *
 */
public class HtmlHelper {

	private static final Map<String, String> characters;
	static {
		//html escape codes retrieved from http://www.theukwebdesigncompany.com/articles/entity-escape-characters.php
		characters = new HashMap<String, String>();
		characters.put("á", "&aacute;");
		characters.put("é", "&eacute;");
		characters.put("í", "&iacute;");
		characters.put("ó", "&oacute;");
		characters.put("ú", "&uacute;");
		characters.put("Á", "&Aacute;");
		characters.put("É", "&Eacute;");
		characters.put("Í", "&Iacute;");
		characters.put("Ó", "&Oacute;");
		characters.put("Ú", "&Uacute;");
		characters.put("â", "&acirc;");
		characters.put("ê", "&ecirc;");
		characters.put("î", "&icirc;");
		characters.put("ô", "&ocirc;");
		characters.put("û", "&ucirc;");
		characters.put("Â", "&Acirc;");
		characters.put("Ê", "&Ecirc;");
		characters.put("Î", "&Icirc;");
		characters.put("Ô", "&Ocirc;");
		characters.put("Û", "&Ucirc;");
		characters.put("ã", "&atilde;");
		characters.put("õ", "&otilde;");
		characters.put("Ã", "&Atilde;");
		characters.put("Õ", "&Otilde;");
		characters.put("ñ", "&ntilde;");
		characters.put("Ñ", "&Ntilde;");
		characters.put("à", "&agrave;");
		characters.put("è", "&egrave;");
		characters.put("ì", "&igrave;");
		characters.put("ò", "&ograve;");
		characters.put("ù", "&ugrave;");
		characters.put("À", "&Agrave;");
		characters.put("È", "&Egrave;");
		characters.put("Ì", "&Igrave;");
		characters.put("Ò", "&Ograve;");
		characters.put("Ù", "&Ugrave;");
		characters.put("ä", "&auml;");
		characters.put("ë", "&euml;");
		characters.put("ï", "&iuml;");
		characters.put("ö", "&ouml;");
		characters.put("ü", "&uuml;");
		characters.put("Ä", "&Auml;");
		characters.put("Ë", "&Euml;");
		characters.put("Ï", "&Iuml;");
		characters.put("Ö", "&Ouml;");
		characters.put("Ü", "&Uuml;");
		characters.put("ç", "&ccedil;");
		characters.put("Ç", "&Ccedil;");
		characters.put("\r\n", "<br/>");
	}

	/**
	 * Method to get a HTML String from a text that contains Special Codes, e.g. the char "ã" will be replaced to "&atilde;"
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
