package br.com.laptracker.business.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.laptracker.business.entities.UserTO;
import br.com.laptracker.business.exceptions.AppException;
import br.com.laptracker.business.persistence.control.PersistenceControl;

public class UserPO extends PersistenceControl {

	public UserTO validateLogin(UserTO user) {
		try {
			UserTO returning = null;
			
			StringBuffer sql = new StringBuffer();
			sql.append(" select id_user, nickname from USER ");
			sql.append(" where nickname = ? ");
			sql.append(" and password = ? ");
			
			PreparedStatement ps = this.prepareStatement(sql.toString());
			ps.setString(1, user.getNickname());
			ps.setString(2, user.getPassword());
			
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				returning = new UserTO();
				returning.setId(result.getLong("id_user"));
				returning.setNickname(result.getString("nickname"));
			}
			
			this.commit();
			
			return returning;
		} catch (Exception e) {
			this.rollback();
			throw new AppException(e);
		} finally {
			this.close();
		}
	}
	
}
