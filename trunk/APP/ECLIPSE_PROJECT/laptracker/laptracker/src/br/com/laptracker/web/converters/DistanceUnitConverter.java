/**
 * 
 */
package br.com.laptracker.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.com.laptracker.commons.Util;
import br.com.laptracker.commons.enums.DistanceUnit;

/**
 * @author villjea
 *
 */
public class DistanceUnitConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value == null) {
			return "";
		}
		DistanceUnit distanceUnit = (DistanceUnit) value;
		if (distanceUnit.equals(DistanceUnit.KILOMETER)) {
			return Util.getMessageResourceString("lapTrackKilometerDistanceUnit");
		} else if (distanceUnit.equals(DistanceUnit.METER)) {
			return Util.getMessageResourceString("lapTrackMeterDistanceUnit");
		} else throw new IllegalArgumentException("Unk");
	}

}
