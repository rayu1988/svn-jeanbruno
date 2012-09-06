package br.com.barganhas.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.Util;

@SuppressWarnings("serial")
public class FileServerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.serverFile(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.serverFile(req, resp);
	}
	
	private void serverFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getParameter("q");
		if (Util.isStringOk(query)) {
			ServiceBusinessFactory serviceBusinessFactory = ServiceBusinessFactory.getInstance();
			File serivce = serviceBusinessFactory.getFile();
			FileTO file = serivce.consult(new FileTO(new Long(query)));
			
			resp.setContentType(file.getContentType());
			resp.getOutputStream().write(file.getData().getBytes());
		}
	}
}
