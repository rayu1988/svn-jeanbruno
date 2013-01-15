/**
 * 
 */
package org.com.tatu.helper.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.com.tatu.helper.FileHelper;
import org.com.tatu.helper.GeneralsHelper;

/**
 * @author Jean Bruno
 *
 */
public class ZipHelper {
	
	private File 							input;
	private ZipOutputStream 				zipOutputStream;

	public ZipHelper(File input, File output) throws IOException {
		if (input == null) throw new IllegalArgumentException("argument input can not be null");
		
		this.input = input;
		
		if (output == null) {
			String outputName = (input.isFile() ? FileHelper.removeExtension(input.getName()) : input.getName()) + ZipConstants.EXTENSION_ZIP;
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
		if (file.isFile()) {
			ZipEntry zipEntry = new ZipEntry(zipEntryName);
			this.zipOutputStream.putNextEntry(zipEntry);
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, ZipConstants.BUFFER);
			byte data[] = new byte[ZipConstants.BUFFER];
			int count;
			while((count = bufferedInputStream.read(data, 0, ZipConstants.BUFFER)) != -1) {
				this.zipOutputStream.write(data, 0, count);
			}
			bufferedInputStream.close();
		} else {
			zipEntryName = zipEntryName.endsWith(System.getProperty("file.separator")) ? zipEntryName : zipEntryName + "/";
			ZipEntry zipEntry = new ZipEntry(zipEntryName);
			this.zipOutputStream.putNextEntry(zipEntry);
		}
	}
	
	private void compressWithinDirectory(File directory, String zipEntryName) throws IOException {
		File[] withinDirectory = directory.listFiles();
		for (File withinFile : withinDirectory) {
			String newZipEntryName = GeneralsHelper.isStringOk(zipEntryName) ? zipEntryName + System.getProperty("file.separator") + withinFile.getName() : withinFile.getName();
			
			this.writeOut(withinFile, newZipEntryName);
			if (withinFile.isDirectory()) {
				this.compressWithinDirectory(withinFile, newZipEntryName);
			}
		}
	}
	
	public void compress() throws IOException {
		if (this.input.isFile()) {
			this.writeOut(this.input, this.input.getName());
		} else if (this.input.isDirectory()) {
			this.compressWithinDirectory(this.input, "");
		}
		this.zipOutputStream.close();
	}
}
