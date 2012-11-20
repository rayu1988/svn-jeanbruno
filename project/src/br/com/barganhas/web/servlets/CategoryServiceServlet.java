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

import br.com.barganhas.business.entities.CategoryTO;
import br.com.barganhas.business.services.Category;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.Util;
import br.com.barganhas.web.beans.AppSessionBean;

@SuppressWarnings("serial")
public class CategoryServiceServlet extends HttpServlet {
	
	private static final Logger 	logger = Logger.getLogger(CategoryServiceServlet.class.getCanonicalName());

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
			logger.log(Level.INFO, "Initializing checking Categories.");
			
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
			
			Category service = ServiceBusinessFactory.getInstance().getCategory();
			if (query.equals("updateAll")) {
				ServletContext servletContext = this.getServletContext();
				InputStream inputStream = servletContext.getResourceAsStream("/xml-data/categories.xml");
				
				Source categorySource = new Source(inputStream);
				List<Element> listCategories = categorySource.getAllElements("category");
				
				for (Element categoryTag : listCategories) {
					CategoryTO category = new CategoryTO();
					category.setName(categoryTag.getAttributeValue("name"));
					category.setDescription(categoryTag.getAttributeValue("description"));
					service.insert(category);
				}
			} else {
				throw new IllegalStateException();
			}
			
			logger.log(Level.INFO, "Ending checking Categories.");
			resp.sendRedirect(req.getContextPath() + "/xhtml/index.jsf");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Problems while checking Categories", e);
			try {
				resp.sendRedirect(req.getContextPath() + "/xhtml/exceptions/pageNotFoundException.jsf");
			} catch (IOException ioException) { }
		}
	}
}
