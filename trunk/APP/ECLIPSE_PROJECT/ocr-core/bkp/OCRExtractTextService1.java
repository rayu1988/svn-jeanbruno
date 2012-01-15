/**
 * 
 */
package br.com.softplan.ocr.service;

import java.util.Set;

import br.com.softplan.ocr.accuracy.listener.OCRAccuracyListener;
import br.com.softplan.ocr.common.OCRComputation;
import br.com.softplan.ocr.common.OCRConstant;
import br.com.softplan.ocr.data.accuracy.OCRIdentification;
import br.com.softplan.ocr.entity.OCRDataTraining;
import br.com.softplan.ocr.entity.OCRExtractedCharacter;
import br.com.softplan.ocr.entity.OCRExtractedText;
import br.com.softplan.ocr.entity.OCRScannedCharacterImage;
import br.com.softplan.ocr.entity.OCRScannedDocumentImage;

/**
 * Utility class to generates a OCRExtractedText through its extractText method.
 * 
 * @author jean.villete
 *
 */
public class OCRExtractTextService {
	
    private static final int 				BEST_MATCH_STORE_COUNT = 8;
    
    private OCRExtractedCharacter[] 		bestExtractedCharacters = new OCRExtractedCharacter[BEST_MATCH_STORE_COUNT];
    private Character[] 					bestChars = new Character[BEST_MATCH_STORE_COUNT];
    private double[] 						bestMSEs = new double[BEST_MATCH_STORE_COUNT];
    
    
    private OCRAccuracyListener 				accuracyListener;
    private OCRExtractedText					extractedText;
    
    private OCRExtractTextService(OCRScannedDocumentImage scannedDocumentImage) {
    	if (scannedDocumentImage == null) throw new IllegalArgumentException();
    	this.extractedText = new OCRExtractedText(scannedDocumentImage);
    	this.extractCharacters();
    }
    
    /**
     * Scan an image and return the decoded text.
     * @param image The <code>Image</code> to be scanned.
     * @param x1 The leftmost pixel position of the area to be scanned, or
     * <code>0</code> to start scanning at the left boundary of the image.
     * @param y1 The topmost pixel position of the area to be scanned, or
     * <code>0</code> to start scanning at the top boundary of the image.
     * @param x2 The rightmost pixel position of the area to be scanned, or
     * <code>0</code> to stop scanning at the right boundary of the image.
     * @param y2 The bottommost pixel position of the area to be scanned, or
     * <code>0</code> to stop scanning at the bottom boundary of the image.
     * @param acceptableChars An array of <code>CharacterRange</code> objects
     * representing the ranges of characters which are allowed to be decoded,
     * or <code>null</code> to not limit which characters can be decoded.
     * @return The decoded text.
     */
    public static final OCRExtractedText extractText(OCRScannedDocumentImage scannedDocumentImage) {
    	return new OCRExtractTextService(scannedDocumentImage).extractedText;
    }
    
