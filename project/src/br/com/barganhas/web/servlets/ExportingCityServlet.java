package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.services.City;
import br.com.barganhas.business.services.ServiceBusinessFactory;

import com.google.appengine.api.datastore.EntityNotFoundException;

@SuppressWarnings("serial")
public class ExportingCityServlet extends ExportingServlet {
	
	protected void printEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.setHeaders(resp, this.getClass());
		
		PrintWriter out = resp.getWriter();
		
		//print header
		City service = ServiceBusinessFactory.getInstance().getCity();
		out.println("id_city, name, id_state");
		
		//print body
		Integer startFrom = Integer.parseInt(req.getParameter("from"));
		for (CityTO to : service.list(startFrom)) {
			try {
				StateTO state = ServiceBusinessFactory.getInstance().getState().consult(new StateTO(to.getKeyState()));
				out.println(
						this.getLine(
								to.getId().toString(),
								to.getName(),
								state.getId().toString()
								)
						);
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
}
