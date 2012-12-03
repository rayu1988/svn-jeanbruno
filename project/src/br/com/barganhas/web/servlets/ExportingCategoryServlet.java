package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.UNHEX;
import br.com.barganhas.commons.Util;

@SuppressWarnings("serial")
public class ExportingCategoryServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		Category service = ServiceBusinessFactory.getInstance().getCategory();
		//print body
		for (CategoryTO to : service.list()) {
			out.println(
				this.getInsertStatement(
						to.getId().toString(),
						new UNHEX(Util.bytesToHex(to.getDescription().getBytes())),
						new UNHEX(Util.bytesToHex(to.getName().getBytes()))
				)
			);
		}
	}

	@Override
	protected String getTable() {
		return "CATEGORY";
	}

	@Override
	protected String getFields() {
		return "id_category, description, name";
	}
	
}
