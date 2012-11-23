package br.com.barganhas.business.services.impl;

import java.util.List;

import org.com.tatu.helper.parameter.ParameterWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.barganhas.business.entities.UseTermTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.UseTerm;
import br.com.barganhas.business.services.persistencies.UseTermPO;

@Service("useTermBO")
public class UseTermBO implements UseTerm {

	public static final String						BEAN_ALIAS = "useTermBO";

	@Autowired
	private UseTermPO								persistencyLayer;
	
	@Override
	@Transactional(readOnly = true)
	public List<UseTermTO> list() {
		return this.persistencyLayer.list();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UseTermTO insert(UseTermTO useTerm) {
		return this.persistencyLayer.insert(useTerm);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UseTermTO consult(UseTermTO useTerm) {
		return this.persistencyLayer.consult(useTerm);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UseTermTO getDefaultUseTerm() {
		return this.persistencyLayer.getDefaultUseTerm();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UseTermTO turnUseTermDefault(UseTermTO useTerm) {
		UseTermTO currentDefaultUseTerm = this.persistencyLayer.getDefaultUseTerm();
		this.persistencyLayer.getHibernateDao().update(currentDefaultUseTerm, ParameterWrapper.instance("isDefault", Boolean.FALSE));
		
		this.persistencyLayer.getHibernateDao().update(useTerm, ParameterWrapper.instance("isDefault", Boolean.TRUE));

		return useTerm;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UseTermTO save(UseTermTO useTerm) {
		return this.persistencyLayer.save(useTerm);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(UseTermTO useTerm) {
		useTerm = this.consult(useTerm);
		
		if (useTerm.getIsDefault()) {
			throw new AppException("useTermDefaultCantBeDeleted");
		}
		
		this.persistencyLayer.delete(useTerm);
	}
}
