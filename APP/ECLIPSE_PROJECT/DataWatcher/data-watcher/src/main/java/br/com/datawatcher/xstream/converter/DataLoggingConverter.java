/**
 * 
 */
package br.com.datawatcher.xstream.converter;

import br.com.datawatcher.common.Util;
import br.com.datawatcher.entity.DataLogging;
import br.com.datawatcher.entity.PasswordLogging;
import br.com.datawatcher.entity.UserLogging;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author carrefour
 *
 */
public class DataLoggingConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return clazz.getSuperclass().equals(DataLogging.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		DataLogging dataLogging = (DataLogging) value;
		if (Util.isStringOk(dataLogging.getDecryptClass())) {
			writer.addAttribute("decrypt-class", dataLogging.getDecryptClass());
		}
		if (dataLogging instanceof UserLogging) {
			writer.setValue(((UserLogging) dataLogging).getUserName());
		} else if (dataLogging instanceof PasswordLogging) {
			writer.setValue(((PasswordLogging) dataLogging).getPassword());
		} else throw new IllegalArgumentException("unknown object value!");
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if (reader.getNodeName().equals("username")) {
			if (Util.isStringOk(reader.getAttribute("decrypt-class"))) {
				String decryptClass = reader.getAttribute("decrypt-class");
				String userName = reader.getValue();
				return new UserLogging(userName, decryptClass);
			}
			return new UserLogging(reader.getValue());
		} else if (reader.getNodeName().equals("password")) {
			if (Util.isStringOk(reader.getAttribute("decrypt-class"))) {
				String decryptClass = reader.getAttribute("decrypt-class");
				String password = reader.getValue();
				return new PasswordLogging(password, decryptClass);
			}
			return new PasswordLogging(reader.getValue());
		} else throw new IllegalArgumentException("unknown object node name!");
	}

}
