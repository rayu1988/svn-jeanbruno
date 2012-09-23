/** POPUP **/
function openPopup(id) {
	RichFaces.$(id).show();
	$('input[TabIndex="-1"]').each(function(){
		$(this).removeAttr('tabindex');
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
