/**
 * 
 */
package org.simplestructruedata.commons;

/**
 * @author carrefour
 *
 */
public class SSDUtils {
	
	public static boolean isReservedCharacter(char character) {
		for (char reservedCharacter : SSDDefaultConstants.RESERVED_CHARACTERS)
			if (reservedCharacter == character)
				return true;
		return false;
	}

}
