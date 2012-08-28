package br.com.barganhas.web.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.barganhas.business.services.ServiceBusinessFactory;
import br.com.barganhas.commons.Util;

public class AppManagedBean {
	
	public String goToIndex() {
		return this.getManagedBean(AdministratorBean.class).list();
	}

	protected ServiceBusinessFactory getServiceBusinessFactory() {
		return ServiceBusinessFactory.getInstance();
	}
	
	protected FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getManagedBean(Class<T> classe) {
		return (T) this.getContext().getELContext().getELResolver().getValue(this.getContext().getELContext(), null, Util.getNameAsJavaBean(classe));
	}
	
	protected void destroyCurrentSession() {
		((HttpSession) this.getContext().getExternalContext().getSession(true)).invalidate();
	}
	
	protected HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) this.getContext().getExternalContext().getResponse();
	}
}
