<html>
<header>
	<title>ICPBravo - Revogação de certificado - Processamento</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1>Revogação de certificado - Processamento</h1>
<h3>Server-Side</h3>
<p>O exemplo corrente visa demonstrar a inclusão de um certificado na lista de revogados.</p>
<p>Este é o passo onde o servidor irá gerar a nova lista de revogados.</p>
<hr />
<%
	String msg="";
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
System.out.println("Gerando CRL");
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = new br.com.oaks.ICPBravo.manager.PKCS12Manager(resourceBundle.getString("home")+"/certs/RaizTreinamento.p12", "1234".toCharArray());
		manager.reload();
		if (manager.getCertificates().size() == 0)
			msg = "Não existe certificado raiz";
		else {
			br.com.oaks.ICPBravo.certs.ICPBravoCertificate certRoot = manager.getCertificates().get(0);
			int reason = Integer.parseInt(request.getParameter("reason"));
			long numSerie=Long.parseLong(request.getParameter("numSerie"));
			java.util.Date from = br.com.oaks.ICPBravo.util.ICPBravoUtil.getDateNow();
			java.util.Date to = br.com.oaks.ICPBravo.util.ICPBravoUtil.getTo(br.com.oaks.ICPBravo.util.ICPBravoUtil.MINUTES, 10);

			br.com.oaks.ICPBravo.certs.crl.ICPBravoCRLGenerator genCRL = new br.com.oaks.ICPBravo.certs.crl.ICPBravoCRLGenerator(manager, certRoot, resourceBundle.getString("home")+"/certs/LatestCRL.crl");
			if (numSerie > 0)
				genCRL.addCRLEntry(java.math.BigInteger.valueOf(numSerie), new java.util.Date(), reason);
			br.com.oaks.ICPBravo.certs.crl.ICPBravoCRL crl = genCRL.generate(certRoot, from, to);
			br.com.oaks.ICPBravo.util.ICPBravoUtil.writeFile(resourceBundle.getString("home")+"/certs/LatestCRL.crl", crl.getEncoded());
			msg = "Novo CRL gerado";
		}
	} catch (Exception e) {
		e.printStackTrace();
		msg = e.getMessage();
	}
%>
<%=msg%>

<hr />
<p>O código para a revogação do certificado é:</p>
<pre class="brush: java; html-script: true;">
&lt;%
	String msg="";
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
System.out.println("Gerando CRL");
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = new br.com.oaks.ICPBravo.manager.PKCS12Manager(resourceBundle.getString("home")+"/certs/RaizTreinamento.p12", "1234".toCharArray());
		manager.reload();
		if (manager.getCertificates().size() == 0)
			msg = "Não existe certificado raiz";
		else {
			br.com.oaks.ICPBravo.certs.ICPBravoCertificate certRoot = manager.getCertificates().get(0);
			int reason = Integer.parseInt(request.getParameter("reason"));
			long numSerie=Long.parseLong(request.getParameter("numSerie"));
			java.util.Date from = br.com.oaks.ICPBravo.util.ICPBravoUtil.getDateNow();
			java.util.Date to = br.com.oaks.ICPBravo.util.ICPBravoUtil.getTo(br.com.oaks.ICPBravo.util.ICPBravoUtil.MONTHS, 1);
			
			br.com.oaks.ICPBravo.certs.crl.ICPBravoCRLGenerator genCRL = new br.com.oaks.ICPBravo.certs.crl.ICPBravoCRLGenerator(manager, certRoot, resourceBundle.getString("home")+"/certs/LatestCRL.crl");
			genCRL.addCRLEntry(java.math.BigInteger.valueOf(numSerie), new java.util.Date(), reason);
			br.com.oaks.ICPBravo.certs.crl.ICPBravoCRL crl = genCRL.generate(certRoot, from, to);
			br.com.oaks.ICPBravo.util.ICPBravoUtil.writeFile(resourceBundle.getString("home")+"/certs/LatestCRL.crl", crl.getEncoded());
			msg = "Novo CRL gerado";
		}
	} catch (Exception e) {
		e.printStackTrace();
		msg = e.getMessage();
	}
%&gt;
&lt;%=msg%&gt;
</pre>

</body></html>