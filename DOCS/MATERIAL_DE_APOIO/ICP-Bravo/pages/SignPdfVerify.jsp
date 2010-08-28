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
	String pdfB64=request.getParameter("pdf");
	try {
		java.util.ResourceBundle resourceBundle1 = java.util.ResourceBundle.getBundle("config");
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = br.com.oaks.ICPBravo.manager.ICPBravoManager.getDefaultManager();
		br.com.oaks.ICPBravo.cms.CMSSignedData sign = new br.com.oaks.ICPBravo.util.PDFSignedData(new br.com.oaks.ICPBravo.cms.content.Base64Content(br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(pdfB64)).getContent());
		sign.verify();
		java.util.List<br.com.oaks.ICPBravo.cms.CMSSignerInformation> signers = sign.getSigners();
%>
	<table>
	<tr><td colspan='2'><b>Assinatura verificada corretamente.</b></td></tr>
	<%
		for(br.com.oaks.ICPBravo.cms.CMSSignerInformation signer : signers) {
			br.com.oaks.ICPBravo.cms.Pkcs9 signedAtt = signer.getSignedAttributes();
			br.com.oaks.ICPBravo.cms.Pkcs9 unsignedAtt = signer.getUnsignedAttributes();
			br.com.oaks.ICPBravo.certs.ICPBravoCertificate cert = signer.getCertificate();
	%>
<%@include file='ShowInfoCertificate.jsp'%>
	<%
		br.com.oaks.ICPBravo.cms.content.Content content = sign.getData();
	%>
		<tr><td colspan='2'><b>Signed Attributes</b></td></tr>
		<% for(br.com.oaks.ICPBravo.cms.pkcs9.Attribute att : signedAtt.getAttributes()) { %>
			<tr><td><b><%= att.description() %>:</b></td><td><%= att.toString() %></td></tr>
		<% } %>
		<tr><td colspan='2'><b>Unsigned Attributes</b></td></tr>
		<% for(br.com.oaks.ICPBravo.cms.pkcs9.Attribute att : unsignedAtt.getAttributes()) { %>
			<tr><td><b><%= att.description() %>:</b></td><td><%= att.toString() %></td></tr>
		<% } %>
	<% } %>
	<tr><td colspan='2' align='center'><b>PDF (Base64)</b></td></tr>
	<tr><td colspan='2'><%= pdfB64 %></td></tr>
	</table>
	
<%
br.com.oaks.ICPBravo.util.ICPBravoUtil.writeFile(resourceBundle1.getString("home")+"/pages/PDFSign.pdf", new br.com.oaks.ICPBravo.cms.content.Base64Content(br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(pdfB64)).getContent());

%>	
	<form action='PDFSign.pdf' method='post'>
		<input type='submit' value='Ver o PDF'>
	</form>
<%
	} catch (Exception e) {
		e.printStackTrace();
%>
Erro ao verificar a assinatura: <%= e.getMessage() %>
<%
	}
%>
</body></html>
