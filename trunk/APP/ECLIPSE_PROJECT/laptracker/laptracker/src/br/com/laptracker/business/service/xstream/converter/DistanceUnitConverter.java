package br.com.laptracker.business.service.xstream.converter;

import br.com.laptracker.commons.enums.DistanceUnit;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DistanceUnitConverter implements Converter {

	private static final String KILOMETER_VALUE = "kilometros";
	private static final String KILOMETER_SHORT_VALUE = "km";
	private static final String METER_VALUE = "metros";
	private static final String METER_SHORT_VALUE = "mt";
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return clazz != null;
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		DistanceUnit distanceUnit = (DistanceUnit) value;
		if (distanceUnit.equals(DistanceUnit.KILOMETER)) {
			writer.setValue(KILOMETER_VALUE);
		} else if (distanceUnit.equals(DistanceUnit.METER)) {
			writer.setValue(METER_VALUE);
		} else throw new IllegalStateException("Unknow type DistanceUnit");
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		String value = reader.getValue();
		value = value.toLowerCase();
		if (value.equals(KILOMETER_VALUE) || value.equals(KILOMETER_SHORT_VALUE)) {
			return DistanceUnit.KILOMETER;
		} else if (value.equals(METER_VALUE) || value.equals(METER_SHORT_VALUE)){
			return DistanceUnit.METER;
		} else throw new IllegalStateException("Unknow type DistanceUnit");
	}

}
