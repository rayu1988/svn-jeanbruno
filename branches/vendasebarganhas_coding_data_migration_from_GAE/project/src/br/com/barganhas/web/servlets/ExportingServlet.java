package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.commons.UNHEX;
import br.com.barganhas.commons.Util;

@SuppressWarnings("serial")
public abstract class ExportingServlet extends HttpServlet {
	
	protected static final SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.printEntity(resp);
		this.printEntity(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.printEntity(resp);
		this.printEntity(req, resp);
	}
	
	protected void setHeaders(HttpServletResponse resp, Class<?> servlet) {
		resp.setHeader("Content-Type", "text/plain");
		resp.setHeader("Content-Disposition", "attachment;filename=\""+ Util.getNameAsJavaBean(servlet) +".txt\"");
	}
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
	}
	
	protected void printEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	}
	
	protected String getInsertStatement(Object... properties) {
		StringBuffer returning = new StringBuffer();
		returning.append(" insert into ");
		returning.append(this.getTable());
		returning.append(" ( ");
		returning.append(this.getFields());
		returning.append(" ) ");
		returning.append(" values ");
		returning.append(" ( ");
		
		for (int i = 0; i < properties.length; i++) {
			Object obj = properties[i];
			String value;
			if (obj == null) {
				value = "''";
			} else if (obj instanceof String) {
				value = (String)obj;
				value = "'" + value + "'";
			} else if (obj instanceof UNHEX) {
				value = ((UNHEX)obj).getUNHEXValue();
			} else throw new IllegalStateException("unknow type.");
			
			returning.append(value);
			
			if ((i+1) < properties.length) {
				returning.append(", ");
			}
		}
		
		returning.append(" ); ");
		
		return returning.toString();
	}
	
	protected abstract String getTable();
	
	protected abstract String getFields();
	
}
