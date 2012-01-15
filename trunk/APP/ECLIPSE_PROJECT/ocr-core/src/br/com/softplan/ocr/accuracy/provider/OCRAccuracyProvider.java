// AccuracyProvider.java
// Copyright (c) 2003-2010 Ronald B. Cemer
// Modified by William Whitney
// All rights reserved.
// This software is released under the BSD license.
// Please see the accompanying LICENSE.txt for details.
package br.com.softplan.ocr.accuracy.provider;

import br.com.softplan.ocr.accuracy.listener.OCRAccuracyListener;

/**
 * Interface to be used by OCR recognizers to accept an accuracy listener to
 * report information to.
 * @author William Whitney
 */
public interface OCRAccuracyProvider {
    public void acceptAccuracyListener(OCRAccuracyListener listener);
}
