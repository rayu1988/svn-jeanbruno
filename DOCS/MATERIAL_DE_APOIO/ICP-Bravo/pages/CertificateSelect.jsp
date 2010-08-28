<select id='certificateSelect'></select>
Carregar de : 
<input type='button' id='capiCombo' disabled='true' value='CapiCOM' onClick='javascript:loadCertificates("Windows-MY");'>
<input type='button' id='pkcs11Combo' disabled='true' value='Dispositivo Criptográfico' onClick='javascript:loadCertificates("PKCS11");'>
<input type='button' id='pkcs12Combo' disabled='true' value='PKCS12' onClick='javascript:loadCertificates("PKCS12");'>
<input type='button' id='cerCombo' disabled='true' value='Carregar certificado exportado' onClick='javascript:loadCertificates("fromFile");'>

<script language='JavaScript'>
	var defaultEmptyCertificate = false;
	
	function certificateSelectField() {
		return document.getElementById('certificateSelect');
	}
	
	function loadCertificates(managerName) {
		// Limpar os certificados do combo
		var field = certificateSelectField();
		for(var a=field.options.length-1; a>=0; a--)
			field.options[a] = null;
		if (defaultEmptyCertificate)
			addCertificate(0, defaultEmptyCertificate);

		// Indica a função que será chamada para adicionar os certificados			
		setAddCertificate(adicionaCertificado);
		// Invoca o applet para carregar os certificados
		ICPBravoApplet().loadCertificates(managerName);
	}
	
	// Função de callback que será invocada pelo applet, ao ser chamado o método 'loadCertificates' a cada novo certificado encontrado
	function adicionaCertificado(alias, name) {
		var field = certificateSelectField();
		var pos = field.options.length;
		field.options[pos] = new Option();
		field.options[pos].value = alias;
		field.options[pos].text = name;
	}
	
	function getComboValue(combo) {
		for(var a=0; a<combo.options.length; a++)
		{
			if (combo.options[a].selected)
				return combo.options[a].value;
		}
		return null;
	}
	
	function certificateSelect() {
		return getComboValue(certificateSelectField());
	}
	
	// Retorna a referência para o certificado selecionado
	function enableSelectButtons() {
		document.getElementById('capiCombo').disabled = false;
		document.getElementById('pkcs11Combo').disabled = false;
		document.getElementById('pkcs12Combo').disabled = false;
		document.getElementById('cerCombo').disabled = false;
	}
	
	setAppletLoadedCallback(enableSelectButtons);	
</script>

<script language='JavaScript'>
function showCertificateSource() {
	document.getElementById('codeCertificate').style.display='block';
	document.getElementById('codeCertificateBt').style.display='none';
} 
</script>
<div id='codeCertificateBt'>
<a href='javascript:showCertificateSource()'>Ver código fonte da carga de certificados</a>
</div><div id='codeCertificate' style='display:none;'>
<p>Source code do trecho que trata os certificados <i>(CertificateSelect.jsp)</i></p>
<pre class="brush: js; html-script: true;">
&lt;select id='certificateSelect'&gt;&lt;/select&gt;
Carregar de : 
&lt;input type='button' id='capiCombo' disabled='true' value='CapiCOM' onClick='javascript:loadCertificates("Windows-MY");'&gt;
&lt;input type='button' id='pkcs11Combo' disabled='true' value='Dispositivo Criptográfico' onClick='javascript:loadCertificates("PKCS11");'&gt;
&lt;input type='button' id='pkcs12Combo' disabled='true' value='PKCS12' onClick='javascript:loadCertificates("PKCS12");'&gt;
&lt;input type='button' id='cerCombo' disabled='true' value='Carregar certificado exportado' onClick='javascript:loadCertificates("fromFile");'&gt;
 
&lt;script language='JavaScript'&gt;
	var defaultEmptyCertificate = false;
	
	function certificateSelectField() {
		return document.getElementById('certificateSelect');
	}
	
	function loadCertificates(managerName) {
		// Limpar os certificados do combo
		var field = certificateSelectField();
		for(var a=field.options.length-1; a&gt;=0; a--)
			field.options[a] = null;
		if (defaultEmptyCertificate)
			addCertificate(0, defaultEmptyCertificate);
			
		// Indica a função que será chamada para adicionar os certificados			
		setAddCertificate(adicionaCertificado);
		// Invoca o applet para carregar os certificados
		ICPBravoApplet().loadCertificates(managerName);
	}
	
	// Função de callback que será invocada pelo applet, ao ser chamado o método 'loadCertificates' a cada novo certificado encontrado
	function adicionaCertificado(alias, name) {
		var field = certificateSelectField();
		var pos = field.options.length;
		field.options[pos] = new Option();
		field.options[pos].value = alias;
		field.options[pos].text = name;
	}
	
	function getComboValue(combo) {
		for(var a=0; a&lt;combo.options.length; a++)
		{
			if (combo.options[a].selected)
				return combo.options[a].value;
		}
		return null;
	}
	
	// Retorna a referência para o certificado selecionado
	function certificateSelect() {
		return getComboValue(certificateSelectField());
	}
	
	function enableSelectButtons() {
		document.getElementById('capiCombo').disabled = false;
		document.getElementById('pkcs11Combo').disabled = false;
		document.getElementById('pkcs12Combo').disabled = false;
		document.getElementById('cerCombo').disabled = false;
	}
	
	setAppletLoadedCallback(enableSelectButtons);	
&lt;/script&gt;
</pre>
</div>