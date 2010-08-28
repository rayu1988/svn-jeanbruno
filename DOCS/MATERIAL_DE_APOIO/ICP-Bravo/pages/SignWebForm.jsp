<html>
<header>
	<title>ICPBravo - Assinatura de formul�rio WEB</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>

<h1 style='width:100%;text-align:center;'>Assinatura de formul�rio WEB - Client Side</h1>
<h3>Client-Side</h3>
<p>O presente exemplo visa demonstrar a capacidade de assinar a p�gina WEB apresentada ao usu�rio, bem como os valores presentes nos campos, onde um XML � gerado para representar este novo conjunto de dados.</p>
<p>Para o nosso exemplo, um form ser� criado com alguns campos, onde ser� submetido a assinatura.</p>
<p>Este exemplo visa tamb�m demonstrar uma caracter�stica de solicitar um selo cronol�gico para a assinatura (poss�vel apenas se tivermos internet dispon�vel).</p>
<p>Notem que o processo de assinatura � bem semelhante ao exemplo onde assinamos uma string, alternando apenas o conte�do que ser� assinado.</p>
<p>Ao final do teste, a assinatura ser� submetida ao servidor, para que esta possa ser verificada.</p>
<hr />
 
<%@include file='AppletICPBravo.jsp'%>
<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
<table>
<tr><td colspan='2'><b>Formul�rio a ser assinado</b></tb></tr>
<tr><td><b>Campo 1</b></tb><td><input type='text' name='campo1'></td></tr>
<tr><td><b>Campo 2</b></tb><td><input type='text' name='campo2'></td></tr>
<tr><td><b>TST</b></tb><td><input type='text' name='tst' value='http://tsp.iaik.at/tsp/TspRequest'></td></tr>
</table>
	<a href='javascript:signCurrentForm();'>Assinar o formul�rio corrente</a>
	<input type='hidden' name='pkcs7'>
</form>
 
<script language='JavaScript'> 
function signCurrentForm() {
	var content = readCurrentDocument();
	alert('Formul�rio Corrente: \n'+content);
	setCMSCallback(signed);
	// Aqui n�s indicamos qual o provedor de TST que iremos utilizar
	ICPBravoApplet().setTstUrl(document.certificate.tst.value);
	// Bem semelhante ao exemplo de assinatura de string, chamamos o applet para assinar o formul�rio corrente
	ICPBravoApplet().signMessage(certificateSelect(), content, null, true);
}
 
function signed(pkcs7) {
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'SignVerify.jsp';
	document.certificate.submit();
}
</script>
<hr /> 
<p>Trecho do c�digo para assinar o formul�rio</p>
<pre class="brush: js; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;form name='certificate' method='post' action='#'&gt;
	Certificado: &lt;%@include file='CertificateSelect.jsp'%&gt;&lt;br&gt;
&lt;table&gt;
&lt;tr&gt;&lt;td colspan='2'&gt;&lt;b&gt;Formul�rio a ser assinado&lt;/b&gt;&lt;/tb&gt;&lt;/tr&gt;
&lt;tr&gt;&lt;td&gt;&lt;b&gt;Campo 1&lt;/b&gt;&lt;/tb&gt;&lt;td&gt;&lt;input type='text' name='campo1'&gt;&lt;/td&gt;&lt;/tr&gt;
&lt;tr&gt;&lt;td&gt;&lt;b&gt;Campo 2&lt;/b&gt;&lt;/tb&gt;&lt;td&gt;&lt;input type='text' name='campo2'&gt;&lt;/td&gt;&lt;/tr&gt;
&lt;tr&gt;&lt;td&gt;&lt;b&gt;TST&lt;/b&gt;&lt;/tb&gt;&lt;td&gt;&lt;input type='text' name='tst' value='http://tsp.iaik.at/tsp/TspRequest'&gt;&lt;/td&gt;&lt;/tr&gt;
&lt;/table&gt;
	&lt;a href='javascript:signCurrentForm();'&gt;Assinar o formul�rio corrente&lt;/a&gt;
	&lt;input type='hidden' name='pkcs7'&gt;
&lt;/form&gt;
 
&lt;script language='JavaScript'&gt;
function signCurrentForm() {
	var content = readCurrentDocument();
	alert('Formul�rio Corrente: \n'+content);
	setCMSCallback(signed);
	// Aqui n�s indicamos qual o provedor de TST que iremos utilizar
	ICPBravoApplet().setTstUrl(document.certificate.tst.value);
	// Bem semelhante ao exemplo de assinatura de string, chamamos o applet para assinar o formul�rio corrente
	ICPBravoApplet().signMessage(certificateSelect(), content, null, true);
}
 
function signed(pkcs7) {
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'SignVerify.jsp';
	document.certificate.submit();
}
&lt;/script&gt;
</pre>
</body></html>
	