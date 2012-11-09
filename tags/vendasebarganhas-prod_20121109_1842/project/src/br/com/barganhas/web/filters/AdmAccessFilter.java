package br.com.barganhas.web.filters;

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

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.commons.Util;
import br.com.barganhas.web.beans.AppSessionBean;

public class AdmAccessFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			Object appSessionBean = httpServletRequest.getSession().getAttribute(Util.getNameAsJavaBean(AppSessionBean.class));
			
			AdministratorTO administrator = null;
			if (appSessionBean != null) {
				administrator = (AdministratorTO) PropertyUtils.getProperty(appSessionBean, "administrator");
			}
			
			if (administrator != null) {
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
		response.sendRedirect("/xhtml/admin/login.jsf");
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
}