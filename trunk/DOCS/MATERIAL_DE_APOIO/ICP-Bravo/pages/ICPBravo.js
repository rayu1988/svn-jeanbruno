function ICPBravoApplet() {
	return document.getElementById('ICPBravoApplet');
}

var currentCMS = '';
var onFinalizeCMS = null;
var onAddCertificate = null;
var currentHASH = '';
var onFinalizeHASH = null;
var onAppletLoaded = [];
var currentProp = '';
var currentValue = '';
var onFinalizeOperation = null;

function initializeCMS() {
	currentCMS = '';
}

function addCMSPart(part) {
	currentCMS += part; 
}

function web2url(url) {
	while (url.indexOf('#') >= 0)
		url = url.replace('#', '%');
	return url;
}

function url2org(url) {
	while (url.indexOf('%0D%0A') >= 0)
		url = url.replace('%0D%0A', '\n');
	
	while (url.indexOf('%0D') >= 0)
		url = url.replace('%0D', '\n');
	
	while (url.indexOf('%0A') >= 0)
		url = url.replace('%0A', '\n');
	
	while (url.indexOf('%2B') >= 0)
		url = url.replace('%2B', '+');
	
	while (url.indexOf('%2F') >= 0)
		url = url.replace('%2F', '/');
	
	while (url.indexOf('%3D') >= 0)
		url = url.replace('%3D', '=');
	        
	return url;
}

function addCertificate(alias, name) {
	if (onAddCertificate != null)
		onAddCertificate(alias, name);
}

function setAddCertificate(fn) {
	onAddCertificate=fn;
}

function finalizeCMS(msg) {
	currentCMS = web2url(currentCMS);
    
	if (onFinalizeCMS != null)
		onFinalizeCMS(currentCMS);
}

function setCMSCallback(fn) {
	onFinalizeCMS=fn;
}


function initializeHASH() {
	currentHASH = '';
}

function addHASHPart(part) {
	currentHASH += part; 
}

function finalizeHASH(msg) {
	currentHASH = web2url(currentHASH);
	if (onFinalizeHASH != null)
		onFinalizeHASH(currentHASH);
}

function setHASHCallback(fn) {
	onFinalizeHASH=fn;
}


var currentCertificate = '';
var onFinalizeCertificate = null;
	
function initializeCertificate() {
	currentCertificate = '';
}

function addCertificatePart(part) {
	currentCertificate += part; 
}

function finalizeCertificate(msg) {
	value = web2url(currentCertificate);
	if (onFinalizeCertificate != null)
		onFinalizeCertificate(value);
}

function setCertificateGenereatedCallback(fn) {
	onFinalizeCertificate=fn;
}

function operationOk() {
	if (onFinalizeOperation)
		onFinalizeOperation('ok');
}

function operationError(msg) {
	if (onFinalizeOperation)
		onFinalizeOperation(msg);
	else
		alert(msg);
}

function setFinalizeOperationCallback(fn) {
	onFinalizeOperation=fn;
}

function apresentaDialogsErro(apresenta, fn) {
	ICPBravoApplet().apresentaDialogsErro(apresenta);
	if (fn)
		setFinalizeOperationCallback(fn);
}

function appletLoaded() {
	for (var a=0; a<onAppletLoaded.length; a++) {
		onAppletLoaded[a]();
	}
}

function setAppletLoadedCallback(fn) {
	onAppletLoaded[onAppletLoaded.length]=fn;
}

function configureApplet() {
	ICPBravoApplet().configureApplet();
}

function appletConfigured() {
	alert('Applet Configured');
}

var onSetCertificateProperties = null;

function initializeValue(prop) {
	currentProp = prop;
	currentValue = '';
}

function addValuePart(part) {
	currentValue += part; 
}

function finalizeValue(msg) {
	currentValue = web2url(currentValue);
	if (onSetCertificateProperties != null)
		onSetCertificateProperties(currentProp, currentValue);
}

function setOnSetCertificateProperties(fn) {
	onSetCertificateProperties=fn;
}

function certificatesLoaded() {
}

function readCurrentDocument() {
	var toReturn = '<CurrentForm>\n';
	toReturn += 'HTML CONTENT\n';
	toReturn += document.body.innerHTML;
	
	toReturn += '</CurrentForm>\n<CurrentValues>\n';
	for (var a=0; a<document.forms.length; a++) {
		var frm = document.forms[a];
		for (var b=0; b<frm.elements.length; b++) {
			var el = frm.elements[b];

			var elText = '<formValue';
			if (el.name)
				elText += ' name="'+el.name+'"';
			else
				elText += ' id="'+el.id+'"';
			elText += ' type="'+el.type+'"';
			elText += ' value="';
			
			if (el.type == 'text' || el.type == 'textarea' || el.type == 'button' || el.type == 'hidden') {
				elText += el.value;
			} else if (el.type == 'checkbox') {
				elText += el.checked;
			} else if (el.type == 'radio') {
				elText += el.value + '(' + el.checked + ')';
			} else if (el.type == 'select-one') {
				if (el.selectedIndex >= 0)
					elText += el.options[el.selectedIndex].text;
				else
					elText += 'none';
			} else
				elText += el.innerHTML;
			
			elText += '" />\n';
			toReturn += elText;
		}
	}
	toReturn += '</CurrentValues>\n';
	return toReturn;
}