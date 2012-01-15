/**
 * 
 */
package br.com.softplan.ocr.service;

import java.util.ArrayList;
import java.util.Iterator;

import br.com.softplan.ocr.common.OCRConstant;
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
	
    private static final int 					BEST_MATCH_STORE_COUNT = 8;
    
    private OCRExtractedCharacter[] 			bestExtractedCharacters = new OCRExtractedCharacter[BEST_MATCH_STORE_COUNT];
//    private Character[] 						bestChars = new Character[BEST_MATCH_STORE_COUNT];
    private double[] 							bestMSEs = new double[BEST_MATCH_STORE_COUNT];
    
//    private OCRAccuracyListener 				accuracyListener;
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
        	Iterator<Character> it = OCRDataTrainingService.getInstance().getDataTraining().keySet().iterator();
        	
        	int bestCount = 0;
        	while (it.hasNext()) {
        		Character currentCharacter = it.next();
        		ArrayList<OCRDataTraining> arrayListTraining = new ArrayList<OCRDataTraining>(OCRDataTrainingService.getInstance().get(currentCharacter));
        		
        		OCRDataTraining dataTraining=null;
        		int nimg = arrayListTraining.size();
        		if (nimg > 0) {
        			double mse = 0.0;
        			boolean gotAny = false;
        			for (int i = 0; i < nimg; i++) {
        				
        				dataTraining = arrayListTraining.get(i);
        				
        				if (this.isTrainingImageACandidate(scannedCharacterImage, dataTraining)) {
        					double thisMSE = this.calcMSE(scannedCharacterImage, dataTraining);
        					if ((!gotAny) || (thisMSE < mse)) {
        						gotAny = true;
        						mse = thisMSE;
        					}
        				}
        			}
        			
        			/// Maybe mse should be required to be below a certain threshold before we store it.
        			/// That would help us to handle things like welded characters, and characters that get improperly
        			/// split into two or more characters.
        			if (gotAny) {
        				boolean inserted = false;
        				for (int i = 0; i < bestCount; i++) {
        					if (mse < bestMSEs[i]) {
        						for (int j = Math.min(bestCount, BEST_MATCH_STORE_COUNT - 1); j > i; j--) {
        							int k = j - 1;
//        							bestChars[j] = bestChars[k];
        							this.bestExtractedCharacters[j] = this.bestExtractedCharacters[k];
        							bestMSEs[j] = bestMSEs[k];
        						}
//        						bestChars[i] = currentCharacter;
        						this.bestExtractedCharacters[i] = new OCRExtractedCharacter(this.extractedText, scannedCharacterImage, dataTraining);
        						bestMSEs[i] = mse;
        						if (bestCount < BEST_MATCH_STORE_COUNT) {
        							bestCount++;
        						}
        						inserted = true;
        						break;
        					}
        				}
        				if ((!inserted) && (bestCount < BEST_MATCH_STORE_COUNT)) {
//        					bestChars[bestCount] = currentCharacter;
        					this.bestExtractedCharacters[bestCount] = new OCRExtractedCharacter(this.extractedText, scannedCharacterImage, dataTraining);
        					bestMSEs[bestCount] = mse;
        					bestCount++;
        				}
        			}
        		} else {
        			throw new IllegalStateException("Big problem...");
        		}
        	}
        	
        	/// We could also put some aspect ratio range checking into the page scanning logic (but only when
        	/// decoding; not when loading training images) so that the aspect ratio of a non-empty character
        	/// block is limited to within the min and max of the aspect ratios in the training set.
        	if (bestCount > 0) {
        		this.extractedText.addExtractedCharacter(this.bestExtractedCharacters[0]);
        		
        		//Send accuracy of this identification to the listener
//        		if (accuracyListener != null) {
//        			OCRIdentification identAccuracy = new OCRIdentification(OCRComputation.MSE);
//        			for (int i = 0; i < bestCount; i++) {
//        				identAccuracy.addChar((char) bestChars[i], bestMSEs[i]);
//        			}
//        			accuracyListener.processCharOrSpace(identAccuracy);
//        		}
        	} else {
//        		if (accuracyListener != null) {
//        			OCRIdentification identAccuracy = new OCRIdentification(OCRComputation.MSE);
//        			accuracyListener.processCharOrSpace(identAccuracy);
//        		}
        		throw new IllegalStateException("Problems...");
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
		 // The aspect ratios must be within tolerance.
       if (((scannedCharacterImage.getAspectRatio() / dataTraining.getImage().getAspectRatio()) - 1.0f) > OCRConstant.ASPECT_RATIO_TOLERANCE) {
           return false;
       }
       if (((dataTraining.getImage().getAspectRatio() / scannedCharacterImage.getAspectRatio()) - 1.0f) > OCRConstant.ASPECT_RATIO_TOLERANCE) {
           return false;
       }
       
       // The top whitespace fractions must be within tolerance.
       if (Math.abs(scannedCharacterImage.getTopWhiteSpaceFraction() - dataTraining.getTopWhiteSpaceFraction()) > OCRConstant.TOP_WHITE_SPACE_FRACTION_TOLERANCE) {
           return false;
       }
       
       // The bottom whitespace fractions must be within tolerance.
       if (Math.abs(scannedCharacterImage.getBottomWhiteSpaceFraction() - dataTraining.getBottomWhiteSpaceFraction()) > OCRConstant.BOTTOM_WHITE_SPACE_FRACTION_TOLERANCE) {
           return false;
       }
       
       // If the area being scanned is really small and we
       // are about to crunch down a training image by a huge
       // factor in order to compare to it, then don't do that.
       if ((scannedCharacterImage.getWidth() <= 4) && (dataTraining.getImage().getImageScale().getWidth() >= (scannedCharacterImage.getWidth() * 10))) {
           return false;
       }
       if ((scannedCharacterImage.getHeight() <= 4) && (dataTraining.getImage().getImageScale().getHeight() >= (scannedCharacterImage.getHeight() * 10))) {
           return false;
       }
       
       // If the area being scanned is really large and we
       // are about to expand a training image by a huge
       // factor in order to compare to it, then don't do that.
       if ((dataTraining.getImage().getImageScale().getWidth() <= 4) && (scannedCharacterImage.getWidth() >= (dataTraining.getImage().getImageScale().getWidth() * 10))) {
           return false;
       }
       if ((dataTraining.getImage().getImageScale().getHeight() <= 4) && (scannedCharacterImage.getHeight() >= (dataTraining.getImage().getImageScale().getHeight() * 10))) {
           return false;
       }
       
       return true;
	}

    /**
     * Calculate the error factor between a block of pixels and our image.
     * @param scannedCharacterImage
     * @param dataTraining
     * @return A <code>double</code> representing the average per-pixel mean square error.
     * Lower numbers indicate a better match.
     */
    public double calcMSE(OCRScannedCharacterImage scannedCharacterImage, OCRDataTraining dataTraining) {
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
            theirIdx = (theirY * scannedCharacterImage.getScannedDocumentImage().getDocumentImage().getImage().getImageScale().getWidth()) + scannedCharacterImage.getCoordinateX1();
            myY = ((yScan * dataTraining.getMyMaxY()) / theirYRange);
            myLineIdx = myY * dataTraining.getImage().getImageScale().getWidth();
            for (int theirX = scannedCharacterImage.getCoordinateX1(), xScan = 0 ; theirX < scannedCharacterImage.getCoordinateX2(); theirX++, theirIdx++, xScan++) {
                myX = ((xScan * dataTraining.getMyMaxX()) / theirXRange);
                if ((myX < 0) || (myX > dataTraining.getMyMaxX()) || (myY < 0) || (myY > dataTraining.getMyMaxY())) {
                    thisError = scannedCharacterImage.getScannedDocumentImage().getDocumentImage().getImage().getBinaries()[theirIdx] - 255;
                } else {
                    thisError = scannedCharacterImage.getScannedDocumentImage().getDocumentImage().getImage().getBinaries()[theirIdx] - dataTraining.getImage().getBinaries()[myLineIdx + myX];
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
