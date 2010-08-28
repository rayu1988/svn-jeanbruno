<html>
<header>
	<title>ICPBravo - Geração de certificado de usuário</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1>Geração de certificado de usuário</h1>
<h3>Client-Side e Server-Side</h3>
<p>O exemplo corrente visa demonstrar a criação de um certificado de usuário por meio de uma requisição de certificado assinado (CSR).</p>
<p>Os passos para esta demonstração são os seguintes:</p>
<ul>
<li>O usuário preenche o formulário com os dados para a geração do CSR, e envia para o Applet para que seja gerado no dispositivo <i>(o que implica em solicitar ao dispositivo a geração do par de chaves)</i>;</li>
<li>O applet devolve o CSR pronto, onde será enviado para para o servidor gerar o certificado;</li>
<li>O servidor cria e nos devolve o certificado (em formato pkcs#7) para que possa ser instalado no dispositivo.</li>
</ul>
<p>Como este exemplo tem características que demandam bastante interação entre o applet e o servidor, faremos uso de uma aplicação em Ajax.</p>
<hr />
<%@include file='AppletICPBravo.jsp'%>
<form name='csrForm'>
Gerenciador: <select name='manager'><option value='PKCS11'>PKCS#11</option><option value='PKCS12' selected>PKCS#12</option><option value='Windows-MY'>CapiCOM</option></select><br />
Alias: <input type='text' name='alias' value='Bira'><br /> 

Common Name: <input type='text' name='cn_cn' style='width:800px;' value='Ubiratan Elias'><br /> 
Organizational: <input type='text' name='cn_o' style='width:800px;' value='OAK Soluções Empresariais LTDA'><br /> 
Organization Unit Name: <input type='text' name='cn_ou' style='width:800px;' value='Desenvolvimento'><br /> 
Locality Name: <input type='text' name='cn_l' style='width:800px;' value='Brasília'><br /> 
State: <input type='text' name='cn_st' style='width:800px;' value='Distrito Federal'><br /> 
Country: <input type='text' name='cn_c' style='width:800px;' value='BR'><br /> 

Número de Série: <input type='text' name='numSerie' value='2'><br /> 
Validade (em dias): <input type='text' name='validade' value='31'><br /> 
Email: <input type='text' name='email' style='width:500px;' value='UbiratanElias@gmail.com'><br /> 
Data de nascimento (yyyymmdd): <input type='text' name='nascimento' value='19691226'><br /> 
CPF (sem pontos): <input type='text' name='cpf' value='46133011149'><br /> 
NIS: <input type='text' name='nis'><br /> 
RG (sem pontos): <input type='text' name='rg' value='1175606'><br /> 
Orgão Expedidor: <input type='text' name='oe' value='SSPDF'><br /> 
Título de Eleitor: <input type='text' name='tituloEleitor'><br /> 
Zona: <input type='text' name='zona'><br /> 
Sessão: <input type='text' name='sessao'><br /> 
Município/UF: <input type='text' name='municipioUf'><br /> 
INSS: <input type='text' name='inss'><br />
<input type='button' id='gerar' value='Gerar' onclick='javascript:gerarCSR()'>
<input type='hidden' name='certificateAndChain'> 
</form>

<script src='json_parse.js'></script>
<script language='JavaScript'> 
	function getComboValue(combo) {
		for(var a=0; a<combo.options.length; a++)
		{
			if (combo.options[a].selected)
				return combo.options[a].value;
		}
		return null;
	}
	
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

	function enableSelectButtons() {
		document.getElementById('gerar').disabled = false;
	}
	
	setAppletLoadedCallback(enableSelectButtons);	
	 
	function certificadoInstalado(msg) {
		if (msg == 'ok') {
			alert('Certificado instalado');
			document.csrForm.action = 'ViewCertificate.jsp';
			document.csrForm.submit();
		} else {
			alert('Erro na instalação do certificado.\n'+msg);
		}
	}
	
	function certificadoGerado() {
		if(AJAX.readyState == 4 && AJAX.status == 200) {
			var myJsonObj = json_parse(AJAX.responseText);
			if (myJsonObj.err) {
				alert('Erro: '+myJsonObj.err);
			} else {
alert('Passo 3-O servidor devolve o certificado, onde será enviado para o applet instalar.\n'+myJsonObj.certificate);
				document.csrForm.certificateAndChain.value = escape(myJsonObj.certificate);
				setFinalizeOperationCallback(certificadoInstalado);
				ICPBravoApplet().saveCartificate(null, document.csrForm.alias.value, myJsonObj.certificate);
			}
		}else if (AJAX.readyState == 4 && AJAX.status != 200) {
			alert('Erro');
		}
	}
	
	function csrGerado(prop, value) {
alert('Passo 2-O applet devolve o CSR pronto, onde será enviado para o servidor.\n'+value);
		var url = "ServerGenerateCertificate.jsp?csr="+escape(value)+
			"&numSerie="+document.csrForm.numSerie.value+
			"&alias="+document.csrForm.alias.value+
			"&email="+escape(document.csrForm.email.value)+
			"&nascimento="+document.csrForm.nascimento.value+
			"&cpf="+document.csrForm.cpf.value+
			"&nis="+document.csrForm.nis.value+
			"&rg="+document.csrForm.rg.value+
			"&oe="+document.csrForm.oe.value+
			"&tituloEleitor="+document.csrForm.tituloEleitor.value+
			"&zona="+document.csrForm.zona.value+
			"&sessao="+document.csrForm.sessao.value+
			"&municipioUf="+document.csrForm.municipioUf.value+
			"&inss="+document.csrForm.inss.value;
	    while (url.indexOf('\n') >= 0)
	        url = url.replace('\n', '');
			
		AJAX.onreadystatechange = certificadoGerado;
		AJAX.open("GET", url);
		AJAX.send("");
	}

	function gerarCSR() {
		var dn = 'CN='+document.csrForm.cn_cn.value+
			', O='+document.csrForm.cn_o.value+
			', OU='+document.csrForm.cn_ou.value+
			', L='+document.csrForm.cn_l.value+
			', ST='+document.csrForm.cn_st.value+
			', C='+document.csrForm.cn_c.value;
		var mgr=getComboValue(document.csrForm.manager);
alert('Passo 1-Solicitando ao applet a geração de um CSR no dispositivo '+mgr+' com a string '+dn+'.');
		setOnSetCertificateProperties(csrGerado);
		ICPBravoApplet().generateCSR(mgr, document.csrForm.alias.value, dn, document.csrForm.validade.value);
	}
</script>

<hr />
<p>O código para o nosso exemplo segue abaixo:</p>
<pre class="brush: js; html-script: true;">
&lt;%@include file='AppletICPBravo.jsp'%&gt;
&lt;form name='csrForm'&gt;
Gerenciador: &lt;select name='manager'&gt;&lt;option value='PKCS11'&gt;PKCS#11&lt;/option&gt;&lt;option value='PKCS12' selected&gt;PKCS#12&lt;/option&gt;&lt;option value='Windows-MY'&gt;CapiCOM&lt;/option&gt;&lt;/select&gt;&lt;br /&gt;
Alias: &lt;input type='text' name='alias' value='Bira'&gt;&lt;br /&gt; 

Common Name: &lt;input type='text' name='cn_cn' style='width:800px;' value='Ubiratan Elias'&gt;&lt;br /&gt; 
Organizational: &lt;input type='text' name='cn_o' style='width:800px;' value='OAK Soluções Empresariais LTDA'&gt;&lt;br /&gt; 
Organization Unit Name: &lt;input type='text' name='cn_ou' style='width:800px;' value='Desenvolvimento'&gt;&lt;br /&gt; 
Locality Name: &lt;input type='text' name='cn_l' style='width:800px;' value='Brasília'&gt;&lt;br /&gt; 
State: &lt;input type='text' name='cn_st' style='width:800px;' value='Distrito Federal'&gt;&lt;br /&gt; 
Country: &lt;input type='text' name='cn_c' style='width:800px;' value='BR'&gt;&lt;br /&gt; 

Número de Série: &lt;input type='text' name='numSerie' value='2'&gt;&lt;br /&gt; 
Validade (em dias): &lt;input type='text' name='validade' value='31'&gt;&lt;br /&gt; 
Email: &lt;input type='text' name='email' style='width:500px;' value='UbiratanElias@gmail.com'&gt;&lt;br /&gt; 
Data de nascimento (yyyymmdd): &lt;input type='text' name='nascimento' value='19691226'&gt;&lt;br /&gt; 
CPF (sem pontos): &lt;input type='text' name='cpf' value='46133011149'&gt;&lt;br /&gt; 
NIS: &lt;input type='text' name='nis'&gt;&lt;br /&gt; 
RG (sem pontos): &lt;input type='text' name='rg' value='1175606'&gt;&lt;br /&gt; 
Orgão Expedidor: &lt;input type='text' name='oe' value='SSPDF'&gt;&lt;br /&gt; 
Título de Eleitor: &lt;input type='text' name='tituloEleitor'&gt;&lt;br /&gt; 
Zona: &lt;input type='text' name='zona'&gt;&lt;br /&gt; 
Sessão: &lt;input type='text' name='sessao'&gt;&lt;br /&gt; 
Município/UF: &lt;input type='text' name='municipioUf'&gt;&lt;br /&gt; 
INSS: &lt;input type='text' name='inss'&gt;&lt;br /&gt;
&lt;input type='button' id='gerar' value='Gerar' onclick='javascript:gerarCSR()'&gt;
&lt;input type='hidden' name='certificateAndChain'&gt; 
&lt;/form&gt;

&lt;script src='json_parse.js'&gt;&lt;/script&gt;
&lt;script language='JavaScript'&gt; 
	function getComboValue(combo) {
		for(var a=0; a&lt;combo.options.length; a++)
		{
			if (combo.options[a].selected)
				return combo.options[a].value;
		}
		return null;
	}
	
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

	function enableSelectButtons() {
		document.getElementById('gerar').disabled = false;
	}
	
	setAppletLoadedCallback(enableSelectButtons);	
	 
	function certificadoInstalado(msg) {
		if (msg == 'ok') {
			alert('Certificado instalado');
			document.csrForm.action = 'ViewCertificate.jsp';
			document.csrForm.submit();
		} else {
			alert('Erro na instalação do certificado.\n'+msg);
		}
	}
	
	function certificadoGerado() {
		if(AJAX.readyState == 4 && AJAX.status == 200) {
			var myJsonObj = json_parse(AJAX.responseText);
			if (myJsonObj.err) {
				alert('Erro: '+myJsonObj.err);
			} else {
alert('Passo 3-O servidor devolve o certificado, onde será enviado para o applet instalar.\n'+myJsonObj.certificate);
				document.csrForm.certificateAndChain.value = myJsonObj.certificate;
				setFinalizeOperationCallback(certificadoInstalado);
				ICPBravoApplet().saveCartificate(null, document.csrForm.alias.value, myJsonObj.certificate);
			}
		}else if (AJAX.readyState == 4 && AJAX.status != 200) {
			alert('Erro');
		}
	}
	
	function csrGerado(prop, value) {
alert('Passo 2-O applet devolve o CSR pronto, onde será enviado para o servidor.\n'+value);
		var url = "ServerGenerateCertificate.jsp?csr="+escape(value)+
			"&numSerie="+document.csrForm.numSerie.value+
			"&alias="+document.csrForm.alias.value+
			"&email="+escape(document.csrForm.email.value)+
			"&nascimento="+document.csrForm.nascimento.value+
			"&cpf="+document.csrForm.cpf.value+
			"&nis="+document.csrForm.nis.value+
			"&rg="+document.csrForm.rg.value+
			"&oe="+document.csrForm.oe.value+
			"&tituloEleitor="+document.csrForm.tituloEleitor.value+
			"&zona="+document.csrForm.zona.value+
			"&sessao="+document.csrForm.sessao.value+
			"&municipioUf="+document.csrForm.municipioUf.value+
			"&inss="+document.csrForm.inss.value;
	    while (url.indexOf('\n') &gt;= 0)
	        url = url.replace('\n', '');
			
		AJAX.onreadystatechange = certificadoGerado;
		AJAX.open("GET", url);
		AJAX.send("");
	}

	function gerarCSR() {
		var dn = 'CN='+document.csrForm.cn_cn.value+
			', O='+document.csrForm.cn_o.value+
			', OU='+document.csrForm.cn_ou.value+
			', L='+document.csrForm.cn_l.value+
			', ST='+document.csrForm.cn_st.value+
			', C='+document.csrForm.cn_c.value;
		var mgr=getComboValue(document.csrForm.manager);
alert('Passo 1-Solicitando ao applet a geração de um CSR no dispositivo '+mgr+' com a string '+dn+'.');
		setOnSetCertificateProperties(csrGerado);
		ICPBravoApplet().generateCSR(mgr, document.csrForm.alias.value, dn, document.csrForm.validade.value);
	}
&lt;/script&gt;
</pre>

<hr />
<p>No lado do servidor, teremos o seguinte código:</p>
<pre class="brush: java; html-script: true;">
&lt;%@page contentType="text/html; charset=UTF-8"%&gt;
&lt;%@page import="org.json.simple.JSONObject"%&gt;
&lt;%
	JSONObject obj=new JSONObject();
	try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
		ICPBravoManager manager = new PKCS12Manager(resourceBundle.getString("home")+"/certs/RaizTreinamento.p12", "1234".toCharArray());
		manager.reload();
		ICPBravoCertificate certRoot = manager.getCertificates().get(0);
		String csrB64=java.net.URLDecoder.decode(request.getParameter("csr"),"UTF-8");
		long numSerie=Long.parseLong(java.net.URLDecoder.decode(request.getParameter("numSerie"),"UTF-8"));
		String alias=java.net.URLDecoder.decode(request.getParameter("alias"),"UTF-8");
		String email=java.net.URLDecoder.decode(request.getParameter("email"),"UTF-8");
		String nascimentoStr=java.net.URLDecoder.decode(request.getParameter("nascimento"),"UTF-8");
		int year = Integer.parseInt(nascimentoStr.substring(0,4));
		int month = Integer.parseInt(nascimentoStr.substring(4,6))-1;
		int day = Integer.parseInt(nascimentoStr.substring(6,8));
		java.util.GregorianCalendar nascimento = new java.util.GregorianCalendar(year, month, day);
		String cpf=java.net.URLDecoder.decode(request.getParameter("cpf"),"UTF-8");
		String nis=java.net.URLDecoder.decode(request.getParameter("nis"),"UTF-8");
		String rg=java.net.URLDecoder.decode(request.getParameter("rg"),"UTF-8");
		String oe=java.net.URLDecoder.decode(request.getParameter("oe"),"UTF-8");
		String tituloEleitor=java.net.URLDecoder.decode(request.getParameter("tituloEleitor"),"UTF-8");
		String zona=java.net.URLDecoder.decode(request.getParameter("zona"),"UTF-8");
		String sessao=java.net.URLDecoder.decode(request.getParameter("sessao"),"UTF-8");
		String municipioUf=java.net.URLDecoder.decode(request.getParameter("municipioUf"),"UTF-8");
		String inss=java.net.URLDecoder.decode(request.getParameter("inss"),"UTF-8");

System.out.println("CSR:"+csrB64 + ", NumSerie:"+numSerie + ", Alias:"+alias + ", Email:"+email + ", NascimentoStr:"+nascimentoStr + ", CPF:"+cpf + ", NIS:"+nis + ", RG:"+rg + ", OE:"+oe + ", TituloEleitor:"+tituloEleitor + ", Zona:"+zona + ", Sessao:"+sessao + ", municipioUf:"+municipioUf + ", INSS:"+inss);
		
		ICPBravoCertificationRequest csr = new ICPBravoCertificationRequest(new Base64Content(csrB64));

		java.util.Date from = ICPBravoUtil.getDateNow();
		java.util.Date to = ICPBravoUtil.getTo(ICPBravoUtil.MONTHS, 1);

		ICPBravoCertificateGenerator gen = new ICPBravoCertificateGenerator(
				manager.getProvider(), alias, java.math.BigInteger.valueOf(numSerie), csr, certRoot,  
				ICPBravoCertificateGenerator.getDefaultKeyUsageForFinal(),
				ICPBravoCertificateGenerator.getDefaultKeyPurposeForFinal(),
				null,
				OIWObjectIdentifiers.OID_ICPBrasilA1,
				null, 
				from, to,  
				email, nascimento.getTime(), cpf, nis, rg, oe,
				tituloEleitor, zona, sessao, municipioUf, inss);
		ICPBravoCertificate cert = gen.generate(manager, false);
		ICPBravoUtil.writeFile(resourceBundle.getString("home")+"/certs/"+alias+".cer", cert.getEncoded());
		obj.put("certificate", cert.getP7BPEM());
	} catch (Exception e) {
		e.printStackTrace();
		obj.put("err",e.getMessage());
	}
	out.print(obj);
	out.flush();
%&gt;
</pre>
</body></html>
