/**
 * Runs when the page finishes loading
 */
window.onload = function() {
	changeTypeMessage(getCurrentTypeMessage());
}

/**
 * return the element(DOM) from the id passed as parameter
 * @return
 */
function $id(id) {
	try {
		var element = document.getElementById(id);
		if (element != null) return element;
		else throw "Element with null value, the motive might be the id is not correct.";
	} catch(e) {
		alert("Problems. I can't find the element with following id:" + id);
	}
}

/**
 * 
 * @param id
 * @return
 */
function hideElement(id) {
	$id(id).style.visibility=visible = 'hidden';
}

/**
 * 
 * @param id
 * @return
 */
function showElement(id) {
	$id(id).style.visibility=visible = 'visible';
}

/**
 * Change type message the between two possible: free message or digital message(file) 
 * @return
 */
function changeTypeMessage(element) {
	switch (element.value) {
		case "f":
			showElement("fieldFreeMessage");
			hideElement("fieldDigitalMessage");
			break;
			
		case "d":
			showElement("fieldDigitalMessage");
			hideElement("fieldFreeMessage");
			break;
	}
}

/**
 * 
 */
function getCurrentTypeMessage() {
	var elementsForm = $id("formBody").elements;
	for (i=0;i<elementsForm.length;i++)
		if (elementsForm[i].type == "radio" && elementsForm[i].checked)
			return elementsForm[i];
}

/**
 * 
 */
function getValueCurrentTypeMessage() {
	return getCurrentTypeMessage().value;
}