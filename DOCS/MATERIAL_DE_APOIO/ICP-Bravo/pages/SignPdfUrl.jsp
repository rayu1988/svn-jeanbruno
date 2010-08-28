<html>
<header>
	<title>ICPBravo - Assinatura de PDF</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<%
java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
String dominio = resourceBundle.getString("dominio");
%>

<h1 style='width:100%;text-align:center;'>Assinatura de PDF</h1>
<h3>Client-Side</h3>
<p>O presente exemplo visa demonstrar a capacidade de assinar um <a href='<%= dominio %>/ICPBravoJSP/pages/Sample.pdf'>PDF presente no servidor</a>.</p>
<p>Ao contrário dos outros exemplos, o produto desta assinatura será um PDF assinado com o pkcs#7 embutido e não um pkcs#7 apenas.</p>
<p>Ao final do teste, a assinatura será submetida ao servidor, para que esta possa ser verificada.</p>
<hr /> 

<%@include file='AppletICPBravo.jsp'%>
<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
	<a href='javascript:signPdf();'>Assinar um PDF</a>
	<input type='hidden' name='pdf'>
</form>
 
<script language='JavaScript'> 
function signPdf() {
	setCMSCallback(signed);
	// Veja que agora mudamos a função chamada para signUrl, ao invés de signMessage utilizado anteriormente.
	ICPBravoApplet().signPDFUrl(certificateSelect(), '<%= dominio %>/ICPBravoJSP/pages/Sample.pdf', 'Treinamento da API', 'Brasília/DF');
}
 
function signed(pdf) {
	document.certificate.pdf.value = pdf;
	document.certificate.action = 'SignPdfVerify.jsp';
	document.certificate.submit();
}
</script>
<hr />
<p>Trecho do código para assinar o PDF</p>
<pre class="brush: js; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;%
java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
String dominio = resourceBundle.getString("dominio");
%&gt;
&lt;form name='certificate' method='post' action='#'&gt;
	Certificado: &lt;%@include file='CertificateSelect.jsp'%&gt;&lt;br&gt;
	&lt;a href='javascript:signPdf();'&gt;Assinar um PDF&lt;/a&gt;
	&lt;input type='hidden' name='pkcs7'&gt;
&lt;/form&gt;
 
&lt;script language='JavaScript'&gt;
function signPdf() {
	setCMSCallback(signed);
	// Veja que agora mudamos a função chamada para signUrl, ao invés de signMessage utilizado anteriormente.
	ICPBravoApplet().signPDFUrl(certificateSelect(), '<%= dominio %>/ICPBravoJSP/pages/Sample.pdf', 'Treinamento da API', 'Brasília/DF');
}
 
function signed(pdf) {
	document.certificate.pkcs7.value = pdf;
	document.certificate.action = 'SignVerify.jsp';
	document.certificate.submit();
}
&lt;/script&gt;</pre>
</body></html>
 