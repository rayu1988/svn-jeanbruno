<html>
<header>
	<title>ICPBravo - Assinatura de conteúdo remoto</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Assinatura de conteúdo remoto</h1>
<h3>Client-Side e Server-Side</h3>
<p>Agora iremos demostrar uma característica um pouco mais avançada.</p>
<p>Neste exemplo, nós utilizaremos o cliente para gerar a criptografia da assinatura, porém a pacote da assinatura será montado no servidor.</p>
<p>Esta característica é útil em casos onde o conteúdo a ser assinado está presente no servidor e é muito grande para ser enviado para a máquina cliente.</p>
<p>Para este caso, iremos quebrar a assinatura em alguns passos:</p>
<ul>
<li>Primeiramente iremos solicitar ao applet que leia o certificado (e sua respectiva cadeia) do dispositivo de assinatura, onde o applet nos devolverá estes dados codificado em base64;</li>
<li>Em seguida enviaremos este certificado para o servidor (por meio de Ajax), onde ele criará o pacote de assinatura com seus atributos (pkcs#9) e nos devolverá o Hash deste para que possamos assinar;</li>
<li>Ao receber o Hash do pkcs#9 do servidor, enviaremos este para o applet assinar;</li>
<li>O Applet nos devolverá a assinatura deste Hash, onde será devolvida para o servidor (também em Ajax) terminar de completar o pacote de assinatura (pkcs#7);</li>
<li>O Servidor nos devolve o pkcs#7 pronto. Neste exemplo, a mesma tela de verificação de assinatura será utilizada.</li>
</ul>
<p>Como podemos notar, este exemplo demandou a criação de um servidor ajax para podermos criar o nosso pacote de assinatura server-side.</p>
<hr />
<%@include file='AppletICPBravo.jsp'%>

<form name='certificate' method='post' action='#'>
	Certificado: <%@include file='CertificateSelect.jsp'%><br>
	<a href='javascript:signRemote();'>Assinar o conteúdo remoto</a>
	<input type='hidden' name='pkcs7'>
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
alert('Passo 5 - O Servidor nos devolve a assinatura pronta.\nPkcs#7: '+myJsonObj.signCMS);
			document.certificate.pkcs7.value = myJsonObj.signCMS;
			document.certificate.action = 'SignVerify.jsp';
			document.certificate.submit();
		}
	}else if (AJAX.readyState == 4 && AJAX.status != 200) {
		alert('Erro');
	}
}
 
function bytesSigned(signature) {
alert('Passo 4 - O applet nos devolve a assinatura do hash, para que possamos enviar para o servidor compor o pacote de assinatura.\nHash Assinado: '+signature);
	AJAX.onreadystatechange = signed;
	AJAX.open("GET", "ServerSign.jsp?signature="+signature);
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
	AJAX.open("GET", "ServerSign.jsp?cert="+value);
	AJAX.send("");
}
 
function signRemote() {
alert('Passo 1 - Solicitar ao applet o certificado codificado');
	// Pega o certificado corrente, com sua respectiva cadeia, codificado em PEM, e chama a callback pegaCertificadoB64 
	setOnSetCertificateProperties(pegaCertificadoB64);
	ICPBravoApplet().getCertificateProperties(certificateSelect(), 'P7B');
}
 
</script>
 
<hr /> 
<p>Para este exemplo, criamos então o seguinte código para o cliente:</p>
<pre class="brush: js; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;form name='certificate' method='post' action='#'&gt;
	Certificado: &lt;%@include file='CertificateSelect.jsp'%&gt;&lt;br&gt;
	&lt;a href='javascript:signRemote();'&gt;Assinar o conteúdo remoto&lt;/a&gt;
	&lt;input type='hidden' name='pkcs7'&gt;
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
alert('Passo 5 - O Servidor nos devolve a assinatura pronta.\nPkcs#7: '+myJsonObj.signCMS);
			document.certificate.pkcs7.value = myJsonObj.signCMS;
			document.certificate.action = 'SignVerify.jsp';
			document.certificate.submit();
		}
	}else if (AJAX.readyState == 4 && AJAX.status != 200) {
		alert('Erro');
	}
}
 
