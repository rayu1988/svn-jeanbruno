/**
 * 
 */
package br.com.softplan.ocr.common;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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
		Properties tesseractProperties = new Properties();
		tesseractProperties.load(OCRUtil.getInstanceReaderUTF8(loadFrom));
		return tesseractProperties;
	}
	
	/**
    * @return the directory of the running jar
    */
   public static File getBaseDir(Object aType) {
       URL dir = aType.getClass().getResource("/" + aType.getClass().getName().replaceAll("\\.", "/") + ".class");
       File dbDir = new File(System.getProperty("user.dir"));

       try {
           if (dir.toString().startsWith("jar:")) {
               dir = new URL(dir.toString().replaceFirst("^jar:", "").replaceFirst("/[^/]+.jar!.*$", ""));
               dbDir = new File(dir.toURI());
           }
       } catch (MalformedURLException mue) {
           mue.printStackTrace();
       } catch (URISyntaxException use) {
           use.printStackTrace();
       }
       return dbDir;
   }
   
	/**
	 * Returns the contents of the file in a byte array.
	 * 
	 * @from @link http://www.java-tips.org/java-se-tips/java.io/reading-a-file-into-a-byte-array.html
	 * @param file
	 * @return
	 * @throws IOException
	 */
   public static byte[] getBytesFromFile(File file) throws IOException {        
       // Get the size of the file
       long length = file.length();

       // You cannot create an array using a long type.
       // It needs to be an int type.
       // Before converting to an int type, check
       // to ensure that file is not larger than Integer.MAX_VALUE.
       if (length > Integer.MAX_VALUE) {
           // File is too large
           throw new IOException("File is too large!");
       }

       // Create the byte array to hold the data
       byte[] bytes = new byte[(int)length];

       // Read in the bytes
       int offset = 0;
       int numRead = 0;
       InputStream is = new FileInputStream(file);
       try {
           while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
               offset += numRead;
           }
       } finally {
           is.close();
       }

       // Ensure all the bytes have been read in
       if (offset < bytes.length) {
           throw new IOException("Could not completely read file "+file.getName());
       }
       return bytes;
   }
}
