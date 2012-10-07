package br.com.barganhas.web.beans;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.JSFunctionTimeRunning.JSFunctionBoxing;
import br.com.barganhas.commons.RequestMessage;
import br.com.barganhas.commons.Util;
import br.com.barganhas.enums.SeverityMessage;
import br.com.barganhas.web.beans.control.ControlRequest;

public class AppManagedBean extends ControlRequest implements Serializable {
	
	private static final long 		serialVersionUID = 5997769430729017199L;
	private static final Logger 	logger = Logger.getLogger(AppManagedBean.class.getCanonicalName());
	
	public static final String 		LIST_TEXT_MESSAGES = "LIST_TEXT_MESSAGES";
	public static final String 		SHOW_MESSAGE_BOX = "SHOW_MESSAGE_BOX";
	public static final String 		JS_FUNCTION_CALLED = "JS_FUNCTION_CALLED";
	public static final String 		NAME_JS_FUNCTION_CALLED = "NAME_JS_FUNCTION_CALLED";
	
	public ServiceBusinessFactory getServiceBusinessFactory() {
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
	
	protected void setRequestMessage(RequestMessage requestMessage) {
		this.setRequestMessage(this.getHttpServletRequest(), requestMessage);
	}
	
	protected void setRequestMessages(List<RequestMessage> requestMessages) {
		this.setRequestMessages(this.getHttpServletRequest(), requestMessages);
	}
	
	protected String manageException(Exception exception) {
		logger.log(Level.SEVERE, exception.getMessage(), exception);
		
		if (exception instanceof AppException) {
			AppException appException = (AppException) exception;
			this.setRequestMessages(appException.getMessages());
		} else if (exception.getCause() != null) {
			this.setRequestMessage(new RequestMessage(exception.getCause().getMessage(), SeverityMessage.ERROR));
		} else {
			this.setRequestMessage(new RequestMessage(exception.getMessage(), SeverityMessage.ERROR));
		}
		return null;
	}
	
	protected void callJSFunction(JSFunctionBoxing timeRunning, String functionName, Object... params) {
		if (!Util.isStringOk(functionName)) throw new IllegalArgumentException();
		
		StringBuilder jSFunction = new StringBuilder(functionName + "(");
		for (Object param : params) {
			if (param instanceof String) {
				jSFunction.append("'").append(param.toString()).append("'");
			} else if (param instanceof Integer) {
				jSFunction.append(param.toString());
			} else throw new IllegalStateException("Parameter data type not supported yet.");
		}
		jSFunction.append(");");
		
		this.setRequestParameter(this.getHttpServletRequest(), JS_FUNCTION_CALLED, true);
		this.setRequestParameter(this.getHttpServletRequest(), NAME_JS_FUNCTION_CALLED, timeRunning.unBoxing(jSFunction.toString()));
	}
	
	protected UserAccountTO getUserAccountLogged() {
		return this.getManagedBean(AppSessionBean.class).getUserAccount();
	}
	
	protected AdministratorTO getAdministratorLogged() {
		return this.getManagedBean(AppSessionBean.class).getAdministrator();
	}
}
