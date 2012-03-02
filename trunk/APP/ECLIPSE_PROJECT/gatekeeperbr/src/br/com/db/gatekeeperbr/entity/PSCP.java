/**
 * 
 */
package br.com.db.gatekeeperbr.entity;

import br.com.db.gatekeeperbr.common.Util;
import br.com.db.gatekeeperbr.exception.ParameterRequiredException;

/**
 * @author JNVE
 *
 */
public class PSCP {
	
	private String				username;
	private String				server;
	private String				directory;
	
	public PSCP(String username, String server, String directory) {
		if (!Util.isStringOk(username) || !Util.isStringOk(server) || !Util.isStringOk(directory)) {
			throw new ParameterRequiredException("username", "server", "directory");
		}
		
		this.username = username.trim();
		this.server = server.trim();
		this.directory = directory.trim();
	}
	
	// getters and setters //
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
}
