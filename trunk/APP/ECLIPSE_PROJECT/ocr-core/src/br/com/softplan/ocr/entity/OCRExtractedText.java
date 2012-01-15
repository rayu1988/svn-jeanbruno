/**
 * 
 */
package br.com.softplan.ocr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.softplan.ocr.common.OCRUtil;

/**
 * Entity class.
 * This class represents a text of a valid Document Image that already has been scanned and extracted its text from.
 * 
 * @author jean.villete
 *
 */
public class OCRExtractedText implements Serializable {

	private static final long 						serialVersionUID = 3801946997608581862L;
	
	private int										wordCount = 0;
	
	private OCRScannedDocumentImage					scannedDocumentImage;
	private List<OCRExtractedCharacter>				extractedCharacters;
	private List<OCRExtractedWord>					extractedWords=null;
	
	public OCRExtractedText(OCRScannedDocumentImage scannedDocumentImage) {
		if (scannedDocumentImage == null) throw new IllegalArgumentException();
		
		this.scannedDocumentImage = scannedDocumentImage;
		this.extractedCharacters = new ArrayList<OCRExtractedCharacter>();
	}
	
	/**
	 * Adds a new extracted charecter that has been found.
	 * The concepts of an extracted character is a bound of a previus scanned (likely) character and a data training, that really have a valid character.
	 * 
	 * @param scannedCharacterImage
	 * @param dataTraining
	 */
	public void addExtractedCharacter(OCRExtractedCharacter extractedCharacter) {
		this.extractedCharacters.add(extractedCharacter);
		this.extractedWords = null;
	}

	private boolean hasNextWord() {
		return wordCount < this.scannedDocumentImage.getListWordDivisor().size();
	}
	
	private OCRExtractedWord nextWord() {
		OCRExtractedWord extractedWord = new OCRExtractedWord(this);
		
		int startWord = this.scannedDocumentImage.getListWordDivisor().get(this.wordCount);
		this.wordCount ++;
		
		int endWord = 0;
		if (this.wordCount >= this.scannedDocumentImage.getListWordDivisor().size()) {
			endWord = this.extractedCharacters.size();
		} else {
			endWord = this.scannedDocumentImage.getListWordDivisor().get(this.wordCount);
		}
		
		for (int i = startWord; i < endWord; i ++ ) {
			extractedWord.addExtractedCharacter(this.extractedCharacters.get(i));
		}
		
		return extractedWord;
	}
	
	// GETTERS //
	public OCRScannedDocumentImage getScannedDocumentImage() {
		return scannedDocumentImage;
	}
	public List<OCRExtractedCharacter> getExtractedCharacters() {
		return extractedCharacters;
	}
	public List<OCRExtractedWord> getExtractedWords() {
		if (extractedWords == null) {
			extractedWords = new ArrayList<OCRExtractedWord>();
			while (this.hasNextWord()) {
				OCRExtractedWord extractedWord = this.nextWord();
				if (OCRUtil.isStringOk(extractedWord.toString())) {
					extractedWords.add(extractedWord);
				}
			}
		}
		return extractedWords;
	}
}
