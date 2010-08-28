<html>
<header>
	<title>ICPBravo - Assinatura de String</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Assinatura de String</h1>
<h3>Client-Side</h3>
<p>O presente exemplo visa demonstrar a capacidade de assinar uma simples string, presente no browser do usu�rio final.</p>
<p>Esta string pode, por exemplo, ser produto de um arquivo gerado no servidor e codificado em base 64 ou, como em nosso exemplo, uma string gerada na esta��o cliente (como o produto de um campo da tela, por exemplo).</p>
<p>Ao final do teste, a assinatura ser� submetida ao servidor, para que esta possa ser verificada.</p>
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
	document.certificate.action = 'SignVerify.jsp';
	document.certificate.submit();
}
</script>

<hr /> 
<p>Trecho do c�digo para assinar a string</p>
<pre class="brush: js; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;form name='certificate' method='post' action='#'&gt;
	Certificado: &lt;%@include file='CertificateSelect.jsp'%&gt;&lt;br&gt;
	&lt;a href='javascript:signString();'&gt;Assinar a String&lt;/a&gt;
	&lt;input type='hidden' name='pkcs7'&gt;
&lt;/form&gt;
 
&lt;script language='JavaScript'&gt;
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
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'SignVerify.jsp';
	document.certificate.submit();
}
&lt;/script&gt;
</pre>
</body></html>