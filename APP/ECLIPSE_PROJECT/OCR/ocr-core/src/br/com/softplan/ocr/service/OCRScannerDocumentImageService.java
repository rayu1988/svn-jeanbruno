/**
 * 
 */
package br.com.softplan.ocr.service;

import java.io.IOException;

import br.com.softplan.ocr.entity.OCRDocumentImage;
import br.com.softplan.ocr.entity.OCRImage;
import br.com.softplan.ocr.entity.OCRScannedCharacterImage;
import br.com.softplan.ocr.entity.OCRScannedDocumentImage;
import br.com.softplan.ocr.entity.OCRScannedRowCharacterImage;
import br.com.softplan.ocr.exception.OCRException;
import br.com.softplan.ocr.scanner.OCRScannerImage;
import br.com.softplan.ocr.scanner.listener.OCRScannerListenerImage;

/**
 * Utility class to generates a OCRScannedDocumentImage through its scan method.
 * @author jean.villete
 *
 */
public class OCRScannerDocumentImageService extends OCRScannerImage implements OCRScannerListenerImage {

	private OCRScannedDocumentImage scannedDocumentImage;
	
	private OCRScannerDocumentImageService(OCRDocumentImage documentImage) throws IOException, InterruptedException, OCRException {
		if (documentImage == null) throw new IllegalArgumentException();
		this.scannedDocumentImage = new OCRScannedDocumentImage(documentImage);
		
		super.scanImage(this.scannedDocumentImage.getDocumentImage().getImage(), this);
	}
	
	public static final OCRScannedDocumentImage scan(OCRDocumentImage documentImage) throws IOException, InterruptedException, OCRException {
		return new OCRScannerDocumentImageService(documentImage).scannedDocumentImage;
	}

	@Override
	public void beginScanImage(OCRImage ocrImage) throws OCRException {
		this.scannedDocumentImage.addWordDivisor(); // start scan
	}

	@Override
	public void beginRow(OCRImage ocrImage, int y1, int y2) throws OCRException { }

	@Override
	public void processCharacter(OCRImage ocrImage, int x1, int y1, int x2, int y2, int rowY1, int rowY2) throws OCRException {
		OCRScannedRowCharacterImage	scannedRowCharacterImage = new OCRScannedRowCharacterImage(rowY1, rowY2);
		OCRScannedCharacterImage scannedCharacterImage = new OCRScannedCharacterImage(this.scannedDocumentImage, scannedRowCharacterImage, y1, y2, x1, x2);
		this.scannedDocumentImage.addScannedCharacterImage(scannedCharacterImage);
	}

	@Override
	public void processSpace(OCRImage ocrImage, int x1, int y1, int x2, int y2) throws OCRException {
		this.scannedDocumentImage.addWordDivisor(); // new space
	}

	@Override
	public void endRow(OCRImage ocrImage, int y1, int y2) throws OCRException {
		this.scannedDocumentImage.addWordDivisor(); // new break line
	}

	@Override
	public void endScanImage(OCRImage ocrImage) throws OCRException {
		this.scannedDocumentImage.addWordDivisor(); // ends scan
	}
}
