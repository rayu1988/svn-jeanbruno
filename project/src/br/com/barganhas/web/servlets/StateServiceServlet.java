package br.com.barganhas.web.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import org.apache.commons.beanutils.PropertyUtils;
import org.com.tatu.helper.GeneralsHelper;

import br.com.barganhas.business.entities.StateTO;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.business.services.State;
import br.com.barganhas.commons.Util;
import br.com.barganhas.web.beans.AppSessionBean;

@SuppressWarnings("serial")
public class StateServiceServlet extends HttpServlet {
	
	private static final Logger 	logger = Logger.getLogger(StateServiceServlet.class.getCanonicalName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		this.commonService(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		this.commonService(req, resp);
	}
	
	private void commonService(HttpServletRequest req, HttpServletResponse resp) {
		try {
			logger.log(Level.INFO, "Initializing checking States.");
			
			// checking if there's a valid sassion for a valid Administrator
			HttpServletRequest httpServletRequest = (HttpServletRequest) req;
			Object appSessionBean = httpServletRequest.getSession().getAttribute(Util.getNameAsJavaBean(AppSessionBean.class));
			if (appSessionBean == null || PropertyUtils.getProperty(appSessionBean, "administrator") == null) {
				throw new IllegalStateException();
			}
			
			String query = req.getParameter("action");
			if (!GeneralsHelper.isStringOk(query)) {
				throw new IllegalArgumentException();
			}
			
			State service = ServiceBusinessFactory.getInstance().getState();
			if (query.equals("updateAll")) {
				if (!service.alreadyExists()) {
					ServletContext servletContext = this.getServletContext();
					InputStream inputStream = servletContext.getResourceAsStream("/xml-data/brazil-states.xml");
					
					Source stateSource = new Source(inputStream);
					List<Element> listStates = stateSource.getAllElements("state");
					
					for (Element stateTag : listStates) {
						StateTO state = new StateTO();
						state.setName(stateTag.getAttributeValue("name"));
						state.setAcronym(stateTag.getAttributeValue("acronym"));
						service.insert(state);
					}
				}
			} else if (query.equals("removeAll")) {
				service.removeAll();
			} else {
				throw new IllegalStateException();
			}
			
			logger.log(Level.INFO, "Ending checking States.");
			resp.sendRedirect("/xhtml/index.jsf");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Problems while checking States", e);
			try {
				resp.sendRedirect("/xhtml/exceptions/pageNotFoundException.jsf");
			} catch (IOException ioException) { }
		}
	}
}
