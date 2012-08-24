package br.com.barganhas.web.beans;

import br.com.barganhas.business.services.ServiceBusinessFactory;

public class AppManagedBean {

	protected ServiceBusinessFactory getServiceBusinessFactory() {
		return ServiceBusinessFactory.getInstance();
	}
}
