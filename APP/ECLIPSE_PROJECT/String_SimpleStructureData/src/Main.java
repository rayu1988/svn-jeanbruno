import org.simplestructruedata.data.SSDContextManager;
import org.simplestructruedata.entities.SSDObjectLeaf;
import org.simplestructruedata.entities.SSDObjectNode;

public class Main {

	public static void main(String[] args) {
		
		String dataBase = " { " +
				" \n	result1 = { code = \"1\" , message = \"my \\\\ \\\"sample message 1\" } , " +
				" 	result2 = { code = \"2\" , \n \t message = \"my sample message 2\" } " +
				" } ";
		
		System.out.println(dataBase);
		
		SSDContextManager ctx = SSDContextManager.build(dataBase);
		SSDObjectNode result1 = (SSDObjectNode) ctx.getRootObject().get("result2");
		SSDObjectLeaf message1 = (SSDObjectLeaf) result1.get("message");
		
		System.out.println(message1.getValue());
	}
}
