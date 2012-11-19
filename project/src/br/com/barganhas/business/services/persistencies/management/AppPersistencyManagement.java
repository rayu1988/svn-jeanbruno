package br.com.barganhas.business.services.persistencies.management;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AppPersistencyManagement implements Serializable {

	private static final long 			serialVersionUID = 6937792027035852280L;
	
	@Autowired
	private AppHibernateDAO				hibernateDao;

	@Resource(name="appHibernateDAO")
	public void setHibernateDao(AppHibernateDAO hibernateDao) {
		this.hibernateDao = hibernateDao;
	}
	
	public AppHibernateDAO getHibernateDao() {
		return hibernateDao;
	}
	
}