function bytesSigned(signature) {
alert('Passo 4 - O applet nos devolve a assinatura do hash, para que possamos enviar para o servidor compor o pacote de assinatura.\nHash Assinado: '+signature);
	AJAX.onreadystatechange = signed;
	AJAX.open("GET", "ServerSign.jsp?signature="+signature);
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
	AJAX.open("GET", "ServerSign.jsp?cert="+value);
	AJAX.send("");
}
 
function signRemote() {
alert('Passo 1 - Solicitar ao applet o certificado codificado');
	// Pega o certificado corrente, codificado em PEM, e chama a callback pegaCertificadoB64 
	setOnSetCertificateProperties(pegaCertificadoB64);
	ICPBravoApplet().getCertificateProperties(certificateSelect(), 'P7B');
}
 
&lt;/script&gt;
</pre>
<p>E o seguinte código para o servidor Ajax:</p>
<pre class="brush: java; html-script: true;">
&lt;%@page contentType="text/html; charset=UTF-8"%&gt;
&lt;%@page import="org.json.simple.JSONObject"%&gt;
&lt;%
	JSONObject obj=new JSONObject();
	try {
		// Neste exemplo, os objetos necessários para a assinatura serão armazenados em sessão, para que possamos quebrar em duas etapas. 
		byte [] conteudoAssinado = "test".getBytes();
		
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate signer = (br.com.oaks.ICPBravo.certs.ICPBravoCertificate) session.getAttribute("signer");  
		if (signer == null) { // Fase 1 - Onde serão criados os atributos a assinar.
System.out.println("Fase 1");
			String certsP7B=request.getParameter("cert");
			certsP7B = br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(certsP7B);
			signer = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromP7BPEM(certsP7B, br.com.oaks.ICPBravo.manager.ICPBravoManager.getDefaultManager(), "signatario");
			br.com.oaks.ICPBravo.cms.CMSSignedData signCMS = new br.com.oaks.ICPBravo.cms.CMSSignedData();

			signCMS.setData(conteudoAssinado);
			br.com.oaks.ICPBravo.cms.policies.Policy policy = new br.com.oaks.ICPBravo.cms.policies.AdCpPolicy();
			br.com.oaks.ICPBravo.cms.CMSSignerInformation si = signCMS.addSigner(signer, policy);
			byte [] toSign = si.generateSignedAttributes().getDEREncoded();
			String toSignB64 = new sun.misc.BASE64Encoder().encode(toSign);
System.out.println("toSign: "+toSignB64);

			session.setAttribute("signer", signer);
			session.setAttribute("toSign", toSign);
			obj.put("toSign",toSignB64);
		} else { // Fase 2 - Concluir a assinatura
System.out.println("Fase 2");
			String signB64=request.getParameter("signature");
System.out.println("sign: "+signB64);
			signB64 = br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(signB64);
			byte [] sign = new sun.misc.BASE64Decoder().decodeBuffer(signB64);
			
			byte [] toSign = (byte []) session.getAttribute("toSign");

			br.com.oaks.ICPBravo.cms.CMSSignedData signCMS = new br.com.oaks.ICPBravo.cms.CMSSignedData();
			signCMS.setEncapsulate(true);
			signCMS.setData(conteudoAssinado);
			br.com.oaks.ICPBravo.cms.CMSSignerInformation si = signCMS.addSigner(signer);
			si.setSign(toSign, sign);
			
			byte[] cmsEncoded = signCMS.getASN1Encoded();
			String cmsEncodedB64 = new sun.misc.BASE64Encoder().encode(cmsEncoded); 
			
System.out.println("signCMS: "+cmsEncodedB64);
			obj.put("signCMS", cmsEncodedB64);
			
			// Limpa a sessão para uma nova assinatura
			session.removeAttribute("signer");
			session.removeAttribute("toSign");
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
	