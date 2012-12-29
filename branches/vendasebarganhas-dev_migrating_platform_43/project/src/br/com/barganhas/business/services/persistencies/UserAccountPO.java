package br.com.barganhas.business.services.persistencies;

import java.util.ArrayList;
import java.util.List;

import org.com.tatu.helper.GeneralsHelper;
import org.com.tatu.helper.querylanguage.QLWhereClause;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.barganhas.business.entities.FileTO;
import br.com.barganhas.business.entities.UserAccountTO;
import br.com.barganhas.business.exceptions.AppException;
import br.com.barganhas.business.services.persistencies.management.AppPersistencyManagement;
import br.com.barganhas.enums.UserAccountStatus;

@SuppressWarnings("serial")
@Repository
public class UserAccountPO extends AppPersistencyManagement {
	
	@SuppressWarnings("unchecked")
	public List<UserAccountTO> list() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select USER_ACCOUNT.id, USER_ACCOUNT.status, USER_ACCOUNT.sinceDate, USER_ACCOUNT.fullname, USER_ACCOUNT.nickname, USER_ACCOUNT.email from ");
		hql.append(UserAccountTO.class.getName()).append(" USER_ACCOUNT ");

		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UserAccountTO> filter(UserAccountTO userAccount) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select USER_ACCOUNT from ").append(UserAccountTO.class.getName()).append(" USER_ACCOUNT ");
		
		QLWhereClause where = new QLWhereClause();
		
		if (GeneralsHelper.isStringOk(userAccount.getFullname())) {
			where.and(" USER_ACCOUNT.fullname like '%" + userAccount.getFullname() + "%' ");
		}
		if (GeneralsHelper.isStringOk(userAccount.getNickname())) {
			where.and(" USER_ACCOUNT.nickname like '%" + userAccount.getNickname() + "%' ");
		}
		if (GeneralsHelper.isStringOk(userAccount.getEmail())) {
			where.and(" USER_ACCOUNT.email like '%" + userAccount.getEmail() + "%' ");
		}
		
		hql.append(where.toString());
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		return query.list();
	}
	
	public UserAccountTO insert(UserAccountTO userAccount) {
		this.getHibernateDao().insert(userAccount);
		return userAccount;
	}
	
	public UserAccountTO getSincronizedPassword(UserAccountTO userAccount) {
		String hql = " select UA.id, UA.password from " + UserAccountTO.class.getName() + " UA where UA = " + userAccount.getId();
		return (UserAccountTO) this.getHibernateDao().createQueryTransform(hql).uniqueResult();
	}
	
	public UserAccountTO save(UserAccountTO userAccount) {
		this.getHibernateDao().update(userAccount);
		return userAccount;
	}

	public UserAccountTO consult(UserAccountTO userAccount) {
		return this.getHibernateDao().consult(userAccount);
	}

	public void delete(UserAccountTO userAccount) {
		this.getHibernateDao().delete(userAccount);
	}
	
	public boolean nicknameAlreadyExist(UserAccountTO userAccount) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(USER_ACCOUNT.id) from ").append(UserAccountTO.class.getName()).append(" USER_ACCOUNT ");
		hql.append(" where USER_ACCOUNT.nickname = '" + userAccount.getNickname() + "' ");
		
		return this.getHibernateDao().queryCount(hql.toString()) > 0;
	}
	
	public boolean emailAlreadyExist(UserAccountTO userAccount) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(USER_ACCOUNT.id) from ").append(UserAccountTO.class.getName()).append(" USER_ACCOUNT ");
		hql.append(" where USER_ACCOUNT.email = '" + userAccount.getEmail() + "' ");
		
		return this.getHibernateDao().queryCount(hql.toString()) > 0;
	}

	public UserAccountTO validateLogin(UserAccountTO userAccount) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select USER_ACCOUNT.id, USER_ACCOUNT.status, USER_ACCOUNT.sinceDate, USER_ACCOUNT.fullname, USER_ACCOUNT.nickname, USER_ACCOUNT.email ");
		hql.append(" , USER_ACCOUNT.contactPhoneNumberOne, USER_ACCOUNT.contactPhoneNumberTwo, USER_ACCOUNT.contactEmail, PROFILE_IMAGE.id from ");
		hql.append(UserAccountTO.class.getName()).append(" USER_ACCOUNT ");
		hql.append(" left join USER_ACCOUNT.profileImage PROFILE_IMAGE ");
		hql.append(" where USER_ACCOUNT.nickname = :nickname ");
		hql.append(" and USER_ACCOUNT.password = :password ");
		
		Query query = this.getHibernateDao().createQueryTransform(hql.toString());
		query.setString("nickname", userAccount.getNickname());
		query.setString("password", userAccount.getPassword());
		
		userAccount = (UserAccountTO) query.uniqueResult();
		
		if (userAccount == null) {
			throw new AppException("loginErrorUserNotFound");
		}
		if (!userAccount.getStatus().equals(UserAccountStatus.ACTIVE)) {
			throw new AppException("userAccountUserNotActivatedYet");
		}
		
		return userAccount;
	}

	public List<UserAccountTO> listHighlightedUsers() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select USER_ACCOUNT.id_user, USER_ACCOUNT.id_profile_image, USER.nickname from USER_ACCOUNT ");
		sql.append(" inner join USER on USER_ACCOUNT.id_user = USER.id_user ");
		sql.append(" inner join ");
		sql.append(" ( ");
		sql.append(" select count(id_advertisement) as aux, id_user_account as id_user from ADVERTISEMENT group by id_user_account order by aux DESC limit 8 ");
		sql.append(" ) as highlighteds ");
		sql.append(" on USER_ACCOUNT.id_user = highlighteds.id_user ");
		
		List<UserAccountTO> userAccountList = new ArrayList<UserAccountTO>();
		for (Object results : this.getHibernateDao().createSQLQuery(sql.toString()).list()) {
			Object[] result = (Object[]) results;
			
			UserAccountTO userAccount = new UserAccountTO(((Number)result[0]).longValue());
			if (result[1] != null) {
				FileTO profileImage = new FileTO(((Number)result[1]).longValue());
				userAccount.setProfileImage(profileImage);
			}
			userAccount.setNickname((String)result[2]);
			
			userAccountList.add(userAccount);
		}
		return userAccountList;
	}
	
	public void lock(UserAccountTO userAccount) {
		userAccount = this.getHibernateDao().load(new UserAccountTO(userAccount.getId()));
		userAccount.setStatus(UserAccountStatus.LOCKED);
		
		this.getHibernateDao().update(userAccount);
	}
	
	public void activate(UserAccountTO userAccount) {
		userAccount = this.getHibernateDao().load(new UserAccountTO(userAccount.getId()));
		userAccount.setStatus(UserAccountStatus.ACTIVE);
		
		this.getHibernateDao().update(userAccount);
	}
}
