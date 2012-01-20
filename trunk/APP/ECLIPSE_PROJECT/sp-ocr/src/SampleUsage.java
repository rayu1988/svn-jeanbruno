import java.io.File;

import br.com.softplan.ocr.enumerator.OCRTypeExtension;
import br.com.softplan.ocr.run.OCRSoftplan;
import br.com.softplan.ocr.run.tesseract.OCRTesseractEngine;

/**
 * java -jar sp-ocr ImageBase.jpg Target.(pdf/txt/hocr)
 * 
 * @author jean.villete
 *
 */
public class SampleUsage {
	public static void main(String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("Must be something like: java -jar sp-ocr ImageBase.jpg Target.(pdf/txt/hocr)");
		}
		
		OCRTypeExtension extension = null;
		String pdfFile = args[1];
		if (pdfFile.toLowerCase().endsWith(".pdf")) {
			extension = OCRTypeExtension.PDF;
		} else if (pdfFile.toLowerCase().endsWith(".txt")) {
			extension = OCRTypeExtension.TXT;
		} else if (pdfFile.toLowerCase().endsWith(".hocr")) {
			extension = OCRTypeExtension.HOCR;
		} else {
			throw new IllegalStateException("Target file must be something with extension pdf, txt or hocr");
		}
		
		try {
			File imageBase = new File(args[0]);
			File pdfTarget = new File(pdfFile);
			
			// preparing softplan ocr
			OCRSoftplan ocrSoftplan = new OCRSoftplan();
			ocrSoftplan.setOCREngine(OCRTesseractEngine.class);
			ocrSoftplan.setImageFile(imageBase);
			
			ocrSoftplan.save(extension, pdfTarget);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}