/**
 * 
 */
package br.com.softplan.ocr.run;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;

import javax.imageio.IIOImage;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import br.com.softplan.ocr.common.OCRConstant;
import br.com.softplan.ocr.common.OCRHOCRConstants;
import br.com.softplan.ocr.common.OCRImageIOHelper;
import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.entity.OCRImageEntity;
import br.com.softplan.ocr.enumerator.OCRTypeExtension;
import br.com.softplan.ocr.exception.OCRExtractingException;
import br.com.softplan.ocr.exception.OCRLoaderException;
import br.com.softplan.ocr.loader.OCRConfigsProp;

/**
 * @author jean.villete
 *
 */
public class OCRSoftplan {

	private List<IIOImage> 		iioImageList;
	private OCRImageEntity		entity;
	private List<File> 			workingFiles;
	private OCREngine 			ocrEngine;
	private Properties			configsProp;
	private String				appEngineInvoking;
	private File 				fileImage;
	private boolean				debugText;
	
	/**
	 * This variable is used just to control a bit information of a type current image file.
	 * A single bit information is used because we "currently" support two possibly type of file image, that is jpeg and/or tiff.
	 * This limitation is defined by PDFBox, that is the current library to generate pdf file.
	 * Thus, we can infer that;
	 * 	true  : jpg
	 *  false : tiff
	 */
	private boolean				controlTypeImage;
	
	/**
	 * A constructor to load certain settings to OCRSoftplan and OCREngine that must exists at this configs property.
	 * 
	 * @param configsProp
	 * @throws OCRLoaderException 
	 */
	public OCRSoftplan(OCRConfigsProp configsProp) throws OCRLoaderException {
		if (configsProp == null) {
			throw new IllegalArgumentException("Argument configsProp can not be null!");
		}
		
		this.configsProp = configsProp.getFileProperties();
		this.resolveAppInvolking(configsProp.getFileProperties());
	}
	
	/**
	 * Method to resolve the string app involking with the property file passed as parameter.
	 * @param fileProperty
	 */
    private void resolveAppInvolking(Properties fileProperty) {
		if (!fileProperty.containsKey(OCRConstant.CONFIG_PROP_APP_NAME) || !fileProperty.containsKey(OCRConstant.CONFIG_PROP_APP_HOME)) {
			throw new IllegalArgumentException("There is some problem with argument configsProp.");
		}
		
		String ocr_app_home = fileProperty.getProperty(OCRConstant.CONFIG_PROP_APP_HOME);
		if (!ocr_app_home.toString().endsWith("\\") && !ocr_app_home.toString().endsWith("/")) {
			ocr_app_home += OCRConstant.FILE_SEPARATOR;
		}
		String ocr_app_name = fileProperty.getProperty(OCRConstant.CONFIG_PROP_APP_NAME);
		
		this.appEngineInvoking = ocr_app_home + ocr_app_name;
	}

	/**
     * Loads an implementation of OCREngine to current instance ocr softplan.
     * @param ocrEngine
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws OCRLoaderException 
     */
	public void setOCREngine(Class<? extends OCREngine> ocrEngine) throws InstantiationException, IllegalAccessException, OCRLoaderException {
		this.ocrEngine = ocrEngine.newInstance();
		
		this.ocrEngine.load(this.configsProp);
		this.ocrEngine.setAppInvoking(this.appEngineInvoking);
	}

	/**
	 * Set an image file that will used as base to extract text from.
	 * @param fileImage
	 * @throws IOException
	 */
	public void setImageFile(File fileImage) throws IOException {
		// validate if file exists
		if (fileImage == null || !fileImage.exists() || !fileImage.isFile()) {
			throw new IllegalArgumentException();
		}
		// validate if is a valid image
		if (fileImage.getName().toLowerCase().endsWith(".jpg")) {
			this.controlTypeImage = true;
		} else if (fileImage.getName().toLowerCase().endsWith(".tif") || fileImage.getName().toLowerCase().endsWith(".tiff")) {
			this.controlTypeImage = false;
		} else {
			throw new IOException("Image type not supported:" + fileImage.getName() );
		}
		
		this.fileImage = fileImage;
		this.iioImageList = OCRImageIOHelper.getIIOImageList(fileImage);
		this.entity = new OCRImageEntity(this.iioImageList, 0, null);
		this.entity.setScreenshotMode(false);
	}
	
	/**
	 * Set an array bytes that represents an image file that will be used as base to extract text from.
	 * 
	 * @param fileName
	 * @param dataImage - Data file of image base
	 * @throws IOException
	 */
	public void setImageFile(String fileName, byte[] dataImage) throws IOException {
		File tempFile = File.createTempFile(OCRConstant.FILE_TEMP_IDENTIFIER + Long.toString(System.nanoTime()), "." + OCRUtil.getExtension(fileName));
		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(dataImage);
		fos.close();
		this.setImageFile(tempFile);
	}
	
	/**
	 * Method to indicates a ocr extracting and save result as a file (saveAs), where the address is passed as parameter (targetFile).
	 * 
	 * @param saveAs PDF, TXT or HOCR
	 * @param targetFile The address to save the extracting result.
	 * @throws OCRExtractingException
	 * @throws IOException
	 * @throws COSVisitorException
	 */
	public void save(OCRTypeExtension saveAs, File targetFile) throws OCRExtractingException, IOException, COSVisitorException {
		if (saveAs == null || targetFile == null) {
			throw new IllegalArgumentException("Arguments can't be null.");
		}
		
		this.validate();
		
		List<String> listHOCR = this.ocrEngine.run(this.getWorkingFiles());
		if (saveAs.equals(OCRTypeExtension.HOCR)) {
			this.saveAsHOCR(listHOCR, targetFile);
		} else if (saveAs.equals(OCRTypeExtension.TXT)) {
			this.saveAsTXT(listHOCR, targetFile);
		} else if (saveAs.equals(OCRTypeExtension.PDF)) {
			this.saveAsPDF(listHOCR, targetFile);
		}
	}
	
