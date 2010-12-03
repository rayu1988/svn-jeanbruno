/*
 * EncryptDecrypt to encrypt and decrypt messages using a certificate file
 * cacert.pem (contains the public key) and a private key file (cakey.p8c).
 * 
 * Created by Mobilefish.com (http://www.mobilefish.com)
 * 
 * File: EncryptDecrypt.java 
 * Needs: Certificate file (cacert.pem). See:
 *        http://www.mobilefish.com/developer/openssl/openssl_quickguide_create_ca.html
 * 
 *        To create private key file (cakey.p8c):
 *        - First create file (cakey.pem):
 *          http://www.mobilefish.com/developer/openssl/openssl_quickguide_create_ca.html
 *        - Convert PEM to PKCS#8 (cakey.pem into cakey.p8c):
 *          http://www.mobilefish.com/developer/openssl/openssl_quickguide_command_examples.html#step7a
 * 
 * Resources: -
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * If you have any questions, please contact contact@mobilefish.com
 */

package demo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateFactory;
import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class EncryptDecrypt {
    
    /* 
     * Convert into hex values
     */
    private static String hex(String  binStr) {

        String newStr = new String();
 
        try {
            String hexStr = "0123456789ABCDEF";
            byte [] p = binStr.getBytes();
            for(int k=0; k < p.length; k++ ){
                int j = ( p[k] >> 4 )&0xF;
                newStr = newStr + hexStr.charAt( j );
                j = p[k]&0xF;
                newStr = newStr + hexStr.charAt( j ) + " ";
            }   
        } catch (Exception e) {
            System.out.println("Failed to convert into hex values: " + e);
        } 
        return newStr;
    }

    /* 
     * Encrypt a message using a certificate file cacert.pem (contains public key).
     * Decrypt the encrypted message using a private key file (cakey.p8c).
     */
    public static void main(String [] args){
    
        String message = "";
        byte[] messageBytes;    
        byte [] tempPub = null;
        String sPub = null;
        byte[] ciphertextBytes = null;
        byte[] textBytes = null;
   
        try {   
                        
            // The source of randomness
            SecureRandom secureRandom = new SecureRandom();
            
            // Obtain a RSA Cipher Object
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding","BC");    
                            
            // Loading certificate file  
            String certFile = "cacert.pem";
            InputStream inStream = new FileInputStream(certFile);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert =(X509Certificate)cf.generateCertificate(inStream);
            inStream.close();
        
            // Read the public key from certificate file
            RSAPublicKey pubkey = (RSAPublicKey) cert.getPublicKey();
            tempPub = pubkey.getEncoded();
            sPub = new String( tempPub );
            System.out.println("Public key from certificate file:\n" + hex(sPub) + "\n");
            System.out.println("Public Key Algorithm = " + cert.getPublicKey().getAlgorithm() + "\n" );
              
            // Loading private key file  
            String keyFile = "cakey.p8c";
            inStream=new FileInputStream(keyFile);
            byte[] encKey=new byte[inStream.available()];
            inStream.read(encKey);
            inStream.close();
            
            // Read the private key from file
            System.out.println("RSA PrivateKeyInfo: " + encKey.length + " bytes\n") ;
            PKCS8EncodedKeySpec privKeySpec=new PKCS8EncodedKeySpec(encKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            System.out.println("KeyFactory Object Info:");
            System.out.println("Algorithm = "+keyFactory.getAlgorithm());
            System.out.println("Provider = "+keyFactory.getProvider());     
            PrivateKey priv= (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
            System.out.println("Loaded " + priv.getAlgorithm() + " " + priv.getFormat() + " private key.");

            // Set plain message
            message = "This is my secret message.";
            messageBytes = message.getBytes();
            System.out.println("Plain message:\n" + message + "\n" );
            
            // Initialize the cipher for encryption
            cipher.init(Cipher.ENCRYPT_MODE, pubkey, secureRandom); 
                                
            // Encrypt the message
            ciphertextBytes = cipher.doFinal(messageBytes);      
            System.out.println("Message encrypted with certificate file public key:\n" + new String(ciphertextBytes) + "\n");  
    
            // Initialize the cipher for decryption
            cipher.init(Cipher.DECRYPT_MODE, priv, secureRandom); 

            // Decrypt the message
            textBytes = cipher.doFinal(ciphertextBytes);     
            System.out.println("Message decrypted with file private key:\n" + new String(textBytes) + "\n");                    
             
        }catch( IOException e ){
            System.out.println( "IOException:" + e );
        }catch( CertificateException e ){
            System.out.println( "CertificateException:" + e );
        }catch( NoSuchAlgorithmException e ){
            System.out.println( "NoSuchAlgorithmException:" + e );
        } catch (Exception e) {
            System.out.println( "Exception:" + e );
        }
   }
 
}