/**
 * 
 */
package br.com.db.gatekeeperbr.exception;

/**
 * @author JNVE
 *
 */
public class GatekeeperBRLoaderException extends GatekeeperBRException {

	private static final long serialVersionUID = -5570273623141791316L;

	public GatekeeperBRLoaderException() {
		super();
	}
	
	public GatekeeperBRLoaderException(String strPlus) {
		super(strPlus);
	}
	
	public GatekeeperBRLoaderException(Exception e, String strPlus) {
		super(e);
		this.appendMessage(strPlus);
	}
}
