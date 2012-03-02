/**
 * 
 */
package br.com.db.gatekeeperbr.common;

import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import au.com.bytecode.opencsv.CSVReader;
import br.com.db.gatekeeperbr.connection.JDBCConnection;
import br.com.db.gatekeeperbr.exception.GatekeeperBRException;

/**
 * @author JNVE
 *
 */
public class TempImportCSVSample {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			File inputCsv = new File("C:\\tests\\sample-gatekeeper.csv");
			CSVReader reader = new CSVReader(new FileReader(inputCsv));
			
			// starts reading with headers (scapes header)
			String[] line = reader.readNext();

			JDBCConnection conn = new JDBCConnection("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/gatekeeper_br", "root", "gftgft");
			// continue reading file contents (records)
			while ((line = reader.readNext()) != null) {
				saveRecord(line, conn);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveRecord(String[] line, JDBCConnection conn) throws GatekeeperBRException, SQLException, ParseException {
		PreparedStatement ps = null;
		
		System.out.println("Starting save " + line[8]);
		String sql = " insert into gatekeeper_br (environment, system, idapp, lastlogin, rights, disabled, firstname, lastname " +
				" , fullname, owner, ownertype, personal, priv, startdate, expiry, description, node, authcontact, approver, groupname) " +
				" values " +
				" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		ps = conn.getPreparedStatement(sql);
		
		//environment
		ps.setString(1, line[0]);
		
		//system
		ps.setString(2, line[1]);
		
		//id (idapp)
		ps.setString(3, line[2]);
		
		//last-login
		ps.setDate(4, (Util.isStringOk(line[3]) ? new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(line[3]).getTime()) : null) );
		
		//rights
		ps.setString(5, line[4]);
		
		//disabled
		ps.setString(6, line[5]);
		
		//first-name
		ps.setString(7, line[6]);
		
		//last-name
		ps.setString(8, line[7]);
		
		//full-name
		ps.setString(9, line[8]);
		
		//owner
		ps.setString(10, line[9]);
		
		//owner-type
		ps.setString(11, line[10]);
		
		//personal
		ps.setString(12, line[11]);
		
		//priv
		ps.setString(13, line[12]);
		
		//start-date
		ps.setDate(14, (Util.isStringOk(line[13]) ? new Date(new SimpleDateFormat("yyyyMMdd").parse(line[13]).getTime()) : null) );
		
		//expiry
		ps.setDate(15, (Util.isStringOk(line[14]) ? new Date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(line[14]).getTime()) : null) );
		
		//description
		ps.setString(16, line[15]);
		
		//node
		ps.setString(17, line[16]);
		
		//auth-contact
		ps.setString(18, line[17]);
		
		//approver
		ps.setString(19, line[18]);
		
		//group-name
		ps.setString(20, line[19]);
		
		ps.execute();
		
		ps.close();
		
		System.out.println("Ending save " + line[8]);
	}
}
