<html>
<header>
	<title>ICPBravo - Verificando Certificado</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
</header>
<body>

<%@include file='Header.jsp'%>
<h1 style='width:100%;text-align:center;'>Verificando Certificado</h1>
<h3>Server-Side</h3>
<p>Este exemplo demonstra como visualizar informações de um certificado digital pelo SDK no lado do servidor.</p>
<hr />

<table>
<%
//	java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
	String certificadoEChain=request.getParameter("certificateAndChain");
	certificadoEChain = br.com.oaks.ICPBravo.util.ICPBravoUtil.readFromUrlAndBase64(certificadoEChain);
	
%>
	<tr><td>Certificado:</td><td><%= certificadoEChain.replaceAll("\n", "<br>") %></td></tr> 
<%	
System.out.println(certificadoEChain);
	try {
		br.com.oaks.ICPBravo.manager.ICPBravoManager manager = br.com.oaks.ICPBravo.manager.ICPBravoManager.getDefaultManager();
		// Carregar o root antes
//		br.com.oaks.ICPBravo.certs.ICPBravoCertificate certRoot = new br.com.oaks.ICPBravo.certs.ICPBravoCertificate(manager,"root",ICPBravoUtil.readFile(resourceBundle.getString("home")+"/certs/RaizTreinamento.cer").toByteArray());
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate cert = null;
		try {
			cert = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromP7BPEM(certificadoEChain,manager,"test");
		} catch (Exception e) {
			cert = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromPEMAndChain(certificadoEChain,manager,"test");
		}
%><%@include file='ShowInfoCertificate.jsp'%><%
	} catch (Exception e) {
		e.printStackTrace();
		%>Erro ao abrir o certificado: <%= e.getMessage() %><%
	}
%>
</table>

<hr />
<p>O código para a execução do programa acima, pode ser visto abaixo:</p>
<pre class="brush: java; html-script: true;">
&lt;table&gt;
&lt;%
	String certificadoEChain=request.getParameter("certificateAndChain");
	certificadoEChain = ICPBravoUtil.readFromUrlAndBase64(certificadoEChain);
%&gt;
	&lt;tr&gt;&lt;td&gt;Certificado:&lt;/td&gt;&lt;td&gt;&lt;%= certificadoEChain.replaceAll("\n", "&lt;br&gt;") %&gt;&lt;/td&gt;&lt;/tr&gt; 
