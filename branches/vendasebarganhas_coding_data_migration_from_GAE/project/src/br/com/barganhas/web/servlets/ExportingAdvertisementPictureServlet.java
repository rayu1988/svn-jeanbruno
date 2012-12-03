package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.ServiceBusinessFactory;

import com.google.appengine.api.datastore.EntityNotFoundException;

@SuppressWarnings("serial")
public class ExportingAdvertisementPictureServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		try {
			AdvertisementPicture service = ServiceBusinessFactory.getInstance().getAdvertisementPicture();
			
			Integer startFrom = Integer.parseInt(req.getParameter("from"));
			//print body
			for (AdvertisementPictureTO to : service.list(startFrom)) {
				FileTO thumbnail = ServiceBusinessFactory.getInstance().getFile().consult(new FileTO(to.getKeyThumbnail()));
				FileTO picture = ServiceBusinessFactory.getInstance().getFile().consult(new FileTO(to.getKeyPicture()));
				
				out.println(
					this.getInsertStatement(
							to.getId().toString(),
							thumbnail.getId().toString(),
							picture.getId().toString()
					)
				);
			}
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getTable() {
		return "ADVERTISEMENT_PICTURE";
	}

	@Override
	protected String getFields() {
		return "id_advertisement_picture, id_picture, id_thumbnail";
	}
	
}
