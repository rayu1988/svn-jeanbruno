package br.com.barganhas.web.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.services.FileTemp;
import br.com.barganhas.business.services.ServiceBusinessFactory;

@SuppressWarnings("serial")
public class ClearTempFilesServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		this.processClear(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		this.processClear(req, resp);
	}
	
	private void processClear(HttpServletRequest req, HttpServletResponse resp) {
		ServiceBusinessFactory serviceBusinessFactory = ServiceBusinessFactory.getInstance();
		FileTemp service = serviceBusinessFactory.getFileTemp();
		service.clearTempFiles();
	}
}
