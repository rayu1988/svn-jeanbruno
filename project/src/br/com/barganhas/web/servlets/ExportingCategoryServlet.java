package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.com.tatu.cypher.Base64;

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.ServiceBusinessFactory;

@SuppressWarnings("serial")
public class ExportingCategoryServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		//print header
		Category service = ServiceBusinessFactory.getInstance().getCategory();
		out.println("id_category, description, name");
		
		//print body
		for (CategoryTO to : service.list()) {
			out.println(
				this.getLine(
						to.getId().toString(),
						Base64.encode(to.getDescription().getBytes()),
						Base64.encode(to.getName().getBytes())
				)
			);
		}
	}
	
}