	/**
	 * Method to indicates a ocr extracting and returns a file result as an array of bytes.
	 * 
	 * @param extractTo
	 * @return
	 * @throws IOException
	 * @throws OCRExtractingException
	 * @throws COSVisitorException
	 */
	public byte[] getBytes(OCRTypeExtension extractTo) throws IOException, OCRExtractingException, COSVisitorException {
		if (extractTo == null) {
			throw new IllegalArgumentException();
		}
		
		File tempFile = File.createTempFile(OCRConstant.FILE_TEMP_IDENTIFIER + Long.toString(System.nanoTime()), ".ocrsoftplantmp");
		this.save(extractTo, tempFile);
		byte[] arrayToReturn = OCRUtil.getBytesFromFile(tempFile);
		tempFile.delete();
		return arrayToReturn;
	}
	
	private void saveAsPDF(List<String> listHOCR, File pdfFile) throws IOException, COSVisitorException {
		// ENTER PDF Box
		PDDocument pdfDocument = new PDDocument();
		
		for (String hOCR : listHOCR) {
			Source rootHOCR = new Source(hOCR);
			rootHOCR.fullSequentialParse();
			for (Element pageHOCR : rootHOCR.getAllElementsByClass(OCRHOCRConstants.HOCR_CLASS_PAGE)) {
				
				PDXObjectImage pdfImage = null;
				if (this.isJPG()) {
					pdfImage = new PDJpeg(pdfDocument, new FileInputStream(this.fileImage));
				} else if (this.isTIFF()) {
					pdfImage = new PDCcitt(pdfDocument, new RandomAccessFile(this.fileImage,"r"));
				} else {
					throw new IllegalStateException();
				}
				
				PDRectangle pdfRectangle = new PDRectangle(pdfImage.getWidth(), pdfImage.getHeight());
				PDPage pdfPage = new PDPage(pdfRectangle);
				pdfDocument.addPage(pdfPage);
				PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage);
				for (Element lineHOCR : pageHOCR.getAllElementsByClass(OCRHOCRConstants.HOCR_CLASS_LINE)) {
					for (Element wordHOCR : lineHOCR.getAllElementsByClass(OCRHOCRConstants.HOCR_CLASS_WORD)) {
						
						String wordText = wordHOCR.getTextExtractor().setIncludeAttributes(false).toString();
						
						Matcher bboxWordMatcher = OCRHOCRConstants.B_BOX_PATTERN.matcher(wordHOCR.getAttributeValue(OCRHOCRConstants.HOCR_ATTRIBUTE_TITLE));
						if (bboxWordMatcher.find()) {
							Matcher bboxWordCoordinateMatcher = OCRHOCRConstants.B_BOX_COORDINATE_PATTERN.matcher(bboxWordMatcher.group());
							bboxWordCoordinateMatcher.find();
							
							int x0 = Integer.parseInt((bboxWordCoordinateMatcher.group(1)));
							int y0 = Integer.parseInt((bboxWordCoordinateMatcher.group(2)));
//							int x1 = Integer.parseInt((bboxWordCoordinateMatcher.group(3)));
							int y1 = Integer.parseInt((bboxWordCoordinateMatcher.group(4)));

							contentStream.setFont(PDType1Font.HELVETICA, y1 - y0);
							
							contentStream.beginText();
							contentStream.moveTextPositionByAmount(x0, pdfImage.getHeight() - y1);
							contentStream.drawString(wordText);
				            contentStream.endText();
						}
					}
				}
				
				if (!debugText) { // the image only can be printed if is not debug mode in the current instance.
					// print image at front end and close content stream to current page (page context)
					contentStream.drawImage(pdfImage, 0, 0);
				}
				contentStream.close();
			}
		}
		
		// save and close pdf document
		pdfDocument.save(pdfFile.getAbsolutePath().toString());
		pdfDocument.close();
	}
	
	private void saveAsTXT(List<String> listHOCR, File txtFile) throws UnsupportedEncodingException, FileNotFoundException {
		PrintWriter printWriter = new PrintWriter(OCRUtil.getInstanceWriterUTF8(txtFile));
		for (String hOCR : listHOCR) {
			Source rootHOCR = new Source(hOCR);
			rootHOCR.fullSequentialParse();
			for (Element pageHOCR : rootHOCR.getAllElementsByClass(OCRHOCRConstants.HOCR_CLASS_PAGE)) {
				for (Element lineHOCR : pageHOCR.getAllElementsByClass(OCRHOCRConstants.HOCR_CLASS_LINE)) {
					printWriter.println(lineHOCR.getTextExtractor().setIncludeAttributes(false).toString());
				}
			}
		}
		printWriter.close();
	}
	
	private void saveAsHOCR(List<String> listHOCR, File hOCRFile) throws UnsupportedEncodingException, FileNotFoundException {
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
		if (!OCRUtil.isCollectionOk(this.iioImageList)) {
			throw new IllegalStateException("There's no valid image to be used to extracting from.");
		}
	}
	
	private boolean isJPG() {
		return this.controlTypeImage == true;
	}
	
	private boolean isTIFF() {
		return this.controlTypeImage == false;
	}

	public boolean isDebugText() {
		return debugText;
	}

	public void setDebugText(boolean debugText) {
		this.debugText = debugText;
	}
}
