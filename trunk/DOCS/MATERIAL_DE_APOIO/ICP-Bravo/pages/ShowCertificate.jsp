<html>
<header>
	<title>ICPBravo - Certificado</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Informações do Certificado</h1>
<h3>Client-Side</h3>
<p>Este exemplo servirá para selecionar um certificado na estação cliente e enviá-lo para que o servidor possa mostrar suas informações.</p>
<hr />

<%@include file='AppletICPBravo.jsp'%>
<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
	<a href='javascript:verCertificado();'>Verificar</a>
	<input type='hidden' name='certificateAndChain'>
</form>
<script language='JavaScript'> 
function pegaCertificadoB64(prop, value) {
	document.certificate.certificateAndChain.value = value;
	document.certificate.action = 'ViewCertificate.jsp';
	document.certificate.submit();
}
function verCertificado() {
	setOnSetCertificateProperties(pegaCertificadoB64);
	ICPBravoApplet().getCertificateProperties(certificateSelect(), 'PEM');
}
</script>
</body></html>