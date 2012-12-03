package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.services.Administrator;
import br.com.barganhas.business.services.ServiceBusinessFactory;

@SuppressWarnings("serial")
public class ExportingAdministratorServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		Administrator service = ServiceBusinessFactory.getInstance().getAdministrator();
		//print body
		for (AdministratorTO to : service.list()) {
			out.println(
				this.getInsertStatement(
						to.getId().toString(),
						to.getEmail(),
						to.getFullname(),
						to.getNickname(),
						to.getPassword()
				)
			);
		}
	}

	@Override
	protected String getTable() {
		return "ADMINISTRATOR";
	}

	@Override
	protected String getFields() {
		return "id_user, email, fullname, nickname, password";
	}
	
}
