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
	System.out.println(pkcs7);
	try {
		br.com.oaks.ICPBravo.cms.CMSSignedData sign = new br.com.oaks.ICPBravo.cms.CMSSignedData(new br.com.oaks.ICPBravo.cms.content.Base64Content(br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(pkcs7)));
		sign.verify();
		java.util.List<br.com.oaks.ICPBravo.cms.CMSSignerInformation> signers = sign.getSigners();
%>
	<table>
	<tr><td colspan='2'><b>Assinatura verificada corretamente.</b></td></tr>
<tr><td colspan='2'><hr /></td></tr>	
	<%
		br.com.oaks.ICPBravo.cms.content.Content content = sign.getData();
		for(br.com.oaks.ICPBravo.cms.CMSSignerInformation signer : signers) {
			br.com.oaks.ICPBravo.cms.Pkcs9 signedAtt = signer.getSignedAttributes();
			br.com.oaks.ICPBravo.cms.Pkcs9 unsignedAtt = signer.getUnsignedAttributes();
			br.com.oaks.ICPBravo.certs.ICPBravoCertificate cert = signer.getCertificate();
	%>
<%@include file='ShowInfoCertificate.jsp'%>
<%
		try {
%><tr><td colspan='2'><b>Verificando police <%= signer.getPolicy().getDescription() %>.</b></td></tr><%
			signer.verifyPolicies();
%><tr><td colspan='2'><b>Police OK.</b></td></tr><%
		} catch(Exception e) {
%><tr><td><b>Falha na verificação da Police.</b></td><td><%= e.getMessage() %></td></tr><%
		}
%>
		<tr><td colspan='2' align='center'><b>Signed Attributes</b></td></tr>
		<% for(br.com.oaks.ICPBravo.cms.pkcs9.SignatureAttribute att : signedAtt.getAttributes()) { %>
			<tr><td><b><%= att.description() %>:</b></td><td><%= att.toString() %></td></tr>
		<% } %>
		<% if (unsignedAtt.getAttributes().size() > 0) { %>
			<tr><td colspan='2' align='center'><b>Unsigned Attributes</b></td></tr>
			<% for(br.com.oaks.ICPBravo.cms.pkcs9.SignatureAttribute att : unsignedAtt.getAttributes()) { %>
				<tr><td><b><%= att.description() %>:</b></td><td><%= att.toString() %></td></tr>
		<% }} %>
<tr><td colspan='2'><hr /></td></tr>	
	<% } %>
	<tr><td colspan='2' align='center'><b>Outros</b></td></tr>
	<tr><td><b>Conte&uacute;do:</b></td><td><pre><%= new String(content.getContent(), "ISO-8859-1").replaceAll("<","&lt;").replaceAll(">","&gt;").toString() %></pre></td></tr>
	<tr><td colspan='2' align='center'><b>CMS/PKCS#7 (Base64)</b></td></tr>
	<tr><td colspan='2'><%= pkcs7 %></td></tr>
	</table>
<%
	} catch (Exception e) {
		e.printStackTrace();
%>
Erro ao verificar a assinatura: <%= e.getMessage() %>
<%
	}
