package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.barganhas.business.entities.AboutSiteTO;
import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.entities.AdvertisementPictureTO;
import br.com.barganhas.business.entities.AdvertisementTO;
import br.com.barganhas.business.entities.AdvertisementTypeTO;
import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.entities.CityTO;
import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.SalesTO;
import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.services.Service;
import br.com.barganhas.business.services.ServiceBusinessFactory;

@SuppressWarnings("serial")
public class CounterEntitiesServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.commom(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		this.commom(req, resp);
	}
	
	protected void commom(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int orderigEntity = Integer.parseInt(req.getParameter("entity"));
		Service service = ServiceBusinessFactory.getInstance().getService();
		int count = 0;
		switch (orderigEntity) {
			case 1:
				count = service.count(AboutSiteTO.class);
				break;
			case 2:
				count = service.count(UseTermTO.class);
				break;
			case 3:
				count = service.count(StateTO.class);
				break;
			case 4:
				count = service.count(CityTO.class);
				break;
			case 5:
				count = service.count(SalesTO.class);
				break;
			case 6:
				count = service.count(CategoryTO.class);
				break;
			case 7:
				count = service.count(AdvertisementTypeTO.class);
				break;
			case 8:
				count = service.count(AdministratorTO.class);
				break;
			case 9:
				count = service.count(FileTO.class);
				break;
			case 10:
				count = service.count(UserAccountTO.class);
				break;
			case 11:
				count = service.count(AdvertisementPictureTO.class);
				break;
			case 12:
				count = service.count(AdvertisementTO.class);
				break;
		}
		
		resp.setHeader("Content-Type", "text/html");
		PrintWriter out = resp.getWriter();
		out.println("<html>" +
				"<head><title>Entities Counter</title></head>" +
				"<body><h1>Total Entities: " + count + "</h1></body>" +
				"</html>");
	}
	
}
