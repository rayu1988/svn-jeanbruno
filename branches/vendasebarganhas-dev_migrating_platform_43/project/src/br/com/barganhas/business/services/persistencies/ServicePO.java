package br.com.barganhas.business.services.persistencies;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.management.TransferObject;
import br.com.barganhas.business.services.Service;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class ServicePO extends AppPersistencyManagement implements Service {

	public static final String BEAN_ALIAS = "servicePO";
	
	@Override
	public void evict(TransferObject to) {
		this.getHibernateDao().evict(to);
	}

	@Override
	@Transactional(readOnly=true)
	public void initialize(TransferObject to) {
		this.getHibernateDao().initialize(to);
	}

	@Override
	public void refresh(TransferObject to) {
		this.getHibernateDao().refresh(to);
	}

	@Override
	public void clear() {
		this.getHibernateDao().clear();
	}

	@Override
	public void flush() {
		this.getHibernateDao().flush();
	}

	@Override
	@Transactional(readOnly=true)
	public <T extends TransferObject> T load(T to) {
		return this.getHibernateDao().load(to);
	}

	@Override
	@Transactional(readOnly=true)
	public <T extends TransferObject> T get(T to) {
		return this.getHibernateDao().get(to);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void delete(TransferObject to) {
		this.getHibernateDao().delete(to);
	}

}
