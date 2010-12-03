package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * <b>Title:</b> ExemploCriptografia.java <br>
 * <b>Description:</b>     <br>
 * <b>Package:</b> demo <br>
 * <b>Project:</b> DocumentSigningDemoWebApp <br>
 * 
 * 
 * @author jean
 * @version Revision: $ Date: 17/11/2010
 */
class Cifrador {
	
	/**
	 * Este método tem por tarefa a geração randômica de uma chave simétrica definida no modelo AES (Advanced Encryption Standard) de 128 bits, com esta 
	 * chave é criptografado o texto claro que é passado como parâmetro e esta chave simétrica é então criptografada com a chave pública do RSA que fora
	 * passada como parâmetro.
	 * Como retorno espera-se uma matriz de dois vetores de bytes, sendo todos os dois vetores de apenas um elemento na posição 0 (zero) e no primeiro
	 * vetor deverá conter o texto que fora cifrado, ou resultado da criptografia simétrica AES aplicada sobre o texto e no segundo vetor o a chave AES
	 * criptografada com a chave pública que fora passada como parâmetro.
	 * 
	 * @param pub
	 * @param textoClaro
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @return byte[][]
	 */
    public byte[][] cifra(PublicKey pub, byte[] textoClaro)
    throws 	NoSuchAlgorithmException,   
    		NoSuchPaddingException, 
    		InvalidKeyException, 
    		IllegalBlockSizeException,  
    		BadPaddingException, 
    		InvalidAlgorithmParameterException {
    	
        byte[] textoCifrado = null;  
        byte[] chaveCifrada = null;  
          
        //-- A) Gerando uma chave simétrica de 128 bits  
        KeyGenerator kg = KeyGenerator.getInstance("AES");  
        kg.init(128);
        SecretKey sk = kg.generateKey();
        byte[] chave = sk.getEncoded();
        //-- B) Cifrando o texto com a chave simétrica gerada
        Cipher aescf = Cipher.getInstance ("AES/CBC/PKCS5Padding");
        IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);  
        aescf.init(Cipher.ENCRYPT_MODE, new SecretKeySpec (chave, "AES"), ivspec);  
        textoCifrado = aescf.doFinal(textoClaro);
        //-- C) Cifrando a chave com a chave pública
        Cipher rsacf = Cipher.getInstance("RSA");
        rsacf.init(Cipher.ENCRYPT_MODE, pub);
        chaveCifrada = rsacf.doFinal(chave);
          
        return new byte[][] { textoCifrado, chaveCifrada };  
    }  
}

/**
 * 
 * <b>Title:</b> ExemploCriptografia.java <br>
 * <b>Description:</b>     <br>
 * <b>Package:</b> demo <br>
 * <b>Project:</b> DocumentSigningDemoWebApp <br>
 * 
 * 
 * @author jean
 * @version Revision: $ Date: 17/11/2010
 */
class Decifrador {
	
	/**
	 * Este método tem por tarefa receber uma chave privada do sistema RSA, um vetor que é o textoCifrado e outro vetor que é a chave AES de 128 bits que
	 * fora anteriormente cifrada com chave privada recebida aqui como parâmetro.
	 * A chave AES deverá ser decifrada com a chave privada e então tendo a chave AES, conseguimos obter o texto claro do texto cifrado passado como
	 * parâmetro, sendo este texto claro obtido o retorno deste método.
	 * @param pvk
	 * @param textoCifrado
	 * @param chaveCifrada
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @return byte[]
	 */
    public byte[] decifra (PrivateKey pvk, byte[] textoCifrado, byte[] chaveCifrada)
    throws 	NoSuchAlgorithmException,
			NoSuchPaddingException, 
			InvalidKeyException, 
			IllegalBlockSizeException, 
			BadPaddingException, 
			InvalidAlgorithmParameterException  {
    	
        byte[] textoDecifrado = null;  

        //-- A) Decifrando a chave simétrica com a chave privada  
        Cipher rsacf = Cipher.getInstance ("RSA");  
        rsacf.init(Cipher.DECRYPT_MODE, pvk);  
        byte[] chaveDecifrada = rsacf.doFinal (chaveCifrada);  
        //-- B) Decifrando o texto com a chave simétrica decifrada  
        Cipher aescf = Cipher.getInstance("AES/CBC/PKCS5Padding");  
        IvParameterSpec ivspec = new IvParameterSpec (new byte[16]);  
        aescf.init (Cipher.DECRYPT_MODE, new SecretKeySpec (chaveDecifrada, "AES"), ivspec);  
        textoDecifrado = aescf.doFinal (textoCifrado);  
          
        return textoDecifrado;  
    }  
}  

