package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.Advertisement;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.Util;

import com.google.appengine.api.datastore.EntityNotFoundException;

@SuppressWarnings("serial")
public class ExportingAdvertisementServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		try {
			//print header
			Advertisement service = ServiceBusinessFactory.getInstance().getAdvertisement();
			out.println("id_advertisement, contact_email, contact_phone_number_one, contact_phone_number_two, " +
					"count_view, description, exchange_by, is_new_product, since_date, status, title, value, id_advertisement_type, " +
					"id_category, id_sales, id_sheet_picture, id_use_term, id_user_account");
			
			Integer startFrom = Integer.parseInt(req.getParameter("from"));
			//print body
			for (AdvertisementTO to : service.list(startFrom)) {
				AdvertisementTypeTO advertisementType = ServiceBusinessFactory.getInstance().getAdvertisementType().consult(new AdvertisementTypeTO(to.getKeyAdvertisementType()));
				CategoryTO category = ServiceBusinessFactory.getInstance().getCategory().consult(new CategoryTO(to.getKeyCategory()));
				SalesTO sales = null;
				if (to.getKeySales() != null) {
					sales = ServiceBusinessFactory.getInstance().getSales().consult(new SalesTO(to.getKeySales()));
				}
				AdvertisementPictureTO sheet = ServiceBusinessFactory.getInstance().getAdvertisementPicture().consult(new AdvertisementPictureTO(to.getKeySheetPicture()));
				UseTermTO useTerm = ServiceBusinessFactory.getInstance().getUseTerm().consult(new UseTermTO(to.getKeyUseTerm()));
				UserAccountTO userAccount = ServiceBusinessFactory.getInstance().getUserAccount().consult(new UserAccountTO(to.getKeyUserAccount()));
				
				out.println(
					this.getLine(
							to.getId().toString(),
							Util.bytesToHex(to.getContactEmail().getBytes()),
							Util.bytesToHex(to.getContactPhoneNumberOne().getBytes()),
							Util.bytesToHex(to.getContactPhoneNumberTwo().getBytes()),
							to.getCountView().toString(),
							Util.bytesToHex(to.getDescription().getBytes()),
							Util.bytesToHex(to.getListExchangeBy().getBytes()),
							to.getIsNewProduct().toString(),
							formater.format(to.getSinceDate()),
							to.getStatus().toString(),
							Util.bytesToHex(to.getTitle().getBytes()),
							to.getValue().toString(),
							advertisementType.getId().toString(),
							category.getId().toString(),
							(sales!=null ? sales.getId().toString() : null),
							sheet.getId().toString(),
							useTerm.getId().toString(),
							userAccount.getId().toString()
					)
				);
			}
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
