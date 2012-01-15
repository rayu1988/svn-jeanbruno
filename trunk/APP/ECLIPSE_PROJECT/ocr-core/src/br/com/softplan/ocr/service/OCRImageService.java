package br.com.softplan.ocr.service;

import br.com.softplan.ocr.common.OCRUtil;
import br.com.softplan.ocr.entity.OCRImage;

public class OCRImageService {
	
	// 10-tap, lowpass Finite Impulse Response (FIR) filter.
    protected static final float[] FILTER_FIR_COEFFS = {
        0.05001757311983922f,
        -0.06430830829693616f,
        -0.0900316316157106f,
        0.1500527193595177f,
        0.45015815807855303f,
        0.45015815807855303f,
        0.1500527193595177f,
        -0.0900316316157106f,
        -0.06430830829693616f,
        0.05001757311983922f,
    };
	
    /**
     * Convert all pixels to grayscale from RGB or RGBA.
     * Do not call this method if the pixels are not currently RGB or RGBA.
     * @param normalize <code>true</code> to normalize the image after converting to
     * grayscale, such that the darkest pixel in the image is all black and the lightest
     * pixel in the image is all white.
     */
    public static int[] getBinariesAtGrayScale(int[] binariesBase, boolean normalize) {
        if (binariesBase == null) {
            throw new IllegalArgumentException();
        }
        int[] binariesToReturn = binariesBase.clone();
        if (!normalize) {
            for (int i = 0; i < binariesToReturn.length; i++) {
                binariesToReturn[i] = rgbToGrayScale(binariesToReturn[i]);
            }
        } else {
            int pix;
            binariesToReturn[0] = pix = rgbToGrayScale(binariesToReturn[0]);
            int min = pix, max = pix;
            for (int i = 1; i < binariesToReturn.length; i++) {
                binariesToReturn[i] = pix = rgbToGrayScale(binariesToReturn[i]);
                min = Math.min(min, pix);
                max = Math.max(max, pix);
            }
            int range = max - min;
            if (range < 1) {
                for (int i = 0; i < binariesToReturn.length; i++) {
                    binariesToReturn[i] = 255;
                }
            } else {
                for (int i = 0; i < binariesToReturn.length; i++) {
                    binariesToReturn[i] = Math.min(255, Math.max(0, ((binariesToReturn[i] - min) * 255) / range));
                }
            }
        }
        return binariesToReturn;
    }

    /**
     * Convert the current array image (pixels) from RGB (Red, Green and Blue) to a Gray Scale (almost black and white).
     * @param pix
     * @return
     */
    public static int rgbToGrayScale(int pix) {
        int r = (pix >> 16) & 0xff;
        int g = (pix >> 8) & 0xff;
        int b = pix & 0xff;
        int Y = ((r * 306) + (g * 601) + (b * 117)) >> 10;
        if (Y < 0) {
            Y = 0;
        } else if (Y > 255) {
            Y = 255;
        }
        return Y;
    }

    /**
     * Applies the filter over the current array image (pixels).
     */
    public static int[] getFilteredBinaries(OCRImage ocrImage) {
    	if (ocrImage == null) {
            throw new IllegalArgumentException();
        }
    	
        float[] firSamples = new float[FILTER_FIR_COEFFS.length];
        float c;
        int lastPos = firSamples.length - 1;
        int[] binariesToReturn = ocrImage.getBinaries().clone();
        
        // Filter horizontally.
        for (int y = 0; y < ocrImage.getImageScale().getHeight(); y++) {
            for (int i = 0; i < firSamples.length; i++) {
                firSamples[i] = 255.0f;
            }
            
            int outX = -(firSamples.length / 2);
            for (int x = 0; x < ocrImage.getImageScale().getWidth(); x++, outX++) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = ocrImage.getBinaryAt(x, y);
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                
                if (outX >= 0) {
                	binariesToReturn[OCRUtil.matrixIndexToArrayIndex(outX, y, ocrImage.getImageScale().getWidth())] = (int) c;
                }
            }
            
            while (outX < ocrImage.getImageScale().getWidth()) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = 255.0f;
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                
                binariesToReturn[OCRUtil.matrixIndexToArrayIndex(outX, y, ocrImage.getImageScale().getWidth())] = (int) c;
                outX++;
            }
        }
        
        // Filter vertically.
        for (int x = 0; x < ocrImage.getImageScale().getWidth(); x++) {
            for (int i = 0; i < firSamples.length; i++) {
                firSamples[i] = 255.0f;
            }
            
            int outY = -(firSamples.length / 2);
            for (int y = 0; y < ocrImage.getImageScale().getHeight(); y++, outY++) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = ocrImage.getBinaryAt(x, y);
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                
                if (outY >= 0) {
                	binariesToReturn[OCRUtil.matrixIndexToArrayIndex(x, outY, ocrImage.getImageScale().getWidth())] = (int) c;
                }
            }
            
            while (outY < ocrImage.getImageScale().getHeight()) {
                c = 0.0f;
                for (int j = 0; j < lastPos; j++) {
                    c += (firSamples[j] * FILTER_FIR_COEFFS[j]);
                    firSamples[j] = firSamples[j + 1];
                }
                
                c += (firSamples[lastPos] * FILTER_FIR_COEFFS[lastPos]);
                firSamples[lastPos] = 255.0f;
                if (c < 0.0f) {
                    c = 0.0f;
                } else if (c > 255.0f) {
                    c = 255.0f;
                }
                
                binariesToReturn[OCRUtil.matrixIndexToArrayIndex(x, outY, ocrImage.getImageScale().getWidth())] = (int) c;
                outY++;
            }
        }
        
        return binariesToReturn;
    }
}
