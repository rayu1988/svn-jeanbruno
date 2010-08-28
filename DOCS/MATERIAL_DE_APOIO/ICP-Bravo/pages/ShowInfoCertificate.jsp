<tr><td><b>Dono:</b></td><td><%= cert.getName() %></td></tr>
<tr><td><b>Número de Série:</b></td><td><%= cert.getSerialNumber().toString() %></td></tr>
<tr><td><b>Documento:</b></td><td><%= cert.getDocumentNumber() %></td></tr>
<tr><td><b>Validade:</b></td><td><%= "De:"+cert.getNotBefore()+" até "+cert.getNotAfter() %></td></tr>
<tr><td><b>Versão:</b></td><td><%= cert.getVersion() %></td></tr>

<tr><td><b>É Assinador Digital:</b></td><td><%= cert.isKeyUsageExtensionDigitalSignature()?"Sim":"Não" %></td></tr>
<tr><td><b>É Autoassinado:</b></td><td><%= cert.isAutoSign()?"Sim":"Não" %></td></tr>
<tr><td><b>É Sigilo:</b></td><td><%= cert.isSecrecy()?"Sim":"Não" %></td></tr>
<tr><td><b>É ICP-Brasil:</b></td><td><%= cert.isICPBrasil()?"Sim":"Não" %></td></tr>
<tr><td><b>É Raiz ICP-Brasil:</b></td><td><%= cert.isICPBrasilRoot()?"Sim":"Não" %></td></tr>
<tr><td><b>Tamanho da Chave:</b></td><td><%= cert.getKeyLengh() %> bits</td></tr>
<tr><td><b>Basic Constraints:</b></td><td><%= cert.getBasicConstraints() %></td></tr>

<tr><td colspan='2' align='center'><b>Subject Alternative Names</b></td></tr>
<% java.util.Vector<br.com.oaks.ICPBravo.certs.CertificateAttribute> infoICPBrasil = cert.constroiSubjectAlternativeNamesExtensions();
for (br.com.oaks.ICPBravo.certs.CertificateAttribute informacaoCertificado : infoICPBrasil) {
	%><tr><td><b><%= informacaoCertificado.getDescription() %>:</b></td><td><%= informacaoCertificado.getValue() %></td></tr><%
} %>

<tr><td colspan='2' align='center'><b>Polices</b></td></tr>
<% java.util.Vector<br.com.oaks.ICPBravo.certs.CertificateAttribute> polices = cert.getCertificatePoliciesExtensions();
for (br.com.oaks.ICPBravo.certs.CertificateAttribute police : polices) {
	%><tr><td><b><%= police.getDescription() %>:</b></td><td><a href='<%= police.getValue() %>'><%= police.getValue() %></a></td></tr><%
} %>

<tr><td colspan='2' align='center'><b>Key Usage</b></td></tr>
<% java.util.Vector<br.com.oaks.ICPBravo.certs.CertificateAttribute> keyUsages = cert.getKeyUsageExtension();
for (br.com.oaks.ICPBravo.certs.CertificateAttribute keyUsage : keyUsages) {
	%><tr><td><b><%= keyUsage.getDescription() %>:</b></td><td><%= keyUsage.getValue() %></td></tr><%
} %>

<tr><td colspan='2' align='center'><b>Extended Key Usage</b></td></tr>
<% java.util.Vector<br.com.oaks.ICPBravo.certs.CertificateAttribute> extKeyUsages = cert.getEnhancedKeyUsageExtension();
for (br.com.oaks.ICPBravo.certs.CertificateAttribute extKeyUsage : extKeyUsages) {
	%><tr><td><b><%= extKeyUsage.getDescription() %>:</b></td><td><%= extKeyUsage.getValue() %></td></tr><%
} %>

<% try {
		java.util.ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("config");
		br.com.oaks.ICPBravo.manager.JKSManager trustedChain = new br.com.oaks.ICPBravo.manager.JKSManager(resourceBundle.getString("home")+"/certs/trusteds.jks", "1234".toCharArray());
		trustedChain.reload();
		for (br.com.oaks.ICPBravo.certs.ICPBravoCertificate certT : trustedChain.getCertificates()) {
			System.out.println(certT.getName());
		}
		cert.tryTrustedValidate(trustedChain);
%> <tr><td><b>Trusted Chain</b></td><td>O certificado é de uma raiz confiável</td></tr> <%
	} catch (Exception e) {
		e.printStackTrace();
%> <tr><td><b>O certificado não é de uma raiz confiável:</b></td><td><%= e.toString() %></td></tr> <%
	}
%>
<tr><td colspan='2' align='center'><b>Cadeia</b></td></tr>
<% br.com.oaks.ICPBravo.certs.ICPBravoCertificate [] chains = cert.getCertificateChain();
for (br.com.oaks.ICPBravo.certs.ICPBravoCertificate chain : chains) {
	%><tr><td colspan='2'><%= chain.getName() %></td></tr><% 
} %>
<% try {
		br.com.oaks.ICPBravo.certs.ICPBravoCA ac = cert.getIssuerObject();
		if (ac != null) {
%> <tr><td><b>Issuer:</b></td><td><%= ac.toString() %></td></tr>   <tr><td><b>CRL:</b></td><td><%= ac.getURLCrl(0) %></td></tr> <%
			br.com.oaks.ICPBravo.certs.crl.ICPBravoCRL crl = ac.getValidCRL();
			try {
				crl.verify(cert);
%><tr><td><b>CRL OK:</b></td><td><%= (crl.getThisUpdate() + " até " + crl.getNextUpdate()) %></td></tr><%
			} catch (Exception e) {
				e.printStackTrace();
%><tr><td><b>CRL Não OK:</b></td><td><%= e.getMessage()%></td></tr><%
			}
		} else {
%><tr><td colspan='2'><b>Este certificado não tem CRL</b></td></tr><%
		}
	} catch (Exception e) {
		e.printStackTrace();
%><tr><td><b>Erro ao verificar CRL:</b></td><td><%= e.toString() %></td></tr><%
	}
%>
<% try {
		cert.verify(cert.getCertificateIssuer().getPublicKey());
%><tr><td><b>Signature</b></td><td>A assinatura do issuer está OK</td></tr><%
	} catch (Exception e) {
		e.printStackTrace();
%> <tr><td><b>Erro ao verificar a assinatura do issuer:</b></td><td><%= e.toString() %></td></tr> <%
	}
%>
