<html>
<header>
	<title>ICPBravo - Verificando Assinatura</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Verificando Assinatura</h1>
<h3>Server-Side</h3>
<p>Este exemplo demonstra como visualizar informações de uma assinatura digital pelo SDK no lado do servidor.</p>
<hr />

<%
	String pkcs7=request.getParameter("pkcs7");
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = new br.com.oaks.ICPBravo.manager.PKCS12Manager(resourceBundle.getString("home")+"/certs/RaizTreinamento.p12", "1234".toCharArray());
		manager.reload();
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate certRoot = manager.getCertificates().get(0);
		br.com.oaks.ICPBravo.cms.CMSSignedData sign = new br.com.oaks.ICPBravo.cms.CMSSignedData(new br.com.oaks.ICPBravo.cms.content.Base64Content(br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(pkcs7)));


//		br.com.oaks.ICPBravo.manager.ICPBravoManager manager2 = new br.com.oaks.ICPBravo.manager.PKCS12Manager("c:/temp/bira.p12", "1234".toCharArray());
//		manager2.reload();
//		br.com.oaks.ICPBravo.certs.ICPBravoCertificate certBira = manager2.getCertificates().get(0);
		
//		br.com.oaks.ICPBravo.cms.CMSSignedData sign = new br.com.oaks.ICPBravo.cms.CMSSignedData();
//		sign.setData("ftvyghubnijkml,".getBytes());
		
		br.com.oaks.ICPBravo.cms.policies.Policy policy = new br.com.oaks.ICPBravo.cms.policies.CMSPolicy();
		sign.addSigner(certRoot, policy);
//		sign.addSigner(certBira, policy);
		
		byte [] newPkcs7 = sign.getASN1Encoded();
		
%>
		<form name='certificate' method='post' action='SignVerify.jsp'>
		<input type='hidden' name='pkcs7' value='<%= new br.com.oaks.ICPBravo.cms.content.BytesContent(newPkcs7).getBase64Encoded() %>'>
		<input type='submit' value='Verificar assinatura'>
		</form>
<%
		 
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<hr />
<p>O código para a execução do programa acima, pode ser visto abaixo:</p>
<pre class="brush: java; html-script: true;">
	String pkcs7=request.getParameter("pkcs7");
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = new br.com.oaks.ICPBravo.manager.PKCS12Manager(resourceBundle.getString("home")+"/certs/RaizTreinamento.p12", "1234".toCharArray());
		manager.reload();
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate certRoot = manager.getCertificates().get(0);
		br.com.oaks.ICPBravo.cms.CMSSignedData sign = new br.com.oaks.ICPBravo.cms.CMSSignedData(new br.com.oaks.ICPBravo.cms.content.Base64Content(br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(pkcs7)));
		
		br.com.oaks.ICPBravo.cms.policies.Policy policy = new br.com.oaks.ICPBravo.cms.policies.CMSPolicy();
		sign.addSigner(certRoot, policy);
		
		byte [] newPkcs7 = sign.getASN1Encoded();
		
%&gt;
		&lt;form name='certificate' method='post' action='SignVerify.jsp'&gt;
		&lt;input type='hidden' name='pkcs7' value='&lt;%= new br.com.oaks.ICPBravo.cms.content.BytesContent(newPkcs7).getBase64Encoded() %&gt;'&gt;
		&lt;input type='submit' value='Verificar assinatura'&gt;
		&lt;/form&gt;
&lt;%
		 
	} catch (Exception e) {
		e.printStackTrace();
	}
</pre>
</body></html>
