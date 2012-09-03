package br.com.barganhas.web.converters;

import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.com.barganhas.commons.Util;

public class ConverterUserAccountSinceDate implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		Object objectValue = null;
		if(Util.isStringOk(value)){
			try {
				objectValue = new SimpleDateFormat("HH:mm").parse(value);
			} catch (Exception e) {
				new ConverterException(e);
			}
		}
		return objectValue;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		String stringValue = "";
		
		if (value != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(new SimpleDateFormat("dd/MM/yyyy").format(value));
			buffer.append(Util.getMessageResourceString("atHours"));
			buffer.append(new SimpleDateFormat("HH:mm").format(value));
			
			stringValue = buffer.toString(); 
		}
		
		return stringValue;
	}

}
