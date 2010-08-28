<html>
<header>
	<title>ICPBravo - Adiciona um certificado confiável</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1>Adiciona um certificado confiável</h1>
<h3>Client-Side</h3>
<p>O exemplo corrente visa demonstrar a inclusão de um certificado na lista de certificados confiáveis no servidor.</p>
<p>Este passo é apenas o formulário para solicitação.</p>
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