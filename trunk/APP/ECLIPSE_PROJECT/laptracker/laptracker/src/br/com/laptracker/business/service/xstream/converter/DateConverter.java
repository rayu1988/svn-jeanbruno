package br.com.laptracker.business.service.xstream.converter;

import java.text.ParseException;
import java.util.Date;

import br.com.laptracker.business.exceptions.AppException;
import br.com.laptracker.business.service.TrackMessage;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return clazz != null;
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Date date = (Date) value;
		writer.setValue(TrackMessage.sdf.format(date));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		try {
			return TrackMessage.sdf.parse(reader.getValue());
		} catch (ParseException e) {
			throw new AppException(e);
		}
	}

}
