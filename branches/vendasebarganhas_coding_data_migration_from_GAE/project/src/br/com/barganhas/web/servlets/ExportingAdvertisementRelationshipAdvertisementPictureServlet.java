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
			Advertisement service = ServiceBusinessFactory.getInstance().getAdvertisement();
			//print body
			for (AdvertisementTO to : service.list()) {
				if (Util.isCollectionOk(to.getPictures())) {
					for (Key keyAdvertisementPicture : to.getPictures()) {
						AdvertisementPictureTO picture = ServiceBusinessFactory.getInstance().getAdvertisementPicture().consult(new AdvertisementPictureTO(keyAdvertisementPicture));
						
						out.println("update ADVERTISEMENT_PICTURE set id_advertisement = " + to.getId() + " where id_advertisement_picture = " + picture.getId() + " ;");
					}
				}
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
		return "id_advertisement, id_advertisement_picture";
	}
	
}
