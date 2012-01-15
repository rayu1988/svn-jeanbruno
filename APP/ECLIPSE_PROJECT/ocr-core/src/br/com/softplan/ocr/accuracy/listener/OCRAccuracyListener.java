// AccuracyListener.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// Modified by William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package br.com.softplan.ocr.accuracy.listener;

import br.com.softplan.ocr.data.accuracy.OCRIdentification;

/**
 * Provides an interface that allows implementers to receive information
 * char by char about the quality of the recognition.
 * @author William Whitney
 */
public interface OCRAccuracyListener {
    public void processCharOrSpace(OCRIdentification identAccuracy);
}