import java.io.File;

import br.com.softplan.ocr.entity.OCRDocumentImage;
import br.com.softplan.ocr.entity.OCRExtractedText;
import br.com.softplan.ocr.entity.OCRExtractedWord;
import br.com.softplan.ocr.entity.OCRScannedDocumentImage;
import br.com.softplan.ocr.service.OCRDataTrainingService;
import br.com.softplan.ocr.service.OCRExtractTextService;
import br.com.softplan.ocr.service.OCRScannerDocumentImageService;


public class Main {
	public static void main(String[] args) {
		try {
			//preparing knowledge base data training
			File folderDataTraining = new File("C:\\temp\\OpticalCharacterRecognationDataTraining");
			OCRDataTrainingService.getInstance().append(folderDataTraining);
			
			// ocr document image
			File inputImageFile = new File("C:\\temp\\image.jpg");
			OCRDocumentImage documentImage = new OCRDocumentImage(inputImageFile);
			
			OCRScannedDocumentImage scannedDocumentImage = OCRScannerDocumentImageService.scan(documentImage);
			OCRExtractedText extractedText = OCRExtractTextService.extractText(scannedDocumentImage);
			
			for (OCRExtractedWord extractedWord : extractedText.getExtractedWords()) {
				System.out.println(extractedWord);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
