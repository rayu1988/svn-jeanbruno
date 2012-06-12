/**
 * 
 */
package br.com.datawatcher.xstream;

import java.io.InputStream;
import java.net.URL;

import br.com.datawatcher.entity.CheckChange;
import br.com.datawatcher.entity.Column;
import br.com.datawatcher.entity.DataMapping;
import br.com.datawatcher.entity.DataWatcher;
import br.com.datawatcher.entity.FolderMapping;
import br.com.datawatcher.entity.JdbcConnection;
import br.com.datawatcher.entity.Listener;
import br.com.datawatcher.entity.Procedure;
import br.com.datawatcher.entity.TableMapping;
import br.com.datawatcher.xstream.converter.DataLoggingConverter;
import br.com.datawatcher.xstream.converter.SqlStatementConverter;

import com.thoughtworks.xstream.XStream;

/**
 * @author carrefour
 *
 */
public class XStreamFactory {

	private XStream 						xStream = new XStream();
	private static XStreamFactory 			xStreamFactory;
	
	private XStreamFactory() {
		this.build();
	}
	
	public static XStreamFactory getInstance() {
		if (xStreamFactory == null) {
			xStreamFactory = new XStreamFactory();
		}
		return xStreamFactory;
	}
	
	private void build() {
		xStream.alias("datawatcher", DataWatcher.class);
		xStream.alias("table-mapping", TableMapping.class);
		xStream.alias("listener", Listener.class);
		xStream.alias("column", Column.class);
		xStream.alias("procedure", Procedure.class);
		xStream.alias("folder-mapping", FolderMapping.class);
		
		xStream.addImplicitCollection(DataWatcher.class, "mappings");
		
		xStream.aliasField("check-change", DataMapping.class, "checkChange");
		xStream.aliasField("cron-expression", CheckChange.class, "cronExpression");
		xStream.aliasField("table-name", TableMapping.class, "name");
		xStream.aliasField("class-name", Listener.class, "classname");
		xStream.aliasField("column-name", Column.class, "columnName");
		xStream.aliasField("column-type", Column.class, "columnType");
		xStream.aliasField("jdbc-connection", TableMapping.class, "jdbcConnection");
		xStream.aliasField("driverclass-name", JdbcConnection.class, "driverClassName");
		xStream.aliasField("username", JdbcConnection.class, "userLogging");
		xStream.aliasField("password", JdbcConnection.class, "passwordLogging");
		xStream.aliasField("declared-result", TableMapping.class, "declaredResult");
		xStream.aliasField("canonical-path", FolderMapping.class, "canonicalPath");
		xStream.aliasField("regex-filter", FolderMapping.class, "regexFilter");
		
		xStream.useAttributeFor(TableMapping.class, "name");
		xStream.useAttributeFor(CheckChange.class, "cronExpression");
		xStream.useAttributeFor(Listener.class, "classname");
		xStream.useAttributeFor(Listener.class, "asynchronous");
		xStream.useAttributeFor(Column.class, "columnName");
		xStream.useAttributeFor(Column.class, "columnType");
		xStream.useAttributeFor(FolderMapping.class, "canonicalPath");
		xStream.useAttributeFor(FolderMapping.class, "regexFilter");
		
		xStream.omitField(TableMapping.class, "tableState");
		
		xStream.registerConverter(new DataLoggingConverter());
		xStream.registerConverter(new SqlStatementConverter());
	}

	public String toXML(Object obj) {
		return xStream.toXML(obj);
	}
	
	public DataWatcher fromXML(URL url) {
		return (DataWatcher) xStream.fromXML(url);
	}
	
	public DataWatcher fromXML(InputStream inputStream) {
		return (DataWatcher) xStream.fromXML(inputStream);
	}
	
}
