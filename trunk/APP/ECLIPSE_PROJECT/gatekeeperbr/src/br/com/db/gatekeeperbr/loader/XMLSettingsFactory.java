/**
 * 
 */
package br.com.db.gatekeeperbr.loader;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.db.gatekeeperbr.common.Constants;
import br.com.db.gatekeeperbr.common.Util;
import br.com.db.gatekeeperbr.connection.JDBCConnection;
import br.com.db.gatekeeperbr.entity.Application;
import br.com.db.gatekeeperbr.entity.Email;
import br.com.db.gatekeeperbr.entity.OutputDirectory;
import br.com.db.gatekeeperbr.entity.PSCP;
import br.com.db.gatekeeperbr.exception.GatekeeperBRException;
import br.com.db.gatekeeperbr.exception.GatekeeperBRLoaderException;
import br.com.db.gatekeeperbr.exception.ParameterRequiredException;

/**
 * Utility abstract class to makes parsing through XML File Settings.
 * This class keep up the retrieved informations as entity classes.
 * 
 * @author JNVE
 *
 */
public abstract class XMLSettingsFactory {
	
	private OutputDirectory			outputDirectory;
	private Email					email;
	private PSCP					pscp;
	private List<Application>		application = new ArrayList<Application>();

	protected File					xmlFile;
	
	public XMLSettingsFactory(File xmlFile) throws GatekeeperBRException {
		if (xmlFile == null || !xmlFile.exists()) {
			throw new ParameterRequiredException("xmlFile");
		}
		
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document xmlDoc = documentBuilder.parse(xmlFile);
			xmlDoc.getDocumentElement().normalize();
			
			this.loadOutputDirectory(xmlDoc);
			this.loadMail(xmlDoc);
			this.loadPSCP(xmlDoc);
			this.loadApplication(xmlDoc);
		} catch (ParserConfigurationException e) {
			throw new GatekeeperBRLoaderException(e, "Problems while trying loads XMLSettings.");
		} catch (SAXException e) {
			throw new GatekeeperBRLoaderException(e, "Problems while trying loads XMLSettings.");
		} catch (IOException e) {
			throw new GatekeeperBRLoaderException(e, "Problems while trying loads XMLSettings.");
		} catch (SQLException e) {
			throw new GatekeeperBRLoaderException(e, "Problems while trying loads XMLSettings.");
		}
	}

	private void loadOutputDirectory(Document xmlDoc) throws GatekeeperBRException {
		NodeList outputNodeList = xmlDoc.getElementsByTagName(Constants.TAG_OUTPUT_DIRECTORY);
		if (outputNodeList == null || outputNodeList.getLength() != 1) {
			throw new GatekeeperBRLoaderException("Must declare one and just one tag: " + Constants.TAG_OUTPUT_DIRECTORY);
		}
		Node outputNode = outputNodeList.item(0);
		NodeList text = outputNode.getChildNodes();
		String address = ((Node) text.item(0)).getNodeValue();
		this.outputDirectory = new OutputDirectory(address);
	}

	private void loadMail(Document xmlDoc) throws GatekeeperBRLoaderException {
		NodeList mailNodeList = xmlDoc.getElementsByTagName(Constants.TAG_MAIL);
		if (mailNodeList == null || mailNodeList.getLength() != 1) {
			throw new GatekeeperBRLoaderException("Must declare one and just one tag: " + Constants.TAG_MAIL);
		}
		Element mailElement = (Element) mailNodeList.item(0);
		
		String host = Util.getValueElementDOM(mailElement, Constants.TAG_HOST, true);
		String mailFrom = Util.getValueElementDOM(mailElement, Constants.TAG_MAIL_FROM, true);
		String emailAddress = Util.getValueElementDOM(mailElement, Constants.TAG_EMAIL_ADDRESS, true);
		
		this.email = new Email(host, mailFrom, emailAddress);
	}

	private void loadPSCP(Document xmlDoc) throws GatekeeperBRLoaderException {
		NodeList pscpNodeList = xmlDoc.getElementsByTagName(Constants.TAG_PSCP);
		if (pscpNodeList == null || pscpNodeList.getLength() != 1) {
			throw new GatekeeperBRLoaderException("Must declare one and just one tag: " + Constants.TAG_PSCP);
		}
		Element pscpElement = (Element) pscpNodeList.item(0);
		
		String username = Util.getValueElementDOM(pscpElement, Constants.TAG_USER_NAME, true);
		String server = Util.getValueElementDOM(pscpElement, Constants.TAG_SERVER, true);
		String directory = Util.getValueElementDOM(pscpElement, Constants.TAG_DIRECTORY, true);
		
		this.pscp = new PSCP(username, server, directory);
	}

	private void loadApplication(Document xmlDoc) throws GatekeeperBRLoaderException, SQLException {
		NodeList applicationNodeList = xmlDoc.getElementsByTagName(Constants.TAG_APPLICATION);
		for (int i = 0; i < applicationNodeList.getLength(); i++) {
			Element applicationElement = (Element) applicationNodeList.item(i);
			if (applicationElement.getNodeType() == Node.ELEMENT_NODE) {
				JDBCConnection jdbcConnection = this.loadConnection(applicationElement);
			}
		}
	}
	
	private JDBCConnection loadConnection(Element applicationElement) throws GatekeeperBRLoaderException {
		NodeList connectionNodeList = applicationElement.getElementsByTagName(Constants.TAG_CONNECTION);
		if (connectionNodeList == null || connectionNodeList.getLength() != 1) {
			throw new GatekeeperBRLoaderException("Must declare one and just one tag: " + Constants.TAG_CONNECTION);
		}
		Element connectionElement = (Element) connectionNodeList.item(0);
		
		String connDriverClass = Util.getValueElementDOM(connectionElement, Constants.TAG_DRIVER_CLASS, true);
		String connUrl = Util.getValueElementDOM(connectionElement, Constants.TAG_URL, true);
		String connUserName = Util.getValueElementDOM(connectionElement, Constants.TAG_USER_NAME, true);
		String connPassword = Util.getValueElementDOM(connectionElement, Constants.TAG_PASSWORD, true);
		return new JDBCConnection(connDriverClass, connUrl, connUserName, connPassword);
	}
	
	// getters and setters //
	public OutputDirectory getOutputDirectory() {
		return outputDirectory;
	}
	public void setOutputDirectory(OutputDirectory outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public PSCP getPscp() {
		return pscp;
	}
	public void setPscp(PSCP pscp) {
		this.pscp = pscp;
	}
}