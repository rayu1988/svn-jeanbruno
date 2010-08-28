<html>
<header>
	<title>ICPBravo - Assinatura de PDF remoto</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Assinatura de PDF remoto</h1>
<h3>Client-Side e Server-Side</h3>
<p>Este exemplo tem o mesmo fluxo que foi apresentado na assinatura remota, porém o produto da assinatura é um PDF assinado.</p>
<hr />
<%@include file='AppletICPBravo.jsp'%>

<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
	<a href='javascript:signRemote();'>Assinar o conteúdo remoto</a>
	<input type='hidden' name='pdf'>
</form>
 
<script src='json_parse.js'></script>
<script language='JavaScript'> 
function createXMLHttpRequest() {
	if( typeof XMLHttpRequest == "undefined" ) XMLHttpRequest = function() {
		try { return new ActiveXObject("Msxml2.XMLHTTP.6.0") } catch(e) {}
		try { return new ActiveXObject("Msxml2.XMLHTTP.3.0") } catch(e) {}
		try { return new ActiveXObject("Msxml2.XMLHTTP") } catch(e) {}
		try { return new ActiveXObject("Microsoft.XMLHTTP") } catch(e) {}
		throw new Error( "This browser does not support XMLHttpRequest." )
	};
	return new XMLHttpRequest();
}
 
var AJAX = createXMLHttpRequest();
 
function signed() {
	if(AJAX.readyState == 4 && AJAX.status == 200) {
		var myJsonObj = json_parse(AJAX.responseText);
		if (myJsonObj.err) {
			alert('Erro: '+myJsonObj.err);
		} else {
alert('Passo 5 - O Servidor nos devolve a assinatura pronta.\nPDF: '+myJsonObj.signPDF);
			document.certificate.pdf.value = myJsonObj.signPDF;
			document.certificate.action = 'SignPdfVerify.jsp';
			document.certificate.submit();
		}
	}else if (AJAX.readyState == 4 && AJAX.status != 200) {
		alert('Erro');
	}
}
 
function bytesSigned(signature) {
alert('Passo 4 - O applet nos devolve a assinatura do hash, para que possamos enviar para o servidor compor o pacote de assinatura.\nHash Assinado: '+signature);
	AJAX.onreadystatechange = signed;
	AJAX.open("GET", "ServerSignPdf.jsp?signature="+signature);
	AJAX.send("");
}
 
function serverPreSign() {
	if(AJAX.readyState == 4 && AJAX.status == 200) {
		var myJsonObj = json_parse(AJAX.responseText);
		if (myJsonObj.err) {
			alert('Erro: '+myJsonObj.err);
		} else {
alert('Passo 3 - O servidor nos devolve o hash do pkcs#9 para que possamos invocar o applet para assinar.\nHash Pkcs#9: '+myJsonObj.toSign);
			setCMSCallback(bytesSigned);
			ICPBravoApplet().signSimpleBytes(certificateSelect(), myJsonObj.toSign);
		}
	}else if (AJAX.readyState == 4 && AJAX.status != 200) {
		alert('Erro');
	}
}
 
function pegaCertificadoB64(prop, value) {
alert('Passo 2 - O applet nos devolve o certificado e invocamos o servidor em ajax para criar o pacote de assinatura.\nCertificado: '+value);
	AJAX.onreadystatechange = serverPreSign;
	AJAX.open("GET", "ServerSignPdf.jsp?cert="+value);
	AJAX.send("");
}
 
function signRemote() {
alert('Passo 1 - Solicitar ao applet o certificado codificado');
	// Pega o certificado corrente, codificado em PEM, e chama a callback pegaCertificadoB64 
	setOnSetCertificateProperties(pegaCertificadoB64);
	ICPBravoApplet().getCertificateProperties(certificateSelect(), 'PEM');
}
 
</script>

<hr /> 
<p>Para este exemplo, criamos então o seguinte código para o cliente:</p>
<pre class="brush: js; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;form name='certificate' method='post' action='#'&gt;
	Certificado: &lt;%@include file='CertificateSelect.jsp'%&gt;&lt;br&gt;
	&lt;a href='javascript:signRemote();'&gt;Assinar o conteúdo remoto&lt;/a&gt;
	&lt;input type='hidden' name='pdf'&gt;
&lt;/form&gt;
 
&lt;script src='json_parse.js'&gt;&lt;/script&gt;
&lt;script language='JavaScript'&gt;
function createXMLHttpRequest() {
	if( typeof XMLHttpRequest == "undefined" ) XMLHttpRequest = function() {
		try { return new ActiveXObject("Msxml2.XMLHTTP.6.0") } catch(e) {}
		try { return new ActiveXObject("Msxml2.XMLHTTP.3.0") } catch(e) {}
		try { return new ActiveXObject("Msxml2.XMLHTTP") } catch(e) {}
		try { return new ActiveXObject("Microsoft.XMLHTTP") } catch(e) {}
		throw new Error( "This browser does not support XMLHttpRequest." )
	};
	return new XMLHttpRequest();
}
 
var AJAX = createXMLHttpRequest();
 
