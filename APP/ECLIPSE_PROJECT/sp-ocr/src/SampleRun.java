import java.io.File;

import br.com.softplan.ocr.enumerator.OCRTypeExtension;
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
			File imageBase = new File("C:\\OCR\\TESTES\\input\\image-mail-example.jpg");
			
			// preparing softplan ocr
			OCRSoftplan ocrSoftplan = new OCRSoftplan();
			ocrSoftplan.setOCREngine(OCRTesseractEngine.class);
			ocrSoftplan.setImageFile(imageBase);
			
//			File pdfFile = new File("C:\\OCR\\TESTES\\output\\hocr.pdf");
			System.out.println( ocrSoftplan.getBytes(OCRTypeExtension.PDF) );
			
//			File hOCRFile = new File("C:\\OCR\\TESTES\\output\\hocr.html");
//			ocrSoftplan.save(OCRTypeExtension.HOCR, hOCRFile);
			
//			File txtFile = new File("C:\\OCR\\TESTES\\output\\hocr.txt");
//			ocrSoftplan.save(OCRTypeExtension.TXT, txtFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}