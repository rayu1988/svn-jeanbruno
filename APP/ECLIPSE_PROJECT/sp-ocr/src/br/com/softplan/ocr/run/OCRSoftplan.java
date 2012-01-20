/**
 * 
 */
package br.com.softplan.ocr.run;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.imageio.IIOImage;

import br.com.softplan.ocr.common.OCRConstant;
import br.com.softplan.ocr.common.OCRImageIOHelper;
import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.entity.OCRImageEntity;
import br.com.softplan.ocr.exception.OCRExtractingException;

/**
 * @author jean.villete
 *
 */
public class OCRSoftplan {

	private List<IIOImage> 		iioImageList;
	private OCRImageEntity		entity;
	private List<File> 			workingFiles;
	private OCREngine 			ocrEngine;
	private Properties 			configsProp;
	
	/**
	 * This constructor hopes that exist at the environment variables one that explict the configs file.
	 * Currently the pair key value hoped is;
	 * 	SP_OCR_CONFIG_PROP = My Address (e.g. /etc/tesseract/sp-ocr/configs.properties or C:/Programs and Files/Tesseract/sp-ocr/configs.properties)
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public OCRSoftplan() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		this(configsEnvironmentVariable());
	}
	
	/**
	 * A constructor that defining explicitly the property file with settings.
	 * 
	 * @param configsProp
	 */
	public OCRSoftplan(Properties configsProp) {
		if (configsProp == null || !configsProp.containsKey("ocr_app_name") || !configsProp.containsKey("ocr_app_home")) {
			throw new IllegalArgumentException("There is some problem with argument configsProp.");
		}
		
		this.configsProp = configsProp;
	}
	
    /**
     * Search for a specified Environment Variable that should contain the address to file properties with configs to sp ocr.
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     */
    private static Properties configsEnvironmentVariable() throws UnsupportedEncodingException, FileNotFoundException, IOException {
    	String strConfigHome = System.getenv(OCRConstant.CONFIG_ENVIRONMENT_VARIABLE);
		if (OCRUtil.isStringOk(strConfigHome)) {
			File fileProp = new File(strConfigHome);
			return OCRUtil.getLoadedProperties(fileProp);
		} else {
			throw new IllegalArgumentException("There's no valid value for \"Environment Variable\" " + OCRConstant.CONFIG_ENVIRONMENT_VARIABLE + " and right now it is required.");
		}
	}
	
    /**
     * Loads an implementation of OCREngine to current instance ocr softplan.
     * @param ocrEngine
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
	public void setOCREngine(Class<? extends OCREngine> ocrEngine) throws InstantiationException, IllegalAccessException {
		this.ocrEngine = ocrEngine.newInstance();
		this.ocrEngine.load(this.configsProp);
		
		String ocr_app_home = configsProp.getProperty("ocr_app_home");
		if (!ocr_app_home.toString().endsWith("\\") && !ocr_app_home.toString().endsWith("/")) {
			ocr_app_home += OCRConstant.FILE_SEPARATOR;
		}
		String ocr_app_name = configsProp.getProperty("ocr_app_name");
		
		this.ocrEngine.setAppInvoking(ocr_app_home + ocr_app_name);
	}

	/**
	 * Set an image file to that will used as base to extract text from.
	 * @param imageFile
	 * @throws IOException
	 */
	public void setImageFile(File imageFile) throws IOException {
		if (imageFile == null || !imageFile.exists() || !imageFile.isFile()) {
			throw new IllegalArgumentException();
		}
		
		this.iioImageList = OCRImageIOHelper.getIIOImageList(imageFile);
		this.entity = new OCRImageEntity(this.iioImageList, 0, null);
		this.entity.setScreenshotMode(false);
	}
	
	public void extractToPDF(File pdfFile) {
		this.validate();
		throw new IllegalStateException("Method not implemented");
	}
	
	public void extractToClearText(File pdfFile) {
		this.validate();
		throw new IllegalStateException("Method not implemented");
	}
	
	/**
	 * 
	 * @param hOCRFile
	 * @throws OCRExtractingException
	 * @throws IOException
	 */
	public void extractToHOCR(File hOCRFile) throws OCRExtractingException, IOException {
		this.validate();
		
		List<String> listHOCR = this.ocrEngine.run(this.getWorkingFiles());
		PrintWriter printWriter = new PrintWriter(OCRUtil.getInstanceWriterUTF8(hOCRFile));
		for (String hOCR : listHOCR) {
			printWriter.print(hOCR);
		}
		printWriter.close();
	}
	
	private List<File> getWorkingFiles() throws IOException {
		if (this.workingFiles == null) {
			this.workingFiles = this.entity.getClonedImageFiles();
		}
		return this.workingFiles;
	}
	
	private void validate() {
		if (this.ocrEngine == null) {
			throw new IllegalStateException("There's no valid instance to ocrEngine.");
		}
	}
}