function signed() {
	if(AJAX.readyState == 4 && AJAX.status == 200) {
		var myJsonObj = json_parse(AJAX.responseText);
		if (myJsonObj.err) {
			alert('Erro: '+myJsonObj.err);
		} else {
alert('Passo 5 - O Servidor nos devolve a assinatura pronta.\nPDF: '+myJsonObj.signPDF);
			document.certificate.pdf.value = myJsonObj.signPDF;
			document.certificate.action = 'SignPdfVerify.jsp';
			document.certificate.submit();
		}
	}else if (AJAX.readyState == 4 && AJAX.status != 200) {
		alert('Erro');
	}
}
 
function bytesSigned(signature) {
alert('Passo 4 - O applet nos devolve a assinatura do hash, para que possamos enviar para o servidor compor o pacote de assinatura.\nHash Assinado: '+signature);
	AJAX.onreadystatechange = signed;
	AJAX.open("GET", "ServerSignPdf.jsp?signature="+signature);
	AJAX.send("");
}
 
function serverPreSign() {
	if(AJAX.readyState == 4 && AJAX.status == 200) {
		var myJsonObj = json_parse(AJAX.responseText);
		if (myJsonObj.err) {
			alert('Erro: '+myJsonObj.err);
		} else {
alert('Passo 3 - O servidor nos devolve o hash do pkcs#9 para que possamos invocar o applet para assinar.\nHash Pkcs#9: '+myJsonObj.toSign);
			setCMSCallback(bytesSigned);
			ICPBravoApplet().signSimpleBytes(certificateSelect(), myJsonObj.toSign);
		}
	}else if (AJAX.readyState == 4 && AJAX.status != 200) {
		alert('Erro');
	}
}
 
function pegaCertificadoB64(prop, value) {
alert('Passo 2 - O applet nos devolve o certificado e invocamos o servidor em ajax para criar o pacote de assinatura.\nCertificado: '+value);
	AJAX.onreadystatechange = serverPreSign;
	AJAX.open("GET", "ServerSignPdf.jsp?cert="+value);
	AJAX.send("");
}
 
function signRemote() {
alert('Passo 1 - Solicitar ao applet o certificado codificado');
	// Pega o certificado corrente, codificado em PEM, e chama a callback pegaCertificadoB64 
	setOnSetCertificateProperties(pegaCertificadoB64);
	ICPBravoApplet().getCertificateProperties(certificateSelect(), 'PEM');
}
 
&lt;/script&gt;
</pre>
<hr />
<p>E o seguinte código para o servidor Ajax:</p>
<pre class="brush: java; html-script: true;">
&lt;%@page contentType="text/html; charset=UTF-8"%&gt;
&lt;%@page import="org.json.simple.JSONObject"%&gt;
&lt;%
	JSONObject obj=new JSONObject();
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = br.com.oaks.ICPBravo.manager.ICPBravoManager.getDefaultManager();
		br.com.oaks.ICPBravo.util.PDFSignedData signPDF = (br.com.oaks.ICPBravo.util.PDFSignedData) session.getAttribute("currentSign");
		// Neste exemplo, os objetos necessários para a assinatura serão armazenados em sessão, para que possamos quebrar em duas etapas. 
		if (signPDF == null) { // Criar uma nova assinatura
System.out.println("Nova Assinatura");
			String certsPEM=request.getParameter("cert");
System.out.println("cert: "+certsPEM);
			if (certsPEM != null) {
				br.com.oaks.ICPBravo.certs.ICPBravoCertificate signer = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromPEMAndChain(certsPEM, manager, "signatario");
				signPDF = new br.com.oaks.ICPBravo.util.PDFSignedData(manager, resourceBundle.getString("home")+"pages/Sample.pdf");
				session.setAttribute("currentSign", signPDF);
				br.com.oaks.ICPBravo.cms.CMSSignerInformation si = signPDF.addSigner(signer);
				session.setAttribute("currentSigner", si);
				byte [] toSign = si.generateSignedAttributes().getDEREncoded();
				String toSignB64 = new sun.misc.BASE64Encoder().encode(toSign);
System.out.println("toSign: "+toSignB64);
				obj.put("toSign",toSignB64);
			} else {
				obj.put("err","cert is null");
			}
		} else { // Concluir a assinatura
System.out.println("Continuação da Assinatura");
			br.com.oaks.ICPBravo.cms.CMSSignerInformation si = (br.com.oaks.ICPBravo.cms.CMSSignerInformation) session.getAttribute("currentSigner"); 
			String signPEM=request.getParameter("signature");
System.out.println("sign: "+signPEM);
//			signPEM = java.net.URLDecoder.decode(signPEM,"UTF-8");
			si.setSign(new sun.misc.BASE64Decoder().decodeBuffer(signPEM));
			byte[] pdfEncoded = signPDF.getPdfEncoded();
			String pdfEncodedB64 = new sun.misc.BASE64Encoder().encode(pdfEncoded); 
System.out.println("signPDF: "+pdfEncodedB64);
			obj.put("signPDF", pdfEncodedB64);
			
			// Limpa a sessão para uma nova assinatura
			session.removeAttribute("currentSigner");
			session.removeAttribute("currentSign");
		}
	} catch (Exception e) {
		e.printStackTrace();
		obj.put("err",e.getMessage());
	}
	out.print(obj);
	out.flush();
%&gt;
</pre>
</body></html>
