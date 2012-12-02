package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.business.services.State;

@SuppressWarnings("serial")
public class ExportingStateServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		//print header
		State service = ServiceBusinessFactory.getInstance().getState();
		out.println("id_state, name, acronym");
		
		//print body
		for (StateTO to : service.list()) {
			out.println(
				this.getLine(
						to.getId().toString(),
						to.getName(),
						to.getAcronym()
				)
			);
		}
	}
	
}
