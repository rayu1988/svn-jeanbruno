package br.com.softplan.ocr.common;

import java.io.PrintStream;

public final class OCRStopWatch {
    
    /** the total time in milliseconds */
    private long total;
    
    /** the start time in milliseconds */
    private long start;
    
    private PrintStream out;
    
    public OCRStopWatch(){
    	this(System.out);
    }

    public OCRStopWatch(PrintStream out){
    	this.out = out;
    }
    
    /**
     * Starts the stop watch.
     */
    public void start() {
        start = System.currentTimeMillis();
    }
    
    /**
     * Stops the stop watch.
     * @return the millis between the last start and current stop of this watch.
     */
    public long stop() {
    	long diff = System.currentTimeMillis() - start;
        total += diff;
        return diff;
    }
    
    public long stop(String message){
    	long time = stop();
    	out.println(message + ": " + time);
    	return time;
    }
    
    /**
     * Resets the stop watch to 0.
     */
    public void reset() {
        total = 0;
    }
    
    public void reset(long total) {
    	this.total = total;
    }
    
    /**
     * @return returns the stopped total time in milliseconds.
     */
    public long getTime() {
        return total;
    }

}