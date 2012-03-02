/**
 * 
 */
package br.com.db.gatekeeperbr.entity;

import java.io.File;

import br.com.db.gatekeeperbr.common.Util;
import br.com.db.gatekeeperbr.exception.GatekeeperBRException;
import br.com.db.gatekeeperbr.exception.ParameterRequiredException;

/**
 * @author JNVE
 *
 */
public class OutputDirectory {
	
	private File			address;

	public OutputDirectory(String address) throws GatekeeperBRException {
		if (!Util.isStringOk(address)) {
			throw new ParameterRequiredException("address");
		}
		this.address = new File(address.trim());
		if (!this.address.exists() || this.address.isFile()) {
			throw new GatekeeperBRException("The address must be a valid address and already exists. The value is invalid:" + address);
		}
	}
	
	// getters and setters //
	public File getAddress() {
		return address;
	}
	public void setAddress(File address) {
		this.address = address;
	}
}
