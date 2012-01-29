// OCRIdentification.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// Modified by William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package br.com.softplan.ocr.data.accuracy;

import java.util.ArrayList;

import br.com.softplan.ocr.common.OCRComputation;
import br.com.softplan.ocr.entity.OCRExtractedCharacter;

/**
 * Provides a data structure to manage an OCR character recognition attempt.
 * 
 * pt.BR Fornece uma estrutura de dados para gerenciar uma tentativa de reconhecimento de caracteres com o OCR.
 * 
 * @author William Whitney
 */
public class OCRIdentification {

    private OCRComputation ocrType;
    private ArrayList<OCRExtractedCharacter> extractedCharacters;
    private ArrayList<Double> identErrors;
    
    public OCRIdentification(OCRComputation ocrType) {
    	if (ocrType == null) throw new IllegalArgumentException("argument can't be a null value");
    	
    	this.ocrType = ocrType;
        this.extractedCharacters = new ArrayList<OCRExtractedCharacter>();
        this.identErrors = new ArrayList<Double>();
    }

    public void addExtractedCharacter(OCRExtractedCharacter extractedCharacter, double identError) {
        this.extractedCharacters.add(extractedCharacter);
        this.identErrors.add(identError);
    }

    public int getNumChars() {
        return extractedCharacters.size();
    }

    public OCRExtractedCharacter getCharIdx(int idx) {
        return extractedCharacters.get(idx);
    }

    public double getIdentErrorIdx(int idx) {
        return identErrors.get(idx);
    }

    public OCRComputation getOCRType() {
        return ocrType;
    }

    @Override
    public String toString() {
        String retStr = "----- OCRIdentification  -----\n";
        retStr += "OCR Type: " + ocrType + "\n";
        for (int i = 0; i < this.extractedCharacters.size(); i++) {
            retStr += "Char: " + this.extractedCharacters.get(i) + " " + this.identErrors.get(i) + "\n";
        }

        return retStr;
    }
}
