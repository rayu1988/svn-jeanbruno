/**
 * 
 */
package br.com.softplan.ocr.service;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import br.com.softplan.ocr.common.OCRConstant;
import br.com.softplan.ocr.common.OCRConstantDataTraining;
import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.entity.OCRCharacterEnumeration;
import br.com.softplan.ocr.entity.OCRDataTraining;
import br.com.softplan.ocr.entity.OCRDocumentImage;
import br.com.softplan.ocr.entity.OCRImage;
import br.com.softplan.ocr.entity.OCRImageScale;
import br.com.softplan.ocr.entity.OCRScannedCharacterImage;
import br.com.softplan.ocr.entity.OCRScannedDocumentImage;
import br.com.softplan.ocr.exception.OCRException;
import br.com.softplan.ocr.exception.OCRLoadingDataTrainingException;
import br.com.softplan.ocr.zip_unzip.OCRCompressService;

/**
 * Utility class to holds the knowledge base (Data Training Base) for OCR system.
 * This class releases services like add data training to system's knowledge base and getting a set of Data Training knowledge to a given character passed as parameter, and so...
 * 
 * @author jean.villete
 *
 */
public class OCRDataTrainingService implements Serializable {
	
	private static final long 									serialVersionUID = 8156055751739018289L;
	
	private static OCRDataTrainingService						instance = new OCRDataTrainingService();
	private Map<Character, Set<OCRDataTraining>>				dataTraining;
	
	private OCRDataTrainingService() {
		this.dataTraining = new HashMap<Character, Set<OCRDataTraining>>();
	}
	
	/**
	 * Retrieves the instance from the OCRDataTrainingService that is a singleton (just one instance).
	 * @return
	 */
	public static OCRDataTrainingService getInstance() {
		return OCRDataTrainingService.instance;
	}
	
	/**
	 * Method to assign a new OCRDataTraining to the knowledge base (Data Training Base).
	 * @param dataTraining
	 */
	public void addTraining(OCRDataTraining dataTraining) {
		if (dataTraining == null) throw new IllegalArgumentException();
		
		Set<OCRDataTraining> currentDataTraining = this.dataTraining.get(dataTraining.getCharacter());
		if (currentDataTraining == null) {
			currentDataTraining = new HashSet<OCRDataTraining>();
			this.dataTraining.put(dataTraining.getCharacter(), currentDataTraining);
		}
		currentDataTraining.add(dataTraining);
	}
	
	/**
	 * Returns a Set of Data Training knowledge to a given character.
	 * @param character
	 * @return
	 */
	public Set<OCRDataTraining> get(Character character) {
		return this.dataTraining.get(character);
	}
	
	/**
	 * Returns the map with current knowledge base data training.
	 * @return
	 */
	public Map<Character, Set<OCRDataTraining>> getDataTraining() {
		return this.dataTraining;
	}
	
	/**
	 * Appends knowledge base to the current (singleton) instance.
	 * @param ocrdt - (Optical Character Recognation Data Training) A known structured file that holds a knowledge base (Data Training Base).
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws OCRException 
	 */
	public OCRDataTrainingService append(File ocrdt) throws IOException, InterruptedException, OCRException {
		if (ocrdt == null || !ocrdt.exists()) throw new IllegalArgumentException("either ocrdt is null or don't exist");
		
		File baseDir;
		if (ocrdt.isDirectory()) { // case is a direct directory, tries load from that
			baseDir = ocrdt;
		} else if (ocrdt.isFile()) { // case is a file, decompress the compact to a temporary directory and tris load from that temporary directory
			baseDir = OCRUtil.createTmpDir(OCRConstant.OCR_TO_TMP_DIR); 
			OCRCompressService.decompress(ocrdt, baseDir);
		} else throw new IllegalStateException();
		
		File descriptorProperties = new File(baseDir.getCanonicalPath() + OCRConstant.FILE_SEPARATOR + OCRConstantDataTraining.DESCRIPTOR_PROPERTIES);
		Properties properties = new Properties();
		properties.load(OCRUtil.getInstanceReaderUTF8(descriptorProperties));
		
		Enumeration<?> propertyKeys = properties.propertyNames();
		while (propertyKeys.hasMoreElements()) { // iterating over description fonts
			String folderWithData = (String) propertyKeys.nextElement();
			String fontDetails = (String) properties.get(folderWithData);
			
			Properties fontProperties = OCRUtil.getDefaultFontProperties(fontDetails);
			Font currentFont = OCRUtil.getFontFromProperties(fontProperties);
			
			if (fontProperties.getProperty(OCRConstantDataTraining.CHARACTERS_AS).equals(OCRConstantDataTraining.CHARACTERS_STRUCTURED)) {
				File fontFolder = new File(baseDir.getCanonicalPath() + OCRConstant.FILE_SEPARATOR + folderWithData);
				this.loadTrainingStructuredCharacters(fontFolder, currentFont);
			} else if (fontProperties.getProperty(OCRConstantDataTraining.CHARACTERS_AS).equals(OCRConstantDataTraining.CHARACTERS_IMAGE_BASE)) {
				File imageFile = new File(baseDir.getCanonicalPath() + OCRConstant.FILE_SEPARATOR + folderWithData + OCRConstant.FILE_SEPARATOR + OCRConstantDataTraining.FILE_IMAGE_BASE);
				File characterFile = new File(baseDir.getCanonicalPath() + OCRConstant.FILE_SEPARATOR + folderWithData + OCRConstant.FILE_SEPARATOR + OCRConstantDataTraining.FILE_CHARACTERS);
				
				try {
					this.loadTrainingImageBase(imageFile, characterFile, currentFont);
				} catch (OCRLoadingDataTrainingException e) {
					e.printStackTrace();
					continue;
				}
			} else {
				throw new IllegalStateException();
			}
		}
		return getInstance();
	}

