package br.com.digitalsignatureapp.control;

import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

public class Controller extends DigitalSignature {
	
	private Application application;
	
	protected FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}
	
	protected Application getApplication() {
		if (this.application == null) this.application = this.getContext().getApplication();
		return this.application;
	}
	
	/**
	 * 
	 * @return
	 */
	public Locale getLocale() {
		return new Locale("pt", "BR");
	}
}