/**
 * 
 */
package br.com.db.gatekeeperbr.loader;

import java.io.File;

import br.com.db.gatekeeperbr.exception.GatekeeperBRException;

/**
 * @author JNVE
 *
 */
public class XMLSettingsFile extends XMLSettingsFactory {

	public XMLSettingsFile(File xmlFile) throws GatekeeperBRException {
		super(xmlFile);
	}
}
