package org.com.tatu.helper.parameter;

import java.util.Collection;

/**
 * 
 * @author villjea
 *
 */
public class Parameter {

	private Object[] parameters;
	
	private Parameter(Object... parameters) {
		this.parameters = parameters;
	}
	
	public static Parameter check(Object... parameters) {
		return new Parameter(parameters);
	}
	
	public Parameter notNull() {
		for (Object parameter : this.parameters) {
			if (parameter == null) {
				throw new IllegalArgumentException("The parameter can't be null.");
			}
		}
		return this;
	}
	
	public Parameter notEmpty() {
		this.notNull();
		for (Object parameter : this.parameters) {
			if (parameter instanceof String) {
				if (((String)parameter).isEmpty()) {
					throw new IllegalStateException("The String can't be empty.");
				}
			} else if (parameter instanceof Collection<?>) {
				if (((Collection<?>) parameter).isEmpty()) {
					throw new IllegalStateException("The Collection can't be empty.");
				}
			} else {
				throw new IllegalStateException("Unknow type to check.");
			}
		}
		return this;
	}
}
