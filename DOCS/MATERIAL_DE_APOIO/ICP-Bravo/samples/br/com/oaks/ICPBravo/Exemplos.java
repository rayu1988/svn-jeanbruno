package br.com.oaks.ICPBravo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.crypto.SecretKey;
import javax.mail.internet.MimeBodyPart;
import javax.net.ssl.SSLContext;

import br.com.oaks.ICPBravo.algorithm.SignatureAlgorithm;
import br.com.oaks.ICPBravo.algorithm.asymmetric.AsymmetricAlgorithm;
import br.com.oaks.ICPBravo.algorithm.asymmetric.DiffieHellman;
import br.com.oaks.ICPBravo.algorithm.asymmetric.DiffieHellmanEC;
import br.com.oaks.ICPBravo.algorithm.asymmetric.RSA;
import br.com.oaks.ICPBravo.algorithm.asymmetric.RSA1024ECB;
import br.com.oaks.ICPBravo.algorithm.asymmetric.RSA2048ECB;
import br.com.oaks.ICPBravo.algorithm.asymmetric.RSA4096ECB;
import br.com.oaks.ICPBravo.algorithm.digest.DigestAlgorithm;
import br.com.oaks.ICPBravo.algorithm.digest.HMAC_SHA1;
import br.com.oaks.ICPBravo.algorithm.digest.MD5;
import br.com.oaks.ICPBravo.algorithm.digest.SHA1;
import br.com.oaks.ICPBravo.algorithm.digest.SHA256;
import br.com.oaks.ICPBravo.algorithm.digest.SHA512;
import br.com.oaks.ICPBravo.algorithm.pbe.PBEAlgorithm;
import br.com.oaks.ICPBravo.algorithm.pbe.PBE_Sha1AndTripleDES112;
import br.com.oaks.ICPBravo.algorithm.prng.LFSR;
import br.com.oaks.ICPBravo.algorithm.prng.PRNGAlgorithm;
import br.com.oaks.ICPBravo.algorithm.prng.SHA1_PRNG;
import br.com.oaks.ICPBravo.algorithm.prng.WindowsPRNG;
import br.com.oaks.ICPBravo.algorithm.secretSharing.ShamirSecretSharing;
import br.com.oaks.ICPBravo.algorithm.symmetric.AES128CBC;
import br.com.oaks.ICPBravo.algorithm.symmetric.AES128ECB;
import br.com.oaks.ICPBravo.algorithm.symmetric.AES192CBC;
import br.com.oaks.ICPBravo.algorithm.symmetric.AES192ECB;
import br.com.oaks.ICPBravo.algorithm.symmetric.AES256CBC;
import br.com.oaks.ICPBravo.algorithm.symmetric.AES256ECB;
import br.com.oaks.ICPBravo.algorithm.symmetric.DESCBC;
import br.com.oaks.ICPBravo.algorithm.symmetric.DESECB;
import br.com.oaks.ICPBravo.algorithm.symmetric.RC2CBC;
import br.com.oaks.ICPBravo.algorithm.symmetric.RC2ECB;
import br.com.oaks.ICPBravo.algorithm.symmetric.RC4;
import br.com.oaks.ICPBravo.algorithm.symmetric.SymmetricAlgorithm;
import br.com.oaks.ICPBravo.algorithm.symmetric.TripleDES112CBC;
import br.com.oaks.ICPBravo.algorithm.symmetric.TripleDES112ECB;
import br.com.oaks.ICPBravo.algorithm.symmetric.TripleDES168CBC;
import br.com.oaks.ICPBravo.algorithm.symmetric.TripleDES168ECB;
import br.com.oaks.ICPBravo.asn1.oiw.OIWObjectIdentifiers;
import br.com.oaks.ICPBravo.certs.CertificateAttribute;
import br.com.oaks.ICPBravo.certs.ICPBravoCA;
import br.com.oaks.ICPBravo.certs.ICPBravoCertificate;
import br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator;
import br.com.oaks.ICPBravo.certs.ICPBravoCertificationRequest;
import br.com.oaks.ICPBravo.certs.crl.ICPBravoCRL;
import br.com.oaks.ICPBravo.certs.crl.ICPBravoCRLGenerator;
import br.com.oaks.ICPBravo.cms.CMSContentCollection;
import br.com.oaks.ICPBravo.cms.CMSContentInfo;
import br.com.oaks.ICPBravo.cms.CMSEnvelopedData;
import br.com.oaks.ICPBravo.cms.CMSSignedData;
import br.com.oaks.ICPBravo.cms.CMSSignerInformation;
import br.com.oaks.ICPBravo.cms.Pkcs9;
import br.com.oaks.ICPBravo.cms.SignedAndEnveloped;
import br.com.oaks.ICPBravo.cms.content.Base64Content;
import br.com.oaks.ICPBravo.cms.content.BytesContent;
import br.com.oaks.ICPBravo.cms.content.Content;
import br.com.oaks.ICPBravo.cms.content.FileContent;
import br.com.oaks.ICPBravo.cms.content.MultipleContent;
import br.com.oaks.ICPBravo.cms.content.PEMContent;
import br.com.oaks.ICPBravo.cms.content.XMLContent;
import br.com.oaks.ICPBravo.cms.content.XMLDSigContent;
import br.com.oaks.ICPBravo.cms.pkcs9.SignatureAttribute;
import br.com.oaks.ICPBravo.cms.pkcs9.CommitmentTypeIndicationAttribute;
import br.com.oaks.ICPBravo.cms.pkcs9.SignedAttribute;
import br.com.oaks.ICPBravo.cms.policies.CMSPolicy;
import br.com.oaks.ICPBravo.cms.policies.Policy;
import br.com.oaks.ICPBravo.exceptions.CertificadoExpiradoException;
import br.com.oaks.ICPBravo.exceptions.ChaveInvalidaException;
import br.com.oaks.ICPBravo.exceptions.ErroIrrecuperavelEmChaveException;
import br.com.oaks.ICPBravo.exceptions.ErroKeystoreException;
import br.com.oaks.ICPBravo.exceptions.InvalidCertificateException;
import br.com.oaks.ICPBravo.exceptions.ManagerNotInitialedException;
import br.com.oaks.ICPBravo.exceptions.NotSuportedAlgorithmException;
import br.com.oaks.ICPBravo.exceptions.SecretRevelationException;
import br.com.oaks.ICPBravo.exceptions.SigningException;
import br.com.oaks.ICPBravo.mail.ICPBravoMailMessage;
import br.com.oaks.ICPBravo.mail.mimePart.DataSourceMailPart;
import br.com.oaks.ICPBravo.mail.mimePart.SignatureMailPart;
import br.com.oaks.ICPBravo.mail.mimePart.dataSource.TextEncodedDataSource;
import br.com.oaks.ICPBravo.mail.server.ICPBravoMailServer;
import br.com.oaks.ICPBravo.mail.server.SMTPMailServer;
import br.com.oaks.ICPBravo.manager.ICPBravoManager;
import br.com.oaks.ICPBravo.manager.JKSManager;
import br.com.oaks.ICPBravo.manager.LdapDNFilter;
import br.com.oaks.ICPBravo.manager.LdapEmailFilter;
import br.com.oaks.ICPBravo.manager.LdapManager;
import br.com.oaks.ICPBravo.manager.LdapSerialNumberFilter;
import br.com.oaks.ICPBravo.manager.PKCS11Manager;
import br.com.oaks.ICPBravo.manager.PKCS12Manager;
import br.com.oaks.ICPBravo.manager.TestManager;
import br.com.oaks.ICPBravo.provider.ICPBravoProvider;
import br.com.oaks.ICPBravo.socket.SSLClient;
import br.com.oaks.ICPBravo.socket.SSLServer;
import br.com.oaks.ICPBravo.util.ICPBravoUtil;
import br.com.oaks.ICPBravo.util.PDFSignedData;
import br.com.oaks.ICPBravo.util.PDFSignedData.PdfSignerInformation;

/**
 * 
 * <script type="text/javascript" src="../../../../../../scripts/shCore.js"></script>
 * <script type="text/javascript" src="../../../../../../scripts/shBrushCSharp.js"></script>
 * <script type="text/javascript" src="../../../../../../scripts/shBrushCss.js"></script>
 * <script type="text/javascript" src="../../../../../../scripts/shBrushDiff.js"></script>
 * <script type="text/javascript" src="../../../../../../scripts/shBrushJava.js"></script>
 * <script type="text/javascript" src="../../../../../../scripts/shBrushJScript.js"></script>
 * <script type="text/javascript" src="../../../../../../scripts/shBrushPlain.js"></script>
 * <script type="text/javascript" src="../../../../../../scripts/shBrushXml.js"></script>
 * <link type="text/css" rel="stylesheet" href="../../../../../../styles/shCore.css"/>
 * <link type="text/css" rel="stylesheet" href="../../../../../../styles/shThemeMidnight.css"/>
 * <script type="text/javascript">
 * 	SyntaxHighlighter.config.clipboardSwf = '../../../../../../scripts/clipboard.swf';
 * 	SyntaxHighlighter.all();
 * </script>
 * 
 * A classe a seguir demonstra alguns exemplos do uso da API ICP-Bravo. <br>
 * <br>
 * <a href=
 * '../../../../../../samples/br/com/oaks/ICPBravo/Exemplos.java'>Download
 * Exemplos.java</a> <br>
 * Para compilar os exemplos, salve-o em %DIRETORIO_SAMPLE/br/com/oaks/ICPBravo
 * e dê o seguinte comando:
 * 
 * <pre class="brush: js;">
 * javac -classpath %PATH_DA_ICPBravoAPI\ICPBravoAPI.jar;%DIRETORIO_SAMPLE br\com\oaks\ICPBravo\Exemplos.java
 * </pre>
 * 
 * <br>
 * Para executar os exemplos, dê o seguinte comando:
 * 
 * <pre class="brush: js;">
 * java -classpath %PATH_DA_ICPBravoAPI\ICPBravoAPI.jar;%DIRETORIO_SAMPLE br.com.oaks.ICPBravo.Exemplos
 * </pre>
 * 
 * <br>
 * Onde %DIRETORIO_SAMPLE é o diretório onde será realizado o teste e
 * %PATH_DA_ICPBravoAPI é o diretório que contém a API do ICPBravo.
 * 
 */
public class Exemplos {
	protected static ICPBravoManager manager;
	protected static ICPBravoManager manager2;
	protected static Provider provider;
	protected static Provider provider2;

	protected static ICPBravoCertificate certRoot;
//	protected static ICPBravoCertificate certRoot2;
	protected static ICPBravoCertificate cert1;
	protected static ICPBravoCertificate cert2;
	protected static ICPBravoCertificate cert3;
	protected static ICPBravoCertificate cert4;
	protected static ICPBravoCertificate certExpired;
	
	protected static Cronometro cron = new Cronometro();

	protected static class Cronometro {
		private long startTime;
		private long lastPartialTime;

		public Cronometro() {
			zera();
		}

		public void zera() {
			startTime = System.currentTimeMillis();
			lastPartialTime = startTime;
		}

		public void marcaParcial(String message) {
			long dif = System.currentTimeMillis() - lastPartialTime;
			System.out.println(message + " (" + dif + " ms)");
			lastPartialTime = System.currentTimeMillis();
		}

		public void marcaTotal(String message) {
			long dif = System.currentTimeMillis() - startTime;
			System.out.println(message + " (" + dif + " ms)");
		}
	}

	/**
	 * Testa algumas das cifras simétricas da API, como DES (CBC/ECB), TripleDES
	 * com 112 e 168 bits (ECB/CBC), AES com 128, 192 e 256 bits (ECB/CBC), RC4 e
	 * RC2 (ECB/CBC).
	 * <p>
	 * O teste criará um chave secreta e criptografará e decriptografará um array
	 * de bytes com esta chave.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	 * public static void testaCifrasSimetricas() {
	 * 	cron.zera();
	 * 	System.out.println(&quot;Testando as cifras simétricas&quot;);
	 * 	SymmetricAlgorithm[] algs = {new DESCBC(provider), new DESECB(provider),
	 * 
	 * 	new TripleDES112ECB(provider), new TripleDES112CBC(provider), new TripleDES168ECB(provider), new TripleDES168CBC(provider),
	 * 
	 * 	new AES128CBC(provider), new AES128ECB(provider), new AES192CBC(provider), new AES192ECB(provider), new AES256CBC(provider), new AES256ECB(provider),
	 * 
	 * 	new RC4(provider),
	 * 
	 * 	new RC2CBC(provider), new RC2ECB(provider),};
	 * 	byte[] bts = &quot;teste&quot;.getBytes();
	 * 
	 * 	for (int a = 0; a &lt; algs.length; a++) {
	 * 		try {
	 * 			Cronometro cron2 = new Cronometro();
	 * 
	 * 			SecretKey secKey = algs[a].generateKey();
	 * 			cron2.marcaParcial(algs[a].toString() + &quot;: Chave gerada&quot;);
	 * 			byte[] cr = algs[a].crypt(bts, secKey);
	 * 			cron2.marcaParcial(algs[a].toString() + &quot;: Criptografado&quot;);
	 * 			byte[] ret = algs[a].decrypt(cr, secKey);
	 * 			cron2.marcaParcial(algs[a].toString() + &quot;: Decriptografado&quot;);
	 * 
	 * 			if (Arrays.equals(bts, ret)) {
	 * 				cron2.marcaTotal(algs[a].toString() + &quot;: OK&quot;);
	 * 			} else {
	 * 				System.out.println(algs[a].toString() + &quot;: Falha&quot;);
	 * 			}
	 * 		} catch (Exception e) {
	 * 			System.out.println(algs[a].toString() + &quot;: Falha &quot; + e.getMessage());
	 * 		}
	 * 	}
	 * 	cron.marcaTotal(&quot;Fim de teste de algoritmo simétrico&quot;);
	 * }
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando as cifras simétricas
DESEDE: Chave gerada (1311 ms)
DESEDE: Criptografado (54 ms)
DESEDE: Decriptografado (1 ms)
DESEDE: OK (1373 ms)
DESECB: Chave gerada (0 ms)
DESECB: Criptografado (1 ms)
DESECB: Decriptografado (0 ms)
DESECB: OK (7 ms)
3DES112ECB: Chave gerada (6 ms)
3DES112ECB: Criptografado (6 ms)
3DES112ECB: Decriptografado (1 ms)
3DES112ECB: OK (20 ms)
3DES112CBC: Chave gerada (0 ms)
3DES112CBC: Criptografado (1 ms)
3DES112CBC: Decriptografado (0 ms)
3DES112CBC: OK (8 ms)
3DES168ECB: Chave gerada (0 ms)
3DES168ECB: Criptografado (1 ms)
3DES168ECB: Decriptografado (0 ms)
3DES168ECB: OK (9 ms)
3DES168CBC: Chave gerada (0 ms)
3DES168CBC: Criptografado (0 ms)
3DES168CBC: Decriptografado (0 ms)
3DES168CBC: OK (9 ms)
AES128CBC: Chave gerada (70 ms)
AES128CBC: Criptografado (5 ms)
AES128CBC: Decriptografado (0 ms)
AES128CBC: OK (81 ms)
AES128ECB: Chave gerada (0 ms)
AES128ECB: Criptografado (2 ms)
AES128ECB: Decriptografado (1 ms)
AES128ECB: OK (10 ms)
AES192CBC: Chave gerada (0 ms)
AES192CBC: Criptografado (0 ms)
AES192CBC: Decriptografado (1 ms)
AES192CBC: OK (9 ms)
AES192ECB: Chave gerada (0 ms)
AES192ECB: Criptografado (1 ms)
AES192ECB: Decriptografado (1 ms)
AES192ECB: OK (9 ms)
AES256CBC: Chave gerada (0 ms)
AES256CBC: Criptografado (1 ms)
AES256CBC: Decriptografado (1 ms)
AES256CBC: OK (10 ms)
AES256ECB: Chave gerada (0 ms)
AES256ECB: Criptografado (6 ms)
AES256ECB: Decriptografado (1 ms)
AES256ECB: OK (14 ms)
RC4: Chave gerada (5 ms)
RC4: Criptografado (7 ms)
RC4: Decriptografado (0 ms)
RC4: OK (20 ms)
RC2CBC: Chave gerada (3 ms)
RC2CBC: Criptografado (8 ms)
RC2CBC: Decriptografado (0 ms)
RC2CBC: OK (20 ms)
RC2ECB: Chave gerada (0 ms)
RC2ECB: Criptografado (1 ms)
RC2ECB: Decriptografado (0 ms)
RC2ECB: OK (8 ms)
Fim de teste de algoritmo simétrico (1652 ms)
	 * </pre>
	 */
	public static void testaCifrasSimetricas() {
		cron.zera();
		System.out.println("Testando as cifras simétricas");
		SymmetricAlgorithm[] algs = {new DESCBC(provider), new DESECB(provider),

		new TripleDES112ECB(provider), new TripleDES112CBC(provider), new TripleDES168ECB(provider), new TripleDES168CBC(provider),

		new AES128CBC(provider), new AES128ECB(provider), new AES192CBC(provider), new AES192ECB(provider), new AES256CBC(provider), new AES256ECB(provider),

		new RC4(provider),

		new RC2CBC(provider), new RC2ECB(provider),};
		byte[] bts = "teste".getBytes();

		for (int a = 0; a < algs.length; a++) {
			try {
				Cronometro cron2 = new Cronometro();

				SecretKey secKey = algs[a].generateKey();
				cron2.marcaParcial(algs[a].toString() + ": Chave gerada");
				byte[] cr = algs[a].crypt(bts, secKey);
				cron2.marcaParcial(algs[a].toString() + ": Criptografado");
				byte[] ret = algs[a].decrypt(cr, secKey);
				cron2.marcaParcial(algs[a].toString() + ": Decriptografado");

				if (Arrays.equals(bts, ret)) {
					cron2.marcaTotal(algs[a].toString() + ": OK");
				} else {
					System.out.println(algs[a].toString() + ": Falha");
				}
			} catch (Exception e) {
				System.out.println(algs[a].toString() + ": Falha " + e.getMessage());
			}
		}
		cron.marcaTotal("Fim de teste de algoritmo simétrico");
	}

