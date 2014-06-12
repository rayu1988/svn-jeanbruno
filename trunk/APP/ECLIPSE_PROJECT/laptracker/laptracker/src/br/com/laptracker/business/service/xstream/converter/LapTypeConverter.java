package br.com.laptracker.business.service.xstream.converter;

import br.com.laptracker.commons.enums.LapType;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LapTypeConverter implements Converter {

	private static final String CONTINOUS_TYPE = "continua";
	private static final String FRACTIONATED_TYPE = "fracionada";
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return clazz != null;
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		LapType lapType = (LapType) value;
		if (lapType.equals(LapType.CONTINOUS)) {
			writer.setValue(CONTINOUS_TYPE);
		} else if (lapType.equals(LapType.FRACTIONATED)) {
			writer.setValue(FRACTIONATED_TYPE);
		} else throw new IllegalStateException("Unknow value to LapType laptrack's attribute");
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if (reader.getNodeName().equals("tipo")) {
			String continousValue = reader.getValue();
			if (continousValue.equals(CONTINOUS_TYPE)) {
				return LapType.CONTINOUS;
			} else if (continousValue.equals(FRACTIONATED_TYPE)) {
				return LapType.FRACTIONATED;
			} else throw new IllegalStateException("Unknow value ContinousType for laptrack element");
		}
		return context.currentObject();
	}

}