&lt;%
	try {
		ICPBravoManager manager = ICPBravoManager.getDefaultManager();
		br.com.oaks.ICPBravo.certs.ICPBravoCertificate cert = null;
		try {
			cert = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromP7BPEM(certificadoEChain,manager,"test");
		} catch (Exception e) {
			cert = br.com.oaks.ICPBravo.certs.ICPBravoCertificate.CreateFromPEMAndChain(certificadoEChain,manager,"test");
		}
	&lt;!---------------------------------------------------------------------------------------------&gt;
	&lt;%@include file='ShowInfoCertificate.jsp'%&gt;
	&lt;!---------------------------------------------------------------------------------------------&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Dono:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getName() %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Número de Série:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getSerialNumber().toString() %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Documento:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getDocumentNumber() %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Validade:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= "De:"+cert.getNotBefore()+" até "+cert.getNotAfter() %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Versão:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getVersion() %&gt;&lt;/td&gt;&lt;/tr&gt;
	
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;É Assinador Digital:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isKeyUsageExtensionDigitalSignature()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;É Autoassinado:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isAutoSign()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;É Sigilo:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isSecrecy()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;É ICP-Brasil:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isICPBrasil()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;É Raiz ICP-Brasil:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.isICPBrasilRoot()?"Sim":"Não" %&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Tamanho da Chave:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getKeyLengh() %&gt; bits&lt;/td&gt;&lt;/tr&gt;
	&lt;tr&gt;&lt;td&gt;&lt;b&gt;Basic Constraints:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= cert.getBasicConstraints() %&gt;&lt;/td&gt;&lt;/tr&gt;
	
	&lt;tr&gt;&lt;td colspan='2' align='center'&gt;&lt;b&gt;Subject Alternative Names&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;% java.util.Vector&lt;CertificateAttribute&gt; infoICPBrasil = cert.constroiSubjectAlternativeNamesExtensions();
	for (CertificateAttribute informacaoCertificado : infoICPBrasil) {
		%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= informacaoCertificado.getDescription() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= informacaoCertificado.getValue() %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
	} %&gt;
	
	&lt;tr&gt;&lt;td colspan='2' align='center'&gt;&lt;b&gt;Polices&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;% java.util.Vector&lt;CertificateAttribute&gt; polices = cert.getCertificatePoliciesExtensions();
	for (CertificateAttribute police : polices) {
		%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= police.getDescription() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;a href='&lt;%= police.getValue() %&gt;'&gt;&lt;%= police.getValue() %&gt;&lt;/a&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
	} %&gt;
	
	&lt;tr&gt;&lt;td colspan='2' align='center'&gt;&lt;b&gt;Key Usage&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;% java.util.Vector&lt;CertificateAttribute&gt; keyUsages = cert.getKeyUsageExtension();
	for (CertificateAttribute keyUsage : keyUsages) {
		%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= keyUsage.getDescription() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= keyUsage.getValue() %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
	} %&gt;
	
	&lt;tr&gt;&lt;td colspan='2' align='center'&gt;&lt;b&gt;Extended Key Usage&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;% java.util.Vector&lt;CertificateAttribute&gt; extKeyUsages = cert.getExtendedKeyUsageExtension();
	for (CertificateAttribute extKeyUsage : extKeyUsages) {
		%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;&lt;%= extKeyUsage.getDescription() %&gt;:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= extKeyUsage.getValue() %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
	} %&gt;
	
	&lt;% try {
			java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
			JKSManager trustedChain = new JKSManager(resourceBundle.getString("home")+"/certs/trusteds.jks", "1234".toCharArray());
			trustedChain.reload();
			for (ICPBravoCertificate certT : trustedChain.getCertificates()) {
				System.out.println(certT.getName());
			}
			cert.tryTrustedValidate(trustedChain);
	%&gt; &lt;tr&gt;&lt;td&gt;&lt;b&gt;Trusted Chain&lt;/b&gt;&lt;/td&gt;&lt;td&gt;O certificado é de uma raiz confiável&lt;/td&gt;&lt;/tr&gt; &lt;%
		} catch (Exception e) {
			e.printStackTrace();
	%&gt; &lt;tr&gt;&lt;td&gt;&lt;b&gt;O certificado não é de uma raiz confiável:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= e.toString() %&gt;&lt;/td&gt;&lt;/tr&gt; &lt;%
		}
	%&gt;
	&lt;tr&gt;&lt;td colspan='2' align='center'&gt;&lt;b&gt;Cadeia&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;
	&lt;% ICPBravoCertificate [] chains = cert.getCertificateChain();
	for (ICPBravoCertificate chain : chains) {
		%&gt;&lt;tr&gt;&lt;td colspan='2'&gt;&lt;%= chain.getName() %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;% 
	} %&gt;
	&lt;% try {
			ICPBravoCA ac = cert.getIssuerObject();
			if (ac != null) {
	%&gt; &lt;tr&gt;&lt;td&gt;&lt;b&gt;Issuer:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= ac.toString() %&gt;&lt;/td&gt;&lt;/tr&gt;   &lt;tr&gt;&lt;td&gt;&lt;b&gt;CRL:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= ac.getURLCrl() %&gt;&lt;/td&gt;&lt;/tr&gt; &lt;%
				ICPBravoCRL crl = ac.getValidCRL();
				try {
					crl.verify(cert);
	%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;CRL OK:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= (crl.getThisUpdate() + " até " + crl.getNextUpdate()) %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
				} catch (Exception e) {
					e.printStackTrace();
	%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;CRL Não OK:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= e.getMessage()%&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
				}
			} else {
	%&gt;&lt;tr&gt;&lt;td colspan='2'&gt;&lt;b&gt;Este certificado não tem CRL&lt;/b&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
			}
		} catch (Exception e) {
			e.printStackTrace();
	%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;Erro ao verificar CRL:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= e.toString() %&gt;&lt;/td&gt;&lt;/tr&gt;&lt;%
		}
	%&gt;
	&lt;% try {
			cert.verify(cert.getCertificateIssuer().getPublicKey());
	%&gt;&lt;tr&gt;&lt;td&gt;&lt;b&gt;Signature&lt;/b&gt;&lt;/td&gt;&lt;td&gt;A assinatura do issuer está OK&lt;/td&gt;&lt;/tr&gt;&lt;%
		} catch (Exception e) {
			e.printStackTrace();
	%&gt; &lt;tr&gt;&lt;td&gt;&lt;b&gt;Erro ao verificar a assinatura do issuer:&lt;/b&gt;&lt;/td&gt;&lt;td&gt;&lt;%= e.toString() %&gt;&lt;/td&gt;&lt;/tr&gt; &lt;%
		}
	%&gt;
	} catch (Exception e) {
		e.printStackTrace();
		%&gt;Erro ao abrir o certificado: &lt;%= e.getMessage() %&gt;&lt;%
	}
%&gt;

&lt;/table&gt;
</pre>
</body></html>
