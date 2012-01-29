import java.io.File;

import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.enumerator.OCRTypeExtension;
import br.com.softplan.ocr.loader.OCRConfigEnvironmentVariable;
import br.com.softplan.ocr.loader.OCRConfigsProp;
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
		String targetFile = args[1];
		if (targetFile.toLowerCase().endsWith(".pdf")) {
			extension = OCRTypeExtension.PDF;
		} else if (targetFile.toLowerCase().endsWith(".txt")) {
			extension = OCRTypeExtension.TXT;
		} else if (targetFile.toLowerCase().endsWith(".hocr")) {
			extension = OCRTypeExtension.HOCR;
		} else {
			throw new IllegalStateException("Target file must be something with extension pdf, txt or hocr");
		}
		
		try {
			File imageBase = new File(args[0]);
			File target = new File(targetFile);
			
			// preparing softplan ocr
			OCRConfigsProp configsProp = new OCRConfigEnvironmentVariable("SP_OCR_CONFIG_PROP");
			OCRSoftplan ocrSoftplan = new OCRSoftplan(configsProp);
			ocrSoftplan.setOCREngine(OCRTesseractEngine.class);
			
			byte[] dataFile = OCRUtil.getBytesFromFile(imageBase);
			
			ocrSoftplan.setImageFile(imageBase.getName(), dataFile);
			
//			ocrSoftplan.getBytes(extension);
			ocrSoftplan.save(extension, target);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}