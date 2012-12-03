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
		
		AdvertisementType service = ServiceBusinessFactory.getInstance().getAdvertisementType();
		
		//print body
		for (AdvertisementTypeTO to : service.list()) {
			out.println(
				this.getInsertStatement(
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

	@Override
	protected String getTable() {
		return "ADVERTISEMENT_TYPE";
	}

	@Override
	protected String getFields() {
		return "id_advertisement_type, days_to_expire, description, score, title, total_pictures, value";
	}
	
}
