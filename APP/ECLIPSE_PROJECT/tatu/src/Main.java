import org.com.tatu.cypher.XORCryption;

public class Main {
	
	public static void main(String[] args) {
//		String root = "c:\\Documents and Settings\\villjea\\Desktop\\emAdapterWeb";
//		
//		FileHelper fileHelper = new FileHelper(root);
//		fileHelper.delete(new String[]{".svn"});
		
		String message = "25625f27d24d25125926d25b25525a26525f2541526125e26e26f27d25926e2601225826e26f27924e25925027227b27225d27125e25425e1225d255151724226e25a27124e26d1527124225627027124d27b25a27d162541525b24226d26f27924d27b25a26124225327025b1725626026124d27b25b26524e26e2601224d26d25a26124e2631527d24626d2602791727e25d27125e25425e1225d255151724226e25a27124e26d1527124225627027124d27b25a27d162541525b24226d26f27924d27b25a26124225327025b1725626026124d27b25b26524e26e2601224d26d25a26124e2631527d24626d2602791727327624426427a27027d24e27b25a2792412552641225e26e26f27d25926e2601225826e26f27a24226e25926124d2562601225b2541427a24226d260121725626f26524726d26f27a24226126f27925b25425c27125d26e151e1625525926526226324f250271273276";
		String key = "7";
		
//		XORCryption encoder = new XORCryption(key);
//		String encodedMessage = encoder.encodeToHexString(message);
//		System.out.println("raw encoded message: " + encodedMessage);
		
		XORCryption decoder = new XORCryption(key);
		String decodedMessage = decoder.decodeHexString(message);
		System.out.println("decoded message: " + decodedMessage);
	}
}
