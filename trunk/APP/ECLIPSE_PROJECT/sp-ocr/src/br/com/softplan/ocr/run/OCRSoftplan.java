/**
 * 
 */
package br.com.softplan.ocr.run;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

	private StringBuffer		appInvoking = new StringBuffer();
	private List<IIOImage> 		iioImageList;
	private OCRImageEntity		entity;
	private List<File> 			workingFiles;
	private OCREngine 			ocrEngine;
	
	public OCRSoftplan(Properties configsProp) {
		if (configsProp == null || !configsProp.containsKey("ocr_app_name") || !configsProp.containsKey("ocr_app_home")) {
			throw new IllegalArgumentException("There is some problem with argument configsProp.");
		}
		
		this.appInvoking.append(configsProp.getProperty("ocr_app_home"));
		if (!this.appInvoking.toString().endsWith("\\") && !this.appInvoking.toString().endsWith("/")) {
			this.appInvoking.append(OCRConstant.FILE_SEPARATOR);
		}
		this.appInvoking.append(configsProp.getProperty("ocr_app_name"));
	}
	
	public void setImageFile(File imageFile) throws IOException {
		if (imageFile == null || !imageFile.exists() || !imageFile.isFile()) {
			throw new IllegalArgumentException();
		}
		
		this.iioImageList = OCRImageIOHelper.getIIOImageList(imageFile);
		this.entity = new OCRImageEntity(this.iioImageList, 0, null);
		this.entity.setScreenshotMode(false);
	}
	
	private void validate() {
		if (this.ocrEngine == null) {
			throw new IllegalStateException("There's no valid instance to ocrEngine.");
		}
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
	
	// GETTERS AND SETTERS //
	private List<File> getWorkingFiles() throws IOException {
		if (this.workingFiles == null) {
			this.workingFiles = this.entity.getClonedImageFiles();
		}
		return this.workingFiles;
	}
	
	public void setOcrEngine(OCREngine ocrEngine) {
		this.ocrEngine = ocrEngine;
		this.ocrEngine.setAppInvoking(this.appInvoking.toString());
	}
}
