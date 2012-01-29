/**
 * 
 */
package br.com.softplan.ocr.common;

/**
 * Utility class to holds Constants to Descriptor File Data Training.
 * 
 * @author jean.villete
 *
 */
public interface OCRConstantDataTraining {

	/**
	 * Descriptor Properties File Name.
	 */
	public static final String					DESCRIPTOR_PROPERTIES = "descriptor.properties";
	
	/**
	 * Descriptor Properties COMMENTS.
	 */
	public static final String					DESCRIPTOR_PROPERTIES_COMMENTS = 
			" --------------------------------------------------------------------------------------------------------------------------------------------------- \n"
			+ " File Descriptor \n"
			+ " Optical Character Recognation Data Training \n"
			+ " Tutorial; \n"
			+ " folder_identifier = font_name { referenced font name }, style { style list }, size { integer size }, characters_as { arrangement of characters at referenced folder } \n"
			+ " where; \n"
			+ "	folder_identifier (String) (required) = Folder name that contains a characters set to knowledge base training \n"
			+ " 	referenced font name (String) (required) = \"Arial\", \"Calibri\", \"Georgia\", \"Times New Roman\", etc... \n"
			+ " 	style list (String) (optional) (default=plain) = either \"plain\", \"italic\", \"bold\" or \"italic_bold\" \n"
			+ " 	size (Integer) (optional) (default=8) = integer value >= 8 and <= 72 \n"
			+ "	characters_as (String) (optional) (default=image_base) = \"structured\" (stored by the system), \"image_base\" (stored by a human) \n"
			+ " So, a real example; \n "
			+ "	Example 1, canonical description (Arial italic 11 as structured) \n"
			+ " myFolderNameContainsMyCharactersSet = font_name { Arial }, style { italic }, size {11}, characters_as { structured } \n"
			+ "	Example 2 , shor description (Arial plain 8 as image_base) \n"
			+ " myFolderNameContainsMyCharactersSetSimple = font_name  { Arial } \n"
			+ " --------------------------------------------------------------------------------------------------------------------------------------------------- ";
	
	/**
	 * Static file name image. Image file base when character_as { image_base }
	 */
	public static final String					FILE_IMAGE_BASE = "image.jpg";
	/**
	 * Static file name characters set. When character_as { image_base }
	 */
	public static final String					FILE_CHARACTERS = "characters.txt";
	
	/**
	 * Attribute to a specified font (descriptor), font_name
	 */
	public static final String					FONT_NAME = "font_name";
	
	/**
	 * Attribute to a specified font (descriptor), style
	 */
	public static final String					STYLE = "style";
	/**
	 * A valid value to attribute style, style { plain }
	 */
	public static final String					STYLE_PLAIN = "plain";
	/**
	 * A valid value to attribute style, style { italic }
	 */
	public static final String					STYLE_ITALIC = "italic";
	/**
	 * A valid value to attribute style, style { bold }
	 */
	public static final String					STYLE_BOLD = "bold";
	/**
	 * A valid value to attribute style, style { italic_bold }
	 */
	public static final String					STYLE_ITALIC_BOLD = "italic_bold";
	/**
	 * A valid value to attribute style, style { plain }
	 */
	public static final String					STYLE_DEFAULT = STYLE_PLAIN;
	
	/**
	 * Attribute to a specified font (descriptor), size
	 */
	public static final String					SIZE = "size";
	/**
	 * A valid value to attribute size, size { 8 }
	 */
	public static final String					SIZE_DEFAULT = "8";
	
	/**
	 * Attribute to a specified font (descriptor), character_as . Describes a disposal of the information within folder identifier.
	 */
	public static final String					CHARACTERS_AS = "characters_as";
	/**
	 * A valid value to attribute character_as, character_as { image_base }
	 */
	public static final String					CHARACTERS_IMAGE_BASE = "image_base";
	/**
	 * A valid value to attribute character_as, character_as { structured }
	 */
	public static final String					CHARACTERS_STRUCTURED = "structured";
	/**
	 * A valid value to attribute character_as, character_as { image_base }
	 */
	public static final String					CHARACTERS_AS_DEFAULT = CHARACTERS_IMAGE_BASE;
	
}
