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
		
		Sales service = ServiceBusinessFactory.getInstance().getSales();
		//print body
		for (SalesTO to : service.list()) {
			out.println(
				this.getInsertStatement(
						to.getId().toString(),
						to.getDescription(),
						to.getSalesCode(),
						to.getTitle()
				)
			);
		}
	}

	@Override
	protected String getTable() {
		return "SALES";
	}

	@Override
	protected String getFields() {
		return "id_sales, description, sales_code, title";
	}
	
}
