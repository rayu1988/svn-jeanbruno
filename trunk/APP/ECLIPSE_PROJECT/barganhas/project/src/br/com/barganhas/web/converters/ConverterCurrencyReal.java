package br.com.barganhas.web.converters;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.com.barganhas.business.exceptions.AppException;

public class ConverterCurrencyReal implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		Object objectValue;
		NumberFormat num = NumberFormat.getInstance(new Locale("pt", "BR"));
		if (value != null && (value.length() == 0 || value.trim().length() == 0)) {
			objectValue = null;
		} else {
			try {
				Number numberValue = num.parse(value);
				objectValue = numberValue.intValue();
			} catch (Exception e) {
				throw new AppException(e);
			}
		}
		return objectValue;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		String stringValue = "";
		if (value != null) {
			stringValue = this.formatarDecimal((Integer)value, 2);
		}
		return stringValue;
	}

	
	public String formatarDecimal(int valor, int decimais) {
        DecimalFormat decimalFormat = null;
        String pattern = "";

        for(int i = 1; i <= decimais; i++)
            pattern = pattern + "0";
        if(decimais > 0)
            pattern = "." + pattern;
        try {
            decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.GERMAN);
        } catch(ClassCastException e) {
            return("");
        }
        
        decimalFormat.applyPattern("##,###,###,###,###,##0" + pattern);

        return(decimalFormat.format(valor));
    }
}
