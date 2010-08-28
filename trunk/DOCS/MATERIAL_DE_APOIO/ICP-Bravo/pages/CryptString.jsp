<html>
<header>
	<title>ICPBravo - Criptografia de String</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Criptografia de String</h1>
<h3>Client-Side</h3>
<p>O presente exemplo visa demonstrar a capacidade de criptografar um conteúdo.</p>
<p>Esta facilidade permite enviar um conteúdo criptografado para um portador de certificado digital, sem a necessidade de trafegar senhas.</p>
<p>Ao final do teste, a criptografia será submetida a uma nova página, para que possa ser decriptografada.</p>
<hr />
<%@include file='AppletICPBravo.jsp'%>
<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
	<a href='javascript:cryptString();'>Criptografar a String</a>
	<input type='hidden' name='pkcs7'>
</form>
 
<script language='JavaScript'> 
function cryptString() {
	// Indica o método de callback que será chamado após a criptografia realizada
	setCMSCallback(crypted);
	var applet = ICPBravoApplet(); 
	applet.initializeEnvelopedData('AES128CBC', 'String', null);
	applet.addTarget(certificateSelect());
	// Invoca o applet para criptografar a mensagem. 
	applet.generateEnvelopedData();
}
 
function crypted(pkcs7) {
	// Neste ponto, o applet está nos devolvendo a nossa assinatura, onde neste exemplo, 
	// estamos enviando para uma nova página para que possa ser verificada.
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'CryptVerify.jsp';
	document.certificate.submit();
}
</script>
<hr />
<p>Trecho do código para criptografar a string</p>
<pre class="brush: js; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;form name='certificate' method='post' action='#'&gt;
	Certificado: &lt;%@include file='CertificateSelect.jsp'%&gt;&lt;br&gt;
	&lt;a href='javascript:cryptString();'&gt;Criptografar a String&lt;/a&gt;
	&lt;input type='hidden' name='pkcs7'&gt;
&lt;/form&gt;
 
&lt;script language='JavaScript'&gt;
function cryptString() {
	// Indica o método de callback que será chamado após a criptografia realizada
	setCMSCallback(crypted);
	var applet = ICPBravoApplet(); 
	applet.initializeEnvelopedData('AES128CBC', 'String', null);
	applet.addTarget(certificateSelect());
	// Invoca o applet para criptografar a mensagem. 
	applet.generateEnvelopedData();
}
 
function crypted(pkcs7) {
	// Neste ponto, o applet está nos devolvendo a nossa assinatura, onde neste exemplo, 
	// estamos enviando para uma nova página para que possa ser verificada.
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'CryptVerify.jsp';
	document.certificate.submit();
}
&lt;/script&gt;
</pre>
</body></html>