	/**
	 * Testa algumas das cifras simétricas da API, como RSA com 1024, 2048 e 4096
	 * bits e o Diffie Hellman.
	 * <p>
	 * O teste criará um chave assimétrica e criptografará e decriptografará um
	 * array de bytes com esta chave.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	 * public static void testaCifrasAssimetricas() {
	 * 	cron.zera();
	 * 	System.out.println(&quot;Testando as cifras assimétricas&quot;);
	 * 	try {
	 * 		AsymmetricAlgorithm[] algs = {new RSA1024ECB(provider), new RSA2048ECB(provider), new RSA4096ECB(provider), new DiffieHellman(provider),};
	 * 		byte[] bts = &quot;teste&quot;.getBytes();
	 * 
	 * 		for (int a = 0; a &lt; algs.length; a++) {
	 * 			try {
	 * 				Cronometro cron2 = new Cronometro();
	 * 
	 * 				KeyPair secKey = algs[a].generateKeyPair();
	 * 				cron2.marcaParcial(algs[a].toString() + &quot;: Chave gerada&quot;);
	 * 				byte[] cr = algs[a].crypt(bts, secKey.getPublic());
	 * 				cron2.marcaParcial(algs[a].toString() + &quot;: Criptografado&quot;);
	 * 				byte[] ret = algs[a].decrypt(cr, secKey.getPrivate());
	 * 				cron2.marcaParcial(algs[a].toString() + &quot;: Decriptografado&quot;);
	 * 
	 * 				if (Arrays.equals(bts, ret)) {
	 * 					cron2.marcaTotal(algs[a].toString() + &quot;: OK&quot;);
	 * 				} else {
	 * 					System.out.println(algs[a].toString() + &quot;: Falha&quot;);
	 * 				}
	 * 			} catch (Exception e2) {
	 * 				System.out.println(algs[a].toString() + &quot;: Falha &quot; + e2.getMessage());
	 * 			}
	 * 		}
	 * 	} catch (Exception e1) {
	 * 		System.out.println(&quot;testaCifrasAssimetricas: Falha &quot; + e1.getMessage());
	 * 	}
	 * 	cron.marcaTotal(&quot;Fim de teste de algoritmo assimétrico&quot;);
	 * }
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando as cifras assimétricas
RSA1024: Chave gerada (1630 ms)
RSA1024: Criptografado (8 ms)
RSA1024: Decriptografado (31 ms)
RSA1024: OK (1676 ms)
RSA2048: Chave gerada (11676 ms)
RSA2048: Criptografado (6 ms)
RSA2048: Decriptografado (200 ms)
RSA2048: OK (11890 ms)
RSA4096: Chave gerada (106462 ms)
RSA4096: Criptografado (21 ms)
RSA4096: Decriptografado (1434 ms)
RSA4096: OK (107918 ms)
Fim de teste de algoritmo assimétrico (121492 ms)
	 * </pre>
	 */
	public static void testaCifrasAssimetricas() {
		cron.zera();
		System.out.println("Testando as cifras assimétricas");
		try {
			AsymmetricAlgorithm[] algs = {new RSA1024ECB(provider), new RSA2048ECB(provider), new RSA4096ECB(provider),};
			byte[] bts = "teste".getBytes();

			for (int a = 0; a < algs.length; a++) {
				try {
					Cronometro cron2 = new Cronometro();

					KeyPair secKey = algs[a].generateKeyPair();
					cron2.marcaParcial(algs[a].toString() + ": Chave gerada");
					byte[] cr = algs[a].crypt(bts, secKey.getPublic());
					cron2.marcaParcial(algs[a].toString() + ": Criptografado");
					byte[] ret = algs[a].decrypt(cr, secKey.getPrivate());
					cron2.marcaParcial(algs[a].toString() + ": Decriptografado");

					if (Arrays.equals(bts, ret)) {
						cron2.marcaTotal(algs[a].toString() + ": OK");
					} else {
						System.out.println(algs[a].toString() + ": Falha");
					}
				} catch (Exception e2) {
					System.out.println(algs[a].toString() + ": Falha " + e2.getMessage());
				}
			}
		} catch (Exception e1) {
			System.out.println("testaCifrasAssimetricas: Falha " + e1.getMessage());
		}
		cron.marcaTotal("Fim de teste de algoritmo assimétrico");
	}

	/**
	 * Testa alguns dos algoritmos de digest da API, como MD5, SHA-1, SHA-2 (com
	 * 256 e 512 bits) e o HMAC-SHA1.
	 * <p>
	 * O teste criará um hash e verificará se o hash criado tem a quantidade de
	 * bytes esperado.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	 * public static void testaDigest() {
	 * 	cron.zera();
	 * 	System.out.println(&quot;Testando os algoritmos de integridade (digest)&quot;);
	 * 	DigestAlgorithm[] algs = {new MD5(provider), new SHA1(provider), new SHA256(provider), new SHA512(provider), new HMAC_SHA1(provider),};
	 * 	byte[] bts = &quot;teste&quot;.getBytes();
	 * 
	 * 	for (int a = 0; a &lt; algs.length; a++) {
	 * 		try {
	 * 			Cronometro cron2 = new Cronometro();
	 * 			byte hash[] = algs[a].digest(bts);
	 * 			if ((hash.length * 8) == algs[a].getKeySize())
	 * 				cron2.marcaTotal(algs[a].toString() + &quot;: OK&quot;);
	 * 			else
	 * 				System.out.println(algs[a].toString() + &quot;: Falha&quot;);
	 * 		} catch (Exception e) {
	 * 			System.out.println(algs[a].toString() + &quot;: Falha &quot; + e.getMessage());
	 * 		}
	 * 	}
	 * 	cron.marcaTotal(&quot;Fim de teste de algoritmo de integridade&quot;);
	 * }
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando os algoritmos de integridade (digest)
MD5: OK (0 ms)
SHA1: OK (0 ms)
SHA256: OK (1 ms)
SHA512: OK (1 ms)
HMACSHA1: OK (1 ms)
Fim de teste de algoritmo de integridade (18 ms)
	 * </pre>
	 */
	public static void testaDigest() {
		cron.zera();
		System.out.println("Testando os algoritmos de integridade (digest)");
		DigestAlgorithm[] algs = {
				new MD5(provider), 
				new SHA1(provider),
				new SHA256(provider), 
				new SHA512(provider), 
				new HMAC_SHA1(provider),};
		byte[] bts = "teste".getBytes();

		for (int a = 0; a < algs.length; a++) {
			try {
				Cronometro cron2 = new Cronometro();
				byte hash[] = algs[a].digest(bts);
				if ((hash.length * 8) == algs[a].getKeySize())
					cron2.marcaTotal(algs[a].toString() + ": OK");
				else
					System.out.println(algs[a].toString() + ": Falha");
			} catch (Exception e) {
				System.out.println(algs[a].toString() + ": Falha " + e.getMessage());
			}
		}
		cron.marcaTotal("Fim de teste de algoritmo de integridade");
	}

