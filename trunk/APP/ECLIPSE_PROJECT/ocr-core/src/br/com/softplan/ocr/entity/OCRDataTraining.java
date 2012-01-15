/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.awt.Font;
import java.io.Serializable;

/**
 * @author jean.villete
 *
 */
public class OCRDataTraining implements Serializable {
	
	private static final long 			serialVersionUID = -8028936867643674606L;

	/**
	 * The correspond pixels (binary) image of the current character.
	 */
	private OCRImage					image;
	
	/**
	 * The font (name, style and size) of the declared character present in the image.
	 */
	private Font						font;
	
	/**
	 * The declared real character present in the image.
	 */
	private Character					character;
	
	/**
     * Fraction of the row height which is occupied by complete whitespace above the character.
     */
    private float 						topWhiteSpaceFraction = 0f;
    private int							topWhiteSpacePixels = 0;
    
    /**
     * Fraction of the row height which is occupied by complete whitespace below the character.
     */
    private float 						bottomWhiteSpaceFraction = 0f;
    private int 						bottomWhiteSpacePixels = 0;
    
    // control attributes
    private int 						myMaxX;
    private int 						myMaxY;
    
    public OCRDataTraining(OCRImage image, int topWhiteSpacePixels, int bottomWhiteSpacePixels, Font font, Character character) {
    	if (image == null || font == null || character == null) throw new IllegalArgumentException();
    	if (topWhiteSpacePixels < 0 || bottomWhiteSpacePixels < 0) throw new IllegalArgumentException();
    	
    	this.image = image;
    	this.font  = font;
    	this.character = character;
    	this.topWhiteSpacePixels = topWhiteSpacePixels;
    	this.bottomWhiteSpacePixels = bottomWhiteSpacePixels;
    	
    	int rowHeight = this.topWhiteSpacePixels + image.getImageScale().getHeight() + this.bottomWhiteSpacePixels;
        
        this.topWhiteSpaceFraction = (float) this.topWhiteSpacePixels / (float) rowHeight;
        this.bottomWhiteSpaceFraction = (float) this.bottomWhiteSpacePixels / (float) rowHeight;
    	
    	this.myMaxX = image.getImageScale().getWidth() - 1;
    	this.myMaxY = image.getImageScale().getHeight() - 1;
    }
    
    /**
     * Calculating hashCode through character concat with font's attributes, then attemption! Can not exists two or more OCRDataTrainig with the same character and font (name, style and size) on the
     * 	OCRDataTrainingService's Map.
     */
    @Override
    public int hashCode() {
    	return new Font(this.character + this.font.getName(), this.font.getStyle(), this.font.getSize()).hashCode();
    }

    // GETTERS //
	public OCRImage getImage() {
		return image;
	}
	public Font getFont() {
		return font;
	}
	public Character getCharacter() {
		return character;
	}
	public float getTopWhiteSpaceFraction() {
		return topWhiteSpaceFraction;
	}
	public float getBottomWhiteSpaceFraction() {
		return bottomWhiteSpaceFraction;
	}
	public int getMyMaxX() {
		return myMaxX;
	}
	public int getMyMaxY() {
		return myMaxY;
	}
	public int getTopWhiteSpacePixels() {
		return topWhiteSpacePixels;
	}
	public int getBottomWhiteSpacePixels() {
		return bottomWhiteSpacePixels;
	}
}