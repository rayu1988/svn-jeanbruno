/**
 * 
 */
package org.simplestructruedata.commons;

/**
 * @author carrefour
 *
 */
public interface SSDDefaultConstants {

	char					OPENS_BRACES = '{';
	char					CLOSES_BRACES = '}';
	char					QUOTATION_MARKS = '"';
	char					COMMA = ',';
	char					ASSIGN = '=';
	char					ESCAPE = '\\';
	
	/**
	 * These are the reserved characters: 	{ (opens braces)
	 * 										} (closes braces)
	 * 										" (quotation marks)
	 * 										, (comma)
	 */
	char[]					RESERVED_CHARACTERS = new char[]{OPENS_BRACES, CLOSES_BRACES, ASSIGN, QUOTATION_MARKS, COMMA, ESCAPE};
	
	String					ROOT_OBJECT = "root_object";
}
