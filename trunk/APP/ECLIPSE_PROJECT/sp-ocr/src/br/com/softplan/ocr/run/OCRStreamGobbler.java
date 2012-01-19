/**
 * 
 */
package br.com.softplan.ocr.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * When Runtime.exec() won't.
 * http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html
 * 
 * @author jean.villete
 */
class OCRStreamGobbler extends Thread {
    InputStream is;
    StringBuilder outputMessage = new StringBuilder();

    protected OCRStreamGobbler(InputStream is) {
        this.is = is;
    }

    protected String getMessage() {
        return outputMessage.toString();
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(this.is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                this.outputMessage.append(line).append(OCRTesseractEngine.END_OF_LINE);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
