<html>
<header>
	<title>ICPBravo - Gera��o do certificado raiz</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1>Gera��o do certificado raiz</h1>
<h3>Server-Side</h3>
<p>O exemplo corrente visa demonstrar a cria��o de um certificado raiz (caso n�o exista) em um reposit�rio PKCS#12 armazenado em diret�rio fixo dentro da aplica��o.</p>
<p>Como continua��o do exemplo, o certificado tamb�m ser� exportado para um arquivo .CER, onde constam os dados do certificado e a chave p�blica, por�m sem os dados de chave privativa.</p>
<p>Tamb�m ser� exemplificada a cria��o de uma lista de certificados revogados vazia, visto que informamos em nosso certificado um endere�o para verifica��o desta.</p>
<p>Outra caracter�stica que devemos observar neste certificado � o fato de ser auto-assinado. Procure saber mais sobre isto.</p>
<p>Utilizaremos este certificado como nosso certificado de AC raiz, para exemplificar a gera��o de certificados de usu�rio e valida��o em cadeia de confian�a.</p>
<hr />

<%
	String msg="";
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
System.out.println("Gerando certificado a partir do CSR");
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = new br.com.oaks.ICPBravo.manager.PKCS12Manager(resourceBundle.getString("home")+"/certs/RaizTreinamento.p12", "1234".toCharArray());
		manager.reload();
		if (manager.getCertificates().size() == 0) {
			java.util.Date from = br.com.oaks.ICPBravo.util.ICPBravoUtil.getDateNow();
			java.util.Date to = br.com.oaks.ICPBravo.util.ICPBravoUtil.getTo(br.com.oaks.ICPBravo.util.ICPBravoUtil.MONTHS, 1);
	
			br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator gen = new br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator(
					manager.getProvider(), 
					"Raiz Treinamento", 
					java.math.BigInteger.valueOf(1),
					"Raiz Treinamento", 
					null, 
					br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator.getDefaultKeyUsageForCA(),
					br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator.getDefaultKeyPurposeForCA(),
					resourceBundle.getString("dominio")+"/ICPBravoJSP/certs/LatestCRL.crl",
					br.com.oaks.ICPBravo.asn1.oiw.OIWObjectIdentifiers.OID_ICPBrasilA1, // Veja lista em http://www.oid-info.com/
					resourceBundle.getString("dominio")+"/ICPBravoJSP/pages/Dpc.jsp", 
					"Treinamento ICPBravo,L=Brasilia,S=DF", "OAK", null, 
					from, to, 
					new br.com.oaks.ICPBravo.algorithm.SignatureAlgorithm(manager.getProvider(), 
						new br.com.oaks.ICPBravo.algorithm.digest.SHA1(manager.getProvider()), 
						new br.com.oaks.ICPBravo.algorithm.asymmetric.RSA1024ECB(manager.getProvider())), 
					null);
	
			br.com.oaks.ICPBravo.certs.ICPBravoCertificate certRoot = gen.generate(manager, true);
			br.com.oaks.ICPBravo.util.ICPBravoUtil.writeFile(resourceBundle.getString("home")+"/certs/RaizTreinamento.cer", certRoot.getEncoded());
			manager.saveTofile();
			
			
			// Criando CRL inicial
			to = br.com.oaks.ICPBravo.util.ICPBravoUtil.getTo(br.com.oaks.ICPBravo.util.ICPBravoUtil.MINUTES, 10);
			br.com.oaks.ICPBravo.certs.crl.ICPBravoCRLGenerator genCRL = new br.com.oaks.ICPBravo.certs.crl.ICPBravoCRLGenerator(manager, certRoot);
			br.com.oaks.ICPBravo.certs.crl.ICPBravoCRL crl = genCRL.generate(from, to);
			br.com.oaks.ICPBravo.util.ICPBravoUtil.writeFile(resourceBundle.getString("home")+"/certs/LatestCRL.crl", crl.getEncoded());
			
			msg = "Certificado gerado corretamente";
		} else
			msg = "Ja havia um certificado";
	} catch (Exception e) {
		e.printStackTrace();
		msg = e.getMessage();
	}
%>
<%=msg%>


<p>O c�digo para a gera��o do certificado �:</p>
<pre class="brush: java; html-script: true;">
&lt;%
	String msg="";
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
System.out.println("Gerando certificado a partir do CSR");
		ICPBravoManager manager = new PKCS12Manager(resourceBundle.getString("home")+"/certs/RaizTreinamento.p12", "1234".toCharArray());
		manager.reload();
		if (manager.getCertificates().size() == 0) {
			java.util.Date from = ICPBravoUtil.getDateNow();
			java.util.Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);
	
			ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(
					manager.getProvider(), 
					"Raiz Treinamento", 
					java.math.BigInteger.valueOf(1),
					"Raiz Treinamento", 
					null, 
					ICPBravoCertificateGenerator.getDefaultKeyUsageForCA(),
					ICPBravoCertificateGenerator.getDefaultKeyPurposeForCA(),
					resourceBundle.getString("dominio")+"/ICPBravoJSP/certs/LatestCRL.crl",
					OIWObjectIdentifiers.OID_ICPBrasilA1, // Veja lista em http://www.oid-info.com/
					resourceBundle.getString("dominio")+"/ICPBravoJSP/pages/Dpc.jsp", 
					"Treinamento ICPBravo,L=Brasilia,S=DF", "OAK", null, 
					from, to, 
					new SignatureAlgorithm(manager.getProvider(), 
						new SHA1(manager.getProvider()), 
						new RSA1024ECB(manager.getProvider())), 
					null);
	
			ICPBravoCertificate certRoot = gen.generate(manager, true);
			ICPBravoUtil.writeFile(resourceBundle.getString("home")+"/certs/RaizTreinamento.cer", certRoot.getEncoded());
			manager.saveTofile();
			
			
			// Criando CRL inicial
			ICPBravoCRLGenerator genCRL = new ICPBravoCRLGenerator(manager, certRoot);
			ICPBravoCRL crl = genCRL.generate(from, to);
			ICPBravoUtil.writeFile(resourceBundle.getString("home")+"/certs/LatestCRL.crl", crl.getEncoded());
			
			msg = "Certificado gerado corretamente";
		} else
			msg = "Ja havia um certificado";
	} catch (Exception e) {
		e.printStackTrace();
		msg = e.getMessage();
	}
%&gt;
&lt;%=msg%&gt;
</pre>

</body></html>