package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.services.File;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.UNHEX;
import br.com.barganhas.commons.Util;

@SuppressWarnings("serial")
public class ExportingFileServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		File service = ServiceBusinessFactory.getInstance().getFile();
		//print body
		Integer startFrom = Integer.parseInt(req.getParameter("from"));
		for (FileTO to : service.list(startFrom)) {
			out.println(
				this.getInsertStatement(
						"FileTO",
						to.getId().toString(),
						to.getContentType(),
						to.getFileName(),
						new UNHEX(Util.bytesToHex(to.getData().getBytes()))
				)
			);
		}
	}

	@Override
	protected String getTable() {
		return "FILE";
	}

	@Override
	protected String getFields() {
		return "DTYPE, id_file, content_type, file_name, since_date, data";
	}
	
}
