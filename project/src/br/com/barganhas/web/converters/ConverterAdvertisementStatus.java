package br.com.barganhas.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.AdvertisementStatus;

public class ConverterAdvertisementStatus implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)throws ConverterException {
		AdvertisementStatus advertisementStatus = (AdvertisementStatus) value;
		String stringValue = "";
		
		if (advertisementStatus.equals(AdvertisementStatus.PENDING)) {
			stringValue = Util.getMessageResourceString("advertisementStatusPending");
		} else if (advertisementStatus.equals(AdvertisementStatus.ENABLED)) {
			stringValue = Util.getMessageResourceString("advertisementStatusEnabled");
		} else if (advertisementStatus.equals(AdvertisementStatus.DISABLED)) {
			stringValue = Util.getMessageResourceString("advertisementStatusDisabled");
		} else if (advertisementStatus.equals(AdvertisementStatus.EXPIRED)) {
			stringValue = Util.getMessageResourceString("advertisementStatusExpired");
		} else throw new IllegalArgumentException();
		
		return stringValue;
	}

}