	/**
	 * Method to do a loading training from a structured characters (character_as {structured})
	 * @param fontFolder
	 * @param currentFont
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	private void loadTrainingStructuredCharacters(File fontFolder, Font currentFont) throws UnsupportedEncodingException, FileNotFoundException {
		for (File characterFile : fontFolder.listFiles()) {
			Scanner systemScanner = new Scanner(OCRUtil.getInstanceReaderUTF8(characterFile));
			Character character = systemScanner.nextLine().charAt(0);
			int widthImageScale = Integer.parseInt(systemScanner.nextLine());
			int heightImageScale = Integer.parseInt(systemScanner.nextLine());
			int topWhiteSpace = Integer.parseInt(systemScanner.nextLine());
			int bottomWhiteSpace = Integer.parseInt(systemScanner.nextLine());
			String binariesAsString = systemScanner.nextLine();
			
			// be sure! string as "255,140,190,255,140" never a comma after last integer
			String[] splitBinariesAsString = binariesAsString.split(",");
			int[] binaries = new int[splitBinariesAsString.length];
			for (int i = 0; i < splitBinariesAsString.length; i++) {
				binaries[i] = Integer.parseInt(splitBinariesAsString[i]);
			}
			
			OCRImageScale imageScale = new OCRImageScale(widthImageScale, heightImageScale);
			OCRImage image = new OCRImage(binaries, imageScale);
			OCRDataTraining dataTraining = new OCRDataTraining(image, topWhiteSpace, bottomWhiteSpace, currentFont, character);
			addTraining(dataTraining);
		}
	}

	/**
	 * Method to do a Loading Traning from a Image Base (character_as {image_base})
	 * @param imageFile
	 * @param characterFile
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws OCRException 
	 */
	private void loadTrainingImageBase(File imageFile, File characterFile, Font currentFont) throws IOException, InterruptedException, OCRException {
		OCRDocumentImage documentImage = new OCRDocumentImage(imageFile);
		OCRScannedDocumentImage scannedDocumentImage = OCRScannerDocumentImageService.scan(documentImage);
		OCRCharacterEnumeration characterEnumeration = new OCRCharacterEnumeration(characterFile);
		if (scannedDocumentImage.getScannedCharacters().size() != characterEnumeration.size()) {
			throw new OCRLoadingDataTrainingException(currentFont,
				"The number of characters found at document image corresponding not with the number of characters found at " + OCRConstantDataTraining.FILE_CHARACTERS + " file.");
		}
		for (OCRScannedCharacterImage scannedCharacterImage : scannedDocumentImage.getScannedCharacters())  {
			int widthImageCharacter = scannedCharacterImage.getCoordinateX2() - scannedCharacterImage.getCoordinateX1();
			int heightImageCharacter = scannedCharacterImage.getCoordinateY2() - scannedCharacterImage.getCoordinateY1();
			OCRImageScale imageScale = new OCRImageScale(widthImageCharacter, heightImageCharacter);
	        int[] binaries = new int[imageScale.getWidth() * imageScale.getHeight()];
	        for (int y = scannedCharacterImage.getCoordinateY1(), destY = 0; y < scannedCharacterImage.getCoordinateY2(); y++, destY++) {
	            System.arraycopy(documentImage.getImage().getBinaries(),
	            		(y * documentImage.getImage().getImageScale().getWidth()) + scannedCharacterImage.getCoordinateX1(),
	            		binaries,
	            		destY * imageScale.getWidth(),
	            		imageScale.getWidth());
	        }
        	OCRImage image = new OCRImage(binaries, imageScale);
        	int topWhiteSpace = scannedCharacterImage.getCoordinateY1() - scannedCharacterImage.getScannedRowCharacterImage().getCoordinateY1();
        	int bottomWhiteSpace = scannedCharacterImage.getScannedRowCharacterImage().getCoordinateY2() - scannedCharacterImage.getCoordinateY2();
        	OCRDataTraining dataTraining = new OCRDataTraining(image, topWhiteSpace, bottomWhiteSpace, currentFont, characterEnumeration.nextElement());
        	addTraining(dataTraining);
		}
	}

