package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.com.tatu.cypher.Base64;

import br.com.barganhas.business.entities.AboutSiteTO;
import br.com.barganhas.business.services.AboutSite;
import br.com.barganhas.business.services.ServiceBusinessFactory;

@SuppressWarnings("serial")
public class ExportingAboutSiteServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		//print header
		AboutSite service = ServiceBusinessFactory.getInstance().getAboutSite();
		out.println("id_about_site, title, text, is_default");
		
		//print body
		for (AboutSiteTO to : service.list()) {
			out.println(
				this.getLine(
						to.getId().toString(),
						Base64.encode(to.getTitle().getBytes()),
						Base64.encode(to.getText().getValue().getBytes()),
						to.getDefaultAboutSite().toString()
				)
			);
		}
	}
	
}
