package br.com.barganhas.web.beans;

import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.SeverityMessage;

public class AppManagedBean {
	
	public String goToIndex() {
		return this.getManagedBean(AdministratorBean.class).list();
	}

	protected ServiceBusinessFactory getServiceBusinessFactory() {
		return ServiceBusinessFactory.getInstance();
	}
	
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getManagedBean(Class<T> classe) {
		return (T) this.getFacesContext().getELContext().getELResolver().getValue(this.getFacesContext().getELContext(), null, Util.getNameAsJavaBean(classe));
	}
	
	protected void destroyCurrentSession() {
		((HttpSession) this.getFacesContext().getExternalContext().getSession(true)).invalidate();
	}
	
	protected HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) this.getFacesContext().getExternalContext().getResponse();
	}
	
	protected HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) this.getFacesContext().getExternalContext().getRequest();
	}
	
	protected void setRequestParameter(String alias, Object value) {
		this.getHttpServletRequest().setAttribute(alias, value);
	}
	
	protected void setRequestMessage(RequestMessage requestMessage) {
		this.setRequestMessages(Arrays.asList(new RequestMessage[]{requestMessage}));
	}
	
	protected void setRequestMessages(List<RequestMessage> requestMessages) {
		this.setRequestParameter("LIST_TEXT_MESSAGES", requestMessages);
		this.setRequestParameter("SHOW_MESSAGE_BOX", Boolean.TRUE);
	}
	
	protected String trateExceptionMessage(Exception exception) {
		this.setRequestMessage(new RequestMessage(exception.getMessage(), SeverityMessage.ERROR));
		return null;
	}
}
