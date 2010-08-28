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
<p>O código abaixo é semelhante ao código de assinatura de string, exceto pelo fato de que a assinatura será submetida a uma página no servidor para que a coassinatura seja realizada.</p>
<hr />
<%@include file='AppletICPBravo.jsp'%>
<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
	<a href='javascript:signString();'>Assinar a String</a>
	<input type='hidden' name='pkcs7'>
</form>
 
<script language='JavaScript'> 
function signString() {
	// Indica o método de callback que será chamado após a assinatura realizada
	setCMSCallback(signed);
	// Invoca o applet para assinar a mensagem. 
	// (o parâmetro null indica a não existência de uma pré-assinatura e o true indica que é para anexar o conteúdo no resultado da assinatura.
	ICPBravoApplet().signMessage(certificateSelect(), 'String', null, true);
}
 
function signed(pkcs7) {
	// Neste ponto, o applet está nos devolvendo a nossa assinatura, onde neste exemplo, 
	// estamos enviando para uma nova página para que possa ser verificada.
	alert(pkcs7);
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'CoSignServer.jsp';
	document.certificate.submit();
}
</script>

</body></html>