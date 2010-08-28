<html>
<header>
	<title>ICPBravo - Decriptografar String</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Decriptografar String</h1>
<h3>Client-Side</h3>
<p>Nesta página buscaremos exemplificar o recebimento de um pacote criptografado, onde tentaremos a descriptografia com o certificado a quem o pacote foi endereçado.</p>
<hr />
<%@include file='AppletICPBravo.jsp'%>
<%
	String pkcs7=request.getParameter("pkcs7");
	pkcs7 = pkcs7.replaceAll("\\+","#");
	pkcs7 = java.net.URLDecoder.decode(pkcs7,"UTF-8");
	pkcs7 = pkcs7.replaceAll("#", "\\+");
	pkcs7 = pkcs7.replaceAll("\r","").replaceAll("\n","");
%>

<form name='certificate' method='post' action='#'>
	Decriptografar com: <%@include file='CertificateSelect.jsp'%><br>
	<input type='button' value='Descriptografar' onclick='javascript:decrypt();'><br>
	Mensagem Criptografada: <%= pkcs7 %>
</form>

<script language='JavaScript'>
	function decrypt() {
		var applet = ICPBravoApplet(); 
		setFinalizeOperationCallback(notDecrypted);
		applet.decrypEnvelopedData('<%= pkcs7 %>', certificateSelect());
	}
	
	function notDecrypted(msg) {
		alert('Não foi possível decriptografar: '+msg);
	}
	
	function decrypted(decryptedData) {
		alert('A mensagem criptografada é: '+decryptedData);
	}
		
	setCMSCallback(decrypted);
</script>
<hr />
<p>Trecho do código para criptografar a string</p>
<pre class="brush: java; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;%
	String pkcs7=request.getParameter("pkcs7");
	pkcs7 = pkcs7.replaceAll("\\+","#");
	pkcs7 = java.net.URLDecoder.decode(pkcs7,"UTF-8");
	pkcs7 = pkcs7.replaceAll("#", "\\+");
	pkcs7 = pkcs7.replaceAll("\r","").replaceAll("\n","");
%&gt;

&lt;form name='certificate' method='post' action='#'&gt;
	Decriptografar com: &lt;%@include file='CertificateSelect.jsp'%&gt;&lt;br&gt;
	&lt;input type='button' value='Descriptografar' onclick='javascript:decrypt();'&gt;&lt;br&gt;
	Mensagem Criptografada: &lt;%= pkcs7 %&gt;
&lt;/form&gt;

&lt;script language='JavaScript'&gt;
	function decrypt() {
		var applet = ICPBravoApplet(); 
		setFinalizeOperationCallback(notDecrypted);
		applet.decrypEnvelopedData('&lt;%= pkcs7 %&gt;', certificateSelect());
	}
	
	function notDecrypted(msg) {
		alert('Não foi possível decriptografar: '+msg);
	}
	
	function decrypted(decryptedData) {
		alert('A mensagem criptografada é: '+decryptedData);
	}
		
	setCMSCallback(decrypted);
&lt;/script&gt;</pre>

</body></html>
