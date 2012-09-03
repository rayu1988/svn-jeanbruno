package br.com.barganhas.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.UserAccountStatus;

public class ConverterUserAccountStatus implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)throws ConverterException {
		String userAccountStatus = (String) value;
		String stringValue = "";
		
		if (userAccountStatus.equals(UserAccountStatus.ACTIVE)) {
			stringValue = Util.getMessageResourceString("userAccountStatusActive");
		} else if (userAccountStatus.equals(UserAccountStatus.LOCKED)) {
			stringValue = Util.getMessageResourceString("userAccountStatusLocked");
		} else if (userAccountStatus.equals(UserAccountStatus.PENDING)) {
			stringValue = Util.getMessageResourceString("userAccountStatusPending");
		} else throw new IllegalArgumentException();
		
		return stringValue;
	}

}