    /**
     * Method to discover and populates the current extractedText with their real characters.
     */
	private void extractCharacters() {
        // iterate over scanned characters
        for (OCRScannedCharacterImage scannedCharacterImage : this.extractedText.getScannedDocumentImage().getScannedCharacters()) {
        	int bestCount = 0;
        	
        	for(Set<OCRDataTraining> setDataTraining : OCRDataTrainingService.getInstance().getDataTraining().values()) {
        		for (OCRDataTraining dataTraining : setDataTraining) {
        			double mse = 0.0;
        			boolean gotAny = false;
        			
    				if (this.isTrainingImageACandidate(scannedCharacterImage, dataTraining)) {
    					double thisMSE = this.calculateMSE(scannedCharacterImage, dataTraining);
    					if ((!gotAny) || (thisMSE < mse)) {
    						gotAny = true;
    						mse = thisMSE;
    					}
    				}
        			
        			/// Maybe mse should be required to be below a certain threshold before we store it.
        			/// That would help us to handle things like welded characters, and characters that get improperly
        			/// split into two or more characters.
        			if (gotAny) {
        				boolean inserted = false;
        				for (int i = 0; i < bestCount; i++) {
        					if (mse < this.bestMSEs[i]) {
        						for (int j = Math.min(bestCount, BEST_MATCH_STORE_COUNT - 1); j > i; j--) {
        							int k = j - 1;
        							
        							this.bestChars[j] = this.bestChars[k];
        							this.bestExtractedCharacters[j] = this.bestExtractedCharacters[k];
        							
        							this.bestMSEs[j] = this.bestMSEs[k];
        						}
        						
        						this.bestChars[i] = dataTraining.getCharacter();
        						this.bestExtractedCharacters[i] = new OCRExtractedCharacter(this.extractedText, scannedCharacterImage, dataTraining);
        						
        						this.bestMSEs[i] = mse;
        						if (bestCount < BEST_MATCH_STORE_COUNT) {
        							bestCount++;
        						}
        						inserted = true;
        						break;
        					}
        				}
        				if ((!inserted) && (bestCount < BEST_MATCH_STORE_COUNT)) {
        					
        					this.bestChars[bestCount] = dataTraining.getCharacter();
        					this.bestExtractedCharacters[bestCount] = new OCRExtractedCharacter(this.extractedText, scannedCharacterImage, dataTraining);
        					
        					this.bestMSEs[bestCount] = mse;
        					bestCount++;
        				}
        			}
        		}
        	}
        	
        	/// We could also put some aspect ratio range checking into the page scanning logic (but only when
        	/// decoding; not when loading training images) so that the aspect ratio of a non-empty character
        	/// block is limited to within the min and max of the aspect ratios in the training set.
        	if (bestCount > 0) {
        		this.extractedText.addExtractedCharacter(this.bestExtractedCharacters[0]);
//        		resultStringBuffer.append(bestChars[0].charValue());
        		
        		//Send accuracy of this identification to the listener
        		if (accuracyListener != null) {
        			OCRIdentification identAccuracy = new OCRIdentification(OCRComputation.MSE);
        			for (int i = 0; i < bestCount; i++) {
        				identAccuracy.addChar((char) bestChars[i], bestMSEs[i]);
        			}
        			accuracyListener.processCharOrSpace(identAccuracy);
        		}
        	} else {
        		if (accuracyListener != null) {
        			OCRIdentification identAccuracy = new OCRIdentification(OCRComputation.MSE);
        			accuracyListener.processCharOrSpace(identAccuracy);
        		}
        	}
        }
	}
	
