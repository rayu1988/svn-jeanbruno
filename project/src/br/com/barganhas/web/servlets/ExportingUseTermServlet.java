package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.business.services.UseTerm;
import br.com.barganhas.commons.UNHEX;
import br.com.barganhas.commons.Util;

@SuppressWarnings("serial")
public class ExportingUseTermServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		UseTerm service = ServiceBusinessFactory.getInstance().getUseTerm();
		//print body
		for (UseTermTO to : service.list()) {
			out.println(
				this.getInsertStatement(
						to.getId().toString(),
						to.getDefaultUseTerm().toString(),
						new UNHEX(Util.bytesToHex(to.getText().getValue().getBytes())),
						new UNHEX(Util.bytesToHex(to.getTitle().getBytes()))
				)
			);
		}
	}

	@Override
	protected String getTable() {
		return "USE_TERM";
	}

	@Override
	protected String getFields() {
		return "id_use_term, is_default, text, title";
	}
	
}
