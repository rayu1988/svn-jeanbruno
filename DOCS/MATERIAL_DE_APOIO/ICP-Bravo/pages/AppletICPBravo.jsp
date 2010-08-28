<applet id='ICPBravoApplet' width='100px' height='20px'
	code='br.com.oaks.ICPBravo.applet.AppletICPBravoManager'
	archive='/ICPBravoJSP/lib/ICPBravoAPI-1.20.jar,/ICPBravoJSP/lib/commons-codec.jar,/ICPBravoJSP/lib/commons-httpclient.jar,/ICPBravoJSP/lib/commons-logging.jar,/ICPBravoJSP/lib/mail.jar,/ICPBravoJSP/lib/iText-2.1.7.jar'
	mayscript>
	<param name='keystore' value='PKCS11'>
	<param name='tiporepositorio' value='5'>
	<param name='Police' value='AD-CP' >
	<param name='PermiteAssinaturaComCertificadoNaoICPBrasil' value='true'>
	<param name='PermiteAssinaturaComCertificadoExpiradoOuInvalido' value='true'>
	<param name='PermiteAssinaturaComCertificadoSemValidarCrl' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoDigitalSignature' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoNonRepudiation' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoKeyEncipherment' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoDataEncipherment' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoKeyAgreement' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoKeyCertisign' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoCrlSign' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoEncipherOnly' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoDecipherOnly' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoServerAuth' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoClientAuth' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoCodeSigning' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoEmailProtection' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoIPSecEndSystem' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoIPSecTunnel' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoIPSecUser' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoIPSecIKE' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoTimeStamping' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoOCSPSigning' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoDvcs' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoSBGPCertAAServerAuth' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoEAPOverPPP' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoEAPOverLan' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoSCVPServer' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoSCVPClient' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoSCVPResponder' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoCapWapac' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoCapWapwtp' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoSmartcardLogon' value='true'>
	<param name='PermiteAssinaturaComCertificadoDeSigilo' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoConfiavel' value='true'>
	<param name='PermiteAssinaturaComCertificadoNaoIssuerIsCA' value='true'>
</applet>

<script src='ICPBravo.js'></script>

<script language='JavaScript'>
function showAppletSource() {
	document.getElementById('codeApplet').style.display='block';
	document.getElementById('codeAppletBt').style.display='none';
} 
</script>
<div id='codeAppletBt'>
<a href='javascript:showAppletSource()'>Ver código fonte da carga do applet</a>
</div><div id='codeApplet' style='display:none;'>
<p>Source code para instanciar o applet <i>(AppletICPBravo.jsp)</i></p>
<pre class="brush: xml;">
&lt;applet id='ICPBravoApplet' width='100px' height='20px'
	code='br.com.oaks.ICPBravo.applet.AppletICPBravoManager'
	archive='/ICPBravoJSP/lib/ICPBravoAPI-1.20.jar,/ICPBravoJSP/lib/commons-codec.jar,/ICPBravoJSP/lib/commons-httpclient.jar,/ICPBravoJSP/lib/commons-logging.jar,/ICPBravoJSP/lib/mail.jar,/ICPBravoJSP/lib/iText-2.1.7.jar'
	mayscript&gt;
	&lt;param name='keystore' value='PKCS11'&gt;
	&lt;param name='tiporepositorio' value='5'&gt;
	&lt;param name='Police' value='AD-CP'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoICPBrasil' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoExpiradoOuInvalido' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoSemValidarCrl' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoDigitalSignature' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoNonRepudiation' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoKeyEncipherment' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoDataEncipherment' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoKeyAgreement' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoKeyCertisign' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoCrlSign' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoEncipherOnly' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoDecipherOnly' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoServerAuth' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoClientAuth' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoCodeSigning' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoEmailProtection' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoIPSecEndSystem' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoIPSecTunnel' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoIPSecUser' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoIPSecIKE' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoTimeStamping' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoOCSPSigning' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoDvcs' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoSBGPCertAAServerAuth' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoEAPOverPPP' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoEAPOverLan' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoSCVPServer' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoSCVPClient' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoSCVPResponder' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoCapWapac' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoCapWapwtp' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoSmartcardLogon' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoDeSigilo' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoConfiavel' value='true'&gt;
	&lt;param name='PermiteAssinaturaComCertificadoNaoIssuerIsCA' value='true'&gt;
&lt;/applet&gt;
 
&lt;script src='ICPBravo.js'&gt;&lt;/script&gt;
</pre>
</div>