/** RESOLVING CONFLICT IN THE $j VARIABLE **/
$j = jQuery.noConflict();

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
