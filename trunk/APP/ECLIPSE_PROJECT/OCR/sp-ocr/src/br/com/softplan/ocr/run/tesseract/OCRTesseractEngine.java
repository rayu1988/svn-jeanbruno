/**
 * 
 */
package br.com.softplan.ocr.run.tesseract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.com.softplan.ocr.common.OCRConstant;
import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.exception.OCRExtractingException;
import br.com.softplan.ocr.exception.OCRLoaderException;
import br.com.softplan.ocr.run.OCREngine;

/**
 * Utility class that aims get a hOCR data result with Tesseract Engine.
 * @author jean.villete
 *
 */
public class OCRTesseractEngine implements OCREngine {
	
	public static final String 				TESSERACT_ENGINE;
	private static final String 			OUTPUT_FILE_NAME;
	private static final String 			FILE_EXTENSION_HOCR;
	private static final String 			LANG_OPTION;
	private static final String 			PSM_OPTION;
	
	static {
		TESSERACT_ENGINE = "Tesseract Engine";
		OUTPUT_FILE_NAME = "TessOutput";
		FILE_EXTENSION_HOCR = ".html";
		LANG_OPTION = "-l";
		PSM_OPTION = "-psm";
	}

	public File 							configsFile;
    private String 							language = "eng";
    private String 							psm = "3"; // Fully automatic page segmentation, but no OSD (default)
    private String 							appInvoking;
    
    /**
     * Creates a new instance of OCRTesseractEngine.
     */
    public OCRTesseractEngine() { }

	/**
     * Method to invoke the tesseract engine to extract text contained at the image passed as parameter.
     * The return is the string extracted formated as hOCR standard.
     * 
     * @param tiffFiles
     * @return To each imageFile passed as parameter, will there a String hOCR in the same index at the List returned. A list of hOCR String.
     * @throws OCRExtractingException 
     */
    @Override
    public List<String> run(final List<File> tiffFiles) throws OCRExtractingException {
    	if (!OCRUtil.isStringOk(this.appInvoking)) {
    		throw new IllegalStateException("There's no valid value to appInvoking.");
    	}
    	
		try {
			File tempTessOutputFile=null;
			tempTessOutputFile = File.createTempFile(OUTPUT_FILE_NAME, FILE_EXTENSION_HOCR);
	        String outputFileName = tempTessOutputFile.getPath().substring(0, tempTessOutputFile.getPath().length() - FILE_EXTENSION_HOCR.length()); // chop the .txt extension
	
	        List<String> cmd = new ArrayList<String>();
	        cmd.add(this.appInvoking);
	        cmd.add(""); // placeholder for inputfile
	        cmd.add(outputFileName);
        	cmd.add(LANG_OPTION);
        	cmd.add(this.language);
	        cmd.add(PSM_OPTION);
	        cmd.add(psm);
	       	cmd.add("+" + this.configsFile.getCanonicalPath());
	
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

    /**
	 * Method to load some settings to current OCR Engine.
	 * @param configsProp
     * @throws OCRLoaderException 
	 */
    @Override
	public void load(Properties configsProp) throws OCRLoaderException {
    	if (configsProp != null) {
    		if (configsProp.containsKey("tesseract_psm")) {
    			this.psm = configsProp.getProperty("tesseract_psm");
    		}
    		if (configsProp.containsKey("tesseract_language")) {
    			this.language = configsProp.getProperty("tesseract_language");
    		}
    		
    		this.loadHOCRConfigs();
    	} else {
    		throw new OCRLoaderException();
    	}
	}

    private void loadHOCRConfigs() throws OCRLoaderException {
		try {
			this.configsFile = File.createTempFile(OCRConstant.FILE_TEMP_IDENTIFIER + Long.toString(System.nanoTime()), ".teshocrconf");
			FileOutputStream fos = new FileOutputStream(this.configsFile);
			fos.write(OCRConstant.TESSERACT_CONFIGS_HOCR.getBytes());
			fos.close();
		} catch (IOException e) {
			throw new OCRLoaderException(e, "Problems loading HOCR configs. \"tesseract_file_configs\" ");
		}
	}

	// GETTERS AND SETTERS //
    /**
     * Set complete String command to invoke the ocr engine.
     * @param appInvoking
     */
    @Override
    public void setAppInvoking(String appInvoking) {
    	this.appInvoking = appInvoking;
    }
    
	public String getAppInvoking() {
		return appInvoking;
	}

	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
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
