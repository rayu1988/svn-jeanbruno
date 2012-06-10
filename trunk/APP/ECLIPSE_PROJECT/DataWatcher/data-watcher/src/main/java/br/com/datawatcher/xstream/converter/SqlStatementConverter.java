/**
 * 
 */
package br.com.datawatcher.xstream.converter;

import br.com.datawatcher.entity.Procedure;
import br.com.datawatcher.entity.Query;
import br.com.datawatcher.entity.SqlStatement;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author carrefour
 *
 */
public class SqlStatementConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class clazz) {
		return clazz.getSuperclass().equals(SqlStatement.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		SqlStatement sqlStatement = (SqlStatement) value;
		writer.setValue(sqlStatement.getStatement());
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		if (reader.getNodeName().equals("procedure")) {
			return new Procedure(reader.getValue());
		} else if (reader.getNodeName().equals("query")) {
			return new Query(reader.getValue());
		} else throw new IllegalArgumentException("unknown object node name!");
	}

}
