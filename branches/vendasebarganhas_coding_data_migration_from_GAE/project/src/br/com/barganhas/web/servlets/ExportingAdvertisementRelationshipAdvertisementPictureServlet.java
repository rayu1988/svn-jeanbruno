package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

@SuppressWarnings("serial")
public class ExportingAdvertisementRelationshipAdvertisementPictureServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		try {
			//print header
			Advertisement service = ServiceBusinessFactory.getInstance().getAdvertisement();
			out.println("id_advertisement, id_advertisement_picture");
			
			//print body
			for (AdvertisementTO to : service.list()) {
				if (Util.isCollectionOk(to.getPictures())) {
					for (Key keyAdvertisementPicture : to.getPictures()) {
						AdvertisementPictureTO picture = ServiceBusinessFactory.getInstance().getAdvertisementPicture().consult(new AdvertisementPictureTO(keyAdvertisementPicture));
						
						out.println(
								this.getLine(
										to.getId().toString(),
										picture.getId().toString()
										)
								);
					}
				}
			}
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
