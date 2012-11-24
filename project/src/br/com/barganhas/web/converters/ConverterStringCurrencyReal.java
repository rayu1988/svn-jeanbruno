package br.com.barganhas.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.com.tatu.helper.GeneralsHelper;

public class ConverterStringCurrencyReal implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (!GeneralsHelper.isStringOk(value)) {
			return null;
		}
		
		// e.g. 2.873,93 -> 2873.93
		// e.g. 2.873,20 -> 2873.2
		// e.g. 2.873,00 -> 2873.0
		return value.replace(".", "").replace(",", ".");
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		String stringValue = "";
		if (value == null) {
			return "";
		} else if (value instanceof String) {
			stringValue = (String) value;
			if (!GeneralsHelper.isStringOk(stringValue))
				return "";
		}
		
		// e.g. 2873.93 -> 2.873,93
		// e.g. 2873.2  -> 2.873,20
		// e.g. 2873.0  -> 2.873,00
		stringValue = formatDecimal(stringValue);
		stringValue = formatThousands(stringValue);
		
		return stringValue;
	}
	
	private String formatThousands(String rawValue) {
		int decimalDot = rawValue.lastIndexOf(",");
		String thousands = "";
		for (int i = 0; i < decimalDot ; i++) {
			if (i > 2 && i%3 == 0) {
				thousands = "." + thousands;
			}
			thousands = rawValue.charAt( (decimalDot-1) -i) + thousands;
		}
		
		return thousands + rawValue.substring(decimalDot);
	}
	
	private String formatDecimal(String rawValue) {
		int decimalDot = rawValue.lastIndexOf(".");
		String decimalValue = rawValue.substring(decimalDot + 1);
		if (decimalValue.length() == 1) {
			decimalValue += "0";
		}
		return rawValue.substring(0, decimalDot) + "," + decimalValue;
	}
}
