<html>
<header>
	<title>ICPBravo - Assinatura de String</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Co-assinatura de String</h1>
<h3>Client-Side e Server-side</h3>
<p>O presente exemplo visa demonstrar a capacidade de coassinar uma string no servidor.</p>
<p>O c�digo abaixo � semelhante ao c�digo de assinatura de string, exceto pelo fato de que a assinatura ser� submetida a uma p�gina no servidor para que a coassinatura seja realizada.</p>
<hr />
<%@include file='AppletICPBravo.jsp'%>
<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
	<a href='javascript:signString();'>Assinar a String</a>
	<input type='hidden' name='pkcs7'>
</form>
 
<script language='JavaScript'> 
function signString() {
	// Indica o m�todo de callback que ser� chamado ap�s a assinatura realizada
	setCMSCallback(signed);
	// Invoca o applet para assinar a mensagem. 
	// (o par�metro null indica a n�o exist�ncia de uma pr�-assinatura e o true indica que � para anexar o conte�do no resultado da assinatura.
	ICPBravoApplet().signMessage(certificateSelect(), 'String', null, true);
}
 
function signed(pkcs7) {
	// Neste ponto, o applet est� nos devolvendo a nossa assinatura, onde neste exemplo, 
	// estamos enviando para uma nova p�gina para que possa ser verificada.
	alert(pkcs7);
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'CoSignServer.jsp';
	document.certificate.submit();
}
</script>

</body></html>