/**
 * 
 * <b>Title:</b> ExemploCriptografia.java <br>
 * <b>Description:</b> <br>
 * <b>Package:</b> demo <br>
 * <b>Project:</b> DocumentSigningDemoWebApp <br>
 * 
 * 
 * @author jean
 * @version Revision: $ Date: 17/11/2010
 */
class CarregadorChavePublica {
	
	/**
	 * Este método tem a única tarefa de receber o File que é o arquivo que mantém a chave pública serializada e então lê-se este arquivo setando-o como
	 * um objeto PublicKey e retornando o mesmo.
	 * 
	 * @param fPub - O File que mantém o ponteiro para o arquivo que mantém a chave pública serializada.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * 
	 * @return PublicKey - O objeto PublicKey que se encontrava serializado, arquivado.
	 */
    public PublicKey carregaChavePublica (File fPub) throws IOException, ClassNotFoundException {  
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPub));  
        PublicKey ret = (PublicKey) ois.readObject();  
        ois.close();  
        return ret;  
    }  
}  

/**
 * 
 * <b>Title:</b> ExemploCriptografia.java <br>
 * <b>Description:</b>     <br>
 * <b>Package:</b> demo <br>
 * <b>Project:</b> DocumentSigningDemoWebApp <br>
 * 
 * 
 * @author jean
 * @version Revision: $ Date: 17/11/2010
 */
class CarregadorChavePrivada {
	
	/**
	 * Este método tem a única tarefa de receber o File que é o arquivo que mantém a chave privada serializada e então lê-se este arquivo setando-o como
	 * um objeto PrivateKey e retornando o mesmo.
	 * 
	 * @param fPvk - O File que mantém o ponteiro para o arquivo que mantém a chave privada serializada.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * 
	 * @return PrivateKey - O objeto PrivateKey que se encontrava serializado, arquivado.
	 */
    public PrivateKey carregaChavePrivada (File fPvk) throws IOException, ClassNotFoundException {  
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPvk));  
        PrivateKey ret = (PrivateKey) ois.readObject();  
        ois.close();
        return ret;
    }  
}

/**
 * 
 * <b>Title:</b> ExemploCriptografia.java <br>
 * <b>Description:</b>
 * Na criptografia assimétrica utiliza-se um par de chaves, denominado chave pública e chave privada. O objetivo deste par de chaves é que a mensagem
 * que é criptografada com a uma das chaves só deve ser descriptografado com a outra chave, ou seja, o que for criptografado com a chave secreta só
 * deverá ser descriptogrado com a chave pública correspondente e vice-versa.
 * 
 * <br>
 * <b>Package:</b> demo <br>
 * <b>Project:</b> DocumentSigningDemoWebApp <br>
 * 
 * 
 * @author jean
 * @version Revision: $ Date: 17/11/2010
 */
class GeradorParChaves {
	/*
	 * As chaves geradas terão um temanho de 1024 bits
	 */
    private static final int RSA_KEY_SIZE = 1024;  
    
