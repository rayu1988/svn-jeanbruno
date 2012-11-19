package br.com.barganhas.business.entities.management;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class TransferObject implements Cloneable, Serializable {
	
	/**
	 * Method must be implemented by the class tha implements TransferObject.
	 * @param id
	 */
	public abstract void setKey(Serializable id);
	
	/**
	 * Method must be implemented by the class tha implements TransferObject.
	 * @return
	 */
	public abstract Serializable getKey();
	
	public final boolean equals(Object another) {
		if (another == null || !this.getClass().isInstance(another) && !another.getClass().isInstance(this)) {
		   return false;
		} else {
           TransferObject castAnother = (TransferObject) another;
           if (this.getKey() != null && castAnother.getKey() != null) {
			  return this.getKey().equals(castAnother.getKey());
	       } else {
			  return super.equals(another);
	       }
		}
	}
	
	public final int hashCode() {
		if (this.getKey() != null) {
		   if (this.getKey() instanceof Long) {
			  return ((Long) getKey()).intValue(); 
		   } else {
			  return (Integer) getKey();
		   }
		} else {
			return super.hashCode();
		}
	}
	
}