	/**
	 * Method to verify if a specified dataTraining is a good candidate for a likely character (scannedCharacterImage)
	 * @param scannedCharacterImage - likely character
	 * @param dataTraining
	 * @return
	 */
    private boolean isTrainingImageACandidate(OCRScannedCharacterImage scannedCharacterImage, OCRDataTraining dataTraining) {
    	int areaW = scannedCharacterImage.getCoordinateX2() - scannedCharacterImage.getCoordinateX1();
    	int areaH = scannedCharacterImage.getCoordinateY2() - scannedCharacterImage.getCoordinateY1();
    	float aspectRatio = ((float) areaW) / ((float) areaH);
    	int rowHeight = scannedCharacterImage.getScannedRowCharacterImage().getCoordinateY2() - scannedCharacterImage.getScannedRowCharacterImage().getCoordinateY1();
    	float topWhiteSpaceFraction = (float) (scannedCharacterImage.getCoordinateY1() - scannedCharacterImage.getScannedRowCharacterImage().getCoordinateY1()) / (float) rowHeight;
    	float bottomWhiteSpaceFraction = (float) (scannedCharacterImage.getScannedRowCharacterImage().getCoordinateY2() - scannedCharacterImage.getCoordinateY2()) / (float) rowHeight;
    	
        // The aspect ratios must be within tolerance.
        if (((aspectRatio / dataTraining.getImage().getAspectRatio()) - 1.0f) > OCRConstant.ASPECT_RATIO_TOLERANCE) {
            return false;
        }
        if (((dataTraining.getImage().getAspectRatio() / aspectRatio) - 1.0f) > OCRConstant.ASPECT_RATIO_TOLERANCE) {
            return false;
        }
        
        // The top whitespace fractions must be within tolerance.
        if (Math.abs(topWhiteSpaceFraction - dataTraining.getTopWhiteSpaceFraction()) > OCRConstant.TOP_WHITE_SPACE_FRACTION_TOLERANCE) {
            return false;
        }
        
        // The bottom whitespace fractions must be within tolerance.
        if (Math.abs(bottomWhiteSpaceFraction - dataTraining.getBottomWhiteSpaceFraction()) > OCRConstant.BOTTOM_WHITE_SPACE_FRACTION_TOLERANCE) {
            return false;
        }
        
        // If the area being scanned is really small and we
        // are about to crunch down a training image by a huge
        // factor in order to compare to it, then don't do that.
        if ((areaW <= 4) && (dataTraining.getImage().getImageScale().getWidth() >= (areaW * 10))) {
            return false;
        }
        if ((areaH <= 4) && (dataTraining.getImage().getImageScale().getHeight() >= (areaH * 10))) {
            return false;

        }
        
        // If the area being scanned is really large and we
        // are about to expand a training image by a huge
        // factor in order to compare to it, then don't do that.
        if ((dataTraining.getImage().getImageScale().getWidth() <= 4) && (areaW >= (dataTraining.getImage().getImageScale().getWidth() * 10))) {
            return false;
        }
        if ((dataTraining.getImage().getImageScale().getHeight() <= 4) && (areaH >= (dataTraining.getImage().getImageScale().getHeight() * 10))) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Calculate the error factor between a block of pixels and our image.
     * @param theirPixels An array of grayscale pixels which contains the block to be compared
     * This should be in binary format, with each pixel having a value of either <code>0</code>
     * or <code>255</code>.
     * @param w The width of the pixel array.
     * @param h The height of the pixel array.
     * @param x1 The position of the left border of the rectangle to be compared.
     * @param y1 The position of the top border of the rectangle to be compared.
     * @param x2 The position of the right border of the rectangle to be compared.
     * Note that pixels up to, but not including this position, will be compared.
     * @param y2 The position of the bottom border of the rectangle to be compared.
     * Note that pixels up to, but not including this position, will be compared.
     * @return A <code>double</code> representing the average per-pixel mean square error.
     * Lower numbers indicate a better match.
     */
    private double calculateMSE(OCRScannedCharacterImage scannedCharacterImage, OCRDataTraining dataTraining) {
        int theirXRange = Math.max((scannedCharacterImage.getCoordinateX2() - scannedCharacterImage.getCoordinateX1()) - 1, 1);
        int theirYRange = Math.max((scannedCharacterImage.getCoordinateY2() - scannedCharacterImage.getCoordinateY1()) - 1, 1);
        int theirNPix = (theirXRange + 1) * (theirYRange + 1);
        boolean firstCompare = true;
        int myX, myY;
        long thisError, totalError;
        long minError = Long.MAX_VALUE;
        int myLineIdx, theirIdx;
        
        totalError = 0L;
        for (int theirY = scannedCharacterImage.getCoordinateY1(), yScan = 0 ; theirY < scannedCharacterImage.getCoordinateY2(); theirY++, yScan++) {
            theirIdx = (theirY * this.extractedText.getScannedDocumentImage().getDocumentImage().getImage().getImageScale().getWidth()) + scannedCharacterImage.getCoordinateX1();
            myY = ((yScan * dataTraining.getMyMaxX()) / theirYRange);
            myLineIdx = myY * dataTraining.getImage().getImageScale().getWidth();
            for (int theirX = scannedCharacterImage.getCoordinateX1(), xScan = 0 ; theirX < scannedCharacterImage.getCoordinateX2(); theirX++, theirIdx++, xScan++) {
                myX = ((xScan * dataTraining.getMyMaxX()) / theirXRange);
                if ((myX < 0) || (myX > dataTraining.getMyMaxX()) || (myY < 0) || (myY > dataTraining.getMyMaxY())) {
                    thisError = this.extractedText.getScannedDocumentImage().getDocumentImage().getImage().getBinaries()[theirIdx] - 255;
                } else {
                    thisError = this.extractedText.getScannedDocumentImage().getDocumentImage().getImage().getBinaries()[theirIdx] - dataTraining.getImage().getBinaries()[myLineIdx + myX];
                }
                totalError += (thisError * thisError);
            }
        }
        if ((firstCompare) || (totalError < minError)) {
            minError = totalError;
        }
        firstCompare = false;
        
        return Math.sqrt((double) minError) / (double) theirNPix;
    }
}
