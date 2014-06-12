package br.com.laptracker.web.filter;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Class to control mainly the caching of the images within the site.
 * 
 * @author villjea
 *
 */
public class CacheControl implements Filter {

	/**
	 * 2 weeks
	 */
	private static final long CACHE_AGE = 1209600L;
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Calendar twoWeeks = Calendar.getInstance();
		twoWeeks.add(Calendar.WEEK_OF_MONTH, 2);
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setDateHeader("Expires", twoWeeks.getTimeInMillis());
		httpResponse.setHeader("Cache-Control", "max-age=" + CACHE_AGE);
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
