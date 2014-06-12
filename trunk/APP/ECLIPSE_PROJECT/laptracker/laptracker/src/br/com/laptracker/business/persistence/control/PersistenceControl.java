package br.com.laptracker.business.persistence.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.laptracker.business.exceptions.AppException;

public class PersistenceControl {

	private Connection						connection = null;
	private List<PreparedStatement>			preparedStatement = null;
	
	protected PersistenceControl() {
		try {
			DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
			Class.forName(dataSourceFactory.getDriverClassName());
			this.connection = DriverManager.getConnection(dataSourceFactory.getUrl(), dataSourceFactory.getUser(), dataSourceFactory.getPassword());
			this.connection.setAutoCommit(false);
			this.preparedStatement = new ArrayList<PreparedStatement>();
		} catch (Exception e) {
			throw new AppException(e);
		}
	}
	
	protected PreparedStatement prepareStatement(String sql) {
		return this.prepareStatement(sql, null);
	}
	
	protected PreparedStatement prepareStatement(String sql, Integer autoGenerateKeys) {
		try {
			PreparedStatement ps = autoGenerateKeys != null ? this.connection.prepareStatement(sql, autoGenerateKeys) : this.connection.prepareStatement(sql);
			this.preparedStatement.add(ps); 
			return ps;
		} catch (SQLException e) {
			throw new AppException(e);
		}
	}
	
	protected void close() {
		try {
			for (PreparedStatement ps : this.preparedStatement) {
				ps.close();
			}
			this.connection.close();
		} catch (SQLException e) {
			throw new AppException(e);
		}
	}
	
	protected void commit() {
		try {
			this.connection.commit();
		} catch (SQLException e) {
			throw new AppException(e);
		}
	}
	
	protected void rollback() {
		try {
			this.connection.rollback();
		} catch (SQLException e) {
			throw new AppException(e);
		}
	}
	
	protected Long getIdInsert(PreparedStatement preparedStatement) {
		try {
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs != null && rs.next()) {
			    return rs.getLong(1);
			} else throw new IllegalStateException("No result was found");
		} catch (SQLException e) {
			throw new AppException(e);
		}
	}
}
