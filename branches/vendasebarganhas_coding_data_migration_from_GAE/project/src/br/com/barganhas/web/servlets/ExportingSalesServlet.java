package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.services.Sales;
import br.com.barganhas.business.services.ServiceBusinessFactory;

@SuppressWarnings("serial")
public class ExportingSalesServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		//print header
		Sales service = ServiceBusinessFactory.getInstance().getSales();
		out.println("id_sales, description, sales_code, title");
		
		//print body
		for (SalesTO to : service.list()) {
			out.println(
				this.getLine(
						to.getId().toString(),
						to.getDescription(),
						to.getSalesCode(),
						to.getTitle()
				)
			);
		}
	}
	
}
