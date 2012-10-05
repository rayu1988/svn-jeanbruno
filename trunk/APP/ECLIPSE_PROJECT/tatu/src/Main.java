import org.com.tatu.helper.FileHelper;

public class Main {
	
	public static void main(String[] args) {
		String root = "c:\\Documents and Settings\\villjea\\Desktop\\emAdapterWeb";
		
		FileHelper fileHelper = new FileHelper(root);
		fileHelper.delete(new String[]{".svn"});
	}
}
