package br.com.barganhas.web.converters;

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
			if (appSessionBean != null) {
				AdministratorTO administrator = (AdministratorTO) PropertyUtils.getProperty(appSessionBean, "administrator");
				if (administrator != null) {
					filterChain.doFilter(request, response);
				}
			} else {
				String url = httpServletRequest.getRequestURL().toString();
				String targetPage = url.substring(url.lastIndexOf('/')+1, url.length());
				if (Util.isStringOk(targetPage)) {
					targetPage = targetPage.substring(0, targetPage.indexOf('.'));
					targetPage = targetPage+".xhtml";
					
					if (targetPage.equals("login.xhtml")) {
						filterChain.doFilter(request, response);
					}
				} else if (httpServletRequest.getQueryString() != null) {
					httpServletRequest.getSession().setAttribute("URL_REDIRECT", buildURLWithQueryParams(httpServletRequest));
					httpServletResponse.sendRedirect(getServerAddr(httpServletRequest));
				} else {
					httpServletResponse.sendRedirect(getServerAddr(httpServletRequest) + "/xhtml/admin/login.jsf");
				}
			}
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public static String getServerAddr(HttpServletRequest request) {
		String hostAddr = request.getScheme().replaceFirst("https", "http") + "://" + request.getServerName() + getPort(request);
		return hostAddr;
	}
	
	public static String getPort(HttpServletRequest request) {
		String port = null;
		if (request.getServerPort() != 80 && request.getServerPort() != 443) {
			port = ":" + request.getServerPort();
		} else {
			port = "";
		}
		return port;
	}
	
	public static String buildURLWithQueryParams(HttpServletRequest hRequest) {
		String url = hRequest.getRequestURL().toString();
		if (hRequest.getQueryString() != null && !hRequest.getQueryString().equals("")) {
			url += "?" + hRequest.getQueryString();
		}
		return url;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
