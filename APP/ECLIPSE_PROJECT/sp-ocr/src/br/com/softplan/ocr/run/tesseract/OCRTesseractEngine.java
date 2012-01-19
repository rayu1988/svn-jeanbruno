/**
 * 
 */
package br.com.softplan.ocr.run.tesseract;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.softplan.ocr.common.OCRConstant;
import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.exception.OCRExtractingException;
import br.com.softplan.ocr.run.OCREngine;

/**
 * Utility class that aims get a hOCR data result with Tesseract Engine.
 * @author jean.villete
 *
 */
public class OCRTesseractEngine implements OCREngine {
	
	public static final String 				TESSERACT_ENGINE = "Tesseract Engine";
	private static final String 			RESOURCE_CONFIGS;
	public static final File 				CONFIGS_FILE;
	private static final String 			OUTPUT_FILE_NAME;
	private static final String 			FILE_EXTENSION_HOCR;
	private static final String 			LANG_OPTION;
	private static final String 			PSM_OPTION;

	static {
		RESOURCE_CONFIGS = "tesseract/tesseract_configs";
		CONFIGS_FILE = new File(ClassLoader.getSystemResource(RESOURCE_CONFIGS).getFile());
		OUTPUT_FILE_NAME = "TessOutput";
		FILE_EXTENSION_HOCR = ".html";
		LANG_OPTION = "-l";
		PSM_OPTION = "-psm";
	}
	
    private String tessPath;
    private String currentLanguage;
    private String psm = "3"; // Fully automatic page segmentation, but no OSD (default)
    
    /** Creates a new instance of OCREngine */
    public OCRTesseractEngine(String tessPath, String currentLanguage, String psm) {
    	if (!OCRUtil.isStringOk(tessPath) || !OCRUtil.isStringOk(currentLanguage) || !OCRUtil.isStringOk(psm)) {
    		throw new IllegalArgumentException();
    	}
    	
        this.tessPath = tessPath;
        this.currentLanguage = currentLanguage;
        this.psm = psm;
    }

    /**
     * Method to invoke the tesseract engine to extract text contained at the image passed as parameter.
     * The return is the string extracted formated as hOCR standard.
     * 
     * @param tiffFiles
     * @return To each imageFile passed as parameter, will there a String hOCR in the same index at the List returned. A list of hOCR String.
     * @throws OCRExtractingException 
     */
    public List<String> run(final List<File> tiffFiles) throws OCRExtractingException {
		try {
			File tempTessOutputFile=null;
			tempTessOutputFile = File.createTempFile(OUTPUT_FILE_NAME, FILE_EXTENSION_HOCR);
	        String outputFileName = tempTessOutputFile.getPath().substring(0, tempTessOutputFile.getPath().length() - FILE_EXTENSION_HOCR.length()); // chop the .txt extension
	
	        List<String> cmd = new ArrayList<String>();
	        cmd.add(tessPath + "/tesseract");
	        cmd.add(""); // placeholder for inputfile
	        cmd.add(outputFileName);
	        if (OCRUtil.isStringOk(this.currentLanguage)) {
	        	cmd.add(LANG_OPTION);
	        	cmd.add(this.currentLanguage);
	        }
	        cmd.add(PSM_OPTION);
	        cmd.add(psm);
	       	cmd.add("+" + CONFIGS_FILE.getAbsoluteFile());
	
	        ProcessBuilder pb = new ProcessBuilder();
	        pb.directory(new File(System.getProperty("user.home")));
	        pb.redirectErrorStream(true);

	        List<String> hOCR = new ArrayList<String>();
	        for (File tiffFile : tiffFiles) {
	            cmd.set(1, tiffFile.getPath());
	            pb.command(cmd);
	            Process process = pb.start();
	            // any error message?
	            // this has become unneccesary b/c the standard error is already merged with the standard output
	            // StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream());
	            // errorGobbler.start();
	            // any output?
	            OCRStreamGobbler outputGobbler = new OCRStreamGobbler(process.getInputStream());
	            outputGobbler.start();
	
	            int w = process.waitFor();
	            System.out.println("Exit value = " + w);
	
	            StringBuilder result = new StringBuilder();
	            if (w == 0) {
	                BufferedReader in = new BufferedReader(OCRUtil.getInstanceReaderUTF8(tempTessOutputFile));
	
	                String str;
	
	                while ((str = in.readLine()) != null) {
	                    result.append(str).append(OCRConstant.END_OF_LINE);
	                }
	
	                int length = result.length();
	                if (length >= OCRConstant.END_OF_LINE.length()) {
	                    result.setLength(length - OCRConstant.END_OF_LINE.length()); // remove last EOL
	                }
	                in.close();
	            } else {
	                tempTessOutputFile.delete();
	                String msg = outputGobbler.getMessage(); // get actual message from the engine;
	                if (msg.trim().length() == 0) {
	                    msg = "Errors occurred.";
	                }
	                throw new RuntimeException(msg);
	            }
	            
	            hOCR.add(result.toString());
	        }
	        tempTessOutputFile.delete();
	        
	        return hOCR;
		} catch (Exception e) {
			throw new OCRExtractingException(e, TESSERACT_ENGINE);
		}
    }

    // GETTERS AND SETTERS //
	public String getCurrentLanguage() {
		return currentLanguage;
	}
	public void setCurrentLanguage(String currentLanguage) {
		this.currentLanguage = currentLanguage;
	}
	/**
	 * Get page segmentation mode.
	 * @return
	 */
	public String getPsm() {
		return psm;
	}
	/**
	 * Set page segmentation mode.
	 * @return
	 */
	public void setPsm(String psm) {
		this.psm = psm;
	}
}
