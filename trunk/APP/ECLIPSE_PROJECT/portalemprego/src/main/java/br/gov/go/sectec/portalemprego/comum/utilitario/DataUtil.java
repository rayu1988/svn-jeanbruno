package br.gov.go.sectec.portalemprego.comum.utilitario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * <b>Title:</b> DataUtil.java
 * </p>
 * 
 * <p>
 * <b>Description:</b>
 * </p>
 * 
 * <p>
 * <b>Company: </b> Premium Consultoria e Sistemas
 * </p>
 * 
 * @author Silvânio
 * 
 * @version 1.0.0
 */
public class DataUtil {

	public static String format(final Date data,String pattern){
			SimpleDateFormat format;     
			
			format = new SimpleDateFormat(pattern);  
			
			try {  
			      
				final Date newData = format.parse(data.toString());  
			
			      return (format.format(newData));  
			   
			} catch (final Exception Ex) {  
			
				return null; 
			   
			}  
			
		}  
	public static String format( Date data){
		
		SimpleDateFormat format;     
		
		format = new SimpleDateFormat("dd/MM/yyyy");  
		
		try {  
			
			return (format.format(data));  
			
		} catch (final Exception Ex) {  
			
			return null; 
			
		}  
		
	}  
		
	 public static Date obterData(String data) {   
	        if (data == null || data.equals(""))  
	            return null;  
	          
	        Date date = null;  
	        try {  
	            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	            date = (java.util.Date)formatter.parse(data);  
	        } catch (Exception e) {              
	        	 return null;
	        }  
	        return date;  
	    }  
		
}
