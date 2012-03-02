import java.io.File;

import br.com.db.gatekeeperbr.exception.GatekeeperBRException;
import br.com.db.gatekeeperbr.loader.XMLSettingsFactory;
import br.com.db.gatekeeperbr.loader.XMLSettingsFile;

/**
 * @author JNVE
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File xmlFile = new File("C:\\workspaces\\eclipse\\indigo\\gatekeeper-br\\gatekeeperbrcore\\temp\\settings.xml");
			XMLSettingsFactory load = new XMLSettingsFile(xmlFile);
			
			System.out.println(load.getEmail().getMailFrom());
		} catch (GatekeeperBRException e) {
			e.printStackTrace();
		}
	}
}