    /**
     * Este método é responsável por utilizar as ferramentas da plataforma Java para gerar uma chave pública e uma chave privada e salvar estas chaves
     * em um arquivo denominado "chave.publica" e "chave.privada" respectivamente e ainda salvar tais chaves nos Files que são passados como parâmetro.
     * 
     * @param fPub - File da chave pública
     * @param fPvk - File da chave privada
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws CertificateException
     * @throws KeyStoreException
     * @return void
     */
    public void geraParChaves(File fPub, File fPvk)
    throws 	IOException,
        	NoSuchAlgorithmException,
        	InvalidAlgorithmParameterException,   
        	CertificateException,
        	KeyStoreException {
              
        KeyPairGenerator kpg = KeyPairGenerator.getInstance ("RSA");  
        kpg.initialize(new RSAKeyGenParameterSpec(RSA_KEY_SIZE, RSAKeyGenParameterSpec.F4));  
        KeyPair kpr = kpg.generateKeyPair();  
        PrivateKey priv = kpr.getPrivate();          
        PublicKey pub = kpr.getPublic();  
          
        //-- Gravando a chave pública em formato serializado  
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fPub));
        oos.writeObject(pub);  
        oos.close();  
          
        //-- Gravando a chave privada em formato serializado
        //-- Não é a melhor forma (deveria ser guardada em um keystore, e protegida por senha),   
        //-- mas isto é só um exemplo  
        oos = new ObjectOutputStream (new FileOutputStream (fPvk));  
        oos.writeObject(priv);  
        oos.close();
    }
}
  
class ExemploCriptografia {  
    static void printHex(byte[] b) {  
        if (b == null) {  
            System.out.println ("(null)");  
        } else {  
	        for (int i = 0; i < b.length; i++) {
	            if (i % 16 == 0) {  
	                System.out.print(Integer.toHexString ((i & 0xFFFF) | 0x10000).substring(1,5) + " - ");  
	            }  
	            System.out.print (Integer.toHexString((b[i]&0xFF) | 0x100).substring(1,3) + " ");  
	            if (i % 16 == 15 || i == b.length - 1) {  
	                int j;  
	                for (j = 16 - i % 16; j > 1; --j)  
	                    System.out.print ("   ");  
	                System.out.print (" - ");  
	                int start = (i / 16) * 16;  
	                int end = (b.length < i + 1) ? b.length : (i + 1);  
	                for (j = start; j < end; ++j)  
	                    if (b[j] >= 32 && b[j] <= 126)  
	                        System.out.print ((char)b[j]);  
	                    else  
	                        System.out.print (".");  
	                System.out.println ();  
	            }  
	        }  
        System.out.println();  
        }  
    }      
      
    public static void main(String[] args) throws Exception {
    	File filePublicKey = new File("chave.publica");
    	File filePrivateKey = new File("chave.privada");
    	
        GeradorParChaves gpc = new GeradorParChaves();  
        gpc.geraParChaves(filePublicKey, filePrivateKey);
  
        //-- Cifrando a mensagem "Hello, world!"  
        byte[] textoClaro = "Hello, world!".getBytes("ISO-8859-1");
        
        CarregadorChavePublica ccp = new CarregadorChavePublica();
        PublicKey pub = ccp.carregaChavePublica (new File ("chave.publica"));
        
        Cifrador cf = new Cifrador();  
        byte[][] cifrado = cf.cifra (pub, textoClaro);
        printHex (cifrado[0]);//print textoCifrado 
        printHex (cifrado[1]);//print chaveCifrada
          
        //-- Decifrando a mensagem
        CarregadorChavePrivada ccpv = new CarregadorChavePrivada();  
        PrivateKey pvk = ccpv.carregaChavePrivada(new File ("chave.privada"));
        Decifrador dcf = new Decifrador();
		byte[] textoDecifrado = dcf.decifra(pvk, cifrado[0], cifrado[1]);
        
        System.out.println (new String (textoDecifrado, "ISO-8859-1"));
    }  
} 