/** POPUP **/
function openPopup(id) {
//	var winW = jQuery(window).width();
//	var winH = jQuery(window).height();
//	var width = winW*32/100;
//	var height = -1;
//	var left = winW/2-width/2;
//	var top = (winH*0.25)*1.3;
//	
//	// Seta os parâmetros de rederização do popup
//	RichFaces.$(id).options.left = left;
//	RichFaces.$(id).options.top = top;
//	RichFaces.$(id).options.width = width;
//	RichFaces.$(id).options.height = height;
	RichFaces.$(id).show();
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