	/**
	 * Testa a utilização do algoritmo HMAC-SHA1.
	 * <br>Neste teste criaremos uma chave secreta e a incluiremos junto com a mensagem
	 * para a geração do hash. 
	 * 
	 * <pre class="brush: java;">
public static void testaHMACSha1() {
	cron.zera();
	System.out.println("Testando o algoritmo HMAC/SHA1");
	HMAC_SHA1 alg = new HMAC_SHA1(provider);
	byte[] bts = "teste".getBytes();

	try {
		Cronometro cron2 = new Cronometro();
		SecretKey sc = alg.generateKey();
		byte hash[] = alg.digest(sc, bts);
		if ((hash.length * 8) == alg.getKeySize())
			cron2.marcaTotal(alg.toString() + ": OK");
		else
			System.out.println(alg.toString() + ": Falha");
	} catch (Exception e) {
		System.out.println(alg.toString() + ": Falha " + e.getMessage());
	}
	cron.marcaTotal("Fim de teste de algoritmo de integridade");
}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando o algoritmo HMAC/SHA1
HMACSHA1: OK (10 ms)
Fim de teste de algoritmo de integridade (15 ms)
	 * </pre>
	 */
	public static void testaHMACSha1() {
		cron.zera();
		System.out.println("Testando o algoritmo HMAC/SHA1");
		HMAC_SHA1 alg = new HMAC_SHA1(provider);
		byte[] bts = "teste".getBytes();

		try {
			Cronometro cron2 = new Cronometro();
			SecretKey sc = alg.generateKey();
			byte hash[] = alg.digest(sc, bts);
			if ((hash.length * 8) == alg.getKeySize())
				cron2.marcaTotal(alg.toString() + ": OK");
			else
				System.out.println(alg.toString() + ": Falha");
		} catch (Exception e) {
			System.out.println(alg.toString() + ": Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de algoritmo de integridade");
	}
	
	/**
	 * Testa a utilização de encriptação de chaves (KEK)
	 * 
	 * <pre class="brush: java;">
protected static void testaKEK() {
	cron.zera();
	System.out.println("Testando encriptografação de chaves (KEK)");
	try {
		SymmetricAlgorithm sym = new AES128CBC(provider);
		AsymmetricAlgorithm algs = new RSA1024ECB(provider);
		byte[] bts = "teste".getBytes();
		KeyPair secKey = algs.generateKeyPair();

		SecretKey key1 = sym.generateKey();
		byte[] cr = sym.crypt(bts, key1);
		byte[] keyEnc = algs.keyCrypt(key1, secKey.getPublic());
		// Neste ponto temos os bytes e a chave secreta criptografados.

		Key key2 = algs.keyDecrypt(keyEnc, secKey.getPrivate(), sym.getCipherName());
		byte[] ret = sym.decrypt(cr, key2);

		if (Arrays.equals(bts, ret)) {
			System.out.println("OK");
		} else {
			System.out.println("Falha");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	cron.marcaTotal("Fim de Teste de KEK");
}
	 * </pre>
	 * 
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
testaKEK() - ######################################################################
Testando encriptografaþÒo de chaves (KEK)
OK
Fim de Teste de KEK (672 ms)
	 * </pre>
	*/
	protected static void testaKEK() {
		cron.zera();
		System.out.println("Testando encriptografação de chaves (KEK)");
		try {
			SymmetricAlgorithm sym = new AES128CBC(provider);
			AsymmetricAlgorithm algs = new RSA1024ECB(provider);
			byte[] bts = "teste".getBytes();
			KeyPair secKey = algs.generateKeyPair();

			SecretKey key1 = sym.generateKey();
			byte[] cr = sym.crypt(bts, key1);
			byte[] keyEnc = algs.keyCrypt(key1, secKey.getPublic());
			// Neste ponto temos os bytes e a chave secreta criptografados.

			Key key2 = algs.keyDecrypt(keyEnc, secKey.getPrivate(), sym.getCipherName());
			byte[] ret = sym.decrypt(cr, key2);

			if (Arrays.equals(bts, ret)) {
				System.out.println("OK");
			} else {
				System.out.println("Falha");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cron.marcaTotal("Fim de Teste de KEK");
	}

	/**
	 * Testa os geradores de números pseudo-aleatórios da API, como LFSR e SHA1-PRNG e o gerador nativo do Windows.
	 * <p>
	 * O teste criará uma sequência de números aleatórios com cada um dos
	 * algoritmos.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	 * public static void testaNumerosAleatorios() {
	 * 	cron.zera();
	 * 	System.out.println(&quot;Testando geração de números aleatórios&quot;);
	 * 	PRNG[] algs = {new LFSR(provider), new SHA1_PRNG(provider), new WindowsPRNG(provider)};
	 * 
	 * 	for (int a = 0; a &lt; algs.length; a++) {
	 * 		try {
	 * 			SecureRandom sr = algs[a].getRandom();
	 * 			System.out.println(&quot;Gerando para &quot; + algs[a].toString() + &quot;: &quot;);
	 * 			for (int b = 0; b &lt; 100; b++)
	 * 				// gerar 100 números aleatórios
	 * 				System.out.print(sr.nextInt(512) + &quot;, &quot;);
	 * 			System.out.println(algs[a].toString() + &quot;: OK&quot;);
	 * 		} catch (Exception e) {
	 * 			System.out.println(algs[a].toString() + &quot;: Falha &quot; + e.getMessage());
	 * 		}
	 * 	}
	 * 	cron.marcaTotal(&quot;Fim de teste de números aleatórios&quot;);
	 * }
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando geração de números aleatórios
Gerando para LFSR: 
498, 182, 35, 345, 428, 55, 427, 195, 100, 427, 144, 310, 437, 446, 464, 322, 109, 196, 81, 444, 252, 115, 237, 24, 166, 125, 315, 18, 193, 152, 158, 239, 475, 246, 360, 356, 75, 43, 153, 35, 84, 446, 166, 208, 97, 112, 301, 156, 74, 225, 56, 236, 398, 171, 93, 157, 4, 192, 113, 261, 301, 340, 226, 250, 465, 401, 167, 103, 447, 508, 174, 469, 316, 76, 102, 231, 321, 176, 66, 387, 128, 389, 480, 317, 27, 254, 268, 198, 479, 323, 446, 224, 451, 65, 131, 295, 244, 373, 191, 102, LFSR: OK
Gerando para SHA1PRNG: 
411, 207, 175, 248, 496, 24, 164, 335, 76, 301, 112, 381, 433, 130, 38, 265, 201, 37, 354, 436, 445, 442, 405, 269, 301, 191, 419, 308, 100, 358, 395, 304, 236, 362, 187, 475, 72, 275, 41, 74, 144, 58, 414, 324, 31, 77, 227, 26, 380, 106, 77, 98, 283, 187, 475, 266, 106, 335, 253, 485, 399, 343, 396, 175, 72, 292, 430, 265, 168, 196, 341, 213, 184, 337, 104, 86, 328, 379, 344, 487, 490, 30, 203, 388, 484, 62, 58, 357, 268, 5, 321, 134, 138, 126, 60, 181, 419, 270, 0, 128, SHA1PRNG: OK
Gerando para WindowsPRNG: 
147, 344, 463, 373, 335, 312, 83, 464, 250, 121, 137, 79, 23, 50, 344, 164, 200, 17, 44, 184, 43, 4, 397, 203, 324, 34, 249, 68, 346, 489, 421, 173, 6, 312, 489, 227, 48, 165, 66, 468, 44, 48, 336, 17, 312, 267, 423, 180, 278, 283, 445, 249, 83, 258, 20, 456, 63, 414, 499, 377, 112, 121, 147, 192, 265, 4, 352, 506, 201, 41, 475, 278, 30, 90, 507, 457, 85, 66, 204, 90, 273, 318, 395, 216, 287, 281, 493, 210, 310, 406, 271, 134, 368, 490, 284, 120, 12, 58, 338, 78, WindowsPRNG: OK
Fim de teste de números aleatórios (31 ms)
	 * </pre>
	 */
	public static void testaNumerosAleatorios() {
		cron.zera();
		System.out.println("Testando geração de números aleatórios");
		PRNGAlgorithm[] algs = {new LFSR(provider), new SHA1_PRNG(provider), new WindowsPRNG(provider)};

		for (int a = 0; a < algs.length; a++) {
			try {
				SecureRandom sr = algs[a].getRandom();
				System.out.println("Gerando para " + algs[a].toString() + ": ");
				for (int b = 0; b < 100; b++)
					// gerar 100 números aleatórios
					System.out.print(sr.nextInt(512) + ", ");
				System.out.println(algs[a].toString() + ": OK");
			} catch (Exception e) {
				System.out.println(algs[a].toString() + ": Falha " + e.getMessage());
			}
		}
		cron.marcaTotal("Fim de teste de números aleatórios");
	}

	/**
	 * Mostra as informações de um certificado X509.V3.
	 * 
	 * <pre class="brush: java;">
	public static void mostraInformacoesCertificado(ICPBravoCertificate cert) throws Exception {
		System.out.println("Gerando certificado X509v3 - ICPBrasil : OK");
		System.out.println(cert.toString());
		System.out.println("é autoassinado: " + cert.isAutoSign());
		System.out.println("é certificador de sigilo: " + cert.isSecrecy());
		System.out.println("é ICPBrasil: " + cert.isICPBrasil());
		System.out.println("é raiz ICPBrasil: " + cert.isICPBrasilRoot());
		System.out.println("pode assinar: " + cert.canSign());
		System.out.println("crlSign: " + cert.isKeyUsageExtensionCrlSign());
		System.out.println("dataEncipherment: " + cert.isKeyUsageExtensionDataEncipherment());
		System.out.println("decipherOnly: " + cert.isKeyUsageExtensionDecipherOnly());
		System.out.println("encipherOnly: " + cert.isKeyUsageExtensionEncipherOnly());
		System.out.println("digitalSignature: " + cert.isKeyUsageExtensionDigitalSignature());
		System.out.println("keyAgreement: " + cert.isKeyUsageExtensionKeyAgreement());
		System.out.println("KeyCertisign: " + cert.isKeyUsageExtensionKeyCertisign());
		System.out.println("KeyEncipherment: " + cert.isKeyUsageExtensionKeyEncipherment());
		System.out.println("NonRepudiation: " + cert.isKeyUsageExtensionNonRepudiation());
		try {
			cert.tryValidate();
			System.out.println("certificado válido: OK");
		} catch (CertificadoExpiradoException e1) {
			System.out.println("certificado válido: Falha " + e1.getMessage());
		} catch (InvalidCertificateException e1) {
			System.out.println("certificado válido: Falha " + e1.getMessage());
		}
		try {
			cert.tryTrustedValidate();
			System.out.println("certificado confiável: OK");
		} catch (Exception e1) {
			System.out.println("certificado confiável: Falha " + e1.getMessage());
		}
		try {
			ICPBravoCA ac = cert.getIssuerObject();
			if (ac != null) {
				System.out.println("AC: " + ac.toString());
				System.out.println("AC URL: " + ac.getURLCrl());
				ICPBravoCRL crl = ac.getValidCRL(null, null);
				System.out.println("CRL OK: " + crl.getThisUpdate() + " até " + crl.getNextUpdate());
			} else
				System.out.println("Não tem AC");
		} catch (Exception e) {
			System.out.println("Falha na verificação de CRL " + e.toString());
		}
		Vector&lt;CertificateAttribute&gt; infoGerais = cert.getGeneralInformation();
		for (CertificateAttribute informacaoCertificado : infoGerais) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector&lt;CertificateAttribute&gt; infoIssuerDNKeys = cert.getIssuerDNKeys();
		for (CertificateAttribute informacaoCertificado : infoIssuerDNKeys) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector&lt;CertificateAttribute&gt; infoSubjectDNKeys = cert.getSubjectDNKeys();
		for (CertificateAttribute informacaoCertificado : infoSubjectDNKeys) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector&lt;CertificateAttribute&gt; infoSubjectAlternativeNameExtensions = cert.getSubjectAlternativeNamesExtensions();
		for (CertificateAttribute informacaoCertificado : infoSubjectAlternativeNameExtensions) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector&lt;CertificateAttribute&gt; infoKeyUsageExtensions = cert.getKeyUsageExtension();
		for (CertificateAttribute informacaoCertificado : infoKeyUsageExtensions) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector&lt;CertificateAttribute&gt; infoExtendedKeyUsageExtension = cert.getExtendedKeyUsageExtension();
		for (CertificateAttribute informacaoCertificado : infoExtendedKeyUsageExtension) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector&lt;CertificateAttribute&gt; infoPoliciesExtensions = cert.getCertificatePoliciesExtensions();
		for (CertificateAttribute informacaoCertificado : infoPoliciesExtensions) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector&lt;CertificateAttribute&gt; infoCRLDistributionPointsExtensions = cert.getCRLDistributionPointsExtensions();
		for (CertificateAttribute informacaoCertificado : infoCRLDistributionPointsExtensions) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector&lt;CertificateAttribute&gt; infoICPBrasil = cert.constroiSubjectAlternativeNamesExtensions();
		for (CertificateAttribute informacaoCertificado : infoICPBrasil) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * ps.: Algumas falhas nas verificações poderão ser observadas, por se tratar de um certificado de exemplo e não haver um servidor que comprove algumas verficações, como por exemplo CRL e cadeia de confiança.
	 * <pre class="brush: plain;">
Pessoa:12345678901
É autoassinado: false
É certificador de sigilo: false
É ICPBrasil: true
É raiz ICPBrasil: false
pode assinar: false
crlSign: false
dataEncipherment: false
decipherOnly: false
encipherOnly: false
digitalSignature: true
keyAgreement: false
KeyCertisign: false
KeyEncipherment: true
NonRepudiation: true
certificado válido: OK
certificado confiável: Falha Não foi possível encontrar o certificado Autoridade Certificadora Raiz Brasileira fake na lista de certificados confiáveis.
AC: Autoridade Certificadora Raiz Brasileira fake
AC URL: http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl
Falha na verificação de CRL br.com.oaks.ICPBravo.exceptions.HttpErrorException: Erro de HTTP: http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl
C: BR
O: ICP-Brasil
ST: DF
L: Brasilia
OU: Instituto Nacional de Tecnologia da Informacao fake - ITIf
CN: Autoridade Certificadora Raiz Brasileira fake
CN: Pessoa:12345678901
OU: OU
OU: Secretaria da Receita Federal fake - SRFf
O: ICP-Brasil
C: BR
campo otherName em certificado de pessoa física, contento os dados do titular (data de nascimento, CPF, PIS/PASEP/CI, RG); Res. 07, de 12.12.2001: 010220091234567890100000000000123456789090000SSPDf
campo otherName em certificado de pessoa física, contendo informações sobre o Título de Eleitor do titular; Res. 13, de 29.04.2002: 00000000000000000000000000000000000000000
campo otherName em certificado de pessoa física, contendo nas 12 posições o número do Cadastro Específico do INSS (CEI) da pessoa física titular do certificado; Res. 31, de 29.01.2004: 000000000000
IA5String: pessoa@oaktech.com.br
DigitalSignature: true
NonRepudiation: true
KeyEncipherment: true
DataEncipherment: false
KeyAgreement: false
KeyCertisign: false
ClrSign: false
EncipherOnly: false
DecipherOnly: false
ServerAuth: false
ClientAuth: true
CodeSigning: true
EmailProtection: true
IpsecEndSystem: false
IpsecTunnel: false
IpsecUser: false
TimeStamping: true
OCSPSigning: false
Dvcs: false
SbgpCertAAServerAuth: false
Scvp_responder: false
EapOverPPP: false
EapOverLAN: false
ScvpServer: false
ScvpClient: false
IpsecIKE: false
CapwapAC: false
CapwapWTP: false
Smartcardlogon: false
Política de Certificado de Assinatura Digital do Tipo A3 da AC SERASA SRF: http://www.certificadodigital.oaks.com.br/repositorio/dpc
cdp_0: http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl
PF.Dt Nascimento: 01/02/2009
PF.CPF: 123.456.789-01
PF.NIS: 00000000000
PF.RG: 123456789090000
PF.OE: SSPDf
PF.Título Eleitor: 000000000000
PF.Zona: 000
PF.SessÒo: 0000
PF.Município/UF: 0000000000000000000000
PF.INSS: 000000000000
	 * </pre>
	 * @param cert
	 * @throws Exception
	 */
	public static void mostraInformacoesCertificado(ICPBravoCertificate cert) throws Exception {
		System.out.println(cert.toString());
		System.out.println("é autoassinado: " + cert.isAutoSign());
		System.out.println("é certificador de sigilo: " + cert.isSecrecy());
		System.out.println("é ICPBrasil: " + cert.isICPBrasil());
		System.out.println("é raiz ICPBrasil: " + cert.isICPBrasilRoot());
		System.out.println("pode assinar: " + cert.canSign());
		System.out.println("crlSign: " + cert.isKeyUsageExtensionCrlSign());
		System.out.println("dataEncipherment: " + cert.isKeyUsageExtensionDataEncipherment());
		System.out.println("decipherOnly: " + cert.isKeyUsageExtensionDecipherOnly());
		System.out.println("encipherOnly: " + cert.isKeyUsageExtensionEncipherOnly());
		System.out.println("digitalSignature: " + cert.isKeyUsageExtensionDigitalSignature());
		System.out.println("keyAgreement: " + cert.isKeyUsageExtensionKeyAgreement());
		System.out.println("KeyCertisign: " + cert.isKeyUsageExtensionKeyCertisign());
		System.out.println("KeyEncipherment: " + cert.isKeyUsageExtensionKeyEncipherment());
		System.out.println("NonRepudiation: " + cert.isKeyUsageExtensionNonRepudiation());
		try {
			cert.tryValidate();
			System.out.println("certificado válido: OK");
		} catch (CertificadoExpiradoException e1) {
			System.out.println("certificado válido: Falha " + e1.getMessage());
		} catch (InvalidCertificateException e1) {
			System.out.println("certificado válido: Falha " + e1.getMessage());
		}
		cert.checkValidity();
		try {
			cert.tryTrustedValidate();
			System.out.println("certificado confiável: OK");
		} catch (Exception e1) {
			System.out.println("certificado confiável: Falha " + e1.getMessage());
		}
		try {
			ICPBravoCA ac = cert.getIssuerObject();
			if (ac != null) {
				System.out.println("AC: " + ac.toString());
				for (int a=0; a<ac.countCrls(); a++)
					System.out.println("AC URL: "+ac.getURLCrl(a));
				ICPBravoCRL crl = ac.getValidCRL(null, null);
				System.out.println("CRL OK: " + crl.getThisUpdate() + " até " + crl.getNextUpdate());
			} else
				System.out.println("Não tem AC");
		} catch (Exception e) {
			System.out.println("Falha na verificação de CRL " + e.toString());
		}
		Vector<CertificateAttribute> infoGerais = cert.getGeneralInformation();
		for (CertificateAttribute informacaoCertificado : infoGerais) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector<CertificateAttribute> infoIssuerDNKeys = cert.getIssuerDNKeys();
		for (CertificateAttribute informacaoCertificado : infoIssuerDNKeys) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector<CertificateAttribute> infoSubjectDNKeys = cert.getSubjectDNKeys();
		for (CertificateAttribute informacaoCertificado : infoSubjectDNKeys) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector<CertificateAttribute> infoSubjectAlternativeNameExtensions = cert.getSubjectAlternativeNamesExtensions();
		for (CertificateAttribute informacaoCertificado : infoSubjectAlternativeNameExtensions) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector<CertificateAttribute> infoKeyUsageExtensions = cert.getKeyUsageExtension();
		for (CertificateAttribute informacaoCertificado : infoKeyUsageExtensions) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector<CertificateAttribute> infoExtendedKeyUsageExtension = cert.getEnhancedKeyUsageExtension();
		for (CertificateAttribute informacaoCertificado : infoExtendedKeyUsageExtension) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector<CertificateAttribute> infoPoliciesExtensions = cert.getCertificatePoliciesExtensions();
		for (CertificateAttribute informacaoCertificado : infoPoliciesExtensions) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector<CertificateAttribute> infoCRLDistributionPointsExtensions = cert.getCRLDistributionPointsExtensions();
		for (CertificateAttribute informacaoCertificado : infoCRLDistributionPointsExtensions) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
		Vector<CertificateAttribute> infoICPBrasil = cert.constroiSubjectAlternativeNamesExtensions();
		for (CertificateAttribute informacaoCertificado : infoICPBrasil) {
			System.out.println(informacaoCertificado.getDescription() + ": " + informacaoCertificado.getValue());
		}
	}

	/**
	 * Testa a geração e verificação de um certificado no padrão X509 v.3, em
	 * conformidade com a ICP-Brasil.
	 * <p>
	 * O teste criará o certifcado e em seguida apresentará no system.out as
	 * interfaces deste certificado.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	public static void testaCertificadoX509v3() {
		cron.zera();
		System.out.println("Testando geração de certificados X509 v3");
		Date from = ICPBravoUtil.getDateNow();
		Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);

		ICPBravoCertificateGenerator gen;
		try {
			gen = new ICPBravoCertificateGenerator(manager.getProvider(), "Pessoa", BigInteger.valueOf(4), "Pessoa", certRoot, ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(),
					ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
					"http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl", OIWObjectIdentifiers.OID_ICPBrasilA3 + ".10",
					"http://www.certificadodigital.oaks.com.br/repositorio/dpc", "OU=SRF e-CPF fake,OU=Secretaria da Receita Federal fake - SRFf", null, null, 
					from, to, 
					new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
					"pessoa@oaktech.com.br", ICPBravoUtil.getDate(2009, 1, 1), // nascimento
					"12345678901", // cpf
					null, // nis
					"12345678909", // rg
					"SSPDf", // oe
					null, // tituloEleitor
					null, // zona
					null, // sessao
					null, // municipioUf
					null // inss
			);

			ICPBravoCertificate cert = gen.generate(manager, true);
			mostraInformacoesCertificado(cert);
		} catch (Exception e) {
			System.out.println("Gerando certificado X509v3: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de geração de X509 v3");
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * ps.: Algumas falhas nas verificações poderão ser observadas, por se tratar de um certificado de exemplo e não haver um servidor que comprove algumas verficações, como por exemplo CRL e cadeia de confiança.
	 * <pre class="brush: plain;">
Testando geração de certificados X509 v3
Gerando certificado X509v3 - ICPBrasil : OK
...
Fim de teste de geração de X509 v3 (2684 ms)
	 * </pre>
	 */
	public static void testaCertificadoX509v3() {
		cron.zera();
		System.out.println("Testando geração de certificados X509 v3");
		Date from = ICPBravoUtil.getDateNow();
		Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);

		ICPBravoCertificateGenerator gen;
		try {
			gen = new ICPBravoCertificateGenerator(manager.getProvider(), "Pessoa", BigInteger.valueOf(4), 
					"Pessoa", certRoot, ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(),
					ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
					"http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl", OIWObjectIdentifiers.OID_ICPBrasilA3 + ".10",
					"http://www.certificadodigital.oaks.com.br/repositorio/dpc", "OU=SRF e-CPF fake,OU=Secretaria da Receita Federal fake - SRFf", null, null, 
					from, to, 
					new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
					"pessoa@oaktech.com.br", ICPBravoUtil.getDate(2009, 1, 1), // nascimento
					"12345678901", // cpf
					null, // nis
					"12345678909", // rg
					"SSPDf", // oe
					null, // tituloEleitor
					null, // zona
					null, // sessao
					null, // municipioUf
					null // inss
			);
			
			ICPBravoCertificate cert = gen.generate(manager, true);
			System.out.println("Gerando certificado X509v3 - ICPBrasil : OK");
			mostraInformacoesCertificado(cert);
		} catch (Exception e) {
			System.out.println("Gerando certificado X509v3: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de geração de X509 v3");
	}

	/**
	 * Verifica um certificado expirado.
	 */
	public static void testaCertificadoX509v3Expirado() {
		cron.zera();
		System.out.println("Testando geração de certificados X509 v3 Expirado");
		try {
			mostraInformacoesCertificado(certExpired);
			System.out.println("Gerando certificado X509v3: ERR - Não deveria verificar ");
		} catch (Exception e) {
			System.out.println("Gerando certificado X509v3: OK " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de geração de X509 v3");
	}
	
	/**
	 * Testa a geração e verificação de um certificado no padrão X509 v.2 (CRL).
	 * <p>
	 * O teste criará o certifcado e em seguida apresentará no system.out as
	 * interfaces deste certificado.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	 * public static void testaCertificadoX509v2() {
	 * 	cron.zera();
	 * 	System.out.println(&quot;Testando geração de certificados X509 v2&quot;);
	 * 	try {
	 * 		ICPBravoCRLGenerator genCRL = new ICPBravoCRLGenerator(manager, certRoot);
	 * 		genCRL.addCRLEntry(cert1.getSerialNumber(), new Date(), ICPBravoCRLGenerator.reason_unspecified);
	 * 		ICPBravoCRL crl = genCRL.generate(certRoot.getPrivateKey(), ICPBravoUtil.getDate(2009, 0, 23), ICPBravoUtil.getDate(2009, 1, 26));
	 * 
	 * 		try {
	 * 			crl.verify(cert1, certRoot);
	 * 			System.out.println(&quot;O certificado não está revogado mais deveria estar&quot;);
	 * 		} catch (Exception e) {
	 * 			System.out.println(&quot;O certificado está revogado &quot; + e.getMessage());
	 * 		}
	 * 
	 * 		try {
	 * 			crl.verify(cert2, certRoot);
	 * 			System.out.println(&quot;O certificado não está revogado&quot;);
	 * 		} catch (Exception e) {
	 * 			System.out.println(&quot;O certificado está revogado &quot; + e.getMessage() + &quot; mais não deveria estar&quot;);
	 * 		}
	 * 		System.out.println(&quot;X509 v2: OK&quot;);
	 * 
	 * 		byte[] encoded = crl.getEncoded();
	 * 		ICPBravoCRL crl2 = new ICPBravoCRL(manager, encoded);
	 * 		if (crl.equals(crl2))
	 * 			System.out.println(&quot;X509 v2 Encoded: OK&quot;);
	 * 		else
	 * 			System.out.println(&quot;X509 v2 Encoded: Fail&quot;);
	 * 	} catch (Exception e) {
	 * 		System.out.println(&quot;X509 v2: Falha&quot;);
	 * 	}
	 * 	cron.marcaTotal(&quot;Fim de teste de geração de X509 v2&quot;);
	 * }
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando geração de certificados X509 v2
O certificado está revogado Certificado revogado - DN:C=BR,O=ICP-Brasil,OU=Secretaria da Receita Federal fake - SRFf,OU=OU\=SRF e-CPF fake,CN=Pessoa 1:12345678901, DT:Fri Aug 28 09:46:50 BRT 2009
O certificado não está revogado
X509 v2: OK
X509 v2 Encoded: OK
Fim de teste de geração de X509 v2 (79 ms)
	 * </pre>
	 */
	public static void testaCertificadoX509v2() {
		cron.zera();
		System.out.println("Testando geração de certificados X509 v2");
		try {
			ICPBravoCRLGenerator genCRL = new ICPBravoCRLGenerator(manager, certRoot);
			genCRL.addCRLEntry(cert1.getSerialNumber(), new Date(), ICPBravoCRLGenerator.reason_unspecified);
			ICPBravoCRL crl = genCRL.generate(ICPBravoUtil.getDate(2009, 0, 23), ICPBravoUtil.getDate(2019, 1, 26));

			try {
				crl.verify(cert1, certRoot);
				System.out.println("O certificado não está revogado mais deveria estar");
			} catch (Exception e) {
				System.out.println("O certificado está revogado " + e.getMessage());
			}

			try {
				crl.verify(cert2, certRoot);
				System.out.println("O certificado não está revogado");
			} catch (Exception e) {
				System.out.println("O certificado está revogado " + e.getMessage() + " mais não deveria estar");
			}
			System.out.println("X509 v2: OK");

//			byte[] encoded = crl.getEncoded();
//			ICPBravoCRL crl2 = new ICPBravoCRL(manager, encoded);
//			if (crl.equals(crl2))
//				System.out.println("X509 v2 Encoded: OK");
//			else
//				System.out.println("X509 v2 Encoded: Fail");
		} catch (Exception e) {
			System.out.println("X509 v2: Falha");
		}
		cron.marcaTotal("Fim de teste de geração de X509 v2");
	}

	/**
	 * Cria um novo manager de dispositivo (no caso é utilizado um para
	 * JavaKeyStore) e pega seu provider, que será utilizado no decorrer dos
	 * testes.
	 * 
	 * <pre class="brush: java;">
public static void inicializaTeste() {
	cron.zera();
	try {
		System.out.println("Criando a infraestrutura necessária para os testes ...");

		manager = new TestManager("JKS", new ICPBravoProvider(), "1234".toCharArray());
		provider = manager.getProvider();
	} catch (Exception e) {
		e.printStackTrace();
	}
	cron.marcaTotal("... infraestrutura criada.");
}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Criando a infraestrutura necessßria para os testes ...
ICPBravo v.X.XX - Uso restrito para: ...
ICPBravo v.X.XX - Uso restrito para: ...
... infraestrutura criada. (1181 ms)
	 * </pre>
	 */
	public static void inicializaTeste() {
		cron.zera();
		try {
			System.out.println("Criando a infraestrutura necessária para os testes ...");

			manager = new TestManager("JKS", new ICPBravoProvider(), "1234".toCharArray());
			provider = manager.getProvider();

			manager2 = new JKSManager("JKS2.jks", "1234".toCharArray());
			provider2 = manager2.getProvider();
		} catch (Exception e) {
			e.printStackTrace();
		}
		cron.marcaTotal("... infraestrutura criada.");
	}

	/**
	 * O teste a seguir cria os certificados necessários para o teste
	 * <p>
	 * Serão criados 3 certificados, sendo um de raiz e dois de usuário.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	public static void criaCertificados() {
		cron.zera();
		try {
			System.out.println("Criando certificados para os testes ...");

			Date from = ICPBravoUtil.getDateNow();
			Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);

			certRoot = criaCertificadoRaiz(from, to);
			cert1 = criaCertificado1(certRoot, from, to);
			cert2 = criaCertificado2(certRoot, from, to);
			cert3 = criaCertificado3(certRoot, from, to);
			cert4 = criaCertificado4(certRoot, from, to);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cron.marcaTotal("... certificados de exemplo criados.");
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Criando certificados para os testes ...
Gerando certificado X509v3 - ICPBrasil (root 1): OK
Gerando certificado X509v3 - ICPBrasil (pessoa 1): OK
Gerando certificado X509v3 - ICPBrasil (pessoa 2): OK
... certificados de exemplo criados. (4043 ms)
	 * </pre>
	 */
	public static void criaCertificados() {
		cron.zera();
		try {
			System.out.println("Criando certificados para os testes ...");

			Date from = ICPBravoUtil.getDateNow();
			Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);

			certRoot = criaCertificadoRaiz(from, to);
//			certRoot2 = criaCertificadoRaiz2(from, to);
			cert1 = criaCertificado1(certRoot, from, to);
			cert2 = criaCertificado2(certRoot, from, to);
			cert3 = criaCertificado3(certRoot, from, to);
			cert4 = criaCertificado4(certRoot, from, to);
			certExpired = criaCertificado2(certRoot, from, from);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cron.marcaTotal("... certificados de exemplo criados.");
	}

	/**
	 * Método chamado para criação de certificado Raiz.
	 * <pre class="brush: java;">
public static ICPBravoCertificate criaCertificadoRaiz(Date from, Date to) throws NoSuchAlgorithmException, NotSuportedAlgorithmException, IOException, ErroKeystoreException, InvalidKeyException, IllegalStateException, SignatureException, KeyStoreException, CertificateException, ChaveInvalidaException, SigningException, ErroIrrecuperavelEmChaveException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
	ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(
		manager.getProvider(), 
		"Autoridade Certificadora Raiz Brasileira fake", 
		BigInteger.valueOf(1),
		"Autoridade Certificadora Raiz Brasileira fake", 
		null, 
		ICPBravoCertificateGenerator.getDefaultKeyUsageForCA(), 
		ICPBravoCertificateGenerator.getDefaultKeyPurposeForCA(),
		"http://acraiz.icpbrasil.gov.oaks.com.br/LCRacraiz.crl", 
		OIWObjectIdentifiers.OID_ICPBrasilDPC + ".0", 
		"http://acraiz.icpbrasil.gov.oaks.com.br/DPCacraiz.pdf",
		"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", 
		from, to, 
		new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
		null);

	System.out.println("Gerando certificado X509v3 - ICPBrasil (root 1): OK");
	return gen.generate(manager, true);
}
	 * </pre>
	 * @throws ManagerNotInitialedException 
	 */
	public static ICPBravoCertificate criaCertificadoRaiz(Date from, Date to) throws NoSuchAlgorithmException, NotSuportedAlgorithmException, IOException, ErroKeystoreException, InvalidKeyException, IllegalStateException, SignatureException, KeyStoreException, CertificateException, ChaveInvalidaException, SigningException, ErroIrrecuperavelEmChaveException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidParameterSpecException, ManagerNotInitialedException {
		ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(
			manager.getProvider(), 
			"Autoridade Certificadora Raiz Brasileira fake", 
			BigInteger.valueOf(1),
			"Autoridade Certificadora Raiz Brasileira fake", 
			null, 
			ICPBravoCertificateGenerator.getDefaultKeyUsageForCA(), 
			ICPBravoCertificateGenerator.getDefaultKeyPurposeForCA(),
			"http://acraiz.icpbrasil.gov.oaks.com.br/LCRacraiz.crl", 
			OIWObjectIdentifiers.OID_ICPBrasilDPC + ".0", 
			"http://acraiz.icpbrasil.gov.oaks.com.br/DPCacraiz.pdf",
			"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
			from, to, 
			new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
			null);

		System.out.println("Gerando certificado X509v3 - ICPBrasil (root 1): OK");
		return gen.generate(manager, true);
	}

	/**
	 * Método chamado para criação de certificado de usuário
	 * 
	 * <pre class="brush: plain;">
	private static ICPBravoCertificate criaCertificado1(ICPBravoCertificate root, Date from, Date to) throws NoSuchAlgorithmException, NotSuportedAlgorithmException, IOException, ErroKeystoreException, InvalidKeyException, IllegalStateException, SignatureException, KeyStoreException, CertificateException, ChaveInvalidaException, SigningException, ErroIrrecuperavelEmChaveException, NoSuchProviderException, ManagerNotInitialedException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
		ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(
				provider, 
				"Pessoa 1", 
				BigInteger.valueOf(2), 
				"Pessoa 1", 
				root, 
				ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(), 
				"http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl", 
				OIWObjectIdentifiers.OID_ICPBrasilA3 + ".10",
				"http://www.certificadodigital.oaks.com.br/repositorio/dpc", 
				"OU=SRF e-CPF fake,OU=Secretaria da Receita Federal fake - SRFf", 
				from, to, 
				new SignatureAlgorithm(provider, new SHA1(provider), new RSA1024ECB(provider)), 
				"pessoa1S@oaktech.com.br", 
				ICPBravoUtil.getDate(2009, 1, 1), // data de nascimento
				"12345678901", // cpf
				null, // nis
				"12345678909", // rg
				"SSPDf", // oe
				null, // tituloEleitor
				null, // zona
				null, // sessao
				null, // municipioUf
				null // inss
		);
		System.out.println("Gerando certificado X509v3 - ICPBrasil (pessoa 1): OK");
		return gen.generate(manager, true);
	}
	 * </pre>
	 */
	protected static ICPBravoCertificate criaCertificado1(ICPBravoCertificate root, Date from, Date to) throws NoSuchAlgorithmException, NotSuportedAlgorithmException, IOException, ErroKeystoreException, InvalidKeyException, IllegalStateException, SignatureException, KeyStoreException, CertificateException, ChaveInvalidaException, SigningException, ErroIrrecuperavelEmChaveException, NoSuchProviderException, ManagerNotInitialedException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
		ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(
				provider, 
				"Pessoa 1", 
				BigInteger.valueOf(2), 
				"Pessoa 1", 
				root, 
				ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(), 
				"http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl", 
				OIWObjectIdentifiers.OID_ICPBrasilA3 + ".10",
				"http://www.certificadodigital.oaks.com.br/repositorio/dpc", 
				"OU=SRF e-CPF fake,OU=Secretaria da Receita Federal fake - SRFf", null, null, 
				from, to, 
				new SignatureAlgorithm(provider, new SHA1(provider), new RSA1024ECB(provider)), 
				"pessoa1S@oaktech.com.br", 
				ICPBravoUtil.getDate(2009, 1, 1), // data de nascimento
				"12345678901", // cpf
				null, // nis
				"12345678909", // rg
				"SSPDf", // oe
				null, // tituloEleitor
				null, // zona
				null, // sessao
				null, // municipioUf
				null // inss
		);
		System.out.println("Gerando certificado X509v3 - ICPBrasil (pessoa 1): OK");
		return gen.generate(manager, true);
	}

	protected static ICPBravoCertificate criaCertificado2(ICPBravoCertificate root, Date from, Date to) throws Exception {
		ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(
				provider2, 
				"Pessoa 2", 
				BigInteger.valueOf(3), 
				"Pessoa 2", 
				root, 
				ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(), 
				"http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl", 
				OIWObjectIdentifiers.OID_ICPBrasilA3 + ".10",
				"http://www.certificadodigital.oaks.com.br/repositorio/dpc", 
				"OU=SRF e-CPF fake,OU=Secretaria da Receita Federal fake - SRFf", null, null, 
				from, to, 
				new SignatureAlgorithm(provider2, new SHA1(provider2), new RSA1024ECB(provider2)), 
				"pessoa2S@oaktech.com.br", 
				ICPBravoUtil.getDate(2009, 1, 1), // data de nascimento
				"12345678901", // cpf
				null, // nis
				"12345678909", // rg
				"SSPDf", // oe
				null, // tituloEleitor
				null, // zona
				null, // sessao
				null, // municipioUf
				null // inss
		);
		System.out.println("Gerando certificado X509v3 - ICPBrasil (pessoa 2): OK");
		return gen.generate(manager2, true);
	}
	
	protected static ICPBravoCertificate criaCertificado3(ICPBravoCertificate root, Date from, Date to) throws NoSuchAlgorithmException, NotSuportedAlgorithmException, IOException,
			ErroKeystoreException, InvalidKeyException, IllegalStateException, SignatureException, KeyStoreException, CertificateException, ChaveInvalidaException, SigningException,
			ErroIrrecuperavelEmChaveException, NoSuchProviderException, ManagerNotInitialedException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
		ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(provider, "Pessoa 3", BigInteger.valueOf(4), "Pessoa 3", root, ICPBravoCertificateGenerator
				.getDefaultKeyUsageForFinal(), ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(), "http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl",
				OIWObjectIdentifiers.OID_ICPBrasilA3 + ".10", "http://www.certificadodigital.oaks.com.br/repositorio/dpc", "OU=SRF e-CPF fake,OU=Secretaria da Receita Federal fake - SRFf", null,
				null, from, to, new SignatureAlgorithm(provider, new SHA1(provider), new RSA1024ECB(provider)), "pessoa2S@oaktech.com.br", ICPBravoUtil.getDate(
						2009, 1, 1), // nascimento
				"12345678901", // cpf
				null, // nis
				"12345678909", // rg
				"SSPDf", // oe
				null, // tituloEleitor
				null, // zona
				null, // sessao
				null, // municipioUf
				null // inss
		);
		System.out.println("Gerando certificado X509v3 - ICPBrasil (pessoa 3): OK");
		return gen.generate(manager, true);
	}
	
	protected static ICPBravoCertificate criaCertificado4(ICPBravoCertificate root, Date from, Date to) throws NoSuchAlgorithmException, NotSuportedAlgorithmException, IOException,
			ErroKeystoreException, InvalidKeyException, IllegalStateException, SignatureException, KeyStoreException, CertificateException, ChaveInvalidaException, SigningException,
			ErroIrrecuperavelEmChaveException, NoSuchProviderException, ManagerNotInitialedException, InvalidAlgorithmParameterException, InvalidParameterSpecException {
		ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(provider, "Pessoa 4", BigInteger.valueOf(5), "Pessoa 4", root, ICPBravoCertificateGenerator
				.getDefaultKeyUsageForFinal(), ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(), "http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl",
				OIWObjectIdentifiers.OID_ICPBrasilA3 + ".10", "http://www.certificadodigital.oaks.com.br/repositorio/dpc", "OU=SRF e-CPF fake,OU=Secretaria da Receita Federal fake - SRFf", null,
				null, from, to, new SignatureAlgorithm(provider, new SHA1(provider), new RSA1024ECB(provider)), "pessoa2S@oaktech.com.br", ICPBravoUtil.getDate(
						2009, 1, 1), // nascimento
				"12345678901", // cpf
				null, // nis
				"12345678909", // rg
				"SSPDf", // oe
				null, // tituloEleitor
				null, // zona
				null, // sessao
				null, // municipioUf
				null // inss
		);
		System.out.println("Gerando certificado X509v3 - ICPBrasil (pessoa 3): OK");
		return gen.generate(manager, true);
	}

	/**
	 * O teste a seguir demonstrará uma característica de assinatura de PDF.
	 * Neste caso, será gerado um pdf assinado na saída que é resultado da assinatura do pdf de entrada.
	 * 
	 * <pre class="brush: java;">
	public static void testaAssinaturaDePDF() {
		try {
			cron.zera();
			
			// Assinando um PDF
			System.out.println("Testando assinatura de PDF");
			PDFSignedData sd = new PDFSignedData(manager, "c:/temp/in.pdf");
			sd.addSigner(cert1, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(cert1.getManager().getProvider())), "ICPBravo", new Date(), "ICPBravo Test 1", "Brasil");
	        sd.signToFile("c:/temp/out.pdf"); 

	        // Abrindo o PDF assinado, verificando e adicionando uma nova assinatura
	        PDFSignedData sd2 = new PDFSignedData(manager, "c:/temp/out.pdf");
	        sd2.verify();
			sd2.addSigner(cert2, new SignatureAlgorithm(cert2.getManager().getProvider(), new SHA1(cert2.getManager().getProvider()), new RSA(cert2.getManager().getProvider())), "ICPBravo", new Date(), "ICPBravo Test Contra-Assinatura", "Brasil");
	        sd2.signToFile("c:/temp/out2.pdf"); 

	        // Abrindo o novo PDF assinado e verificando suas assinaturas
	        PDFSignedData sd3 = new PDFSignedData(manager, "c:/temp/out2.pdf");
	        sd3.verify();
			System.out.println("SignedData: OK");
			List&lt;CMSSignerInformation&gt; signers = sd3.getSigners();
			for (CMSSignerInformation signerInformation : signers) {
				System.out.println("Signer: " + signerInformation.getCertificate().getName());
				Pkcs9 pkcs9SignedAttributes = signerInformation.getSignedAttributes();
				List&lt;SignatureAttribute&gt; signedAttributes = pkcs9SignedAttributes.getAttributes();
				for (Attribute attribute : signedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
				Pkcs9 pkcs9UnsignedAttributes = signerInformation.getUnsignedAttributes();
				List&lt;SignatureAttribute&gt; unsignedAttributes = pkcs9UnsignedAttributes.getAttributes();
				for (Attribute attribute : unsignedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
				
				PdfSignerInformation pdfSignerInformation = sd3.getPdfSignerInformation(signerInformation.getCertificate());
				System.out.println("PDF Date: "+pdfSignerInformation.getDate().toString());
				System.out.println("PDF Reason: "+pdfSignerInformation.getReason());
				System.out.println("PDF Location: "+pdfSignerInformation.getLocation());
				System.out.println("PDF SignName: "+pdfSignerInformation.getSignName());
			}
	        
			cron.marcaTotal("Fim de teste de assinatura de PDF");
		} catch (Exception e) {
			System.out.println("SignedData: Falha " + e.getMessage());
		}
	}
	 * </pre>
	 */
	public static void testaAssinaturaDePDF() {
		try {
			cron.zera();
			
			// Assinando um PDF
			System.out.println("Testando assinatura de PDF");
			PDFSignedData sd = new PDFSignedData("c:/temp/in.pdf");
			sd.addSigner(cert1, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(cert1.getManager().getProvider())), "ICPBravo", new Date(), "ICPBravo Test 1", "Brasil");
	        sd.signToFile("c:/temp/out.pdf"); 

	        // Abrindo o PDF assinado, verificando e adicionando uma nova assinatura
	        PDFSignedData sd2 = new PDFSignedData("c:/temp/out.pdf");
	        sd2.verify();
			sd2.addSigner(cert2, new SignatureAlgorithm(cert2.getManager().getProvider(), new SHA1(cert2.getManager().getProvider()), new RSA(cert2.getManager().getProvider())), "ICPBravo", new Date(), "ICPBravo Test Contra-Assinatura", "Brasil");
	        sd2.signToFile("c:/temp/out2.pdf"); 

	        // Abrindo o novo PDF assinado e verificando suas assinaturas
	        PDFSignedData sd3 = new PDFSignedData("c:/temp/out2.pdf");
	        sd3.verify();
			System.out.println("SignedData: OK");
			List<CMSSignerInformation> signers = sd3.getSigners();
			for (CMSSignerInformation signerInformation : signers) {
				System.out.println("Signer: " + signerInformation.getCertificate().getName());
				Pkcs9 pkcs9SignedAttributes = signerInformation.getSignedAttributes();
				List<SignatureAttribute> signedAttributes = pkcs9SignedAttributes.getAttributes();
				for (SignatureAttribute attribute : signedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
				Pkcs9 pkcs9UnsignedAttributes = signerInformation.getUnsignedAttributes();
				List<SignatureAttribute> unsignedAttributes = pkcs9UnsignedAttributes.getAttributes();
				for (SignatureAttribute attribute : unsignedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
				
				PdfSignerInformation pdfSignerInformation = sd3.getPdfSignerInformation(signerInformation.getCertificate());
				System.out.println("PDF Date: "+pdfSignerInformation.getDate().toString());
				System.out.println("PDF Reason: "+pdfSignerInformation.getReason());
				System.out.println("PDF Location: "+pdfSignerInformation.getLocation());
				System.out.println("PDF SignName: "+pdfSignerInformation.getSignName());
			}
	        
			cron.marcaTotal("Fim de teste de assinatura de PDF");
		} catch (Exception e) {
			System.out.println("SignedData: Falha " + e.getMessage());
		}
	}

	/**
	 * O teste a seguir demonstrará um exemplo de CMS da API chamado de
	 * SignedData.
	 * <p>
	 * Neste teste, será criado um pacote CMS (PKCS#7) com uma assinatura, este
	 * envelope será codificado em um array de bytes, e em seguida será
	 * remontado, tendo sua assinatura verificada e seu conteúdo (PKCS#9)
	 * apresentado.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	public static void testaSignedData() {
		byte[] bts = "teste".getBytes();

		try {
			cron.zera();
			System.out.println("Testando geração de signedData");

			Policy policy = new CMSPolicy();
			policy.setTSTUrl("http://tsp.iaik.at/tsp/TspRequest");
			SignedAttribute [] singedAttrs = new SignedAttribute [] {new CommitmentTypeIndicationAttribute(CommitmentTypeIndicationAttribute.proofOfCreation)};
			policy.setSignedAttributes(singedAttrs);
			
			CMSSignedData sd = new CMSSignedData(policy);
			sd.setData(bts);
			sd.setEncapsulate(true); // Attachment
			sd.addSigner(cert1, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(cert1.getManager().getProvider())));
			byte[] sdPkcs7CMS = sd.getASN1Encoded();
			
			sd = new CMSSignedData(sdPkcs7CMS);
			sd.verify();
			
			System.out.println("SignedData: OK");

			List&lt;CMSSignerInformation&gt; signers = sd.getSigners();
			for (CMSSignerInformation signerInformation : signers) {
				System.out.println("Signer: " + signerInformation.getCertificate().getName());
				Pkcs9 pkcs9SignedAttributes = signerInformation.getSignedAttributes();
				List&lt;SignatureAttribute&gt; signedAttributes = pkcs9SignedAttributes.getAttributes();
				for (Attribute attribute : signedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
				Pkcs9 pkcs9UnsignedAttributes = signerInformation.getUnsignedAttributes();
				List&lt;SignatureAttribute&gt; unsignedAttributes = pkcs9UnsignedAttributes.getAttributes();
				for (Attribute attribute : unsignedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
			}
			
			// Variações das assinaturas ....
			// Co-assinatura. Trata-se de uma forma de adicionar um signatário a uma assinatura pré-existente, independente da ordem da assinatura
			CMSSignedData coAssinatura = new CMSSignedData(sdPkcs7CMS); // Constroi o pkcs#7 a partir de uma assinatura existente
			coAssinatura.addSigner(cert2); // Adiciona um signatário ao pacote
			byte[] sdCoAssinatura = coAssinatura.getASN1Encoded(); // Gera a nova assinatura
			
			// Contra-assinatura. Trata-se de uma forma de assinatura onde um signatário assina um conteúdo assinado. A ordem das assinaturas são relevantes.
			CMSSignedData contraAssinatura = new CMSSignedData(); // Cria um novo pacote pkcs#7
			contraAssinatura.setData(sdCoAssinatura); // Indica qual é a assinatura que está sendo contra-assinada
			contraAssinatura.addSigner(cert3); // Adiciona um signatário ao pacote
			byte[] sdContraAssinatura = contraAssinatura.getASN1Encoded(); // Gera a nova assinatura
			
			// Assinatura de lotes. Necessário para casos onde assina-se pastas, cujo seu conteúdo são documentos assinados.
			CMSSignedData multiplasAssinatura = new CMSSignedData(); // Cria um novo pacote pkcs#7
			MultipleContent contents = new MultipleContent(); // Cria um conteúdo que representa um conjunto de conteúdo
			contents.addContent(new BytesContent(sdCoAssinatura)); // Adiciona uma assinatura pre-existente ao conteúdo
			contents.addContent(new BytesContent(sdContraAssinatura));
			contents.addContent(new BytesContent("Conteúdo".getBytes())); // Pode-se adicionar conteúdos que não são assinados
			multiplasAssinatura.setData(contents);
			multiplasAssinatura.addSigner(cert4); // Adiciona um signatário ao pacote
			byte[] sdMultiplasAssinatura = multiplasAssinatura.getASN1Encoded(); // Gera a nova assinatura
			
			// recupera os documentos de dentro da assinatura
			multiplasAssinatura = new CMSSignedData(sdMultiplasAssinatura);
			CMSContentCollection info = (CMSContentCollection) multiplasAssinatura.getDataAsContentInfo(); 
			for (int a=0; a&lt;info.size(); a++) {
				CMSContentInfo ct = info.getCMSContentInfo(a);
				System.out.println(ct.getClass().toString());
			}
			
			cron.marcaTotal("Fim de teste de signedData");
		} catch (Exception e) {
			System.out.println("SignedData: Falha " + e.getMessage());
		}
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando geração de signedData
SignedData: OK
Signer: Pessoa 1:12345678901
MessageDigest: Lm+bDViFtgEPkWd4dEVhf1U6c18=
ContentType: 1.2.840.113549.1.7.1
CommitmentTypeIndication: proofOfCreation, [1.2]
SigningTime: Fri Aug 28 15:25:05 BRT 2009
TimeStampToken: Fri Aug 28 15:25:07 BRT 2009, Signatário CN=IAIK-TSP DEMO Service, OU=Institute for Applied Information Processing and Communications, O=GRAZ UNIVERSITY OF TECHNOLOGY, L=Graz, ST=Styria, C=AT OK
class br.com.oaks.ICPBravo.cms.CMSSignedData
class br.com.oaks.ICPBravo.cms.CMSSignedData
class br.com.oaks.ICPBravo.cms.CMSData
Fim de teste de signedData (564 ms)
	 * </pre>
	 */
	public static void testaSignedData() {
		byte[] bts = "teste".getBytes();

		try {
			cron.zera();
			System.out.println("Testando geração de signedData");

			Policy policy = new CMSPolicy();
			policy.setTSTUrl("http://tsp.iaik.at/tsp/TspRequest");
			SignedAttribute [] singedAttrs = new SignedAttribute [] {new CommitmentTypeIndicationAttribute(CommitmentTypeIndicationAttribute.proofOfCreation)};
			policy.setSignedAttributes(singedAttrs);
			
			CMSSignedData sd = new CMSSignedData(policy);
			sd.setData(bts);
			sd.setEncapsulate(true); // Attachment
			sd.addSigner(cert1, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(cert1.getManager().getProvider())));
			byte[] sdPkcs7CMS = sd.getASN1Encoded();
			
			sd = new CMSSignedData(sdPkcs7CMS);
			sd.verify();
			
			System.out.println("SignedData: OK");

			List<CMSSignerInformation> signers = sd.getSigners();
			for (CMSSignerInformation signerInformation : signers) {
				System.out.println("Signer: " + signerInformation.getCertificate().getName());
				Pkcs9 pkcs9SignedAttributes = signerInformation.getSignedAttributes();
				List<SignatureAttribute> signedAttributes = pkcs9SignedAttributes.getAttributes();
				for (SignatureAttribute attribute : signedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
				Pkcs9 pkcs9UnsignedAttributes = signerInformation.getUnsignedAttributes();
				List<SignatureAttribute> unsignedAttributes = pkcs9UnsignedAttributes.getAttributes();
				for (SignatureAttribute attribute : unsignedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
			}
			
			// Variações das assinaturas ....
			// Co-assinatura. Trata-se de uma forma de adicionar um signatário a uma assinatura pré-existente, independente da ordem da assinatura
			CMSSignedData coAssinatura = new CMSSignedData(sdPkcs7CMS); // Constroi o pkcs#7 a partir de uma assinatura existente
			coAssinatura.addSigner(cert2); // Adiciona um signatário ao pacote
			byte[] sdCoAssinatura = coAssinatura.getASN1Encoded(); // Gera a nova assinatura
			
			// Contra-assinatura. Trata-se de uma forma de assinatura onde um signatário assina um conteúdo assinado. A ordem das assinaturas são relevantes.
			CMSSignedData contraAssinatura = new CMSSignedData(); // Cria um novo pacote pkcs#7
			contraAssinatura.setData(sdCoAssinatura); // Indica qual é a assinatura que está sendo contra-assinada
			contraAssinatura.addSigner(cert3); // Adiciona um signatário ao pacote
			byte[] sdContraAssinatura = contraAssinatura.getASN1Encoded(); // Gera a nova assinatura
			
			// Assinatura de lotes. Necessário para casos onde assina-se pastas, cujo seu conteúdo são documentos assinados.
			CMSSignedData multiplasAssinatura = new CMSSignedData(); // Cria um novo pacote pkcs#7
			MultipleContent contents = new MultipleContent(); // Cria um conteúdo que representa um conjunto de conteúdo
			contents.addContent(new BytesContent(sdCoAssinatura)); // Adiciona uma assinatura pre-existente ao conteúdo
			contents.addContent(new BytesContent(sdContraAssinatura));
			contents.addContent(new BytesContent("Conteúdo".getBytes())); // Pode-se adicionar conteúdos que não são assinados
			multiplasAssinatura.setData(contents);
			multiplasAssinatura.addSigner(cert4); // Adiciona um signatário ao pacote
			byte[] sdMultiplasAssinatura = multiplasAssinatura.getASN1Encoded(); // Gera a nova assinatura
			
			// recupera os documentos de dentro da assinatura
			multiplasAssinatura = new CMSSignedData(sdMultiplasAssinatura);
			CMSContentCollection info = (CMSContentCollection) multiplasAssinatura.getDataAsContentInfo(); 
			for (int a=0; a<info.size(); a++) {
				CMSContentInfo ct = info.getCMSContentInfo(a);
				System.out.println(ct.getClass().toString());
			}
			
			cron.marcaTotal("Fim de teste de signedData");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SignedData: Falha " + e.getMessage());
		}
	}

	/**
	 * O teste a seguir demonstrará um exemplo de CMS da API chamado de
	 * SignedData.
	 * <p>
	 * Neste teste, será criado um pacote CMS (PKCS#7) com uma assinatura, este
	 * envelope será codificado em um array de bytes, e em seguida será
	 * remontado, tendo sua assinatura verificada e seu conteúdo (PKCS#9)
	 * apresentado.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	public static void testaXmlDSig() {
		byte[] bts = "teste".getBytes();

		try {
			cron.zera();
			System.out.println("Testando geração de XMLDSig");

			Policy policy = new CMSPolicy();
			policy.setTSTUrl("http://tsp.iaik.at/tsp/TspRequest");
			SignedAttribute [] singedAttrs = new SignedAttribute [] {new CommitmentTypeIndicationAttribute(CommitmentTypeIndicationAttribute.proofOfCreation)};
			policy.setSignedAttributes(singedAttrs);
			
			CMSSignedData sd = new CMSSignedData(policy);
			sd.setData(bts);
			sd.setEncapsulate(true);
			sd.addSigner(cert1, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(cert1.getManager().getProvider())));
			
			String xml = sd.getXMLEncoded();
			
			sd = new CMSSignedData(new XMLDSigContent(xml));
			sd.verify();
			
			System.out.println("XMLDSig: OK");

			List&lt;CMSSignerInformation&gt; signers = sd.getSigners();
			for (CMSSignerInformation signerInformation : signers) {
				System.out.println("Signer: " + signerInformation.getCertificate().getName());
				Pkcs9 pkcs9SignedAttributes = signerInformation.getSignedAttributes();
				List&lt;SignatureAttribute&gt; signedAttributes = pkcs9SignedAttributes.getAttributes();
				for (SignatureAttribute attribute : signedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
				Pkcs9 pkcs9UnsignedAttributes = signerInformation.getUnsignedAttributes();
				List&lt;SignatureAttribute&gt; unsignedAttributes = pkcs9UnsignedAttributes.getAttributes();
				for (SignatureAttribute attribute : unsignedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
			}
			
			cron.marcaTotal("Fim de teste de XMLDSig");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("XMLDSig: Falha " + e.getMessage());
		}
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
	 * </pre>
	 */
	public static void testaXmlDSig() {

		try {
			cron.zera();
			System.out.println("Testando geração de XMLDSig");

			Policy policy = new CMSPolicy();
			policy.setTSTUrl("http://tsp.iaik.at/tsp/TspRequest");
			SignedAttribute [] singedAttrs = new SignedAttribute [] {new CommitmentTypeIndicationAttribute(CommitmentTypeIndicationAttribute.proofOfCreation)};
			policy.setSignedAttributes(singedAttrs);
			
			CMSSignedData sd = new CMSSignedData(policy);
			sd.setData(new XMLContent("<MY_XML>teste</MY_XML>"));
			sd.setEncapsulate(true);
			sd.addSigner(cert1, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(cert1.getManager().getProvider())));
			
			String xml = sd.getXMLEncoded();
			System.out.println(xml);
			
			sd = new CMSSignedData(new XMLDSigContent(xml));
			sd.verify();
			
			System.out.println("XMLDSig: OK");

			List<CMSSignerInformation> signers = sd.getSigners();
			for (CMSSignerInformation signerInformation : signers) {
				System.out.println("Signer: " + signerInformation.getCertificate().getName());
				Pkcs9 pkcs9SignedAttributes = signerInformation.getSignedAttributes();
				List<SignatureAttribute> signedAttributes = pkcs9SignedAttributes.getAttributes();
				for (SignatureAttribute attribute : signedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
				Pkcs9 pkcs9UnsignedAttributes = signerInformation.getUnsignedAttributes();
				List<SignatureAttribute> unsignedAttributes = pkcs9UnsignedAttributes.getAttributes();
				for (SignatureAttribute attribute : unsignedAttributes) {
					System.out.println(attribute.description() + ": " + attribute.toString());
				}
			}
			
			cron.marcaTotal("Fim de teste de XMLDSig");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("XMLDSig: Falha " + e.getMessage());
		}
	}
	
	/**
	 * O teste a seguir demonstrará um exemplo de CMS da API chamado de
	 * EnvelopedData.
	 * <p>
	 * Neste teste será criado um envelopedData com um conteúdo criptogrado,
	 * endereçado a um certificados único. Assim como no teste de assinatura, o
	 * envelope é codificado e decodifcado e em seguida o conteúdo será
	 * decriptografado com a chave privada do certificado.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	 * public static void testaEnvelopedData() {
	 * 	byte[] bts = &quot;teste&quot;.getBytes();
	 * 	try {
	 * 		cron.zera();
	 * 		System.out.println(&quot;Testando geração de envelopedData&quot;);
	 * 
	 * 		CMSEnvelopedData sd = new CMSEnvelopedData(provider, new AES128CBC(provider));
	 * 		sd.setData(bts);
	 * 		sd.addKeyTransRecipient(cert1);
	 * 		byte[] sdPkcs7CMS = sd.getASN1Encoded();
	 * 
	 * 		sd = new CMSEnvelopedData(provider, sdPkcs7CMS);
	 * 		if (Arrays.equals(sd.getRecipient(cert1, cert1.getPrivateKey()), bts))
	 * 			System.out.println(&quot;EnvelopedData: OK&quot;);
	 * 		else
	 * 			System.out.println(&quot;EnvelopedData: Falha&quot;);
	 * 		cron.marcaTotal(&quot;Fim de teste de envelopedData&quot;);
	 * 	} catch (Exception e) {
	 * 		System.out.println(&quot;EnvelopedData: Falha &quot; + e.getMessage());
	 * 	}
	 * }
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando geraþÒo de envelopedData
&lt;?xml version="1.0" encoding="ISO-8859-1" standalone="no"?&gt;
&lt;EnvelopedData&gt;
    &lt;EncryptedData xmlns="http://www.w3.org/2001/04/xmlenc#" Type="http://www.w3.org/2001/04/xmlenc#Element"&gt;
        &lt;EncryptionMethod/&gt;
        &lt;ds:KeyInfo xmlns:ds="http://www.w3.org/2000/09/xmldsig#"&gt;
&lt;ds:KeyValue&gt;
&lt;ds:RSAKeyValue&gt;
&lt;ds:Modulus&gt;
hpZHIfrR0nVBIg4A0kLlP8VDcjGBcyKC1XpL6y3VaPTZt8zhqtPTaaxRBlACmOvkwdZbSxZUrRdo
8LvISwhZ921jlmoNrSIDqCbhmv1jgLlmiiBE9EoKZeTFJrtE+11jyIKR6U0PXJ8hi/UvfshTjy4I
L39jM+fV4/vqaK09VaU=
&lt;/ds:Modulus&gt;
&lt;ds:Exponent&gt;AQAB&lt;/ds:Exponent&gt;
&lt;/ds:RSAKeyValue&gt;
&lt;/ds:KeyValue&gt;
&lt;/ds:KeyInfo&gt;
        &lt;CipherData&gt;
            &lt;CipherValue&gt;ow1zXHhMTUrGcbiC4WJ0kA==&lt;/CipherValue&gt;
            &lt;CipherReference/&gt;
        &lt;/CipherData&gt;
        &lt;EncryptionProperties/&gt;
    &lt;/EncryptedData&gt;
&lt;/EnvelopedData&gt;

EnvelopedData: OK
Fim de teste de envelopedData (2523 ms)
	 * </pre>
	 */
	public static void testaEnvelopedData() {
		byte[] bts = "teste".getBytes();
		try {
			cron.zera();
			System.out.println("Testando geração de envelopedData");

			CMSEnvelopedData sd = new CMSEnvelopedData(provider, new AES128CBC(provider));
			sd.setData(bts);
			
			ICPBravoManager mgr;

			mgr = new PKCS11Manager();
			mgr.reload();
			Vector<ICPBravoCertificate> certs = mgr.getCertificates();
			for (ICPBravoCertificate cert1 : certs) {
				sd.addKeyTransRecipient(cert1);
			}
			
//			sd.addKeyTransRecipient(cert1);
			byte[] sdPkcs7CMS = sd.getASN1Encoded();
			System.out.println(sd.getXMLEncoded());

			sd = new CMSEnvelopedData(provider, sdPkcs7CMS);
			if (Arrays.equals(sd.getRecipient(cert1, cert1.getPrivateKey()), bts))
				System.out.println("EnvelopedData: OK");
			else
				System.out.println("EnvelopedData: Falha");
			cron.marcaTotal("Fim de teste de envelopedData");
		} catch (Exception e) {
			System.out.println("EnvelopedData: Falha " + e.getMessage());
		}
	}

	/**
	 * O teste a seguir demonstrará um exemplo de CMS da API chamado de
	 * SignedAndEnvelopedData.
	 * <p>
	 * Neste teste, será apresentado um envelope com conteúdo criptografado e
	 * assinado.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	 * public static void testaSignedAndEnvelopedData() {
	 * 	byte[] bts = &quot;teste&quot;.getBytes();
	 * 	try {
	 * 		cron.zera();
	 * 		System.out.println(&quot;Testando geração de signedAndEnvelopedData&quot;);
	 * 
	 * 		List&lt;ICPBravoCertificate&gt; destinatarios = new ArrayList&lt;ICPBravoCertificate&gt;();
	 * 		destinatarios.add(cert2);
	 * 
	 * 		SignedAndEnveloped sae = new SignedAndEnveloped(manager, cert1, destinatarios, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(cert1
	 * 				.getManager().getProvider())), new AES128CBC(provider), SignedAndEnveloped.SIGN_AND_ENVELOPED, new BytesContent(bts));
	 * 
	 * 		String saeBase64 = new BytesContent(sae.getASN1Encoded()).getBase64Encoded();
	 * 
	 * 		sae = new SignedAndEnveloped(manager, new Base64Content(saeBase64), cert2, cert2.getPrivateKey());
	 * 
	 * 		if (Arrays.equals(sae.getDataBytes(), bts))
	 * 			System.out.println(&quot;SignedAndEnvelopedData: OK&quot;);
	 * 		else
	 * 			System.out.println(&quot;SignedAndEnvelopedData: Falha&quot;);
	 * 		cron.marcaTotal(&quot;Fim de teste de signedAndEnvelopedData&quot;);
	 * 	} catch (Exception e) {
	 * 		System.out.println(&quot;SignedAndEnvelopedData: Falha &quot; + e.getMessage());
	 * 	}
	 * }
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando geração de signedAndEnvelopedData
SignedAndEnvelopedData: OK
Fim de teste de signedAndEnvelopedData (98 ms)
	 * </pre>
	 */
	public static void testaSignedAndEnvelopedData() {
		byte[] bts = "teste".getBytes();
		try {
			cron.zera();
			System.out.println("Testando geração de signedAndEnvelopedData");

			List<ICPBravoCertificate> destinatarios = new ArrayList<ICPBravoCertificate>();
			destinatarios.add(cert2);

			SignedAndEnveloped sae = new SignedAndEnveloped(cert1, destinatarios, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(
					cert1.getManager().getProvider())), new AES128CBC(provider), SignedAndEnveloped.SIGN_AND_ENVELOPED, new BytesContent(bts));

			String saeBase64 = new BytesContent(sae.getASN1Encoded()).getBase64Encoded();

			sae = new SignedAndEnveloped(new Base64Content(saeBase64), cert2, cert2.getPrivateKey());

			if (Arrays.equals(sae.getDataBytes(), bts))
				System.out.println("SignedAndEnvelopedData: OK");
			else
				System.out.println("SignedAndEnvelopedData: Falha");
			cron.marcaTotal("Fim de teste de signedAndEnvelopedData");
		} catch (Exception e) {
			System.out.println("SignedAndEnvelopedData: Falha " + e.getMessage());
		}
	}

	/**
	 * O teste a seguir demonstrará alguns exemplos de CMS da API, como
	 * SignedData, EnvelopedData e o SignedAndEnvelopedData.
	 * <p>
	 * No primeiro teste, será criado um pacote CMS (PKCS#7) com uma assinatura,
	 * este envelope será codificado em um array de bytes, e em seguida será
	 * remontado, tendo sua assinatura verificada e seu conteúdo (PKCS#9)
	 * apresentado.
	 * </p>
	 * <p>
	 * No segundo teste será criado um envelopedData com um conteúdo criptogrado,
	 * endereçado a um certificados único. Assim como no teste de assinatura, o
	 * envelope é codificado e decodifcado e em seguida o conteúdo será
	 * decriptografado com a chave privada do certificado.
	 * </p>
	 * <p>
	 * No último teste, será apresentado um envelope com conteúdo criptografado e
	 * assinado.
	 * </p>
	 */
	public static void testaEnvelopesDigitais() {
		System.out.println("testaXmlDSig() - ######################################################################");
		testaXmlDSig();
		System.out.println("testaSignedData() - ######################################################################");
		testaSignedData();
		System.out.println("testaEnvelopedData() - ######################################################################");
		testaEnvelopedData();
		System.out.println("testaSignedAndEnvelopedData() - ######################################################################");
		testaSignedAndEnvelopedData();
	}

	/**
	 * Demonstra o uso da criptografia baseada em senhas.
	 * 
	 * <pre class="brush: java;">
	public static void testaPBE() {
		cron.zera();
		System.out.println("Testando geração de PBE");
		try {
			PBEAlgorithm pbe = new PBE_Sha1AndTripleDES112(provider);
			char[] password = "password".toCharArray();
			
			// Gera uma chave a partir de uma senha
			Key sKey = pbe.generateKey(password);

			byte[] input = "teste".getBytes();
			
			// criptograda com a chave
			byte[] out = pbe.crypt(input, sKey);
			
			// decriptografa com a chave
			byte [] dec = pbe.decrypt(out, sKey);

			if (Arrays.equals(dec, input))
				System.out.println("PBE: OK");
			else
				System.out.println("PBE: Falha");
		} catch (Exception e) {
			System.out.println("PBE: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de PBE");
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando geração de PBE
PBE: OK
Fim de teste de PBE (102 ms)
	 * </pre>
	 */
	public static void testaPBE() {
		cron.zera();
		System.out.println("Testando geração de PBE");
		try {
			PBEAlgorithm pbe = new PBE_Sha1AndTripleDES112(provider);
			char[] password = "password".toCharArray();
			
			// Gera uma chave a partir de uma senha
			Key sKey = pbe.generateKey(password);

//			String encoded = new String(sKey.getEncoded()); 
			byte[] input = "teste".getBytes();
			
			// criptograda com a chave
			byte[] out = pbe.crypt(input, sKey);
			
			// decriptografa com a chave
			byte [] dec = pbe.decrypt(out, sKey);

			if (Arrays.equals(dec, input))
				System.out.println("PBE: OK");
			else
				System.out.println("PBE: Falha");
		} catch (Exception e) {
			System.out.println("PBE: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de PBE");
	}

	/**
	 * Demonstra o uso do algoritmo ShamirSecretSharing.
	 * <p>
	 * Trata-se de um algoritmo que permite criptografar um conteúdo, onde m
	 * entre n pessoas podem decriptografar seu conteúdo.
	 * </p>
	 * <p>
	 * O teste criptografará um conteúdo para 10 pessoas, sendo que no mínimo 3
	 * poderão abrir.
	 * </p>
	 * <p>
	 * Em seguida tentará abrir com apenas 2 senhas, o que deverá resultar em um
	 * erro.
	 * <p>
	 * <p>
	 * O próximo passo será tentar abrir com 3 senhas, onde o conteúdo poderá ser
	 * verficado contra o original.
	 * </p>
	 * 
	 * <pre class="brush: java;">
	 * public static void testaShamirSecretSharing() {
	 * 	cron.zera();
	 * 	System.out.println(&quot;Testando geração de Shamir Secret Sharing&quot;);
	 * 	try {
	 * 		ShamirSecretSharing sss = new ShamirSecretSharing(SecureRandom.getInstance(&quot;SHA1PRNG&quot;));
	 * 		byte[] secret = &quot;segredo&quot;.getBytes();
	 * 		ArrayList&lt;byte[]&gt; shares = new ArrayList&lt;byte[]&gt;();
	 * 		System.out.println(&quot;Shamir Secret Sharing: testando com 10 shares e quorum de 3&quot;);
	 * 		sss.shareSecret(secret, 3, 10, shares, true);
	 * 		ArrayList&lt;byte[]&gt; knownShares = new ArrayList&lt;byte[]&gt;();
	 * 		knownShares.add(shares.get(0));
	 * 		knownShares.add(shares.get(1));
	 * 		try {
	 * 			if (Arrays.equals(sss.revealSecret(knownShares), secret))
	 * 				System.out.println(&quot;Shamir Secret Sharing: Falha - Segredo revelado quando não deveria&quot;);
	 * 			else
	 * 				System.out.println(&quot;Shamir Secret Sharing: OK - Segredo não revelado com apenas 2 shares&quot;);
	 * 		} catch (SecretRevelationException ex) {
	 * 			System.out.println(&quot;Shamir Secret Sharing: OK - Segredo não revelado com apenas 2 shares&quot;);
	 * 		}
	 * 
	 * 		knownShares.add(shares.get(2));
	 * 		try {
	 * 			if (Arrays.equals(sss.revealSecret(knownShares), secret))
	 * 				System.out.println(&quot;Shamir Secret Sharing: OK - Segredo revelado com 3 shares&quot;);
	 * 			else
	 * 				System.out.println(&quot;Shamir Secret Sharing: Falha - Segredo não revelado quando deveria&quot;);
	 * 		} catch (SecretRevelationException ex) {
	 * 			System.out.println(&quot;Shamir Secret Sharing: Falha - Segredo não revelado quando deveria&quot;);
	 * 		}
	 * 	} catch (Exception e) {
	 * 		System.out.println(&quot;Shamir Secret Sharing: Falha &quot; + e.getMessage());
	 * 	}
	 * 	cron.marcaTotal(&quot;Fim de teste de Shamir Secret Sharing&quot;);
	 * }
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando geração de Shamir Secret Sharing
Shamir Secret Sharing: testando com 10 shares e quorum de 3
Shamir Secret Sharing: OK - Segredo não revelado com apenas 2 shares
Shamir Secret Sharing: OK - Segredo revelado com 3 shares
Fim de teste de Shamir Secret Sharing (19 ms)
	 * </pre>
	 */
	public static void testaShamirSecretSharing() {
		cron.zera();
		System.out.println("Testando geração de Shamir Secret Sharing");
		try {
			ShamirSecretSharing sss = new ShamirSecretSharing(SecureRandom.getInstance("SHA1PRNG"));
			byte[] secret = "segredo".getBytes();
			ArrayList<byte[]> shares = new ArrayList<byte[]>();
			System.out.println("Shamir Secret Sharing: testando com 10 shares e quorum de 3");
			sss.shareSecret(secret, 3, 10, shares, true);
			ArrayList<byte[]> knownShares = new ArrayList<byte[]>();
			knownShares.add(shares.get(4));
			knownShares.add(shares.get(5));
			try {
				if (Arrays.equals(sss.revealSecret(knownShares), secret))
					System.out.println("Shamir Secret Sharing: Falha - Segredo revelado quando não deveria");
				else
					System.out.println("Shamir Secret Sharing: OK - Segredo não revelado com apenas 2 shares");
			} catch (SecretRevelationException ex) {
				System.out.println("Shamir Secret Sharing: OK - Segredo não revelado com apenas 2 shares");
			}

			knownShares.add(shares.get(6));
			knownShares.add(shares.get(7));
			try {
				if (Arrays.equals(sss.revealSecret(knownShares), secret))
					System.out.println("Shamir Secret Sharing: OK - Segredo revelado com 3 shares");
				else
					System.out.println("Shamir Secret Sharing: Falha - Segredo não revelado quando deveria");
			} catch (SecretRevelationException ex) {
				System.out.println("Shamir Secret Sharing: Falha - Segredo não revelado quando deveria");
			}
		} catch (Exception e) {
			System.out.println("Shamir Secret Sharing: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de Shamir Secret Sharing");
	}

	/**
	 * Demonstra a relação com os dispositivos criptográficos do tipo PKCS#11 <br>
	 * O código a seguir abrirá um PKCS#11 e mostrará todos os certificados
	 * dentro do dispositivo.
	 * 
	 * <pre class="brush: java;">
	public static void testaPKCS11() {
		cron.zera();
		System.out.println("Testando PKCS11");
		try {
			ICPBravoManager mgr;

			mgr = new PKCS11Manager();
			mgr.reload();
			Vector&lt;ICPBravoCertificate&gt; certs = mgr.getCertificates();
			for (ICPBravoCertificate bravoCertificate : certs) {
				System.out.println(bravoCertificate.getName());
			}
			System.out.println("PKCS#11: OK");
		} catch (Exception e) {
			System.out.println("PKCS#11: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de PKCS11");
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando PKCS11
UBIRATAN DE ALMEIDA ELIAS:46133011149
PKCS#11: OK
Fim de teste de PKCS11 (278 ms)
	 * </pre>
	 */
	public static void testaPKCS11() {
		cron.zera();
		System.out.println("Testando PKCS11");
		try {
			ICPBravoManager mgr;

			mgr = new PKCS11Manager();
			mgr.reload();
			Vector<ICPBravoCertificate> certs = mgr.getCertificates();
			for (ICPBravoCertificate bravoCertificate : certs) {
				System.out.println(bravoCertificate.getName());
				bravoCertificate.getCertificatePoliciesExtensions();
			}
			System.out.println("PKCS#11: OK");
		} catch (Exception e) {
			System.out.println("PKCS#11: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de PKCS11");
	}

	
	/**
	 * Demonstra a relação com os dispositivos criptográficos do tipo PKCS#12. <br>
	 * <br>O teste abaixo fará a criação de dois certificados dentro de um repositório PKCS#12 (selecionado por um dialog box) e, em seguida, abrirá o repositório e apresentará os certificados no repositório.
	 * 
	 * <pre class="brush: java;">
	public static void testaPKCS12() {
		cron.zera();
		System.out.println("Testando PKCS12");
		try {
			Date from = ICPBravoUtil.getDateNow();
			Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);
			
			ICPBravoManager mgr = new PKCS12Manager();
			ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasRoot, BigInteger.valueOf(1),
					aliasRoot, 
					null, 
					ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
					ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
					null, null, null,
					"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
					from, to, 
					new SignatureAlgorithm(mgr.getProvider(), new SHA1(mgr.getProvider()), new RSA1024ECB(mgr.getProvider())), 
					null);
			ICPBravoCertificate root = gen.generate(mgr, true);

			gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasServidor, BigInteger.valueOf(4),
					"Certificado de Servidor", root, false, ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
					ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
					null, null, null,
					"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
					from, to, 
					new SignatureAlgorithm(mgr.getProvider(), new SHA1(mgr.getProvider()), new RSA1024ECB(mgr.getProvider())), 
					null);
			gen.generate(mgr, true);
			mgr.saveTofile();
			
			mgr = new PKCS12Manager();
			mgr.reload();
			Vector&lt;ICPBravoCertificate&gt; certs = mgr.getCertificates();
			for (ICPBravoCertificate bravoCertificate : certs) {
				System.out.println(bravoCertificate.getName());
			}
			System.out.println("PKCS#12: OK");
		} catch (Exception e) {
			System.out.println("PKCS#12: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de PKCS12");
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando PKCS12
Certificado Raiz
Certificado de Servidor
PKCS#12: OK
Fim de teste de PKCS12 (25595 ms)
	 * </pre>
	 */
	public static void testaPKCS12() {
		cron.zera();
		System.out.println("Testando PKCS12");
		try {
			Date from = ICPBravoUtil.getDateNow();
			Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);
			
			ICPBravoManager mgr = new PKCS12Manager();
			ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasRoot, BigInteger.valueOf(1),
					aliasRoot, 
					null, 
					ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
					ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
					null, null, null,
					"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
					from, to, 
					new SignatureAlgorithm(mgr.getProvider(), new SHA1(mgr.getProvider()), new RSA1024ECB(mgr.getProvider())), 
					null);
			ICPBravoCertificate root = gen.generate(mgr, true);

			gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasServidor, BigInteger.valueOf(4),
					"Certificado de Servidor", root, false, ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
					ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
					null, null, null,
					"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
					from, to, 
					new SignatureAlgorithm(mgr.getProvider(), new SHA1(mgr.getProvider()), new RSA1024ECB(mgr.getProvider())), 
					null);
			gen.generate(mgr, true);
			mgr.saveTofile();
			
			mgr = new PKCS12Manager();
			mgr.reload();
			Vector<ICPBravoCertificate> certs = mgr.getCertificates();
			for (ICPBravoCertificate bravoCertificate : certs) {
				System.out.println(bravoCertificate.getName());
			}
			System.out.println("PKCS#12: OK");
		} catch (Exception e) {
			System.out.println("PKCS#12: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de PKCS12");
	}

	/**
	 * Executa um teste das capacidades de integração da API com LDAP.
	 * <p>
	 * Primeiramente, o teste grava um certificado em um diretório LDAP,
	 * depois recupera-o pelo DN do registro, e depois pelo número de série
	 * do certificado e pelo nome do emissor do certificado.
	 * <p>
	 * Depois, o teste acessa um diretório LDAP público e faz a leitura de
	 * um certificado baseado no email do titular. 
	 * 
	 * <pre class="brush: java;">
		public static void testaLDAP() {
			cron.zera();
			System.out.println("Testando LDAP");
			try {
				
				String host = "ldap://piv.cognitio.com.br:389/dc=oaktech,dc=com,dc=br";
				String name = "uid=john,ou=people";
				String credentials = "cn=admin,dc=oaktech,dc=com,dc=br";
				String password = "ldappass";
				
				List&lt;ICPBravoCertificate&gt; retrievedCerts;
				ICPBravoCertificate retrievedCert;
				
				LdapManager manager = new LdapManager(host, new LdapDNFilter(name), credentials, password);
				manager.removeCertificates(name);
				manager.addCertificate(name, cert1);
				System.out.println("Certificado armazenado no registro \"" + name + "\".");
				
				manager.reload();
				retrievedCerts = manager.getCertificates();
				Assert.assertTrue("Não foi possível recuperar o registro pelo DN", retrievedCerts != null);
				Assert.assertTrue("Mais de um certificado encontrado pelo DN", retrievedCerts.size() == 1);
				retrievedCert = retrievedCerts.get(0);
				Assert.assertTrue("O certificado recuperado não confere com o gravado", retrievedCert.equals(cert1));
				System.out.println("Certificado recuperado com sucesso por nome de registro (LDAP DN).");
				
				manager.setFilter(new LdapSerialNumberFilter(null, retrievedCert.getSerialNumber(), retrievedCert.getIssuerName()));
				manager.reload();
				retrievedCerts = manager.getCertificates();
				Assert.assertTrue("Não foi possível recuperar o registro pelo issuer name e serial number", retrievedCerts != null);
				Assert.assertTrue("Mais de um certificado foi encontrado pelo issuer name e serial number", retrievedCerts.size() == 1);
				retrievedCert = retrievedCerts.get(0);
				Assert.assertTrue("O certificado recuperado não confere com o gravado", retrievedCert.equals(cert1));
				System.out.println("Certificado recuperado pelo isser name e serial number.");
				
				manager.removeCertificate(name, cert1);
				manager.setFilter(new LdapDNFilter(name));
				manager.reload();
				retrievedCerts = manager.getCertificates();
				Assert.assertTrue("O certificado não pôde ser excluído do diretório", retrievedCerts == null || retrievedCerts.size() == 0);
				System.out.println("Certificado excluído do diretório.");
				
				host = "ldap://directory.certisign.com.br";
				String mail = "pmattos@certisign.com.br";
				manager = new LdapManager(host, new LdapEmailFilter(null, mail));
				manager.reload();
				for (ICPBravoCertificate bravoCertificate : manager.getCertificates()) {
					System.out.println("Certificado encontrado em no diretório \"" + host + "\" para o email \"" + mail + "\": " + bravoCertificate.getName());
				}
				
				System.out.println("LDAP: OK");
			} catch (Exception e) {
				System.out.println("LDAP: Falha " + e.getMessage());
			}
			cron.marcaTotal("Fim de teste de LDAP");
		}
	 * </pre>
	 */
	public static void testaLDAP() {
		cron.zera();
		System.out.println("Testando LDAP");
		try {
			
			String host = "ldap://piv.cognitio.com.br:389/dc=oaktech,dc=com,dc=br";
			String name = "uid=john,ou=people";
			String credentials = "cn=admin,dc=oaktech,dc=com,dc=br";
			String password = "ldappass";
			
			List<ICPBravoCertificate> retrievedCerts;
			ICPBravoCertificate retrievedCert;
			
			LdapManager manager = new LdapManager(host, new LdapDNFilter(name), credentials, password);
			manager.removeCertificates(name);
			manager.addCertificate(name, cert1);
			System.out.println("Certificado armazenado no registro \"" + name + "\".");
			
			manager.reload();
			retrievedCerts = manager.getCertificates();
			retrievedCert = retrievedCerts.get(0);
			System.out.println("Certificado recuperado com sucesso por nome de registro (LDAP DN).");
			
			manager.setFilter(new LdapSerialNumberFilter(null, retrievedCert.getSerialNumber(), retrievedCert.getIssuerName()));
			manager.reload();
			retrievedCerts = manager.getCertificates();
			retrievedCert = retrievedCerts.get(0);
			System.out.println("Certificado recuperado pelo isser name e serial number.");
			
			manager.removeCertificate(name, cert1);
			manager.setFilter(new LdapDNFilter(name));
			manager.reload();
			retrievedCerts = manager.getCertificates();
			System.out.println("Certificado excluído do diretório.");
			
			host = "ldap://directory.certisign.com.br";
			String mail = "pmattos@certisign.com.br";
			manager = new LdapManager(host, new LdapEmailFilter(null, mail));
			manager.reload();
			for (ICPBravoCertificate bravoCertificate : manager.getCertificates()) {
				System.out.println("Certificado encontrado em no diretório \"" + host + "\" para o email \"" + mail + "\": " + bravoCertificate.getName());
			}
			
			System.out.println("LDAP: OK");
		} catch (Exception e) {
			System.out.println("LDAP: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de LDAP");
	}

	/**
	 * Apresenta um envio de email assinado. <br>
	 * O teste a seguir criará um email com um conteúdo e adicionará uma
	 * assinatura para o conteúdo. <br>
	 * No momento em que rodar, abrirá uma janela solicitando a senha para acesso
	 * ao provedor de SMTP.
	 * 
	 * <pre class="brush: java;">
	 * private static void testaSMIME() {
	 * 	cron.zera();
	 * 	System.out.println(&quot;Testando S/MIME&quot;);
	 * 	try {
	 * 		String senhaEmail = &quot;&quot;;
	 * 		char[] senhaEmailBt = ICPBravoManager.getPIN(&quot;Informe a senha do provedor de SMTP&quot;);
	 * 		for (char c : senhaEmailBt) {
	 * 			senhaEmail += c;
	 * 		}
	 * 
	 * 		ICPBravoMailServer smtpServer = new SMTPMailServer(&quot;mail.oaktech.com.br&quot;, &quot;UbiratanElias@oak&quot;, senhaEmail);
	 * 		ICPBravoMailMessage message = new ICPBravoMailMessage(smtpServer, &quot;UbiratanElias@lanchecerto.com.br&quot;, &quot;Teste de email&quot;);
	 * 		message.setSender(&quot;UbiratanElias@oaktech.com.br&quot;);
	 * 		MimeBodyPart part = message.addMailPart(new DataSourceMailPart(new TextEncodedDataSource(&quot;ttt&quot;, &quot;Teste&quot;)));
	 * 
	 * 		message.addMailPart(new SignatureMailPart(manager, cert1, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(cert1.getManager()
	 * 				.getProvider())), part));
	 * 
	 * 		message.sendMessage();
	 * 		System.out.println(&quot;S/MIME: OK&quot;);
	 * 	} catch (Exception e) {
	 * 		System.out.println(&quot;S/MIME: Falha &quot; + e.getMessage());
	 * 	}
	 * 	cron.marcaTotal(&quot;Fim de teste de S/MIME&quot;);
	 * }
	 * </pre>
	 */
	public static void testaSMIME() {
		cron.zera();
		System.out.println("Testando S/MIME");
		try {
			String senhaEmail = "";
			char[] senhaEmailBt = ICPBravoManager.getPIN("Informe a senha do provedor de SMTP");
			for (char c : senhaEmailBt) {
				senhaEmail += c;
			}

			ICPBravoMailServer smtpServer = new SMTPMailServer("mail.oaktech.com.br", "UbiratanElias@oak", senhaEmail);
			ICPBravoMailMessage message = new ICPBravoMailMessage(smtpServer, "UbiratanElias@lanchecerto.com.br", "Teste de email");
			message.setSender("UbiratanElias@oaktech.com.br");
			MimeBodyPart part = message.addMailPart(new DataSourceMailPart(new TextEncodedDataSource("ttt", "Teste")));
			message.addMailPart(new SignatureMailPart(manager, cert1, new SignatureAlgorithm(cert1.getManager().getProvider(), new SHA1(cert1.getManager().getProvider()), new RSA(cert1.getManager().getProvider())), part));
			message.sendMessage();
			System.out.println("S/MIME: OK");
		} catch (Exception e) {
			System.out.println("S/MIME: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de S/MIME");
	}

	/**
	 * Testa o uso do algoritmo DiffieHelman para a troca de chave.
	 * 
	 * <pre class="brush: java;">
	 * public static void testaDiffieHellman() {
	 * 	try {
	 * 		cron.zera();
	 * 		byte[] bts = &quot;mensagem&quot;.getBytes();
	 * 		DiffieHellman source = new DiffieHellman(provider);
	 * 		DiffieHellman target = new DiffieHellman(provider);
	 * 		SymmetricAlgorithm sym = new AES128ECB(provider);
	 * 
	 * 		KeyPair sourceKeyPair = source.generateKeyPair();
	 * 		KeyPair targetKeyPair = target.generateKeyPair(sourceKeyPair.getPublic());
	 * 		cron.marcaParcial(&quot;DiffieHelmman: chave estabelecida dos dois lados&quot;);
	 * 
	 * 		SecretKey sourceSecretKey = source.generateSecretKey(sourceKeyPair.getPrivate(), targetKeyPair.getPublic(), sym);
	 * 		byte[] crypt = sym.crypt(bts, sourceSecretKey);
	 * 		cron.marcaParcial(&quot;DiffieHelmman: criptografado&quot;);
	 * 
	 * 		SecretKey targetSecretKey = source.generateSecretKey(targetKeyPair.getPrivate(), sourceKeyPair.getPublic(), sym);
	 * 		byte[] ret = sym.decrypt(crypt, targetSecretKey);
	 * 		cron.marcaParcial(&quot;DiffieHelmman: decriptografado&quot;);
	 * 
	 * 		if (Arrays.equals(bts, ret)) {
	 * 			cron.marcaTotal(&quot;DiffieHelmman: OK&quot;);
	 * 		} else {
	 * 			System.out.println(&quot;DiffieHelmman: Falha&quot;);
	 * 		}
	 * 	} catch (Exception ex) {
	 * 		System.out.println(&quot;testaDiffieHellman: Falha &quot; + ex.getMessage());
	 * 	}
	 * }
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
DiffieHelmman: chave estabelecida dos dois lados (28793 ms)
DiffieHelmman: criptografado (97 ms)
DiffieHelmman: decriptografado (92 ms)
DiffieHelmman: OK (28983 ms)
	 * </pre>
	 */
	public static void testaDiffieHellman() {
		try {
			cron.zera();
			byte[] bts = "mensagem".getBytes();
			DiffieHellman source = new DiffieHellman(provider);
			DiffieHellman target = new DiffieHellman(provider);
			SymmetricAlgorithm sym = new AES128ECB(provider);

			KeyPair sourceKeyPair = source.generateKeyPair();
			KeyPair targetKeyPair = target.generateKeyPair(sourceKeyPair.getPublic());
			cron.marcaParcial("DiffieHelmman: chave estabelecida dos dois lados");

			SecretKey sourceSecretKey = source.generateSecretKey(sourceKeyPair.getPrivate(), targetKeyPair.getPublic(), sym);
			byte[] crypt = sym.crypt(bts, sourceSecretKey);
			cron.marcaParcial("DiffieHelmman: criptografado");

			SecretKey targetSecretKey = source.generateSecretKey(targetKeyPair.getPrivate(), sourceKeyPair.getPublic(), sym);
			byte[] ret = sym.decrypt(crypt, targetSecretKey);
			cron.marcaParcial("DiffieHelmman: decriptografado");

			if (Arrays.equals(bts, ret)) {
				cron.marcaTotal("DiffieHelmman: OK");
			} else {
				System.out.println("DiffieHelmman: Falha");
			}
		} catch (Exception ex) {
			System.out.println("testaDiffieHellman: Falha " + ex.getMessage());
		}
	}

	/**
	 * Este exemplo cria um servidor SSL/TSL que aguarda uma conexão do cliente
	 * com certificado. O Servidor devolve os mesmos dados recebidos prefixados
	 * por uma string "Cliente Enviou : ". Para informar que a comunicação chegou
	 * ao fim o cliente deve enviar o caracter '!'.
	 * 
	 * <pre class="brush: java;">
protected static final String arqRoot = "root.jks";
protected static final String arqServer = "server.jks";
protected static final String arqCliente = "cliente.jks";

protected static char [] senhaRoot = "1234".toCharArray();
protected static char [] senhaServidor = "1234".toCharArray();
protected static char [] senhaCliente = "1234".toCharArray();

protected static final String aliasRoot = "Certificado Raiz";
protected static final String aliasServidor = "Certificado de Servidor";
protected static final String aliasCliente = "Certificado de Cliente";

public static final int sslPort = 9021;
public static final String host = "localhost";

public static void testaSSL() {
	cron.zera();
	Security.addProvider(new ICPBravoProvider());
	System.out.println("Testando SSL");
	try {
		SSLServerWithClientAuthId server = new SSLServerWithClientAuthId(sslPort,true);
		Thread t = new Thread(server);
		t.setName("TSL_Test");
		t.setDaemon(false);
		t.start();
		Thread.sleep(100);
		SSLClientImpl client = new SSLClientImpl(sslPort);
		client.doProtocol();
		Thread.sleep(100);
		if (t.isAlive())
			t.interrupt();
		Thread.sleep(100);
		System.out.println("SSL: OK");
	} catch (Exception e) {
		System.out.println("SSL: Falha " + e.getMessage());
		e.printStackTrace();
	}
	cron.marcaTotal("Fim de teste de SSL");
}
	
public static class SSLServerWithClientAuthId extends SSLServer {
	public SSLServerWithClientAuthId(int port, boolean needClientAuth) throws KeyStoreException, NoSuchAlgorithmException {
		super(port, needClientAuth, new JKSManager(arqServer, senhaServidor), new JKSManager(arqRoot, senhaRoot), SSLContext.getInstance("TLS", provider), senhaServidor);
	}

	protected void doProtocol(Socket sSock) throws IOException {
		System.out.println("session started.");
		InputStream in = sSock.getInputStream();
		OutputStream out = sSock.getOutputStream();
		out.write("Cliente Enviou : ".getBytes());
		int ch = 0;
		while ((ch = in.read()) != '!') {
			out.write(ch);
		}

		out.write('!');

		sSock.close();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		System.out.println("session closed.");
	}
}

public static class SSLClientImpl extends SSLClient {
	public SSLClientImpl(int port) throws Exception {
		super(host, port, new JKSManager(arqCliente, senhaCliente), new JKSManager(arqRoot, senhaRoot), SSLContext.getInstance("TLS", provider), senhaCliente);
	}

	protected void doProtocol(Socket sSock) throws IOException {
		OutputStream out = sSock.getOutputStream();
		InputStream in = sSock.getInputStream();

		out.write("Dados Cliente".getBytes());
		out.write('!');

		int ch = 0;
		while ((ch = in.read()) != '!') {
			System.out.print((char) ch);
		}
		System.out.println((char) ch);
	}
}
	 * </pre>
	 */
	public static void testaSSL() {
		cron.zera();
		Security.addProvider(new ICPBravoProvider());
		System.out.println("Testando SSL");
		try {
			SSLServerWithClientAuthId server = new SSLServerWithClientAuthId(sslPort,true);
			Thread t = new Thread(server);
			t.setName("TSL_Test");
			t.setDaemon(false);
			t.start();
			Thread.sleep(100);
			SSLClientImpl client = new SSLClientImpl(sslPort);
			client.doProtocol();
			Thread.sleep(100);
			if (t.isAlive())
				t.interrupt();
			Thread.sleep(100);
			System.out.println("SSL: OK");
		} catch (Exception e) {
			System.out.println("SSL: Falha " + e.getMessage());
			e.printStackTrace();
		}
		cron.marcaTotal("Fim de teste de SSL");
	}
	
	protected static final String arqRoot = "root.jks";
	protected static final String arqServer = "server.jks";
	protected static final String arqCliente = "cliente.jks";
	
	protected static char [] senhaRoot = "1234".toCharArray();
	protected static char [] senhaServidor = "1234".toCharArray();
	protected static char [] senhaCliente = "1234".toCharArray();

	protected static final String aliasRoot = "Certificado Raiz";
	protected static final String aliasServidor = "Certificado de Servidor";
	protected static final String aliasCliente = "Certificado de Cliente";

	public static final int sslPort = 9021;
	public static final String host = "localhost";
	
	public static class SSLServerWithClientAuthId extends SSLServer {
		public SSLServerWithClientAuthId(int port, boolean needClientAuth) throws KeyStoreException, NoSuchAlgorithmException {
			super(port, needClientAuth, new JKSManager(arqServer, senhaServidor), new JKSManager(arqRoot, senhaRoot), SSLContext.getInstance("TLS", provider), senhaServidor);
		}

		protected void doProtocol(Socket sSock) throws IOException {
			System.out.println("session started.");
			InputStream in = sSock.getInputStream();
			OutputStream out = sSock.getOutputStream();
			out.write("Cliente Enviou : ".getBytes());
			int ch = 0;
			while ((ch = in.read()) != '!') {
				out.write(ch);
			}

			out.write('!');

			sSock.close();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			System.out.println("session closed.");
		}
	}

	public static class SSLClientImpl extends SSLClient {
		public SSLClientImpl(int port) throws Exception {
			super(host, port, new JKSManager(arqCliente, senhaCliente), new JKSManager(arqRoot, senhaRoot), SSLContext.getInstance("TLS", provider), senhaCliente);
		}

		protected void doProtocol(Socket sSock) throws IOException {
			OutputStream out = sSock.getOutputStream();
			InputStream in = sSock.getInputStream();

			out.write("Dados Cliente".getBytes());
			out.write('!');

			int ch = 0;
			while ((ch = in.read()) != '!') {
				System.out.print((char) ch);
			}
			System.out.println((char) ch);
		}
	}

	/**
	 * Criar certificados e keystore para o teste de SSL.
	 * 
	 * <pre class="brush: java;">
	public static void testaSSLCriaCertificados() throws Exception {
		ICPBravoManager mgr;
		ICPBravoCertificateGenerator gen;
		
		Date from = ICPBravoUtil.getDateNow();
		Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);
		
		// trust store for client
		mgr = new JKSManager(arqRoot, senhaRoot);
		gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasRoot, BigInteger.valueOf(1),
				aliasRoot, 
				null, 
				ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
				null, null, null,
				"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
				from, to, 
				new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
				null);
		ICPBravoCertificate root = gen.generate(mgr, true);
		mgr.saveTofile();

		// server credentials
		mgr = new JKSManager(arqServer, senhaServidor);
		gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasServidor, BigInteger.valueOf(4),
				"Certificado de Servidor", root, false, ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
				null, null, null,
				"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
				from, to, 
				new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
				null);
		gen.generate(mgr, true);
		mgr.saveTofile();
		
		// client credentials
		mgr = new JKSManager(arqCliente, senhaCliente);
		gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasCliente, BigInteger.valueOf(5),
				"Certificado de Cliente", root, false, ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
				null, null, null,
				"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
				from, to, 
				new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
				null);
		gen.generate(mgr, true);
		mgr.saveTofile();
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
	 * </pre>
	 * @throws Exception
	 */
	public static void testaSSLCriaCertificados() throws Exception {
		ICPBravoManager mgr;
		ICPBravoCertificateGenerator gen;
		
		Date from = ICPBravoUtil.getDateNow();
		Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);
		
		// trust store for client
		mgr = new JKSManager(arqRoot, senhaRoot);
		gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasRoot, BigInteger.valueOf(1),
				aliasRoot, 
				null, 
				ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
				null, null, null,
				"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
				from, to, 
				new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
				null);
		ICPBravoCertificate root = gen.generate(mgr, true);
		mgr.saveTofile();

		// server credentials
		mgr = new JKSManager(arqServer, senhaServidor);
		gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasServidor, BigInteger.valueOf(4),
				"Certificado de Servidor", root, false, ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
				null, null, null,
				"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
				from, to, 
				new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
				null);
		gen.generate(mgr, true);
		mgr.saveTofile();
		
		// client credentials
		mgr = new JKSManager(arqCliente, senhaCliente);
		gen = new ICPBravoCertificateGenerator(mgr.getProvider(), aliasCliente, BigInteger.valueOf(5),
				"Certificado de Cliente", root, false, ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
				null, null, null,
				"Instituto Nacional de Tecnologia da Informacao fake - ITIf,L=Brasilia,S=DF", null, null, 
				from, to, 
				new SignatureAlgorithm(manager.getProvider(), new SHA1(manager.getProvider()), new RSA1024ECB(manager.getProvider())), 
				null);
		gen.generate(mgr, true);
		mgr.saveTofile();
	}
	
	/**
	 * Demonstra o uso do algoritmo ECDH (Elliptic Curve Diffie-Hellman) para
	 * troca de chaves.
	 * <p>
	 * <pre class="brush: java;">
	public static void testaECDH() {

		try {
			DiffieHellmanEC ec = new DiffieHellmanEC(provider);
			KeyPair sourceKeyPair = ec.generateKeyPair(); 
			KeyPair targetKeyPair = ec.generateKeyPair(sourceKeyPair.getPublic());
			byte[] sourceSecret = ec.generateSecretKey(sourceKeyPair.getPrivate(), targetKeyPair.getPublic());
			byte[] targetSecret = ec.generateSecretKey(targetKeyPair.getPrivate(), sourceKeyPair.getPublic());

			if (Utils.arraysEquals(sourceSecret, targetSecret)) {
				System.out.println("ECDH KeyAgreement: OK");
			} else {
				System.out.println("ECDH KeyAgreement: Falha");
			}
		} catch (Exception e) {
			System.out.println("ECDH KeyAgreement: Falha " + e.getMessage());
		}
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
ECDH KeyAgreement: OK
	 * </pre>
	 */
	public static void testaECDH() {

		try {
			DiffieHellmanEC ec = new DiffieHellmanEC(provider);
			KeyPair sourceKeyPair = ec.generateKeyPair(); 
			KeyPair targetKeyPair = ec.generateKeyPair(sourceKeyPair.getPublic());
			byte[] sourceSecret = ec.generateSecretKey(sourceKeyPair.getPrivate(), targetKeyPair.getPublic());
			byte[] targetSecret = ec.generateSecretKey(targetKeyPair.getPrivate(), sourceKeyPair.getPublic());

			if (Arrays.equals(sourceSecret, targetSecret)) {
				System.out.println("ECDH KeyAgreement: OK");
			} else {
				System.out.println("ECDH KeyAgreement: Falha");
			}
		} catch (Exception e) {
			System.out.println("ECDH KeyAgreement: Falha " + e.getMessage());
		}
	}

	private static void testaUnsupportedPKCS11() {
		cron.zera();
		System.out.println("Testando PKCS11 unsupported");
		try {
//			ICPBravoManager mgr = new PKCS11OpenSCManager();
			ICPBravoManager mgr = new PKCS11Manager();
			Provider provider = mgr.getProvider();
			mgr.reload();
			
			byte [] content = "value".getBytes();
			
			Vector<ICPBravoCertificate> certs = mgr.getCertificates();
			for (ICPBravoCertificate bravoCertificate : certs) {
				System.out.println(bravoCertificate.getName());
				
				SignatureAlgorithm sign = new SignatureAlgorithm(provider, new SHA1(provider), new RSA(provider));
				byte [] hash = sign.sign(content, bravoCertificate);
				if (hash != null) {
					try {
						bravoCertificate.getChain();
						bravoCertificate.tryTrustedValidate();
						sign.verify(bravoCertificate.getPublicKey(), content, hash);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					System.out.println("Hash sign: "+hash.toString());
				} else
					System.out.println("Not sign");
			}
			System.out.println("PKCS#11: OK");
		} catch (Exception e) {
			System.out.println("PKCS#11: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de PKCS11 unsupported");
	}

	/**
	 * Demonstração de alteração da cadeia de confiança ao testar se um certificado é confiável.
	 * <p>
	 * <pre class="brush: java;">
	public static void changeTrustedChain() throws Exception {
		// Cria um repositório de trusteds (no caso abaixo, no arquivo /temp/trusted.jks com o respectivo pin 1234)
		JKSManager trustedChain = new JKSManager("/temp/trusted.jks", "1234".toCharArray());
		trustedChain.reload();

		// Importa um certificado .CER para dentro da cadeia.
		// Ps. esta operação precisa ser feita apenas uma única vez, pois após feita, o certificado já foi importado para o JKS
		Content certBts = new FileContent("/temp/AC OAK.cer");
		ICPBravoCertificate certOak = new ICPBravoCertificate("Raiz OAK", certBts.getContent());
		trustedChain.addCertificate("Raiz OAK", certOak);
		
		// Salva a cadeia no arquivo
		trustedChain.saveTofile();

		trustedChain.reload();
		
		// Mostrar os certificados dentro da cadeia de confiáveis
		for (ICPBravoCertificate c : trustedChain.getCertificates()) {
			System.out.println(c.getName());
		}
		
		// Mudar a cadeia de confiança default
		ICPBravoManager.setTrustedManager(trustedChain);
		
		// A partir deste ponto, todos os certificados onde for testado o método isTrusted() ou tryTrustedValidate() já farão sua verificação dentro desta nova cadeia
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
OAK Solucoes Empresariais em Informatica LTDA
	 * </pre>
	 */
	public static void changeTrustedChain() throws Exception {
		cron.zera();
		System.out.println("Testando Alteração de Cadeia de Confiança");
		try {
			// Cria um repositório de trusteds (no caso abaixo, no arquivo /temp/trusted.jks com o respectivo pin 1234)
			JKSManager trustedChain = new JKSManager("/temp/trusted.jks", "1234".toCharArray());
			trustedChain.reload();
	
			// Importa um certificado .CER para dentro da cadeia.
			// Ps. esta operação precisa ser feita apenas uma única vez, pois após feita, o certificado já foi importado para o JKS
			Content certBts = new FileContent("/temp/AC OAK.cer");
			ICPBravoCertificate certOak = new ICPBravoCertificate("Raiz OAK", certBts.getContent());
			trustedChain.addCertificate("Raiz OAK", certOak);
			
			// Salva a cadeia no arquivo
			trustedChain.saveTofile();
	
			trustedChain.reload();
			
			// Mostrar os certificados dentro da cadeia de confiáveis
			for (ICPBravoCertificate c : trustedChain.getCertificates()) {
				System.out.println(c.getName());
			}
			
			// Mudar a cadeia de confiança default
			ICPBravoManager.setTrustedManager(trustedChain);
			
			// A partir deste ponto, todos os certificados onde for testado o método isTrusted() ou tryTrustedValidate() já farão sua verificação dentro desta nova cadeia
			System.out.println("Alteração de Cadeia de Confiança: OK");
		} catch (Exception e) {
			System.out.println("Alteração de Cadeia de Confiança: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de alteração de Cadeia de Confiança");
	}

	/**
	 * O seguinte exemplo busca orientar a construção do código de servidor para a validação de uma assinatura em ambiente
	 * de autenticação de usuário com certificado digital.
	 * 
	 * O trecho de código para realizar tal operação é:
	 * <pre class="brush: java;">
	public static String autenticaUsuario(String challenge, String pkcs7) throws Exception {
		// Reabre o pacote de assinatura
		CMSSignedData sign = new CMSSignedData(new Base64Content(ICPBravoUtil.readFromUrlAndBase64(pkcs7)));
		
		// Verifica se as assinaturas correspondem ao conteúdo assinado
		sign.verify();
		
		// Recupera o conteúdo assinado
		String signChallenge = sign.getData().getString(); // Aqui teremos o conteúdo que foi assinado

		// Se o conteúdo assinado corresponde ao que se desejava que fosse assinado
		if (challenge.equals(signChallenge)) { // É sinal que o nosso desafio foi respondido
			// Recupera a primeira informação de assinatura. Neste ponto fazemos um
			// pré-julgamento que o nosso pacote de resposta contém como primeira assinatura
			// a assinatura que nos interessa, coisa que em ambiente de challenge-response pode ser
			// considerado como uma afirmação verdadeira.
			CMSSignerInformation signatario = sign.getSigners().get(0);
			
			// Recupera o certificado do signatário
			ICPBravoCertificate certificadoSignatario = signatario.getCertificate();

			// Recupera nossa lista de certificados confiáveis (vide changeTrustedChain()) 
			JKSManager trustedChain = new JKSManager("/temp/trusted.jks", "1234".toCharArray());
			trustedChain.reload();

			try {
				// Verifica se o certificado é confiável em nossa cadeia
				certificadoSignatario.tryTrustedValidate(trustedChain);

				// Em alguns casos pode ser interessante fazer alguns testes no certificado do usuário,
				// como se está ou não válido ou revogado, ou outro dentre os inúmeros testes de certificado.
				// (vide testes de certificado em mostraInformacoesCertificado())

				// Se chegou até aqui é porque o conteúdo (challenge) foi assinado corretamente e 
				// o certificado foi emitido por uma raiz na qual confiamos.
				// Neste caso podemos entender que esta raiz só poderia emitir um certificado com 
				// um determinado número de CPF ao real portador daquele CPF, ou seja, nós confiamos
				// naquela ICP.
				// Podemos então devolver ao nosso sistema este número de CPF indicando que a pessoa
				// que está tentando o login é o real portador daquele número de CPF, onde basta recuperar
				// em nossa base de dados o registro de usuário correspondente aquele número de CPF (tarefa esta
				// que foje ao escopo desta nossa demostração).
				return certificadoSignatario.getDocumentNumber();
			} catch (Exception e) {
				throw new Exception ("O certificado não foi emitido por uma raiz que confiamos.");
			}
		} else 
			throw new Exception ("O conteúdo assinado não confere com o desafio lançado.");
	}
	 * </pre>
	 * 
	 * @param challenge O desafio que foi enviado para o usuário, para que este pudesse assiná-lo.
	 * @param pkcs7 A resposta ao desafio, ou seja, a assinatura deste desafio. Esta assinatura está em 
	 * formato Base64 de forma a representar um conteúdo adquirido em ambiente que, para este caso, normalmente é Web.
	 * @return
	 * @throws Exception Caso o desafio não tenha sido concluído.
	 */
	public static String autenticaUsuario(String challenge, String pkcs7) throws Exception {
		// Reabre o pacote de assinatura
		CMSSignedData sign = new CMSSignedData(new Base64Content(ICPBravoUtil.readFromUrlAndBase64(pkcs7)));
		
		// Verifica se as assinaturas correspondem ao conteúdo assinado
		sign.verify();
		
		// Recupera o conteúdo assinado
		String signChallenge = sign.getData().getString(); // Aqui teremos o conteúdo que foi assinado

		// Se o conteúdo assinado corresponde ao que se desejava que fosse assinado
		if (challenge.equals(signChallenge)) { // É sinal que o nosso desafio foi respondido
			// Recupera a primeira informação de assinatura. Neste ponto fazemos um
			// pré-julgamento que o nosso pacote de resposta contém como primeira assinatura
			// a assinatura que nos interessa, coisa que em ambiente de challenge-response pode ser
			// considerado como uma afirmação verdadeira.
			CMSSignerInformation signatario = sign.getSigners().get(0);
			
			// Recupera o certificado do signatário
			ICPBravoCertificate certificadoSignatario = signatario.getCertificate();

			// Recupera nossa lista de certificados confiáveis (vide changeTrustedChain()) 
			JKSManager trustedChain = new JKSManager("/temp/trusted.jks", "1234".toCharArray());
			trustedChain.reload();

			try {
				// Verifica se o certificado é confiável em nossa cadeia
				certificadoSignatario.tryTrustedValidate(trustedChain);
				
				// Em alguns casos pode ser interessante fazer alguns testes no certificado do usuário,
				// como se está ou não válido ou revogado, ou outro dentre os inúmeros testes de certificado.
				// (vide testes de certificado em mostraInformacoesCertificado())

				// Se chegou até aqui é porque o conteúdo (challenge) foi assinado corretamente e 
				// o certificado foi emitido por uma raiz na qual confiamos.
				// Neste caso podemos entender que esta raiz só poderia emitir um certificado com 
				// um determinado número de CPF ao real portador daquele CPF, ou seja, nós confiamos
				// naquela ICP.
				// Podemos então devolver ao nosso sistema este número de CPF indicando que a pessoa
				// que está tentando o login é o real portador daquele número de CPF, onde basta recuperar
				// em nossa base de dados o registro de usuário correspondente aquele número de CPF (tarefa esta
				// que foje ao escopo desta nossa demostração).
				return certificadoSignatario.getDocumentNumber();
			} catch (Exception e) {
				throw new Exception ("O certificado não foi emitido por uma raiz que confiamos.");
			}
		} else 
			throw new Exception ("O conteúdo assinado não confere com o desafio lançado.");
	}

	/**
	 * Teste que permite a leitura e criação de certificados a partir de PEM Encoded.
	 * <p>
	 * <pre class="brush: java;">
	public static void testaCertificadoPEM() throws Exception {
		cron.zera();
		System.out.println("Testando Certificados em PEM");
		try {
			String pem = cert1.getPEM();
			System.out.println(pem);
			ICPBravoCertificate c = ICPBravoCertificate.CreateFromPEM(pem, manager, "test");
			c.verify(c.getPublicKey());
			System.out.println("Certificados em PEM: OK");
		} catch (Exception e) {
			System.out.println("Certificados em PEM: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de Certificados em PEM");
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando Certificados em PEM
-----BEGIN CERTIFICATE-----
MIIEljCCA/+gAwIBAgIBAjANBgkqhkiG9w0BAQUFADCBvzELMAkGA1UEBhMCQlIxEzARBgNVBAoT
CklDUC1CcmFzaWwxCzAJBgNVBAgTAkRGMREwDwYDVQQHEwhCcmFzaWxpYTFDMEEGA1UECxM6SW5z
dGl0dXRvIE5hY2lvbmFsIGRlIFRlY25vbG9naWEgZGEgSW5mb3JtYWNhbyBmYWtlIC0gSVRJZjE2
MDQGA1UEAxMtQXV0b3JpZGFkZSBDZXJ0aWZpY2Fkb3JhIFJhaXogQnJhc2lsZWlyYSBmYWtlMB4X
DTA5MDkyNTE0MTUzM1oXDTA5MTAyNTEzMTUzM1owgZExHTAbBgNVBAMMFFBlc3NvYSAxOjEyMzQ1
Njc4OTAxMRowGAYDVQQLDBFPVT1TUkYgZS1DUEYgZmFrZTEyMDAGA1UECwwpU2VjcmV0YXJpYSBk
YSBSZWNlaXRhIEZlZGVyYWwgZmFrZSAtIFNSRmYxEzARBgNVBAoMCklDUC1CcmFzaWwxCzAJBgNV
BAYTAkJSMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEqz25Mw6klUnJ6ui5g/B0HLvPVTUj
5SgR12bQrgWeHxEPRPEDcxa44QEa3ZNrJ74rs6+ITapxYDTiKaxuG3B2PYVLVlaBgcXZ3lTfjeGL
iQAG05eZIBoKDVDmaOkvUdAwBMW0IMXl/3vME8X7rUaURnPPZkxkg9hCvMnbq4HZvQIDAQABo4IB
zDCCAcgwXwYDVR0gAQH/BFUwUzBRBgZgTAECAwowRzBFBggrBgEFBQcCARY5aHR0cDovL3d3dy5j
ZXJ0aWZpY2Fkb2RpZ2l0YWwub2Frcy5jb20uYnIvcmVwb3NpdG9yaW8vZHBjMFsGA1UdHwEB/wRR
ME8wTaBLoEmGR2h0dHA6Ly93d3cuY2VydGlmaWNhZG9kaWdpdGFsLm9ha3MuY29tLmJyL3JlcG9z
aXRvcmlvL2xjci9TZXJhc2FTUkYuY3JsMAwGA1UdEwEB/wQCMAAwDgYDVR0PAQH/BAQDAgXgMDQG
A1UdJQEB/wQqMCgGCCsGAQUFBwMCBggrBgEFBQcDBAYIKwYBBQUHAwMGCCsGAQUFBwMIMIGzBgNV
HREEgaswgaiBF3Blc3NvYTFTQG9ha3RlY2guY29tLmJyoD4GBWBMAQMBoDUTMzAxMDIyMDA5MTIz
NDU2Nzg5MDEwMDAwMDAwMDAwMDEyMzQ1Njc4OTA5MDAwMFNTUERmIKA0BgVgTAEDBaArEykwMDAw
MDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMKAXBgVgTAEDBqAOEwwwMDAwMDAw
MDAwMDAwDQYJKoZIhvcNAQEFBQADgYEAhvLu1ybn7b5VwlPhU2WwZ4Wx+e0hnnLYXzk0OJfeRRDn
E8/RbbjrD8lCNiszk8W6NINUBmyrZZIKbl4OtWS8ePS+OpZ9AFOUE0ILQzujk6LRq3qSDHhVET3j
GcIitoNhfckUHNdzTUgCPvjif2W8CjEtkVJpCsSAR39yYs1EL+Q=
-----END CERTIFICATE-----
Certificados em PEM: OK
Fim de teste de Certificados em PEM (63 ms)
	 * </pre>
	 */
	public static void testaCertificadoPEM() throws Exception {
		cron.zera();
		System.out.println("Testando Certificados em PEM");
		try {
			String pem = cert1.getPEM();
			System.out.println(pem);
			ICPBravoCertificate c = ICPBravoCertificate.CreateFromPEM(pem, manager, "test");
			c.verify(c.getPublicKey());
			System.out.println("Certificados em PEM: OK");
		} catch (Exception e) {
			System.out.println("Certificados em PEM: Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de Certificados em PEM");
	}

	/**
	 * Teste que permite a criação de um CSR, e a criação de um certificado a partir do CSR.
	 * <p>
	 * <pre class="brush: java;">
	public static void testaCSR() throws Exception {
		cron.zera();
		System.out.println("Testando CSR");
		try {
			RSA rsa = new RSA1024ECB(provider);
			KeyPair kp = rsa.generateKeyPair();
			SignatureAlgorithm sigAlg = new SignatureAlgorithm(provider, new SHA1(provider), rsa);
			ICPBravoCertificationRequest request = new ICPBravoCertificationRequest(provider, "CN=Ubiratan Elias, O=OAK Soluções Empresariais LTDA, OU=Desenvolvimento, L=Brasília, ST=Distrito Federal, C=BR", sigAlg, kp);
			byte[] encodedCSR = request.getEncoded();
			
			System.out.println("CSR:" + request.toString());
			String pemEncoded = new BytesContent(encodedCSR).getPEMEncoded("CSR");
			System.out.println(pemEncoded);
			ICPBravoCertificationRequest recoveredCSR = new ICPBravoCertificationRequest(new PEMContent(pemEncoded, "CSR"));
			System.out.println(recoveredCSR.getSubjectName());
			
			// Gerando um certificado a partir de um CSR
			Date from = ICPBravoUtil.getDateNow();
			Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);
			
			ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(
					provider, 
					"Pessoa 1", 
					BigInteger.valueOf(2), 
					recoveredCSR,
					certRoot,  
					ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
					ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(), 
					"http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl",  
					OIWObjectIdentifiers.OID_ICPBrasilA3 + ".10",
					"http://www.certificadodigital.oaks.com.br/repositorio/dpc", 
					from, to,  
					new SignatureAlgorithm(provider, new SHA1(provider), new RSA(provider)), 
					"pessoa1S@oaktech.com.br", 
					ICPBravoUtil.getDate(2009, 1, 1), // data de nascimento
					"12345678901", // cpf
					null, // nis
					"12345678909", // rg
					"SSPDf", // oe
					null, // tituloEleitor
					null, // zona
					null, // sessao
					null, // municipioUf
					null // inss
			);
			ICPBravoCertificate cert = gen.generate(manager, true);
			cert.verify(kp.getPublic());
			
			System.out.println("CSR: OK");
		} catch (Exception e) {
			System.out.println("CSR: : Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de CSR");
	}
	 * </pre>
	 * 
	 * O método apresenta como saída as seguintes informações:<br />
	 * <pre class="brush: plain;">
Testando CSR
CSR:[PKCS #10 certificate request:
Sun RSA public key, 1024 bits
  modulus: 128707597015876842290022556293750384458361619893257285761039770433645437200783477089745082977526203792473124256037853550012448431020364316579725105712373116225107711693906051535385826792663837038953639580356223403790584344745634044861625955131117603521179651234061075057289971256268058847969793112562421259551
  public exponent: 65537 subject: &lt;CN=Ubiratan Elias, O=OAK Soluções Empresariais LTDA, OU=Desenvolvimento, L=Brasília, ST=Distrito Federal, C=BR&gt;
 attributes: 0
{}
]
-----BEGIN CSR-----
MIIB2zCCAUQCAQAwgZoxCzAJBgNVBAYTAkJSMRkwFwYDVQQIExBEaXN0cml0byBGZWRlcmFsMRIw
EAYDVQQHDAlCcmFzw61saWExGDAWBgNVBAsTD0Rlc2Vudm9sdmltZW50bzEpMCcGA1UECgwgT0FL
IFNvbHXDp8O1ZXMgRW1wcmVzYXJpYWlzIExUREExFzAVBgNVBAMTDlViaXJhdGFuIEVsaWFzMIGf
MA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3SSPFoRRp7wsD7LVeGjQFnoj9LQBjmqYKaB7JGNBe
tYmNkQx+PRDcABkYjSyrPrDdUaq0Tl3Ok10P9lX7dALwDR7pMGHLwRtqOXSx+hMCELpWjSVLLL22
ZEKmlnzjLMeIe1i28IaiXXJJ5XdReLWfHL4TY+352kE/dNmz7OjdHwIDAQABoAAwDQYJKoZIhvcN
AQEFBQADgYEAelOW614uW24qbyXX4+FJ+JFoksu1DxpZS7dYK62xML1PlfVBRQFGpeSpQYvYKo21
+nyHX/gowU17cPbB3XxBTyspaNSm+PA7J3sNy50544o8Qfnu7JqWWYigKlOQQCAlHNAoinILO+A5
0M7NT/+oaovCRPM1h0cFkA+FCbUQ2V4=
-----END CSR-----
CN=Ubiratan Elias, O=OAK Soluções Empresariais LTDA, OU=Desenvolvimento, L=Brasília, ST=Distrito Federal, C=BR
CSR: OK
Fim de teste de CSR (4947 ms)
	 * </pre>
	 */
	public static void testaCSR() throws Exception {
		cron.zera();
		System.out.println("Testando CSR");
		try {
			RSA rsa = new RSA1024ECB(provider);
			SignatureAlgorithm sigAlg = new SignatureAlgorithm(provider, new SHA1(provider), rsa);

			PKCS12Manager dispositivo = new PKCS12Manager("c:/temp/ttt.p12", "1234".toCharArray());
			String alias = "Bira";
			ICPBravoCertificationRequest request = dispositivo.generateCSR(alias, "CN=Ubiratan Elias, O=OAK Soluções Empresariais LTDA, OU=Desenvolvimento, L=Brasília, ST=Distrito Federal, C=BR", sigAlg, (long) 0);
//			KeyPair kp = rsa.generateKeyPair();
//			ICPBravoCertificationRequest request = new ICPBravoCertificationRequest("CN=Ubiratan Elias, O=OAK Soluções Empresariais LTDA, OU=Desenvolvimento, L=Brasília, ST=Distrito Federal, C=BR", sigAlg, kp);

			byte[] encodedCSR = request.getEncoded();
			
			System.out.println("CSR:" + request.toString());
			String pemEncoded = new BytesContent(encodedCSR).getPEMEncoded("CSR");
			System.out.println(pemEncoded);
			ICPBravoCertificationRequest recoveredCSR = new ICPBravoCertificationRequest(new PEMContent(pemEncoded, "CSR"));
			System.out.println(recoveredCSR.getSubjectName());
			
			// Gerando um certificado a partir de um CSR
			Date from = ICPBravoUtil.getDateNow();
			Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);
			
			ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(
					provider, 
					"Pessoa 1", 
					BigInteger.valueOf(2), 
					recoveredCSR,
					certRoot,  
					ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(), 
					ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(), 
					"http://www.certificadodigital.oaks.com.br/repositorio/lcr/SerasaSRF.crl",  
					OIWObjectIdentifiers.OID_ICPBrasilA3 + ".10",
					"http://www.certificadodigital.oaks.com.br/repositorio/dpc", 
					from, to,  
//					new SignatureAlgorithm(provider, new SHA1(provider), new RSA(provider)), 
					"pessoa1S@oaktech.com.br", 
					ICPBravoUtil.getDate(2009, 1, 1), // data de nascimento
					"12345678901", // cpf
					null, // nis
					"12345678909", // rg
					"SSPDf", // oe
					null, // tituloEleitor
					null, // zona
					null, // sessao
					null, // municipioUf
					null // inss
			);
			ICPBravoCertificate cert = gen.generate(manager, false);
			
			String certp7BPEM = cert.getP7BPEM();
			dispositivo.install(alias, certp7BPEM);
			
			System.out.println("CSR: OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CSR: : Falha " + e.getMessage());
		}
		cron.marcaTotal("Fim de teste de CSR");
	}

	/**
	 * Roda os testes.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("inicializaTeste() - ######################################################################");
		inicializaTeste();
		System.out.println("criaCertificados() - ######################################################################");
		criaCertificados();
		System.out.println("testaEnvelopesDigitais() - ######################################################################");
		testaEnvelopesDigitais();
		System.out.println("testaCertificadoX509v3Expirado() - ######################################################################");
		testaCertificadoX509v3Expirado();
		System.out.println("testaCSR() - ######################################################################");
		testaCSR();
		System.out.println("testaCertificadoPEM() - ######################################################################");
		testaCertificadoPEM();
		System.out.println("testaSSLCriaCertificados() - ######################################################################");
		testaSSLCriaCertificados();
		System.out.println("testaCifrasSimetricas() - ######################################################################");
		testaCifrasSimetricas();
		System.out.println("testaCifrasAssimetricas() - ######################################################################");
		testaCifrasAssimetricas();
		System.out.println("testaDiffieHellman() - ######################################################################");
		testaDiffieHellman();
		System.out.println("testaECDH() - ######################################################################");
		testaECDH();
		System.out.println("testaDigest() - ######################################################################");
		testaDigest();
		System.out.println("testaHMACSha1() - ######################################################################");
		testaHMACSha1();
		System.out.println("testaNumerosAleatorios() - ######################################################################");
		testaNumerosAleatorios();
		System.out.println("testaCertificadoX509v3() - ######################################################################");
		testaCertificadoX509v3();
		System.out.println("testaCertificadoX509v2() - ######################################################################");
		testaCertificadoX509v2();
		System.out.println("testaAssinaturaDePDF() - ######################################################################");
//		testaAssinaturaDePDF();
		System.out.println("testaKEK() - ######################################################################");
		testaKEK();
		System.out.println("testaPBE() - ######################################################################");
		testaPBE();
		System.out.println("testaShamirSecretSharing() - ######################################################################");
		testaShamirSecretSharing();
		System.out.println("testaPKCS11() - ######################################################################");
		testaPKCS11();
		System.out.println("testaPKCS12() - ######################################################################");
		testaPKCS12();
		System.out.println("testaLDAP() - ######################################################################");
		testaLDAP();
		System.out.println("testaSSL() - ######################################################################");
		testaSSL();
		System.out.println("testaSMIME() - ######################################################################");
		testaSMIME();
		System.out.println("testaUnsupportedPKCS11() - ######################################################################");
		testaUnsupportedPKCS11();
		System.out.println("changeTrustedChain() - ######################################################################");
		changeTrustedChain();
		System.exit(0);
	}
}