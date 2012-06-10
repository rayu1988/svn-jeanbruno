import br.com.datawatcher.entity.CheckChange;
import br.com.datawatcher.entity.Column;
import br.com.datawatcher.entity.DataMapping;
import br.com.datawatcher.entity.DataWatcher;
import br.com.datawatcher.entity.DeclaredResult;
import br.com.datawatcher.entity.FolderMapping;
import br.com.datawatcher.entity.Id;
import br.com.datawatcher.entity.JdbcConnection;
import br.com.datawatcher.entity.Listener;
import br.com.datawatcher.entity.PasswordLogging;
import br.com.datawatcher.entity.Procedure;
import br.com.datawatcher.entity.Query;
import br.com.datawatcher.entity.TableMapping;
import br.com.datawatcher.entity.UserLogging;
import br.com.datawatcher.xstream.XStreamFactory;

/**
 * 
 */

/**
 * @author carrefour
 *
 */
public class MainToXML {
	
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataWatcher dataWatcher = new DataWatcher().addMapping(getTableSample()).addMapping(getFolderSample());
		System.out.println(XStreamFactory.getInstance().toXML(dataWatcher));
	}
	
	public static DataMapping getTableSample() {
		JdbcConnection jdbcConnection = new JdbcConnection();
		jdbcConnection.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		jdbcConnection.setUrl("jdbc:sqlserver://192.168.200.251:1433;databaseName=docflow4_sebrae_go");
		jdbcConnection.setUserLogging(new UserLogging("jean", "br.com.encrypt.DecryptUserName"));
		jdbcConnection.setPasswordLogging(new PasswordLogging("bruno", "br.com.encrypt.DecryptPassword"));

		DeclaredResult declaredResult = new DeclaredResult(new Query("select * from myTable"));
		declaredResult.addProcedure(new Procedure("exec procedure1"));
		declaredResult.addProcedure(new Procedure("exec procedure2"));
		declaredResult.addProcedure(new Procedure("exec procedure3"));
		
		TableMapping tableMapping = new TableMapping();
		tableMapping.setName("myTable");
		tableMapping.setId(new Id("java.lang.Integer", "idcolumn"));
		tableMapping.setJdbcConnection(jdbcConnection);
		tableMapping.setDeclaredResult(declaredResult);
		tableMapping.setCheckChange(new CheckChange("* 1-1 0 *"));
		tableMapping.addColumn(new Column("java.lang.String", "Column1")).
					addColumn(new Column("java.lang.Integer", "Column2")).
					addColumn(new Column("java.util.Date", "Column3"));
		tableMapping.addListeners(new Listener("br.com.database.MyClass1")).
					addListeners(new Listener("br.com.database.MyClass2")).
					addListeners(new Listener("br.com.database.MyClass3"));
		return tableMapping;
	}
	
	public static DataMapping getFolderSample() {
		FolderMapping folderMapping = new FolderMapping("C:\\myfolder\\mappedfolder", "*.pdf");
		folderMapping.setCheckChange(new CheckChange("* 1-1 0 *"));
		folderMapping.addListeners(new Listener("br.com.folder.MyClass1")).
					addListeners(new Listener("br.com.folder.MyClass2")).
					addListeners(new Listener("br.com.folder.MyClass3"));
		return folderMapping;
	}
}
