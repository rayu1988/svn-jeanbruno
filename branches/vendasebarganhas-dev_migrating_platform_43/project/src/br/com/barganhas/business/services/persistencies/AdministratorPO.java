package br.com.barganhas.business.services.persistencies;

import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.querylanguage.QLWhereClause;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.AdministratorTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;

@SuppressWarnings("serial")
@Repository
public class AdministratorPO extends AppPersistencyManagement {

	@SuppressWarnings("unchecked")
	public List<AdministratorTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADMINISTRATOR.id, ADMINISTRATOR.nickname, ADMINISTRATOR.fullname, ADMINISTRATOR.email from ");
		hql.append(AdministratorTO.class.getName()).append(" ADMINISTRATOR ");

		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AdministratorTO> filter(AdministratorTO administrator) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADMINISTRATOR.id, ADMINISTRATOR.nickname, ADMINISTRATOR.fullname, ADMINISTRATOR.email from ");
		hql.append(AdministratorTO.class.getName()).append(" ADMINISTRATOR ");
		
		QLWhereClause where = new QLWhereClause();
		
		if (GeneralsHelper.isStringOk(administrator.getFullname())) {
			where.and(" ADMINISTRATOR.fullname like '%" + administrator.getFullname() + "'% ");
		}
		if (GeneralsHelper.isStringOk(administrator.getNickname())) {
			where.and(" ADMINISTRATOR.nickname like '%" + administrator.getNickname() + "'% ");
		}
		if (GeneralsHelper.isStringOk(administrator.getEmail())) {
			where.and(" ADMINISTRATOR.email like '%" + administrator.getEmail() + "'% ");
		}
		
		hql.append(where.toString());
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	public AdministratorTO insert(AdministratorTO administrator) {
		String hql = " select count(AD.id) from " + AdministratorTO.class.getName() + " AD where AD.nickname = '" + administrator.getNickname() + "' ";
		if (this.getHibernateDao().queryCount(hql) > 0) {
			throw new AppException("administratorUserAccountAlreadyExists");
		}
		
		this.getHibernateDao().insert(administrator);
		return administrator;
	}
	
	public AdministratorTO save(AdministratorTO administrator) {
		if (!GeneralsHelper.isStringOk(administrator.getPassword())) {
			String hql = " select AD.id, AD.password from " + AdministratorTO.class.getName() + " AD where AD = " + administrator.getId();
			
			AdministratorTO syncronizedTO = (AdministratorTO) this.getHibernateDao().createQueryTransform(hql).uniqueResult();
			administrator.setPassword(syncronizedTO.getPassword());
		}
		
		this.getHibernateDao().update(administrator);
		
		return administrator;
	}

	public AdministratorTO consult(AdministratorTO administrator) {
		return this.getHibernateDao().consult(administrator);
	}

	public void delete(AdministratorTO administrator) {
		this.getHibernateDao().delete(administrator);
	}
	
	public AdministratorTO validateLogin(AdministratorTO administrator) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select ADMINISTRATOR.id, ADMINISTRATOR.nickname, ADMINISTRATOR.fullname, ADMINISTRATOR.email from ");
		hql.append(AdministratorTO.class.getName()).append(" ADMINISTRATOR ");
		hql.append(" where ADMINISTRATOR.nickname = :nickname ");
		hql.append(" and ADMINISTRATOR.password = :password ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		query.setString("nickname", administrator.getNickname());
		query.setString("password", administrator.getPassword());
		
		administrator = (AdministratorTO) query.uniqueResult();
		
		if (administrator == null) {
			throw new AppException("loginErrorUserNotFound");
		}

		return administrator;
	}
	
	public long countAdvertisements() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(ADVERTISEMENT.id) from ").append(AdministratorTO.class.getName()).append(" ADVERTISEMENT ");
		
		return this.getHibernateDao().queryCount(hql.toString());
	}
	
}
