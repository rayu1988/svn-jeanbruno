package br.com.barganhas.web.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;

import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.Util;
import br.com.barganhas.web.beans.AppSessionBean;

public class UserAccountAccessFilter implements Filter {

	private static final Logger 					logger = Logger.getLogger(UserAccountAccessFilter.class.getCanonicalName());
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		logger.log(Level.INFO, "Initializing filter");
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			Object appSessionBean = httpServletRequest.getSession().getAttribute(Util.getNameAsJavaBean(AppSessionBean.class));
			
			UserAccountTO userAccount = null;
			logger.log(Level.INFO, "checking if already has a valid session bean");
			if (appSessionBean != null) {
				logger.log(Level.INFO, "YES, it has a valid session bean, getting the current userAccount");
				userAccount = (UserAccountTO) PropertyUtils.getProperty(appSessionBean, "userAccount");
			}
			
			logger.log(Level.INFO, "checking if already has a user account logge in the session");
			if (userAccount != null) {
				logger.log(Level.INFO, "YES, it has an user account logged in the session, releasing the filter.");
				
				logger.log(Level.INFO, "before releasing the filter");
				filterChain.doFilter(request, response);
				logger.log(Level.INFO, "after releasing the filter");
			} else {
				logger.log(Level.INFO, "NO, it has not none user account logged in the session.");
				
				String url = httpServletRequest.getRequestURL().toString();
				logger.log(Level.INFO, "The url that target is: " + url);
				
				String targetPage = url.substring(url.lastIndexOf('/')+1, url.length());
				logger.log(Level.INFO, "The target page is." + targetPage);
				
				logger.log(Level.INFO, "Checking if the target page is really a page or not.");
				if (Util.isStringOk(targetPage) && targetPage.indexOf('.') >= 0) {
					targetPage = targetPage.substring(0, targetPage.indexOf('.'));
					targetPage = targetPage+".xhtml";
					
					logger.log(Level.INFO, "The new target page trated: " + targetPage);
					
					logger.log(Level.INFO, "Checking if the target page is login.xhtml.");
					if (targetPage.equals("login.xhtml")) {
						logger.log(Level.INFO, "YES, the taget pate is login.xhtml, releasing the filter.");
						
						logger.log(Level.INFO, "before releasing filter.");
						filterChain.doFilter(request, response);
						logger.log(Level.INFO, "after releasing filter.");
					} else {
						this.redirectToLogin(httpServletResponse, httpServletRequest);
					}
				} else {
					this.redirectToLogin(httpServletResponse, httpServletRequest);
				}
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	private void redirectToLogin(HttpServletResponse response, HttpServletRequest request) throws IOException {
		logger.log(Level.INFO, "(before) Redirecting to login.jsf.");
		response.sendRedirect("/xhtml/account/login.jsf");
		logger.log(Level.INFO, "(after) Redirecting to login.jsf.");
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
}