package org.com.tatu.helper.checking;

import java.util.Collection;

/**
 * 
 * @author villjea
 *
 */
public class Parameter {

	private Object parameter;
	
	private Parameter(Object parameter) {
		this.parameter = parameter;
	}
	
	public static Parameter check(Object parameter) {
		return new Parameter(parameter);
	}
	
	public Parameter notNull() {
		if (this.parameter == null) {
			throw new IllegalArgumentException("The parameter can't be null.");
		}
		return this;
	}
	
	public Parameter notEmpty() {
		this.notNull();
		if (this.parameter instanceof String) {
			if (((String)this.parameter).isEmpty()) {
				throw new IllegalStateException("The String can't be empty.");
			}
		} else if (this.parameter instanceof Collection<?>) {
			if (((Collection<?>) this.parameter).isEmpty()) {
				throw new IllegalStateException("The Collection can't be empty.");
			}
		} else {
			throw new IllegalStateException("Unknow type to check.");
		}
		return this;
	}
}
