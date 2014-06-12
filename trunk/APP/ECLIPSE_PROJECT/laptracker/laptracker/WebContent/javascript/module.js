/** RESOLVING CONFLICT IN THE $j VARIABLE **/
try {
	$j = jQuery.noConflict();
} catch(e) {
	alert(e);
}

/** POPUP **/
function openPopup(id) {
	RichFaces.$(id).show();
	$j('input[TabIndex="-1"]').each(function(){
		$j(this).removeAttr('tabindex');
	});
}
function closePopup(id) {
	RichFaces.$(id).hide();
}
/** POPUP **/

function displayMessageBox(id, duringTime) {
	document.getElementById(id).style.display = "block";
	window.setTimeout(function(){ hideMessageBox(id); }, duringTime);
}

function hideMessageBox(id) {
	document.getElementById(id).style.display = "none";
}

function lessThan(value, comparator) {
	return value < comparator;
}

function changeSrcImage(idImg, newSrc) {
	$j("#" + idImg).attr("src", newSrc);
}

/** Original:  Ronnie T. Moore
 * Web Site:  The JavaScript Source
 * Dynamic 'fix' by: Nannette Thacker
 * Web Site: http://www.shiningstar.net
 */
function limitTextArea(field, countFieldId, maxlimit) {
	var countField = getSpan(countFieldId);
	if (field.value.length > maxlimit) // if too long...trim it!
		field.value = field.value.substring(0, maxlimit);
		// otherwise, update 'characters left' counter
	else 
		countField.firstChild.nodeValue = maxlimit - field.value.length;
}
/**
 * Retorna um Label com base em um ID.
 */
function getSpan(idLabel){
	var fields = document.getElementsByTagName("span");
	for (var i = 0; i <= fields.length - 1; i++) {
    	if(fields[i].id.indexOf(idLabel) != -1){
    		retorno = fields[i];
    		break;
    	}
	}
	return retorno;
}