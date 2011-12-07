/**
 * 
 */

window.onload = function() {
	var form = document.getElementById("formLogin");
	form.onsubmit = function() {
		var input = document.getElementById("pseudo");
		if (input.value.length < 3) {
			input.className = "loginputWrong";
			return false;
		}
		return true;
	};
	var input = document.getElementById("pseudo");
	input.onkeyup = function() {
		if (input.value.length >= 3) {
			input.className = "loginput";
		}
	};
};