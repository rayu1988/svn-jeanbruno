function displayMessageBox(id, duringTime) {
	document.getElementById(id).style.display = "block";
	window.setTimeout(function(){ hideMessageBox(id); }, duringTime);
}

function hideMessageBox(id) {
	document.getElementById(id).style.display = "none";
}