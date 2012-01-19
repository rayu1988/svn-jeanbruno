/**
 * 
 */
package br.com.softplan.ocr.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that aims get a hOCR data result with Tesseract Engine.
 * @author jean.villete
 *
 */
public class OCRTesseractEngine {
	
	public static final String 				END_OF_LINE;
	private static final String 			RESOURCE_CONFIGS;
	private static final File 				CONFIGS_FILE;
	private static final String 			OUTPUT_FILE_NAME;
	private static final String 			FILE_EXTENSION_HOCR;
	private static final String 			LANG_OPTION;
	private static final String 			PSM_OPTION;

	static {
		RESOURCE_CONFIGS = "configs";
		CONFIGS_FILE = new File(ClassLoader.getSystemResource(RESOURCE_CONFIGS).getFile());
		OUTPUT_FILE_NAME = "TessOutput";
		FILE_EXTENSION_HOCR = ".html";
		LANG_OPTION = "-l";
		PSM_OPTION = "-psm";
		END_OF_LINE = "\n";
	}
	
    private String tessPath;
    private String psm = "3"; // Fully automatic page segmentation, but no OSD (default)
    
    /** Creates a new instance of OCREngine */
    public OCRTesseractEngine(String tessPath) {
        this.tessPath = tessPath;
    }

    /**
     * Method to invoke the tesseract engine to extract text contained at the image passed as parameter.
     * The return is the string extracted formated as hOCR standard.
     * 
     * @param tiffFiles
     * @param lang
     * @return A hOCR String
     * @throws Exception
     */
    String run(final List<File> tiffFiles, final String lang) throws Exception {
        File tempTessOutputFile = File.createTempFile(OUTPUT_FILE_NAME, FILE_EXTENSION_HOCR);
        String outputFileName = tempTessOutputFile.getPath().substring(0, tempTessOutputFile.getPath().length() - FILE_EXTENSION_HOCR.length()); // chop the .txt extension

        List<String> cmd = new ArrayList<String>();
        cmd.add(tessPath + "/tesseract");
        cmd.add(""); // placeholder for inputfile
        cmd.add(outputFileName);
        cmd.add(LANG_OPTION);
        cmd.add(lang);
        cmd.add(PSM_OPTION);
        cmd.add(psm);
       	cmd.add("+" + CONFIGS_FILE.getAbsoluteFile());

        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(new File(System.getProperty("user.home")));
        pb.redirectErrorStream(true);

        StringBuilder result = new StringBuilder();

        for (File tiffFile : tiffFiles) {
            cmd.set(1, tiffFile.getPath());
            pb.command(cmd);
            Process process = pb.start();
            // any error message?
            // this has become unneccesary b/c the standard error is already merged with the standard output
//            StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream());
//            errorGobbler.start();
            // any output?
            OCRStreamGobbler outputGobbler = new OCRStreamGobbler(process.getInputStream());
            outputGobbler.start();

            int w = process.waitFor();
            System.out.println("Exit value = " + w);

            if (w == 0) {
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(tempTessOutputFile), "UTF-8"));

                String str;

                while ((str = in.readLine()) != null) {
                    result.append(str).append(END_OF_LINE);
                }

                int length = result.length();
                if (length >= END_OF_LINE.length()) {
                    result.setLength(length - END_OF_LINE.length()); // remove last EOL
                }
                in.close();
            } else {
                tempTessOutputFile.delete();
                String msg = outputGobbler.getMessage(); // get actual message from the engine;
                if (msg.trim().length() == 0) {
                    msg = "Errors occurred.";
                }
                throw new RuntimeException(msg);
            }
        }

        tempTessOutputFile.delete();
        return result.toString();
    }

    // GETTERS AND SETTERS //
    /**
     * Sets page segmentation mode.
     * @param mode 
     */
    public void setPSM(String mode) {
        psm = mode;
    }
}
