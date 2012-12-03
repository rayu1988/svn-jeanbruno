package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.business.services.UserAccount;

import com.google.appengine.api.datastore.EntityNotFoundException;

@SuppressWarnings("serial")
public class ExportingUserAccountServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		try {
			UserAccount service = ServiceBusinessFactory.getInstance().getUserAccount();
			//print body
			Integer startFrom = Integer.parseInt(req.getParameter("from"));
			for (UserAccountTO to : service.list(startFrom)) {
				CityTO city = ServiceBusinessFactory.getInstance().getCity().consult(new CityTO(to.getKeyCity()));
				
				FileTO file = null;
				if (to.getKeyProfileImage() != null) {
					file = ServiceBusinessFactory.getInstance().getFile().consult(new FileTO(to.getKeyProfileImage()));
				}
				
				out.println(
					this.getInsertStatement(
							to.getId().toString(),
							to.getEmail(),
							to.getFullname(),
							to.getNickname(),
							to.getPassword(),
							to.getContactEmail(),
							to.getContactPhoneNumberOne(),
							to.getContactPhoneNumberTwo(),
							formater.format(to.getSinceDate()),
							to.getStatus().toString(),
							city.getId().toString(),
							(file != null ? file.getId().toString() : null)
					)
				);
			}
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getTable() {
		return "USER_ACCOUNT";
	}

	@Override
	protected String getFields() {
		return "id_user, email, fullname, nickname, password, contact_email, contact_phone_number_one, " +
				" contact_phone_number_two, since_date, status, id_city, id_profile_image";
	}
	
}
