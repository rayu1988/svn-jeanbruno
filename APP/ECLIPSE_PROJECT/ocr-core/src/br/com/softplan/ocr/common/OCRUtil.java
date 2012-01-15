/**
 * 
 */
package br.com.softplan.ocr.common;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Properties;

/**
 * @author jean.villete
 *
 */
public class OCRUtil {
	
	public static boolean isStringOk(String str) {
		return str != null && !str.trim().isEmpty();
	}
	
	public static boolean isCollectionOk(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}
	
	public static String removeExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	
	/**
	 * Returns an array index from a matrix coordinates.
	 * @param x
	 * @param y
	 * @return
	 */
	public static int matrixIndexToArrayIndex(int x, int y, int matrixWidth) {
		return (y * matrixWidth) + x;
	}
	
    /**
     * 
     * @param rgbPixels
     * @param width
     * @param height
     * @param comp
     * @return
     */
    public static Image rgbToImage(int[] rgbPixels, int width, int height, Component comp) {
        return comp.createImage(new MemoryImageSource(width, height, rgbPixels, 0, width));
    }

    /**
     * Converte os bits de informação da imagem 
     * @param pixels
     * @return
     */
    public static final int[] grayScaleToRGB(int[] pixels) {
        int[] newPixels = new int[pixels.length];
        for (int i = 0; i < newPixels.length; i++) {
            int pix = pixels[i] & 0xff;
            newPixels[i] = pix | (pix << 8) | (pix << 16) | 0xff000000;
        }
        
        return newPixels;
    }


	/**
	 * Method to create a defined idetifier font, identifier to Properties and font's folder.
	 * @param font
	 * @return
	 */
	public static final String getFontPropertyKey(Font font) {
		StringBuffer strReturn = new StringBuffer();
		strReturn.append(font.getName());
		strReturn.append(OCRUtil.getStyleAsText(font.getStyle()));
		strReturn.append(font.getSize());
		
		return strReturn.toString().replace(" ", "").replace(".", "");
	}
	
	/**
	 * font_name {?}, style { ? }, size {?}, characters_as { structured }
	 * @param font
	 * @return
	 */
	public static final String getFontPropertyValue(Font font) {
		StringBuffer strReturn = new StringBuffer();
		// font_name {?}
		strReturn.append(OCRConstantDataTraining.FONT_NAME + "{").append(font.getName()).append("}");
		// , style { ? }
		strReturn.append(", ").append(OCRConstantDataTraining.STYLE + "{").append(OCRUtil.getStyleAsText(font.getStyle())).append("}");
		// , size {?}
		strReturn.append(", ").append(OCRConstantDataTraining.SIZE + "{").append(font.getSize()).append("}");
		// , characters_as { structured }
		strReturn.append(", ").append(OCRConstantDataTraining.CHARACTERS_AS + "{").append(OCRConstantDataTraining.CHARACTERS_STRUCTURED).append("}");
		
		return strReturn.toString();
	}
    
    public static final String getStyleAsText(int style) {
    	if (style == 0) {
    		return OCRConstantDataTraining.STYLE_PLAIN;
    	} else if (style == 1) {
    		return OCRConstantDataTraining.STYLE_BOLD;
    	} else if (style == 2) {
    		return OCRConstantDataTraining.STYLE_ITALIC;
    	} else if (style == 3) {
    		return OCRConstantDataTraining.STYLE_ITALIC_BOLD;
    	} else throw new IllegalArgumentException();
    }
    
    /**
	 * Build inner properties.
	 * Inner properties are detailed declaration of the current font.
	 * @param fontProperties
	 * @return
	 */
	public static final Properties getDefaultFontProperties(String fontProperties) {
		Properties propsToReturn = new Properties();
		for (String splitString : fontProperties.split(",")) {
			String fontPropertyKey = splitString.split("\\{")[0];
			String fontPropertyValue = splitString.split("\\{")[1];
			propsToReturn.setProperty(fontPropertyKey.trim(), fontPropertyValue.replace("}", "").trim());
		}
		
		// verify declaring requires
		if (propsToReturn.get(OCRConstantDataTraining.FONT_NAME) == null) {
			throw new IllegalStateException(OCRConstantDataTraining.FONT_NAME + " is required");
		}

		// verify declaring defaults
		if (propsToReturn.get(OCRConstantDataTraining.STYLE) == null) { // default style
			propsToReturn.setProperty(OCRConstantDataTraining.STYLE, OCRConstantDataTraining.STYLE_DEFAULT);
		}
		if (propsToReturn.get(OCRConstantDataTraining.SIZE) == null) { // default size
			propsToReturn.setProperty(OCRConstantDataTraining.SIZE, OCRConstantDataTraining.SIZE_DEFAULT);
		}
		if (propsToReturn.get(OCRConstantDataTraining.CHARACTERS_AS) == null) { // default characters_as
			propsToReturn.setProperty(OCRConstantDataTraining.CHARACTERS_AS, OCRConstantDataTraining.CHARACTERS_AS_DEFAULT);
		}
		
		return propsToReturn;
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

	/**
	 * Method to get a prepared font which information within fontProperties.
	 * Properties must be like: font_name {?}, style { ? }, size {?}, characters_as { structured }
	 * @param fontProperties
	 * @return
	 */
	public static final Font getFontFromProperties(Properties fontProperties) {
		String fontName = fontProperties.getProperty(OCRConstantDataTraining.FONT_NAME); 
		int size = Integer.parseInt(fontProperties.getProperty(OCRConstantDataTraining.SIZE));
		int style;
		if (fontProperties.getProperty(OCRConstantDataTraining.STYLE).equals(OCRConstantDataTraining.STYLE_PLAIN)) {
			style = Font.PLAIN;
		} else if (fontProperties.getProperty(OCRConstantDataTraining.STYLE).equals(OCRConstantDataTraining.STYLE_ITALIC)) {
			style = Font.ITALIC;
		} else if (fontProperties.getProperty(OCRConstantDataTraining.STYLE).equals(OCRConstantDataTraining.STYLE_BOLD)) {
			style = Font.BOLD;
		} else if (fontProperties.getProperty(OCRConstantDataTraining.STYLE).equals(OCRConstantDataTraining.STYLE_ITALIC_BOLD)) {
			style = Font.ITALIC | Font.BOLD;
		} else throw new IllegalStateException();
		
		return new Font(fontName, style, size);
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