	/**
	 * Persists at the ocrdt (Optical Character Recognation Data Training) file passed as parameter the current knowledge base.
	 * @param ocrdt - Must be a valid File (not a Folder), however don't need exist yet!
	 * @throws IOException 
	 */
	public void persist(File ocrdt) throws IOException {
		if (ocrdt == null) throw new IllegalArgumentException();

		File tempDir = OCRUtil.createTmpDir(OCRConstant.OCR_TO_TMP_DIR);
		Properties descriptorProperties = new Properties();
		for (Set<OCRDataTraining> setDataTraining : this.dataTraining.values()) {
			for (OCRDataTraining currentDataTraining : setDataTraining) {
				String fontPropertyKey = OCRUtil.getFontPropertyKey(currentDataTraining.getFont());
				if (descriptorProperties.get(fontPropertyKey) == null) {
					String fontPropertyValue = OCRUtil.getFontPropertyValue(currentDataTraining.getFont());
					descriptorProperties.put(fontPropertyKey, fontPropertyValue);
				}
				File fontFolder = new File(tempDir.getCanonicalPath() + OCRConstant.FILE_SEPARATOR + fontPropertyKey);
				if (!fontFolder.exists()) {
					fontFolder.mkdir(); // tries create a physical folder
				}
				File characterFile = new File(fontFolder.getCanonicalPath() + OCRConstant.FILE_SEPARATOR + (int) currentDataTraining.getCharacter());
				PrintWriter printWriter = new PrintWriter(OCRUtil.getInstanceWriterUTF8(characterFile));
				printWriter.println(currentDataTraining.getCharacter()); //writes character representation
				printWriter.println(currentDataTraining.getImage().getImageScale().getWidth()); //writes width image scale
				printWriter.println(currentDataTraining.getImage().getImageScale().getHeight()); //writes height image scale
				printWriter.println(currentDataTraining.getTopWhiteSpacePixels()); //writes top white spaces
				printWriter.println(currentDataTraining.getBottomWhiteSpacePixels()); //writes bottom white spaces
				
				// be sure! string as "255,140,190,255,140" never a comma after last integer
				StringBuffer stringBinaries = new StringBuffer();
				for (int i = 0; i < (currentDataTraining.getImage().getBinaries().length - 1); i++) {
					stringBinaries.append(currentDataTraining.getImage().getBinaries()[i] + ",");
				}
				stringBinaries.append(currentDataTraining.getImage().getBinaries().length - 1);
				
				printWriter.println(stringBinaries.toString()); //writes binaries as a String Character separated by comma ( , )
				printWriter.close();
			}
		}
		File fileDescriptor = new File(tempDir.getCanonicalPath() + OCRConstant.FILE_SEPARATOR + OCRConstantDataTraining.DESCRIPTOR_PROPERTIES);
		OutputStreamWriter outputDescriptor = OCRUtil.getInstanceWriterUTF8(fileDescriptor);
		descriptorProperties.store(outputDescriptor, OCRConstantDataTraining.DESCRIPTOR_PROPERTIES_COMMENTS);
		
		OCRCompressService.compress(tempDir, ocrdt);
	}
	
	/**
	 * Clear all knowledge (Data training) base.
	 */
	public void clear() {
		this.dataTraining = new HashMap<Character, Set<OCRDataTraining>>();
	}
}
