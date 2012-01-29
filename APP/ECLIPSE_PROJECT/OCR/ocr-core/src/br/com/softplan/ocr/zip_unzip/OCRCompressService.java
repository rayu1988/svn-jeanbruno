/**
 * 
 */
package br.com.softplan.ocr.zip_unzip;

import java.io.File;
import java.io.IOException;


/**
 * @author jean.villete
 *
 */
public class OCRCompressService {
	
	protected static final String 			EXTENSION_ZIP = ".zip";
	protected static final int 				BUFFER = 2048;
	
	public static void compress(File fileInput, File fileOutput) throws IOException {
		OCRZip zipping = new OCRZip(fileInput, fileOutput);
		zipping.compress();
	}
	
	public static void decompress(File fileInput, File folderOutput) throws IOException {
		OCRUnzip unzipping = new OCRUnzip(fileInput, folderOutput);
		unzipping.decompress();
	}
}
