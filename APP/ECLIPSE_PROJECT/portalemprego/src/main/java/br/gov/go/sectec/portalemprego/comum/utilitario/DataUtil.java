package br.gov.go.sectec.portalemprego.comum.utilitario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	public static final SimpleDateFormat brazilianShortFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat formatterTodayMidNight = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

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
		try {  
			return (brazilianShortFormat.format(data));  
		} catch (final Exception Ex) {  
			return null; 
		}  
	}  
		
	public static Date obterData(String data) {   
		if (data == null || data.equals("")) {  
			return null;  
		}
		
        Date date = null;  
        try {  
            date = brazilianShortFormat.parse(data);  
        } catch (Exception e) {              
        	 return null;
        }  
        return date;  
    }
	
	@SuppressWarnings("deprecation")
	public static Date getTodayMidNight() {
		Date now = new Date();
		try {
			return formatterTodayMidNight.parse(now.getDate() + "/" + (now.getMonth() + 1) + "/" + (1900 + now.getYear()) + " 00:00:00");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
