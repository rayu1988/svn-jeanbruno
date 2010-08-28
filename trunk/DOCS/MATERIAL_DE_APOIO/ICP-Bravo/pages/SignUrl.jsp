<html>
<header>
	<title>ICPBravo - Assinatura de URL</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Assinatura de URL</h1>
<h3>Client-Side</h3>
<p>O presente exemplo visa demonstrar a capacidade de assinar o conte�do produzido por uma URL.</p>
<p>Este conte�do pode ser um arquivo presente para download, ou o resultado de uma p�gina produzida pelo servidor.</p>
<p>Para o nosso exemplo, criamos uma p�gina simples no servidor que retorna um conte�do para podermos assinar (vide <a href='GetContent.jsp'>GetContent.jsp</a>).</p>
<p>Notem que o processo de assinatura � bem semelhante ao exemplo onde assinamos uma string ou o formul�rio. por�m agora chamamos outra fun��o de assinatura do applet.</p>
<p>Ao final do teste, a assinatura ser� submetida ao servidor, para que esta possa ser verificada.</p>
<hr /> 

<%@include file='AppletICPBravo.jsp'%>
<%
java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
String dominio = resourceBundle.getString("dominio");
%>
<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
	<a href='javascript:signUrl();'>Assinar a URL</a>
	<input type='hidden' name='pkcs7'>
</form>
 
<script language='JavaScript'> 
function signUrl() {
	setCMSCallback(signed);
	// Veja que agora mudamos a fun��o chamada para signUrl, ao inv�s de signMessage utilizado anteriormente.
	ICPBravoApplet().signUrl(certificateSelect(), '<%= dominio %>/ICPBravoJSP/pages/GetContent.jsp', null, true);
}
 
function signed(pkcs7) {
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'SignVerify.jsp';
	document.certificate.submit();
}
</script>
<hr /> 
<p>Trecho do c�digo para assinar a Url</p>
<pre class="brush: js; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;%
java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
String dominio = resourceBundle.getString("dominio");
%&gt;
&lt;form name='certificate' method='post' action='#'&gt;
	Certificado: &lt;%@include file='CertificateSelect.jsp'%&gt;&lt;br&gt;
	&lt;a href='javascript:signUrl();'&gt;Assinar a URL&lt;/a&gt;
	&lt;input type='hidden' name='pkcs7'&gt;
&lt;/form&gt;
 
&lt;script language='JavaScript'&gt;
function signUrl() {
	setCMSCallback(signed);
	// Veja que agora mudamos a fun��o chamada para signUrl, ao inv�s de signMessage utilizado anteriormente.
	ICPBravoApplet().signUrl(certificateSelect(), '<%= dominio %>/ICPBravoJSP/pages/GetContent.jsp', null, true);
}
 
function signed(pkcs7) {
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'SignVerify.jsp';
	document.certificate.submit();
}
&lt;/script&gt;</pre>
</body></html>
 