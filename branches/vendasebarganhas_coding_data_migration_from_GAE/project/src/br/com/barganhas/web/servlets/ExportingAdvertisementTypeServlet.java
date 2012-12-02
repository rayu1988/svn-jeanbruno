package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.services.AdvertisementType;
import br.com.barganhas.business.services.ServiceBusinessFactory;

@SuppressWarnings("serial")
public class ExportingAdvertisementTypeServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		//print header
		AdvertisementType service = ServiceBusinessFactory.getInstance().getAdvertisementType();
		out.println("id_advertisement_type, days_to_expire, description, score, title, total_pictures, value");
		
		//print body
		for (AdvertisementTypeTO to : service.list()) {
			out.println(
				this.getLine(
						to.getId().toString(),
						to.getDaysToExpire().toString(),
						to.getDescription(),
						to.getAdvertisementScore().toString(),
						to.getTitle(),
						to.getTotalPictures().toString(),
						to.getValue()
				)
			);
		}
	}
	
}
