package br.com.laptracker.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.com.tatu.helper.GeneralsHelper;

import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.exceptions.AppException;
import br.com.laptracker.commons.Util;
import br.com.laptracker.web.managedbeans.AppSessionBean;

public class UserLoginFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			Object appSessionBean = httpServletRequest.getSession().getAttribute(Util.getNameAsJavaBean(AppSessionBean.class));
			
			UserTO user = null;
			if (appSessionBean != null) {
				user = (UserTO) PropertyUtils.getProperty(appSessionBean, "userAccount");
			}
			
			if (user != null) {
				filterChain.doFilter(request, response);
			} else {
				String url = httpServletRequest.getRequestURL().toString();
				String targetPage = url.substring(url.lastIndexOf('/')+1, url.length());
				if (GeneralsHelper.isStringOk(targetPage) && targetPage.indexOf('.') >= 0) {
					targetPage = targetPage.substring(0, targetPage.indexOf('.'));
					targetPage = targetPage+".xhtml";
					
					if (targetPage.equals("login.xhtml")) {
						filterChain.doFilter(request, response);
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
		response.sendRedirect(request.getContextPath() + "/xhtml/login.jsf");
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
