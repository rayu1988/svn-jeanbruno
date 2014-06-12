/**
 * 
 */
package br.com.laptracker.web.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.com.laptracker.commons.Util;
import br.com.laptracker.commons.enums.LapType;

/**
 * @author villjea
 *
 */
public class LapTypeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value == null) {
			return "";
		}
		LapType lapType = (LapType) value;
		if (lapType.equals(LapType.CONTINOUS)) {
			return Util.getMessageResourceString("lapTrackContinousLapType");
		} else if (lapType.equals(LapType.FRACTIONATED)) {
			return Util.getMessageResourceString("lapTrackFractionatedLapType");
		} else throw new IllegalArgumentException("Unk");
	}

}
