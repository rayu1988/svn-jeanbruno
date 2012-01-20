import java.io.File;
import java.util.Properties;

import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.run.OCREngine;
import br.com.softplan.ocr.run.OCRSoftplan;
import br.com.softplan.ocr.run.tesseract.OCRTesseractEngine;

/**
 * 
 * @author jean.villete
 *
 */
public class Main {
	public static void main(String[] args) {
		// image base with some text at
		File imageBase = new File("C:\\OCR\\TESTES\\input\\mail-example.jpg");
		
		try {
			// preparing ocr engine
			File fileTesseractProp = new File("C:\\workspaces\\eclipse\\ocr\\sp-ocr\\sp-ocr\\configs\\tesseract.properties");
			Properties tesseractProp = OCRUtil.getLoadedProperties(fileTesseractProp);
			OCREngine ocrEngine = new OCRTesseractEngine(tesseractProp);
			
			// preparing softplan ocr
			File fileConfigs = new File("C:\\workspaces\\eclipse\\ocr\\sp-ocr\\sp-ocr\\configs\\configs.properties");
			Properties configsProp = OCRUtil.getLoadedProperties(fileConfigs);
			OCRSoftplan ocrSoftplan = new OCRSoftplan(configsProp);
			ocrSoftplan.setImageFile(imageBase);
			ocrSoftplan.setOcrEngine(ocrEngine);
			
			// file to persist hOCR result
			File hOCRFile = new File("C:\\OCR\\TESTES\\output\\hocr.html");
			ocrSoftplan.extractToHOCR(hOCRFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}