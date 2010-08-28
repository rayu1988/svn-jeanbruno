<html>
<header>
	<title>ICPBravo - Adiciona um certificado confi�vel</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1>Adiciona um certificado confi�vel</h1>
<h3>Client-Side</h3>
<p>O exemplo corrente visa demonstrar a inclus�o de um certificado na lista de certificados confi�veis no servidor.</p>
<p>Este passo � apenas o formul�rio para solicita��o.</p>
<hr />
<%@include file='AppletICPBravo.jsp'%>
<form name='certificate' action='AddTrustedServer.jsp'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
	<a href='javascript:adicionarCertificado();'>Adicionar</a>
	<input type='hidden' name='certificateAndChain'>
</form>
<script language='JavaScript'> 
function pegaCertificadoB64(prop, value) {
	document.certificate.certificateAndChain.value = value;
	document.certificate.submit();
}
function adicionarCertificado() {
	setOnSetCertificateProperties(pegaCertificadoB64);
	ICPBravoApplet().getCertificateProperties(certificateSelect(), 'PEM');
}
</script>
</body></html>