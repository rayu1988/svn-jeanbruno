<html>
<header>
	<title>ICPBravo - Adiciona um certificado confiável - Processamento</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1>Adiciona um certificado confiável - Processamento</h1>
<h3>Server-Side</h3>
<p>O exemplo corrente visa demonstrar a inclusão de um certificado na lista de certificados confiáveis no servidor.</p>
<p>Este é o passo onde o servidor irá gerar a nova lista de confiáveis.</p>
<hr />
<%
	String msg="";
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
System.out.println("Gerando Trusted");

		String certificadoEChain=request.getParameter("certificateAndChain");
		certificadoEChain = br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(certificadoEChain);
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = br.com.oaks.ICPBravo.manager.ICPBravoManager.getDefaultManager();
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate cert = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromPEMAndChain(certificadoEChain,manager,"test");
		
		br.com.oaks.ICPBravo.manager.JKSManager trustedChain = new br.com.oaks.ICPBravo.manager.JKSManager(resourceBundle.getString("home")+"/certs/trusteds.jks", "1234".toCharArray());
		trustedChain.reload();
		trustedChain.addCertificate("Trusted "+(trustedChain.getCertificates().size()+1), cert);
		trustedChain.saveTofile();
		trustedChain.reload();
		
		for (br.com.oaks.ICPBravo.certs.ICPBravoCertificate c : trustedChain.getCertificates()) {
			System.out.println(c.getName());
		}
		
		msg = "Novo trusted adicionado";
	} catch (Exception e) {
		e.printStackTrace();
		msg = e.getMessage();
	}
%>
<%=msg%>

<hr />
<p>O código para a manutenção de trusteds é:</p>
<pre class="brush: java; html-script: true;">
&lt;%
	String msg="";
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
System.out.println("Gerando Trusted");

		String certificadoEChain=request.getParameter("certificateAndChain");
		certificadoEChain = br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(certificadoEChain);
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = br.com.oaks.ICPBravo.manager.ICPBravoManager.getDefaultManager();
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate cert = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromPEMAndChain(certificadoEChain,manager,"test");
		
		br.com.oaks.ICPBravo.manager.JKSManager trustedChain = new br.com.oaks.ICPBravo.manager.JKSManager(resourceBundle.getString("home")+"/certs/trusteds.jks", "1234".toCharArray());
		trustedChain.reload();
		trustedChain.addCertificate("Trusted "+(trustedChain.getCertificates().size()+1), cert);
		trustedChain.saveTofile();
		trustedChain.reload();
		
		for (br.com.oaks.ICPBravo.certs.ICPBravoCertificate c : trustedChain.getCertificates()) {
			System.out.println(c.getName());
		}
		
		msg = "Novo trusted adicionado";
	} catch (Exception e) {
		e.printStackTrace();
		msg = e.getMessage();
	}
%&gt;
&lt;%=msg%&gt;
</pre>

</body></html>
