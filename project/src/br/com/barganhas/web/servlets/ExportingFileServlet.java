package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.Util;

@SuppressWarnings("serial")
public class ExportingFileServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		//print header
		File service = ServiceBusinessFactory.getInstance().getFile();
		out.println("id_file, content_type, file_name, data");
		
		//print body
		Integer startFrom = Integer.parseInt(req.getParameter("from"));
		for (FileTO to : service.list(startFrom)) {
			out.println(
				this.getLine(
						to.getId().toString(),
						to.getContentType(),
						to.getFileName(),
						Util.bytesToHex(to.getData().getBytes())
				)
			);
		}
	}
	
}
