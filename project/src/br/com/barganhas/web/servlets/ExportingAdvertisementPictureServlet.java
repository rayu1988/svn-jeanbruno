package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.services.AdvertisementPicture;
import br.com.barganhas.business.services.ServiceBusinessFactory;

import com.google.appengine.api.datastore.EntityNotFoundException;

@SuppressWarnings("serial")
public class ExportingAdvertisementPictureServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		try {
			//print header
			AdvertisementPicture service = ServiceBusinessFactory.getInstance().getAdvertisementPicture();
			out.println("id_advertisement_picture, id_picture, id_thumbnail");
			
			//print body
			for (AdvertisementPictureTO to : service.list()) {
				FileTO thumbnail = ServiceBusinessFactory.getInstance().getFile().consult(new FileTO(to.getKeyThumbnail()));
				FileTO picture;
					picture = ServiceBusinessFactory.getInstance().getFile().consult(new FileTO(to.getKeyPicture()));
				
				out.println(
					this.getLine(
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
	
}
