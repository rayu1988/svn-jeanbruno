/**
 * 
 */
package br.com.softplan.ocr.zip_unzip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import br.com.softplan.ocr.common.OCRUtil;

/**
 * @author jean.villete
 *
 */
public class OCRZip {

	private File 							input;
	private ZipOutputStream 				zipOutputStream;

	protected OCRZip(File input, File output) throws IOException {
		if (input == null) throw new IllegalArgumentException("argument input can not be null");
		
		this.input = input;
		
		if (output == null) {
			String outputName = (input.isFile() ? OCRUtil.removeExtension(input.getName()) : input.getName()) + OCRCompressService.EXTENSION_ZIP;
			output = new File(outputName);
		} else if (!output.exists()) {
			output.createNewFile();
		} else if (!output.isFile()) {
			throw new IllegalStateException("argument output must be a valid file");
		}
		
		FileOutputStream fileOutputStream = new FileOutputStream(output);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		this.zipOutputStream = new ZipOutputStream(bufferedOutputStream);
	}

	private void writeOut(File file, String zipEntryName) throws IOException {
		ZipEntry zipEntry = new ZipEntry(zipEntryName);
		this.zipOutputStream.putNextEntry(zipEntry);
		if (file.isFile()) {
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, OCRCompressService.BUFFER);
			byte data[] = new byte[OCRCompressService.BUFFER];
			int count;
			while((count = bufferedInputStream.read(data, 0, OCRCompressService.BUFFER)) != -1) {
				this.zipOutputStream.write(data, 0, count);
			}
			bufferedInputStream.close();
		}
	}
	
	private void compressWithinDirectory(File directory, String zipEntryName) throws IOException {
		File[] withinDirectory = directory.listFiles();
		for (File withinFile : withinDirectory) {
			String newZipEntryName = OCRUtil.isStringOk(zipEntryName) ? zipEntryName + System.getProperty("file.separator") + withinFile.getName() : withinFile.getName();
			
			if (withinFile.isDirectory()) {
				this.compressWithinDirectory(withinFile, newZipEntryName);
			} else {
				this.writeOut(withinFile, newZipEntryName);
			}
		}
	}
	
	protected void compress() throws IOException {
		if (this.input.isFile()) {
			this.writeOut(this.input, this.input.getName());
		} else if (this.input.isDirectory()) {
			this.compressWithinDirectory(this.input, "");
		}
		this.zipOutputStream.close();
	}
}
