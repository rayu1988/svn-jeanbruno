package br.com.barganhas.business.services.persistencies.hbn;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class AppHbnBinayType implements UserType {

	@Override
	public Object assemble(Serializable cached, Object object) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) return null; 
		byte[] bytes = (byte[]) value; 
		byte[] result = new byte[bytes.length]; 
		System.arraycopy(bytes, 0, result, 0, bytes.length); 
		
		return result;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value; 
	}

	@Override
	public boolean equals(Object x, Object y){ 
		return (x == y) || (x != null && y != null && java.util.Arrays.equals((byte[]) x, (byte[]) y)); 
	}

	@Override
	public int hashCode(Object object) throws HibernateException {
		return 0;
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		byte[] bytes = rs.getBytes(names[0]);
		return bytes;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		st.setBytes(index, (byte[])value);
	}

	@Override
	public Object replace(Object original, Object arg1, Object arg2) throws HibernateException {
		return original;
	}

	@Override
	public Class returnedClass() {
		return byte[].class; 
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.BLOB };
	}

}
