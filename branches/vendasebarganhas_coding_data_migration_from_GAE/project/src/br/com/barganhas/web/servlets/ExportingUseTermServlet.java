package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.business.services.UseTerm;
import br.com.barganhas.commons.Util;

@SuppressWarnings("serial")
public class ExportingUseTermServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		//print header
		UseTerm service = ServiceBusinessFactory.getInstance().getUseTerm();
		out.println("id_use_term, title, text, is_default");
		
		//print body
		for (UseTermTO to : service.list()) {
			out.println(
				this.getLine(
						to.getId().toString(),
						Util.bytesToHex(to.getTitle().getBytes()),
						Util.bytesToHex(to.getText().getValue().getBytes()),
						to.getDefaultUseTerm().toString()
				)
			);
		}
	}
	
}
