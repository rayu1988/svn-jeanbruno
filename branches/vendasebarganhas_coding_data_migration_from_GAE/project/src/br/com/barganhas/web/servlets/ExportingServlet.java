package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		resp.setHeader("Content-Type", "text/csv");
		resp.setHeader("Content-Disposition", "attachment;filename=\""+ Util.getNameAsJavaBean(servlet) +".csv\"");
	}
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
	}
	
	protected void printEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	}
	
	protected String getLine(String... properties) {
		StringBuffer returning = new StringBuffer(" VALUES ");
		
		for (int i = 0; i < properties.length; i++) {
			String value = properties[i];
			if (value == null) {
				returning.append("''");
			} else {
				returning.append("'" + value + "'");
			}
			
			if ((i+1) < properties.length) {
				returning.append(", ");
			}
		}
		
		return returning.toString();
	}
}
