/**
 * 
 */

window.onbeforeunload = function() {
	if (confirm("Vous êtes sur le point de quitter cette partie, " +
			"ceci resultera en une défaite par abandon. Voulez-vous continuer ?")) {
		var req = new AjaxRequest("GET", "handleLeavePartie.html", "", true);
		req.handleResponse = function() {
		};
		req.process();
	} else {
		return false;
	}
};