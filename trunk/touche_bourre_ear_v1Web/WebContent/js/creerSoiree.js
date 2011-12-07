/**
 * 
 */

window.onload = function() {
	var form = document.getElementById("formCreerSoiree");
	form.onsubmit = function() {
		var input = document.getElementById("nomSoiree");
		if (input.value.length < 3) {
			input.className = "loginputWrong";
			return false;
		}
		return true;
	};
	var input = document.getElementById("nomSoiree");
	input.onkeyup = function() {
		if (input.value.length >= 3) {
			input.className = "loginput";
		}
	};
};