%>
<hr />
<p>O código para a execução do programa acima, pode ser visto abaixo:</p>
<pre class="brush: java; html-script: true;">
&lt;%
	String pkcs7=request.getParameter("pkcs7");
	try {
		CMSSignedData sign = new CMSSignedData(new Base64Content(ICPBravoUtil.readFromUrlAndBase64(pkcs7)));
		sign.verify();
		java.util.List&lt;CMSSignerInformation&gt; signers = sign.getSigners();
%&gt;
	&lt;table&gt;
	&lt;tr&gt;&lt;td colspan='2'&gt;&lt;b&gt;Assinatura verificada corretamente.&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;%
		for(CMSSignerInformation signer : signers) {
			Pkcs9 signedAtt = signer.getSignedAttributes();
			Pkcs9 unsignedAtt = signer.getUnsignedAttributes();
			ICPBravoCertificate cert = signer.getCertificate();
	%&gt;
	&lt;!---------------------------------------------------------------------------------------------&gt;
	&lt;%@include file='ShowInfoCertificate.jsp'%&gt;
	&lt;!---------------------------------------------------------------------------------------------&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Signat&aacute;rio:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getName() %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;N&uacute;mero de S&eacute;rie:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getSerialNumber().toString() %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Documento:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getDocumentNumber() %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Validade:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= "De:"+cert.getNotBefore()+" at&eacute; "+cert.getNotAfter() %&gt;&lt;/td&gt;&lt;/tr&gt;
	
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;&Eacute; Assinador Digital:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isKeyUsageExtensionDigitalSignature()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;&Eacute; Autoassinado:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isAutoSign()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;&Eacute; Sigilo:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isSecrecy()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;&Eacute; ICP-Brasil:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isICPBrasil()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;&Eacute; Raiz ICP-Brasil:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isICPBrasilRoot()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Tamanho da Chave:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getKeyLengh() %&gt; bits&lt;/td&gt;&lt;/tr&gt;
	
	&lt;%
	java.util.Vector&lt;CertificateAttribute&gt; infoICPBrasil = cert.constroiSubjectAlternativeNamesExtensions();
	for (CertificateAttribute informacaoCertificado : infoICPBrasil) {
		%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= informacaoCertificado.getDescription() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= informacaoCertificado.getValue() %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
	} %&gt;
	
	&lt;%
	java.util.Vector&lt;CertificateAttribute&gt; polices = cert.getCertificatePoliciesExtensions();
	for (CertificateAttribute police : polices) {
		%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= police.getDescription() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= police.getValue() %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
	} %&gt;
	
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Basic Constraints:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getBasicConstraints() %&gt;&lt;/td&gt;&lt;/tr&gt;
	
	&lt;%
	java.util.Vector&lt;CertificateAttribute&gt; keyUsages = cert.getKeyUsageExtension();
	for (CertificateAttribute keyUsage : keyUsages) {
		%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= keyUsage.getDescription() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= keyUsage.getValue() %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
	} %&gt;
	
	&lt;%
	java.util.Vector&lt;CertificateAttribute&gt; extKeyUsages = cert.getExtendedKeyUsageExtension();
	for (CertificateAttribute extKeyUsage : extKeyUsages) {
		%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= extKeyUsage.getDescription() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= extKeyUsage.getValue() %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
	} %&gt;
	
	&lt;tr&gt;&lt;td colspan='2'&gt;&lt;b&gt;Cadeia&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;%
	ICPBravoCertificate [] chains = cert.getCertificateChain();
	for (ICPBravoCertificate chain : chains) {
	%&gt;
		&lt;tr&gt;&lt;td colspan='2'&gt;&lt;%= chain.getName() %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;% } %&gt;
	
	
	&lt;%
		try {
			ICPBravoCA ac = cert.getIssuerObject();
			if (ac != null) {
	%&gt;
		&lt;tr&gt;&lt;td&gt;&lt;b&gt;AC:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= ac.toString() %&gt;&lt;/td&gt;&lt;/tr&gt;
		&lt;tr&gt;&lt;td&gt;&lt;b&gt;AC URL:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= ac.getURLCrl() %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;%
				ICPBravoCRL crl = ac.getValidCRL(null, null);
	%&gt;
		&lt;tr&gt;&lt;td&gt;&lt;b&gt;CRL OK:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= (crl.getThisUpdate() + " até " + crl.getNextUpdate()) %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;%
			} else {
	%&gt;
		&lt;tr&gt;&lt;td colspan='2'&gt;&lt;b&gt;Este certificado não tem CRL&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;%
			}
		} catch (Exception e) {
	%&gt;
		&lt;tr&gt;&lt;td colspan='2'&gt;&lt;b&gt;Erro ao verificar CRL: &lt;%= e.toString() %&gt;&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;%
		}
	%&gt;
	&lt;!---------------------------------------------------------------------------------------------&gt;
	
	&lt;%
		Content content = sign.getData();
	%&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Conte&uacute;do:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;pre&gt;&lt;%= new String(content.getContent(), "ISO-8859-1").replaceAll("&lt;","&lt;").replaceAll("&gt;","&gt;").toString() %&gt;&lt;/pre&gt;&lt;/td&gt;&lt;/tr&gt;
		&lt;tr&gt;&lt;td colspan='2'&gt;&lt;b&gt;Signed Attributes&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
		&lt;% for(SignatureAttribute att : signedAtt.getAttributes()) { %&gt;
			&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= att.description() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= att.toString() %&gt;&lt;/td&gt;&lt;/tr&gt;
		&lt;% } %&gt;
		&lt;tr&gt;&lt;td colspan='2'&gt;&lt;b&gt;Unsigned Attributes&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
		&lt;% for(SignatureAttribute att : unsignedAtt.getAttributes()) { %&gt;
			&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= att.description() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= att.toString() %&gt;&lt;/td&gt;&lt;/tr&gt;
		&lt;% } %&gt;
	&lt;% } %&gt;
	&lt;tr&gt;&lt;td colspan='2' align='center'&gt;&lt;b&gt;CMS/PKCS#7 (Base64)&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td colspan='2'&gt;&lt;%= pkcs7 %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;/table&gt;
&lt;%
	} catch (Exception e) {
		e.printStackTrace();
%&gt;
Erro ao verificar a assinatura: &lt;%= e.getMessage() %&gt;
&lt;%
	}
%&gt;
</pre>
</body></html>
