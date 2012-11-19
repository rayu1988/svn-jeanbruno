package br.com.barganhas.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import org.omnifaces.converter.SelectItemsConverter;

import br.com.barganhas.business.entities.AdvertisementPictureTO;

public class SelectAdvertisementSheetConverter extends SelectItemsConverter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value instanceof AdvertisementPictureTO) {
			AdvertisementPictureTO advertisementPicture = (AdvertisementPictureTO) value;
			return advertisementPicture.getThumbnail().getId().toString();
		}
		return null;
	}
	
}
