import java.io.File;

import br.com.softplan.ocr.run.OCRSoftplan;
import br.com.softplan.ocr.run.tesseract.OCRTesseractEngine;

/**
 * 
 * @author jean.villete
 *
 */
public class SampleRun {
	public static void main(String[] args) {
		try {
			// explicitly file configs
//			File fileConfigs = new File("C:\\workspaces\\eclipse\\ocr\\sp-ocr\\sp-ocr\\sp-configs\\configs.properties");
//			Properties configsProp = OCRUtil.getLoadedProperties(fileConfigs);
			
			// image base with some text at
			File imageBase = new File("C:\\OCR\\TESTES\\input\\email-text.tif");
			
			// preparing softplan ocr
			OCRSoftplan ocrSoftplan = new OCRSoftplan();
			ocrSoftplan.setOCREngine(OCRTesseractEngine.class);
			ocrSoftplan.setImageFile(imageBase);
			
			// file to persist hOCR result
			File hOCRFile = new File("C:\\OCR\\TESTES\\output\\hocr.html");
			File txtFile = new File("C:\\OCR\\TESTES\\output\\hocr.txt");
			
			ocrSoftplan.extractToClearText(txtFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}