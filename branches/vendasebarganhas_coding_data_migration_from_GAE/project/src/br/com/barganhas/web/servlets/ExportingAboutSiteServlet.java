package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.AboutSiteTO;
import br.com.barganhas.business.services.AboutSite;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.UNHEX;
import br.com.barganhas.commons.Util;

@SuppressWarnings("serial")
public class ExportingAboutSiteServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		AboutSite service = ServiceBusinessFactory.getInstance().getAboutSite();
		//print body
		for (AboutSiteTO to : service.list()) {
			out.println(
				this.getInsertStatement(
						to.getId().toString(),
						to.getDefaultAboutSite().toString(),
						new UNHEX(Util.bytesToHex(to.getText().getValue().getBytes())),
						new UNHEX(Util.bytesToHex(to.getTitle().getBytes()))
				)
			);
		}
	}

	@Override
	protected String getTable() {
		return "ABOUT_SITE";
	}

	@Override
	protected String getFields() {
		return "id_about_site, is_default, text, title";
	}
	
}
