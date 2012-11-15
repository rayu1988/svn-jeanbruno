package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.tatu.helper.GeneralsHelper;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.ServiceBusinessFactory;

import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class FileServerServlet extends HttpServlet {

	private static final Logger 	logger = Logger.getLogger(FileServerServlet.class.getCanonicalName());
	
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
		if (GeneralsHelper.isStringOk(query)) {
			try {
				ServiceBusinessFactory serviceBusinessFactory = ServiceBusinessFactory.getInstance();
				File serivce = serviceBusinessFactory.getFile();
				FileTO file = serivce.consult(new FileTO(KeyFactory.stringToKey(query)));
				
				resp.setContentType(file.getContentType());
				resp.getOutputStream().write(file.getData().getBytes());
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
}
