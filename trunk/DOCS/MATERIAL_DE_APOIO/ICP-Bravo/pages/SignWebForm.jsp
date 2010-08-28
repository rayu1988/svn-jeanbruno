<html>
<header>
	<title>ICPBravo - Assinatura de formulário WEB</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>

<h1 style='width:100%;text-align:center;'>Assinatura de formulário WEB - Client Side</h1>
<h3>Client-Side</h3>
<p>O presente exemplo visa demonstrar a capacidade de assinar a página WEB apresentada ao usuário, bem como os valores presentes nos campos, onde um XML é gerado para representar este novo conjunto de dados.</p>
<p>Para o nosso exemplo, um form será criado com alguns campos, onde será submetido a assinatura.</p>
<p>Este exemplo visa também demonstrar uma característica de solicitar um selo cronológico para a assinatura (possível apenas se tivermos internet disponível).</p>
<p>Notem que o processo de assinatura é bem semelhante ao exemplo onde assinamos uma string, alternando apenas o conteúdo que será assinado.</p>
<p>Ao final do teste, a assinatura será submetida ao servidor, para que esta possa ser verificada.</p>
<hr />
 
<%@include file='AppletICPBravo.jsp'%>
<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
<table>
<tr><td colspan='2'><b>Formulário a ser assinado</b></tb></tr>
<tr><td><b>Campo 1</b></tb><td><input type='text' name='campo1'></td></tr>
<tr><td><b>Campo 2</b></tb><td><input type='text' name='campo2'></td></tr>
<tr><td><b>TST</b></tb><td><input type='text' name='tst' value='http://tsp.iaik.at/tsp/TspRequest'></td></tr>
</table>
	<a href='javascript:signCurrentForm();'>Assinar o formulário corrente</a>
	<input type='hidden' name='pkcs7'>
</form>
 
<script language='JavaScript'> 
function signCurrentForm() {
	var content = readCurrentDocument();
	alert('Formulário Corrente: \n'+content);
	setCMSCallback(signed);
	// Aqui nós indicamos qual o provedor de TST que iremos utilizar
	ICPBravoApplet().setTstUrl(document.certificate.tst.value);
	// Bem semelhante ao exemplo de assinatura de string, chamamos o applet para assinar o formulário corrente
	ICPBravoApplet().signMessage(certificateSelect(), content, null, true);
}
 
function signed(pkcs7) {
	document.certificate.pkcs7.value = pkcs7;
	document.certificate.action = 'SignVerify.jsp';
	document.certificate.submit();
}
</script>
<hr /> 
<p>Trecho do código para assinar o formulário</p>
<pre class="brush: js; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;form name='certificate' method='post' action='#'&gt;
	Certificado: &lt;%@include file='CertificateSelect.jsp'%&gt;&lt;br&gt;
&lt;table&gt;
&lt;tr&gt;&lt;td colspan='2'&gt;&lt;b&gt;Formulário a ser assinado&lt;/b&gt;&lt;/tb&gt;&lt;/tr&gt;
&lt;tr&gt;&lt;td&gt;&lt;b&gt;Campo 1&lt;/b&gt;&lt;/tb&gt;&lt;td&gt;&lt;input type='text' name='campo1'&gt;&lt;/td&gt;&lt;/tr&gt;
&lt;tr&gt;&lt;td&gt;&lt;b&gt;Campo 2&lt;/b&gt;&lt;/tb&gt;&lt;td&gt;&lt;input type='text' name='campo2'&gt;&lt;/td&gt;&lt;/tr&gt;
&lt;tr&gt;&lt;td&gt;&lt;b&gt;TST&lt;/b&gt;&lt;/tb&gt;&lt;td&gt;&lt;input type='text' name='tst' value='http://tsp.iaik.at/tsp/TspRequest'&gt;&lt;/td&gt;&lt;/tr&gt;
&lt;/table&gt;
	&lt;a href='javascript:signCurrentForm();'&gt;Assinar o formulário corrente&lt;/a&gt;
	&lt;input type='hidden' name='pkcs7'&gt;
&lt;/form&gt;
 
&lt;script language='JavaScript'&gt;
function signCurrentForm() {
	var content = readCurrentDocument();
	alert('Formulário Corrente: \n'+content);
	setCMSCallback(signed);
	// Aqui nós indicamos qual o provedor de TST que iremos utilizar
	ICPBravoApplet().setTstUrl(document.certificate.tst.value);
	// Bem semelhante ao exemplo de assinatura de string, chamamos o applet para assinar o formulário corrente
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
	