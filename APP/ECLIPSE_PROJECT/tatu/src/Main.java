import org.com.tatu.cypher.XORCryption;

public class Main {
	
	public static void main(String[] args) {
//		String root = "c:\\Documents and Settings\\villjea\\Desktop\\emAdapterWeb";
//		
//		FileHelper fileHelper = new FileHelper(root);
//		fileHelper.delete(new String[]{".svn"});
		
		String message = "ahJzfnZlbmRhc2ViYXJnYW5oYXNyngELEjFici5jb20uYmFyZ2FuaGFzLmJ1c2luZXNzLmVudGl0aWVzLlRyYW5zZmVyT2JqZWN0IjFici5jb20uYmFyZ2FuaGFzLmJ1c2luZXNzLmVudGl0aWVzLlRyYW5zZmVyT2JqZWN0DAsSMGJyLmNvbS5iYXJnYW5oYXMuYnVzaW5lc3MuZW50aXRpZXMuVXNlckFjY291bnRUTxgBDA";
		String key = "7";
		
		XORCryption encoder = new XORCryption(key);
		String encodedMessage = encoder.encodeToBase64(message);
//		String encodedMessage = encoder.encodeToHexString(message);
		System.out.println("raw encoded message: " + encodedMessage);
		
		XORCryption decoder = new XORCryption(key);
		String decodedMessage = decoder.decodeFromBase64(encodedMessage);
		System.out.println("decoded message: " + decodedMessage);
	}
}
