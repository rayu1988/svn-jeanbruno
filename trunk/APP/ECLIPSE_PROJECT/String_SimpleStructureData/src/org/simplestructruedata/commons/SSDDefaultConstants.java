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
	char					NEW_LINE = '\n';
	char					TAB = '\t';
	
	/**
	 * These are the reserved characters: 	{ (opens braces)
	 * 										} (closes braces)
	 * 										" (quotation marks)
	 * 										, (comma)
	 * 
	 * These are characters to escape: 		\\ (escape)
	 * 										\n (new line)
	 * 										\t (a tab)
	 */
	char[]					RESERVED_CHARACTERS = new char[]{OPENS_BRACES,
															CLOSES_BRACES, 
															ASSIGN, 
															QUOTATION_MARKS, 
															COMMA,
															ESCAPE, 
															NEW_LINE, 
															TAB};
	
	String					ROOT_OBJECT = "root_object";
}
