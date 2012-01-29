/**
 * 
 */
package br.com.softplan.ocr.zip_unzip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * @author jean.villete
 *
 */
public class OCRUnzip {

	private File							folderOutput;
	private ZipInputStream 					zipInputStream;
	
	protected OCRUnzip(File input, File folderOutput) throws FileNotFoundException {
		if (input == null || folderOutput == null) throw new IllegalArgumentException("arguments can not be null");
		
		if (!folderOutput.isDirectory()) { throw new IllegalStateException("folderOutput must be a valid directory");
		} else if (folderOutput.exists()) { folderOutput.mkdir(); }
		
		this.folderOutput = folderOutput;
		
		FileInputStream fileInputStream = new FileInputStream(input);
		this.zipInputStream = new ZipInputStream(fileInputStream);
	}
	
	protected void decompress() throws IOException {
		ZipEntry currentEntry;
		while ((currentEntry = this.zipInputStream.getNextEntry()) != null) {
			File currentFile = new File(this.folderOutput.getPath() + System.getProperty("file.separator") + currentEntry.getName());
			
            if (currentFile.isDirectory()) {
            	currentFile.mkdir();
            	continue;
            } else {
            	File parent = new File(currentFile.getParent());
            	if (!parent.exists()) parent.mkdir();
            }
            
			byte data[] = new byte[OCRCompressService.BUFFER];
			int count;
			FileOutputStream fileOutputStream = new FileOutputStream(currentFile);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, OCRCompressService.BUFFER);
			while ((count = this.zipInputStream.read(data, 0, OCRCompressService.BUFFER)) != -1) {
               bufferedOutputStream.write(data, 0, count);
            }
			bufferedOutputStream.flush();
            bufferedOutputStream.close();
		}
		this.zipInputStream.close();
	}
}
