/**
 * 
 */

window.onbeforeunload = function() {
	if (confirm("Vous �tes sur le point de quitter cette partie, " +
			"ceci resultera en une d�faite par abandon. Voulez-vous continuer ?")) {
		var req = new AjaxRequest("GET", "handleLeavePartie.html", "", true);
		req.handleResponse = function() {
		};
		req.process();
	} else {
		return false;
	}
};