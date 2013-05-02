/**
 * 
 */
package org.com.tatu.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.parameter.Parameter;

/**
 * @author Jean Bruno
 *
 */
public class FileHelper {
	
	private String rootDirectory;
	private String[] filePattern;
	
	public FileHelper(String rootDirectory) {
		Parameter.check(rootDirectory).notEmpty();
		this.rootDirectory = rootDirectory;
	}
	
	/**
	 * Method to get a List of files/directories desired in the filePattern array of String.
	 * @param filePattern
	 * @return
	 */
	public List<File> find(String[] filePattern) {
		Parameter.check(filePattern).notNull();
		this.filePattern = filePattern;
		return this.find(new File(this.rootDirectory), new ArrayList<File>());
	}
	
	/**
	 * Method to delete the files/directories inside the rootDirectory.
	 * @param filePattern
	 */
	public void delete(String[] filePattern) {
		Parameter.check(filePattern).notNull();
		this.filePattern = filePattern;
		for (File inside : this.find(filePattern)) {
			if (inside.isFile()) {
				inside.delete();
			} else if (inside.isDirectory()) {
				this.cleanDirectory(inside, true);
			}
		}
	}
	
	/**
	 * Method to clean a directory.
	 * Optionally the file base can be deleted too.
	 * 
	 * @param deleteBase
	 */
	public void cleanDirectory(boolean deleteBase) {
		this.cleanDirectory(new File(this.rootDirectory), deleteBase);
	}
	
	/**
	 * Method to clean a directory.
	 * Optionally the file base can be deleted too.
	 * 
	 * @param base
	 * @param deleteBase
	 */
	private void cleanDirectory(File base, boolean deleteBase) {
		if (base.isFile()) {
			base.delete();
		} else if (base.isDirectory()) {
			for (File inside : base.listFiles()) {
				this.cleanDirectory(inside, true);
			}
			if (deleteBase) {
				base.delete();
			}
		} else throw new IllegalStateException("Point should never be achieved!");
	}

	private List<File> find(File base, List<File> found) {
		if (this.isMapped(base.getName()))
			found.add(base);
		
		if (base.isDirectory())
			for (File inside : base.listFiles())
				this.find(inside, found);
		
		return found;
	}
	
	private boolean isMapped(String str) {
		for (String mapped : this.filePattern) {
			if (str.equals(mapped)) return true;
			if (mapped.startsWith("*")) {
				mapped = mapped.substring(1);
				if (str.contains(mapped)) return true;
			}
		}
		return false;
	}
	
	public static String removeExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
}
